import { Outlet } from "react-router-dom";
import { useAuth } from "../features/auth/useAuth";

export default function MainLayout() {
  const { user, logout } = useAuth();

  return (
    <>
      <Navbar user={user} onLogout={logout} />
      <main>
        <Outlet />
      </main>
    </>
  );
}