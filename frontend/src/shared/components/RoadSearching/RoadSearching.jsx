
import { useState } from "react";
import "styles/RoadSearching.css";

// Dữ liệu mẫu để chạy frontend độc lập.
// Sau này có thể thay bằng API địa điểm của backend.
const LOCATIONS = [
  { id: "hcm", name: "TP. Hồ Chí Minh" },
  { id: "hn", name: "Hà Nội" },
  { id: "dn", name: "Đà Nẵng" },
  { id: "ct", name: "Cần Thơ" },
  { id: "dl", name: "Đà Lạt" },
  { id: "vt", name: "Vũng Tàu" },
  { id: "nt", name: "Nha Trang" },
  { id: "hp", name: "Hải Phòng" },
  { id: "bmt", name: "Buôn Ma Thuột" },
  { id: "qn", name: "Quy Nhơn" },
];

function getLocalDateString() {
  const now = new Date();
  const year = now.getFullYear();
  const month = String(now.getMonth() + 1).padStart(2, "0");
  const day = String(now.getDate()).padStart(2, "0");

  return `${year}-${month}-${day}`;
}

function LocationField({
  label,
  placeholder,
  value,
  onChange,
  onSelect,
  excludeId,
}) {
  const [isOpen, setIsOpen] = useState(false);

  const filteredLocations = LOCATIONS.filter((location) => {
    const matchesText = location.name
      .toLowerCase()
      .includes(value.trim().toLowerCase());

    const isNotExcluded = location.id !== excludeId;

    return matchesText && isNotExcluded;
  });

  const handleSelect = (location) => {
    onSelect(location);
    setIsOpen(false);
  };

  return (
    <div className="road-search__field road-search__location">
      <label className="road-search__label">{label}</label>

      <div className="road-search__input-wrap">
        <svg
          className="road-search__field-icon"
          viewBox="0 0 24 24"
          fill="none"
          aria-hidden="true"
        >
          <path
            d="M20 10c0 5-8 12-8 12S4 15 4 10a8 8 0 1 1 16 0Z"
            stroke="currentColor"
            strokeWidth="1.8"
            strokeLinejoin="round"
          />
          <circle
            cx="12"
            cy="10"
            r="2.5"
            stroke="currentColor"
            strokeWidth="1.8"
          />
        </svg>

        <input
          type="text"
          className="road-search__input"
          placeholder={placeholder}
          value={value}
          autoComplete="off"
          role="combobox"
          aria-expanded={isOpen}
          aria-autocomplete="list"
          onFocus={() => setIsOpen(true)}
          onChange={(event) => {
            onChange(event.target.value);
            setIsOpen(true);
          }}
          onKeyDown={(event) => {
            if (event.key === "Escape") {
              setIsOpen(false);
            }

            if (event.key === "Enter" && isOpen) {
              event.preventDefault();

              if (filteredLocations.length > 0) {
                handleSelect(filteredLocations[0]);
              }
            }
          }}
        />

        {isOpen && (
          <div className="road-search__suggestions" role="listbox">
            {filteredLocations.length > 0 ? (
              filteredLocations.map((location) => (
                <button
                  key={location.id}
                  type="button"
                  className="road-search__suggestion"
                  role="option"
                  aria-selected={false}
                  onMouseDown={(event) => event.preventDefault()}
                  onClick={() => handleSelect(location)}
                >
                  <span className="road-search__suggestion-icon">
                    <svg viewBox="0 0 24 24" fill="none" aria-hidden="true">
                      <path
                        d="M12 21s7-6.2 7-12a7 7 0 1 0-14 0c0 5.8 7 12 7 12Z"
                        stroke="currentColor"
                        strokeWidth="1.7"
                      />
                      <circle
                        cx="12"
                        cy="9"
                        r="2.3"
                        stroke="currentColor"
                        strokeWidth="1.7"
                      />
                    </svg>
                  </span>
                  <span>{location.name}</span>
                </button>
              ))
            ) : (
              <div className="road-search__empty">
                Không tìm thấy địa điểm
              </div>
            )}
          </div>
        )}
      </div>
    </div>
  );
}

