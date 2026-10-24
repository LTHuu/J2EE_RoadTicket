import { useState } from "react";
import { NavLink, Link } from "react-router-dom";
import { roleConfig } from "../../../config/roleConfig.js";
import "styles/Navbar.css";

export default function Navbar({ user = null, onLogout }) {
  const [menuOpen, setMenuOpen] = useState(false);

  // Chưa đăng nhập: hiển thị menu dành cho khách
  const role = user?.role ?? "GUEST";
  const roleSettings = roleConfig[role] ?? roleConfig.GUEST;
  const { homePath } = roleSettings;
  const {
    theme,
    items: menuItems,
    showLogin,
    showTicketLookup,
  } = roleSettings.navbar;

  return (
    <header
      className="site-header"
      style={{
        "--navbar-primary": theme.primary,
        "--navbar-background": theme.background,
      }}
    >
      <div className="navbar">
        <Link to={homePath} className="navbar-brand">
          <span className="brand-icon">🚌</span>
          <span className="brand-text">
            <strong>RoadTicket</strong>
            <small>Đặt vé xe đường dài</small>
          </span>
        </Link>

        <button
          className="mobile-menu-toggle"
          type="button"
          aria-label="Mở hoặc đóng menu"
          aria-expanded={menuOpen}
          onClick={() => setMenuOpen(!menuOpen)}
        >
          <span />
          <span />
          <span />
        </button>

        <nav className={`navbar-menu ${menuOpen ? "is-open" : ""}`}>
          {menuItems.map((item) => (
            <NavLink
              key={item.path}
              to={item.path}
              end={item.path === "/"}
              className={({ isActive }) =>
                `navbar-link ${isActive ? "active" : ""}`
              }
              onClick={() => setMenuOpen(false)}
            >
              {item.label}
            </NavLink>
          ))}

          {showTicketLookup && (
            <Link
              to="/check-ticket"
              className="ticket-button mobile-ticket-button"
              onClick={() => setMenuOpen(false)}
            >
              🎫 Kiểm tra vé
            </Link>
          )}
        </nav>

        <div className="navbar-actions">
          {user ? (
            <>
              <span className="navbar-user">
                {user.fullName ?? "Tài khoản"}
              </span>

              <button
                type="button"
                className="logout-button"
                onClick={onLogout}
              >
                Đăng xuất
              </button>
            </>
          ) : showLogin ? (
            <Link to="/login" className="login-link">
              Đăng nhập
            </Link>
          ) : null}

          {showTicketLookup && (
            <Link to="/check-ticket" className="ticket-button">
              🎫 Kiểm tra vé
            </Link>
          )}
        </div>
      </div>
    </header>
  );
}