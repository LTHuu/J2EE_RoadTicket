import { useEffect, useRef, useState } from "react";
import { googleLogin } from "../api.js";

const GOOGLE_SCRIPT_URL = "https://accounts.google.com/gsi/client";
const GOOGLE_CLIENT_ID = import.meta.env.VITE_GOOGLE_CLIENT_ID;

function loadGoogleIdentityServices() {
  if (window.google?.accounts?.id) {
    return Promise.resolve(window.google);
  }

  return new Promise((resolve, reject) => {
    let script = document.querySelector(
      `script[src="${GOOGLE_SCRIPT_URL}"]`,
    );

    if (!script) {
      script = document.createElement("script");
      script.src = GOOGLE_SCRIPT_URL;
      script.async = true;
      script.defer = true;
      document.head.appendChild(script);
    }

    script.addEventListener(
      "load",
      () => {
        if (window.google?.accounts?.id) {
          resolve(window.google);
        } else {
          reject(new Error("Không thể khởi tạo dịch vụ đăng nhập Google."));
        }
      },
      { once: true },
    );
    script.addEventListener(
      "error",
      () => reject(new Error("Không thể tải dịch vụ đăng nhập Google.")),
      { once: true },
    );
  });
}

export default function GoogleLoginButton({ onAuthenticated }) {
  const buttonRef = useRef(null);
  const onAuthenticatedRef = useRef(onAuthenticated);
  const [error, setError] = useState("");
  const [isSubmitting, setIsSubmitting] = useState(false);

  useEffect(() => {
    onAuthenticatedRef.current = onAuthenticated;
  }, [onAuthenticated]);

  useEffect(() => {
    if (!GOOGLE_CLIENT_ID) {
      setError("Đăng nhập Google chưa được cấu hình.");
      return undefined;
    }

    let isMounted = true;
    loadGoogleIdentityServices()
      .then((google) => {
        if (!isMounted || !buttonRef.current) return;

        google.accounts.id.initialize({
          client_id: GOOGLE_CLIENT_ID,
          callback: async ({ credential }) => {
            if (!credential) {
              setError("Google không trả về thông tin xác thực.");
              return;
            }

            setError("");
            setIsSubmitting(true);
            try {
              const session = await googleLogin(credential);
              if (isMounted) onAuthenticatedRef.current?.(session);
            } catch (loginError) {
              if (isMounted) setError(loginError.message);
            } finally {
              if (isMounted) setIsSubmitting(false);
            }
          },
        });
        google.accounts.id.renderButton(buttonRef.current, {
          theme: "outline",
          size: "large",
          text: "continue_with",
          shape: "rectangular",
          width: Math.min(buttonRef.current.clientWidth, 376),
        });
      })
      .catch((loadError) => {
        if (isMounted) setError(loadError.message);
      });

    return () => {
      isMounted = false;
      if (buttonRef.current) buttonRef.current.replaceChildren();
    };
  }, []);

  return (
    <div className="google-login">
      <div ref={buttonRef} className="google-login-button" />
      {isSubmitting && (
        <p className="auth-google-status" role="status">
          Đang đăng nhập bằng Google...
        </p>
      )}
      {error && (
        <p className="auth-message auth-message-error" role="alert">
          {error}
        </p>
      )}
    </div>
  );
}
