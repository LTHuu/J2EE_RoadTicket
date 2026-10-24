export const roleConfig = {
  GUEST: {
    layout: "main",
    homePath: "/",
    navbar: {
      theme: {
        primary: "#1677D2",
        background: "#ffffffb6",
      },
      items: [
        { label: "Đặt vé xe", path: "/" },
        { label: "Hướng dẫn", path: "/guide" },
        { label: "Nhà xe", path: "/bus-companies" },
        { label: "Giới thiệu", path: "/about" },
      ],
      showLogin: true,
      showTicketLookup: true,
    },
    footer: {
      visible: true,
      variant: "public",
    },
  },

  CUSTOMER: {
    layout: "main",
    homePath: "/",
    navbar: {
      theme: {
        primary: "#e26d19",
        background: "#5f73a5",
      },
      items: [
        { label: "Đặt vé xe", path: "/" },
        { label: "Hướng dẫn", path: "/guide" },
        { label: "Nhà xe", path: "/bus-companies" },
        { label: "Vé của tôi", path: "/my-tickets" },
        { label: "Tài khoản", path: "/profile" },
      ],
      showLogin: false,
      showTicketLookup: true,
    },
    footer: {
      visible: true,
      variant: "public",
    },
  },

  DRIVER: {
    layout: "operations",
    homePath: "/operations",
    navbar: {
      theme: {
        primary: "#16805D",
        background: "#60c375",
      },
      items: [
        { label: "Tổng quan", path: "/operations" },
        { label: "Chuyến được phân công", path: "/operations/my-trips" },
      ],
      showLogin: false,
      showTicketLookup: false,
    },
    footer: {
      visible: false,
      variant: "none",
    },
  },

  CONDUCTOR: {
    layout: "operations",
    homePath: "/operations",
    navbar: {
      theme: {
        primary: "#16805D",
        background: "#FFFFFF",
      },
      items: [
        { label: "Tổng quan", path: "/operations" },
        { label: "Hành khách", path: "/operations/passengers" },
      ],
      showLogin: false,
      showTicketLookup: false,
    },
    footer: {
      visible: false,
      variant: "none",
    },
  },

  COMPANY_ADMIN: {
    layout: "operations",
    homePath: "/operations",
    navbar: {
      theme: {
        primary: "#16805D",
        background: "#df8a91",
      },
      items: [
        { label: "Tổng quan", path: "/operations" },
        { label: "Quản lý chuyến", path: "/operations/trips" },
        { label: "Quản lý xe", path: "/operations/vehicles" },
        { label: "Nhân sự", path: "/operations/staff" },
      ],
      showLogin: false,
      showTicketLookup: false,
    },
    footer: {
      visible: false,
      variant: "none",
    },
  },

  SYSTEM_ADMIN: {
    layout: "admin",
    homePath: "/admin",
    navbar: {
      theme: {
        primary: "#DC3545",
        background: "#59a3d7",
      },
      items: [
        { label: "Tổng quan", path: "/admin" },
        { label: "Người dùng", path: "/admin/users" },
        { label: "Nhà xe", path: "/admin/companies" },
      ],
      showLogin: false,
      showTicketLookup: false,
    },
    footer: {
      visible: false,
      variant: "none",
    },
  },
};
