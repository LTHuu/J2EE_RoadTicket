import "../components/ticket.css";

const STATUS_CONFIG = {
  RESERVED: {
    label: "Đã đặt",
    className: "reserved",
  },
  CONFIRMED: {
    label: "Đã xác nhận",
    className: "confirmed",
  },
  USED: {
    label: "Đã sử dụng",
    className: "used",
  },
  CANCELLED: {
    label: "Đã hủy",
    className: "cancelled",
  },
};

function TicketStatusBadge({ status }) {
  const config = STATUS_CONFIG[status] || {
    label: status || "Không xác định",
    className: "unknown",
  };

  return (
    <span className={`ticket-status-badge ${config.className}`}>
      {config.label}
    </span>
  );
}

export default TicketStatusBadge;
