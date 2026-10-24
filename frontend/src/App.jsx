import { useState } from "react";
import { Route, Routes } from "react-router-dom";
import Navbar from "./shared/components/Navbar/Navbar.jsx";
import HomePage from "./pages/Home.jsx";
import BusCompanyPage from "./features/company/pages/ListBusCompany.jsx";

const testUsers = {
  GUEST: {
    role: "GUEST",
    fullName: "Khách truy cập",
  },
  CUSTOMER: {
    role: "CUSTOMER",
    fullName: "Khách hàng Demo",
  },
  DRIVER: {
    role: "DRIVER",
    fullName: "Tài xế Demo",
  },
  CONDUCTOR: {
    role: "CONDUCTOR",
    fullName: "Lơ xe Demo",
  },
  COMPANY_ADMIN: {
    role: "COMPANY_ADMIN",
    fullName: "Quản lý nhà xe Demo",
  },
};

export default function App() {
  const [role, setRole] = useState("DRIVER");
  const user = role === "GUEST" ? null : testUsers[role];

  return (
    <>
      <Navbar
        user={user}
        onLogout={() => setRole("GUEST")}
      />

      <main>
        <Routes>
          <Route path="/" element={<HomePage />} />
          <Route path="/bus-companies" element={<BusCompanyPage />} />
        </Routes>
      </main>

      {/* <main style={{ padding: 24 }}>
        <h2>RoadTicket - Navbar Test</h2>

        <label htmlFor="role">Chọn role để kiểm tra: </label>

        <select
          id="role"
          value={role}
          onChange={(e) => setRole(e.target.value)}
        >
          {Object.keys(testUsers).map((item) => (
            <option key={item} value={item}>
              {item}
            </option>
          ))}
        </select>

        <p>Role hiện tại: {role}</p>
      </main> */}
    </>
  );
}