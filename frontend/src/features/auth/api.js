const API_BASE_URL = (
  import.meta.env.VITE_API_BASE_URL || "http://localhost:8080"
).replace(/\/+$/, "");
const AUTH_SESSION_KEY = "roadticket-auth";

async function request(path, options = {}) {
  let response;

  try {
    response = await fetch(`${API_BASE_URL}${path}`, {
      ...options,
      headers: {
        "Content-Type": "application/json",
        ...options.headers,
      },
    });
  } catch (error) {
    if (error instanceof TypeError) {
      throw new Error("Không thể kết nối đến máy chủ. Vui lòng thử lại.");
    }
    throw error;
  }

  const responseText = await response.text();
  let body = null;

  if (responseText) {
    try {
      body = JSON.parse(responseText);
    } catch {
      body = { message: responseText };
    }
  }

  if (!response.ok) {
    throw new Error(
      body?.message ||
        body?.detail ||
        body?.error ||
        "Đã xảy ra lỗi. Vui lòng thử lại.",
    );
  }

  return body;
}

export function register(credentials) {
  return request("/api/auth/register", {
    method: "POST",
    body: JSON.stringify(credentials),
  });
}

export async function login(credentials) {
  const session = await request("/api/auth/login", {
    method: "POST",
    body: JSON.stringify(credentials),
  });

  if (!session?.accessToken) {
    throw new Error("Máy chủ không trả về access token hợp lệ.");
  }

  localStorage.setItem(AUTH_SESSION_KEY, JSON.stringify(session));
  return session;
}

export async function googleLogin(credential) {
  const session = await request("/api/auth/google", {
    method: "POST",
    body: JSON.stringify({ credential }),
  });

  if (!session?.accessToken) {
    throw new Error("Máy chủ không trả về access token hợp lệ.");
  }

  localStorage.setItem(AUTH_SESSION_KEY, JSON.stringify(session));
  return session;
}

export function getAuthSession() {
  const storedSession = localStorage.getItem(AUTH_SESSION_KEY);
  if (!storedSession) return null;

  try {
    const session = JSON.parse(storedSession);
    return session?.accessToken ? session : null;
  } catch {
    localStorage.removeItem(AUTH_SESSION_KEY);
    return null;
  }
}

export function logout() {
  localStorage.removeItem(AUTH_SESSION_KEY);
}
