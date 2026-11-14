
const TICKET_STATUSES = [
  { value: "RESERVED", label: "Đã đặt" },
  { value: "CONFIRMED", label: "Đã xác nhận" },
  { value: "USED", label: "Đã sử dụng" },
  { value: "CANCELLED", label: "Đã hủy" },
];

function formatCurrency(amount) {
  return new Intl.NumberFormat("vi-VN", {
    style: "currency",
    currency: "VND",
  }).format(amount || 0);
}

function TicketTable({
  tickets = [],
  onView,
  onDelete,
  onStatusChange,
}) {
  if (tickets.length === 0) {
    return (
      <div className="ticket-empty">
        <p>Không tìm thấy vé nào.</p>
      </div>
    );
  }

  return (
    <div className="ticket-table-wrapper">
      <table className="ticket-table">
        <thead>
          <tr>
            <th>Mã vé</th>
            <th>Mã booking</th>
            <th>Chuyến đi</th>
            <th>Ghế</th>
            <th>Hành khách</th>
            <th>Giá vé</th>
            <th>Trạng thái</th>
            <th>Thao tác</th>
          </tr>
        </thead>

        <tbody>
          {tickets.map((ticket) => (
            <tr key={ticket.id}>
              <td>
                <span className="ticket-code">
                  {ticket.ticketCode}
                </span>
              </td>

              <td>{ticket.bookingCode || "-"}</td>

              <td>#{ticket.tripId}</td>

              <td>{ticket.seatNumber || "-"}</td>

              <td>{ticket.passengerName || "-"}</td>

              <td className="ticket-price">
                {formatCurrency(ticket.price)}
              </td>

              <td>
                <select
                  className={`ticket-status-select ${ticket.status?.toLowerCase()}`}
                  value={ticket.status}
                  onChange={(e) =>
                    onStatusChange?.(ticket, e.target.value)
                  }
                  aria-label={`Trạng thái vé ${ticket.ticketCode}`}
                >
                  {TICKET_STATUSES.map((item) => (
                    <option
                      key={item.value}
                      value={item.value}
                    >
                      {item.label}
                    </option>
                  ))}
                </select>
              </td>

              <td>
                <div className="ticket-actions">
                  <button
                    type="button"
                    className="ticket-action-btn view"
                    onClick={() => onView?.(ticket)}
                  >
                    Xem
                  </button>

                  <button
                    type="button"
                    className="ticket-action-btn delete"
                    onClick={() => onDelete?.(ticket)}
                  >
                    Xóa
                  </button>
                </div>
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}

export default TicketTable;