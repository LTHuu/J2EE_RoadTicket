import { useState } from "react";
import { Route, Routes } from "react-router-dom";
import Navbar from "./shared/components/Navbar/Navbar.jsx";
import HomePage from "./pages/Home.jsx";
import BusCompanyPage from "./features/company/pages/ListBusCompany.jsx";
import LoginPage from "./features/auth/pages/Login.jsx";
import RegisterPage from "./features/auth/pages/Register.jsx";
import {
  getAuthSession,
  logout as clearAuthSession,
} from "./features/auth/api.js";

export default function App() {
  const [session, setSession] = useState(getAuthSession);
  const user = session
    ? {
        role: session.role,
        fullName: session.username || session.email,
      }
    : null;

  function handleLogout() {
    clearAuthSession();
    setSession(null);
  }

  return (
    <>
      <Navbar user={user} onLogout={handleLogout} />

      <main>
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/bus-companies" element={<BusCompanyPage />} />
          <Route
            path="/login"
            element={<LoginPage onAuthenticated={setSession} />}
          />
          <Route
            path="/register"
            element={<RegisterPage onAuthenticated={setSession} />}
          />
        </Routes>
      </main>
    </>
  );
}