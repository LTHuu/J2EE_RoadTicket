import { useEffect, useMemo, useState } from "react";
import {
  getAllTickets,
  updateTicketStatus,
  deleteTicket,
} from "../api";
import TicketDetail from "../components/TicketDetail";
import TicketTable from "../components/TicketTable";
import "../components/ticket.css";

function TicketList() {
  const [tickets, setTickets] = useState([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [keyword, setKeyword] = useState("");
  const [selectedTicket, setSelectedTicket] = useState(null);

  // Fetch danh sách vé
  const fetchTickets = async () => {
    try {
      setLoading(true);
      setError("");

      const data = await getAllTickets();
      setTickets(Array.isArray(data) ? data : []);
    } catch (err) {
      setError(err.message || "Không thể tải danh sách vé.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    fetchTickets();
  }, []);

  // Tìm kiếm vé
  const filteredTickets = useMemo(() => {
    const search = keyword.trim().toLowerCase();

    if (!search) return tickets;

    return tickets.filter((ticket) =>
      [
        ticket.ticketCode,
        ticket.bookingCode,
        ticket.passengerName,
        ticket.seatNumber,
      ].some((value) =>
        String(value || "").toLowerCase().includes(search)
      )
    );
  }, [tickets, keyword]);

  // Cập nhật trạng thái vé
  const handleStatusChange = async (ticket, newStatus) => {
    if (ticket.status === newStatus) return;

    try {
      const updatedTicket = await updateTicketStatus(
        ticket.id,
        newStatus
      );

      setTickets((prev) =>
        prev.map((item) =>
          item.id === ticket.id
            ? { ...item, ...updatedTicket }
            : item
        )
      );

      window.alert("Cập nhật trạng thái vé thành công!");
    } catch (err) {
      window.alert(
        err.message || "Không thể cập nhật trạng thái vé."
      );
    }
  };

  // Xóa vé
  const handleDelete = async (ticket) => {
    const confirmed = window.confirm(
      `Bạn có chắc chắn muốn xóa vé ${ticket.ticketCode}?`
    );

    if (!confirmed) return;

    try {
      await deleteTicket(ticket.id);

      setTickets((prev) =>
        prev.filter((item) => item.id !== ticket.id)
      );

      window.alert("Xóa vé thành công!");
    } catch (err) {
      window.alert(err.message || "Không thể xóa vé.");
    }
  };

  // Xem chi tiết
  const handleView = (ticket) => {
    setSelectedTicket(ticket);
  };

  return (
    <div className="ticket-page">
      <div className="ticket-page-header">
        <div>
          <h1>Quản lý vé</h1>
          <p>Quản lý thông tin và trạng thái vé xe.</p>
        </div>
      </div>

      <div className="ticket-toolbar">
        <input
          type="text"
          placeholder="Tìm mã vé, booking, hành khách, số ghế..."
          value={keyword}
          onChange={(e) => setKeyword(e.target.value)}
        />

        <button
          className="ticket-refresh-btn"
          onClick={fetchTickets}
          disabled={loading}
        >
          Làm mới
        </button>
      </div>

      <div className="ticket-content">
        {loading ? (
          <div className="ticket-message">
            Đang tải danh sách vé...
          </div>
        ) : error ? (
          <div className="ticket-error">
            <p>{error}</p>
            <button onClick={fetchTickets}>
              Thử lại
            </button>
          </div>
        ) : (
          <>
            <div className="ticket-result-count">
              Hiển thị {filteredTickets.length} / {tickets.length} vé
            </div>

            <TicketTable
              tickets={filteredTickets}
              onView={handleView}
              onDelete={handleDelete}
              onStatusChange={handleStatusChange}
            />
          </>
        )}
      </div>

    {selectedTicket && (
        <TicketDetail
            ticket={selectedTicket}
            onClose={() => setSelectedTicket(null)}
        />
    )}

    </div>
  );
}

export default TicketList;
