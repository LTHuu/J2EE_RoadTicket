import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { login } from "../api.js";
import GoogleLoginButton from "./GoogleLoginButton.jsx";
import "./auth.css";

export default function LoginForm({ onAuthenticated }) {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");
  const [isSubmitting, setIsSubmitting] = useState(false);
  const navigate = useNavigate();

  async function handleSubmit(event) {
    event.preventDefault();
    setError("");
    setIsSubmitting(true);

    try {
      const session = await login({ email: email.trim(), password });
      onAuthenticated?.(session);
      navigate("/");
    } catch (submitError) {
      setError(submitError.message);
    } finally {
      setIsSubmitting(false);
    }
  }

  function handleGoogleAuthenticated(session) {
    onAuthenticated?.(session);
    navigate("/");
  }

  return (
    <section className="auth-card" aria-labelledby="login-title">
      <h1 id="login-title">Đăng nhập</h1>
      <p className="auth-intro">Đăng nhập vào tài khoản RoadTicket của bạn.</p>

      {error && (
        <p className="auth-message auth-message-error" role="alert">
          {error}
        </p>
      )}

      <form onSubmit={handleSubmit}>
        <div className="auth-field">
          <label htmlFor="login-email">Email</label>
          <input
            id="login-email"
            name="email"
            type="email"
            autoComplete="email"
            value={email}
            onChange={(event) => setEmail(event.target.value)}
            placeholder="name@example.com"
            required
          />
        </div>

        <div className="auth-field">
          <label htmlFor="login-password">Mật khẩu</label>
          <input
            id="login-password"
            name="password"
            type="password"
            autoComplete="current-password"
            value={password}
            onChange={(event) => setPassword(event.target.value)}
            placeholder="Nhập mật khẩu"
            required
          />
        </div>

        <button className="auth-submit" type="submit" disabled={isSubmitting}>
          {isSubmitting ? "Đang đăng nhập..." : "Đăng nhập"}
        </button>
      </form>

      <div className="auth-divider" aria-hidden="true">
        <span>hoặc</span>
      </div>
      <GoogleLoginButton onAuthenticated={handleGoogleAuthenticated} />

      <p className="auth-switch">
        Chưa có tài khoản? <Link to="/register">Đăng ký</Link>
      </p>
    </section>
  );
}
