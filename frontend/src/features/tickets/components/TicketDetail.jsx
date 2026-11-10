
import { useEffect, useState } from "react";
import { getTicketById } from "../api";
import TicketStatusBadge from "./TicketStatusBadge";

function formatCurrency(amount) {
  return new Intl.NumberFormat("vi-VN", {
    style: "currency",
    currency: "VND",
  }).format(amount || 0);
}

function DetailItem({ label, value }) {
  return (
    <div className="ticket-detail-item">
      <span className="detail-label">{label}</span>
      <span className="detail-value">{value || "Chưa cập nhật"}</span>
    </div>
  );
}

function TicketDetail({ ticket, onClose }) {
  const [detail, setDetail] = useState(ticket);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  useEffect(() => {
    let active = true;

    const fetchDetail = async () => {
      try {
        setLoading(true);
        setError("");

        const data = await getTicketById(ticket.id);

        if (active) setDetail(data);
      } catch (err) {
        if (active) {
          setError(err.message || "Không thể tải thông tin vé.");
        }
      } finally {
        if (active) setLoading(false);
      }
    };

    fetchDetail();

    return () => {
      active = false;
    };
  }, [ticket.id]);

  useEffect(() => {
    const handleKeyDown = (event) => {
      if (event.key === "Escape") onClose();
    };

    window.addEventListener("keydown", handleKeyDown);
    return () => window.removeEventListener("keydown", handleKeyDown);
  }, [onClose]);

  return (
    <div
      className="ticket-modal-overlay"
      onMouseDown={(e) => {
        if (e.target === e.currentTarget) onClose();
      }}
    >
      <div
        className="ticket-detail-modal"
        role="dialog"
        aria-modal="true"
        aria-labelledby="ticket-detail-title"
      >
        <div className="ticket-detail-header">
          <div>
            <h2 id="ticket-detail-title">Chi tiết vé</h2>
          </div>

          <button
            type="button"
            className="ticket-modal-close"
            onClick={onClose}
            aria-label="Đóng"
          >
            &times;
          </button>
        </div>

        {loading ? (
          <div className="ticket-detail-state">
            <div className="ticket-spinner" />
            <p>Đang tải thông tin vé...</p>
          </div>
        ) : error ? (
          <div className="ticket-detail-state ticket-detail-error">
            <p>{error}</p>
            <button type="button" onClick={onClose}>
              Đóng
            </button>
          </div>
        ) : (
          <>
            <div className="ticket-detail-body">
              <div className="ticket-identity">
                <div>
                  <span className="detail-label">MÃ VÉ</span>
                  <h3>{detail.ticketCode}</h3>
                </div>

                <TicketStatusBadge status={detail.status} />
              </div>

              <section className="ticket-detail-section">
                <div className="ticket-section-heading">
                  <span className="ticket-section-icon">✈</span>
                  <h3>Thông tin chuyến đi</h3>
                </div>

                <div className="ticket-detail-grid">
                  <DetailItem
                    label="Mã chuyến đi"
                    value={detail.tripId ? `#${detail.tripId}` : null}
                  />
                  <DetailItem
                    label="Mã booking"
                    value={detail.bookingCode}
                  />
                  <DetailItem
                    label="Điểm đón"
                    value={detail.pickupLocation}
                  />
                  <DetailItem
                    label="Điểm trả"
                    value={detail.dropoffLocation}
                  />
                </div>
              </section>

              <section className="ticket-detail-section">
                <div className="ticket-section-heading">
                  <span className="ticket-section-icon">♙</span>
                  <h3>Thông tin hành khách</h3>
                </div>

                <div className="ticket-detail-grid">
                  <DetailItem
                    label="Họ và tên"
                    value={detail.passengerName}
                  />
                  <DetailItem
                    label="Số ghế"
                    value={detail.seatNumber}
                  />
                </div>
              </section>

              <div className="ticket-price-summary">
                <div>
                  <span>Giá vé</span>
                  <small>Giá trị thanh toán của vé</small>
                </div>
                <strong>{formatCurrency(detail.price)}</strong>
              </div>
            </div>

            {/* <div className="ticket-detail-footer">
              <button
                type="button"
                className="ticket-btn-secondary"
                onClick={onClose}
              >
                Đóng
              </button>
            </div> */}
          </>
        )}
      </div>
    </div>
  );
}

export default TicketDetail;