export default function RoadSearching({
  searchPath = "/dat-ve-truc-tuyen",
  onSearch,
}) {
  const [from, setFrom] = useState(null);
  const [to, setTo] = useState(null);
  const [departDate, setDepartDate] = useState(getLocalDateString());
  const [error, setError] = useState("");

  const swapLocations = () => {
    setFrom(to);
    setTo(from);
    setError("");
  };

  const handleSearch = (event) => {
    event.preventDefault();

    if (!from || !to) {
      setError("Vui lòng chọn nơi đi và nơi đến từ danh sách gợi ý.");
      return;
    }

    if (from.id === to.id) {
      setError("Nơi đi và nơi đến không được trùng nhau.");
      return;
    }

    if (!departDate || departDate < getLocalDateString()) {
      setError("Vui lòng chọn ngày khởi hành hợp lệ.");
      return;
    }

    setError("");

    // Giữ định dạng ngày dd-mm-yyyy giống form Vexere.
    const [year, month, day] = departDate.split("-");
    const formattedDate = `${day}-${month}-${year}`;

    const params = new URLSearchParams({
      from: from.id,
      to: to.id,
      departDate: formattedDate,
    });

    const searchData = {
      from: from.id,
      fromName: from.name,
      to: to.id,
      toName: to.name,
      departDate: formattedDate,
    };

    // Cho phép component cha tự xử lý nếu cần.
    if (onSearch) {
      onSearch(searchData);
      return;
    }

    // Mặc định chuyển đến trang kết quả tìm kiếm.
    window.location.href = `${searchPath}?${params.toString()}`;
  };

  return (
    <section className="road-search" aria-label="Tìm kiếm vé xe">
      <form className="road-search__form" onSubmit={handleSearch}>
        <div className="road-search__fields">
          <LocationField
            label="Nơi khởi hành"
            placeholder="Nhập nơi đi"
            value={from?.name ?? ""}
            excludeId={to?.id}
            onChange={(text) => {
              setFrom(
                LOCATIONS.find((location) => location.name === text) ?? null
              );
              // Cho phép nhập lại khi đổi địa điểm.
              if (from?.name !== text) setFrom(null);
            }}
            onSelect={(location) => {
              setFrom(location);
              setError("");
            }}
          />

          <button
            type="button"
            className="road-search__swap"
            onClick={swapLocations}
            aria-label="Đổi nơi đi và nơi đến"
            title="Đổi chiều"
          >
            <svg viewBox="0 0 24 24" fill="none" aria-hidden="true">
              <path
                d="M7 7h13m0 0-3-3m3 3-3 3M17 17H4m0 0 3 3m-3-3 3-3"
                stroke="currentColor"
                strokeWidth="1.8"
                strokeLinecap="round"
                strokeLinejoin="round"
              />
            </svg>
          </button>

          <LocationField
            label="Điểm đến"
            placeholder="Nhập nơi đến"
            value={to?.name ?? ""}
            excludeId={from?.id}
            onChange={(text) => {
              setTo(
                LOCATIONS.find((location) => location.name === text) ?? null
              );
              if (to?.name !== text) setTo(null);
            }}
            onSelect={(location) => {
              setTo(location);
              setError("");
            }}
          />

          <div className="road-search__field road-search__date">
            <label className="road-search__label" htmlFor="road-depart-date">
              Ngày khởi hành
            </label>

            <div className="road-search__input-wrap">
              <svg
                className="road-search__field-icon"
                viewBox="0 0 24 24"
                fill="none"
                aria-hidden="true"
              >
                <rect
                  x="3"
                  y="5"
                  width="18"
                  height="16"
                  rx="2"
                  stroke="currentColor"
                  strokeWidth="1.8"
                />
                <path
                  d="M16 3v4M8 3v4M3 10h18"
                  stroke="currentColor"
                  strokeWidth="1.8"
                  strokeLinecap="round"
                />
              </svg>

              <input
                id="road-depart-date"
                className="road-search__input"
                type="date"
                min={getLocalDateString()}
                value={departDate}
                onChange={(event) => {
                  setDepartDate(event.target.value);
                  setError("");
                }}
                required
              />
            </div>
          </div>
        </div>

        {error && (
          <p className="road-search__error" role="alert">
            {error}
          </p>
        )}

        <button className="road-search__submit" type="submit">
          <svg viewBox="0 0 24 24" fill="none" aria-hidden="true">
            <path
              d="M3 13v-2l2-5h14l2 5v2M5 13h14M6 18h.01M18 18h.01M5 13v5h14v-5M7 6l1-3h8l1 3"
              stroke="currentColor"
              strokeWidth="1.7"
              strokeLinecap="round"
              strokeLinejoin="round"
            />
          </svg>
          <span>TÌM KIẾM VÉ</span>
        </button>
      </form>
    </section>
  );
}
