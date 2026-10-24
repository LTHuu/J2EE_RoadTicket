import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import { register } from "../api.js";
import GoogleLoginButton from "./GoogleLoginButton.jsx";
import "./auth.css";

export default function RegisterForm({ onAuthenticated }) {
  const [form, setForm] = useState({
    username: "",
    email: "",
    password: "",
    confirmPassword: "",
  });
  const [error, setError] = useState("");
  const [success, setSuccess] = useState("");
  const [isSubmitting, setIsSubmitting] = useState(false);
  const navigate = useNavigate();

  function updateField(event) {
    const { name, value } = event.target;
    setForm((currentForm) => ({ ...currentForm, [name]: value }));
  }

  function handleGoogleAuthenticated(session) {
    onAuthenticated?.(session);
    navigate("/");
  }

  async function handleSubmit(event) {
    event.preventDefault();
    setError("");
    setSuccess("");

    if (form.password !== form.confirmPassword) {
      setError("Mật khẩu xác nhận không khớp.");
      return;
    }

    setIsSubmitting(true);
    try {
      const result = await register({
        username: form.username.trim(),
        email: form.email.trim(),
        password: form.password,
      });
      setSuccess(
        result?.message ||
          "Đăng ký thành công. Vui lòng kiểm tra email để xác thực tài khoản.",
      );
      setForm({
        username: "",
        email: "",
        password: "",
        confirmPassword: "",
      });
    } catch (submitError) {
      setError(submitError.message);
    } finally {
      setIsSubmitting(false);
    }
  }

  return (
    <section className="auth-card" aria-labelledby="register-title">
      <h1 id="register-title">Tạo tài khoản</h1>
      <p className="auth-intro">Đăng ký để bắt đầu sử dụng RoadTicket.</p>

      {error && (
        <p className="auth-message auth-message-error" role="alert">
          {error}
        </p>
      )}
      {success && (
        <p className="auth-message auth-message-success" role="status">
          {success}
        </p>
      )}

      <form onSubmit={handleSubmit}>
        <div className="auth-field">
          <label htmlFor="register-username">Tên người dùng</label>
          <input
            id="register-username"
            name="username"
            type="text"
            autoComplete="username"
            minLength={4}
            maxLength={50}
            value={form.username}
            onChange={updateField}
            placeholder="Nhập tên người dùng"
            required
          />
        </div>

        <div className="auth-field">
          <label htmlFor="register-email">Email</label>
          <input
            id="register-email"
            name="email"
            type="email"
            autoComplete="email"
            value={form.email}
            onChange={updateField}
            placeholder="name@example.com"
            required
          />
        </div>

        <div className="auth-field">
          <label htmlFor="register-password">Mật khẩu</label>
          <input
            id="register-password"
            name="password"
            type="password"
            autoComplete="new-password"
            minLength={8}
            maxLength={72}
            value={form.password}
            onChange={updateField}
            placeholder="Tối thiểu 8 ký tự"
            required
          />
        </div>

        <div className="auth-field">
          <label htmlFor="register-confirm-password">Xác nhận mật khẩu</label>
          <input
            id="register-confirm-password"
            name="confirmPassword"
            type="password"
            autoComplete="new-password"
            minLength={8}
            maxLength={72}
            value={form.confirmPassword}
            onChange={updateField}
            placeholder="Nhập lại mật khẩu"
            required
          />
        </div>

        <button className="auth-submit" type="submit" disabled={isSubmitting}>
          {isSubmitting ? "Đang tạo tài khoản..." : "Đăng ký"}
        </button>
      </form>

      <div className="auth-divider" aria-hidden="true">
        <span>hoặc</span>
      </div>
      <GoogleLoginButton onAuthenticated={handleGoogleAuthenticated} />

      <p className="auth-switch">
        Đã có tài khoản? <Link to="/login">Đăng nhập</Link>
      </p>
    </section>
  );
}
