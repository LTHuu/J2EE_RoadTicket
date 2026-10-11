# Mô hình dữ liệu bán vé xe đường dài

Các entity dùng Jakarta Persistence và Lombok, theo cấu trúc có sẵn của project.

| Entity | Vai trò và quan hệ |
| --- | --- |
| BusCompany | Nhà xe. |
| User | Tài khoản đăng nhập. |
| User_BusCom | Thành viên của nhà xe, kèm chức vụ và trạng thái hoạt động. |
| Vehicle | Xe thuộc một nhà xe, có biển số duy nhất, loại xe và sức chứa. |
| Seat | Ghế của xe; số ghế duy nhất trong cùng xe. |
| Route | Tuyến đi, điểm đến, khoảng cách (km), thời gian dự kiến (phút). |
| TripInfo | Chuyến cụ thể trên một tuyến, sử dụng một xe; giờ đi, giờ đến và giá vé. |
| TripStaff | Phân công thành viên nhà xe cho chuyến; một thành viên chỉ xuất hiện một lần trong cùng chuyến. |
| Passenger | Thông tin hành khách, độc lập với tài khoản đặt vé. |
| Booking | Đơn đặt vé với thông tin liên hệ, tổng tiền, thời hạn giữ chỗ; tài khoản có thể trống cho khách vãng lai. |
| Ticket | Một hành khách và một ghế trên một chuyến, thuộc một đơn đặt vé; lưu giá tại thời điểm đặt. |
| Payment | Lần thanh toán của đơn; một đơn có thể có nhiều lần thử thanh toán. |

Quan hệ hiện dùng khóa ngoại một chiều (`ManyToOne`, `LAZY`). Có thể truy vấn danh sách vé theo booking, ghế theo xe, hoặc chuyến theo tuyến qua repository mà không cần collection hai chiều.

Tiền dùng `BigDecimal`, cột `DECIMAL(12,2)`; ứng dụng cần thống nhất đơn vị tiền (ví dụ VND). Thời gian dùng `LocalDateTime`; ứng dụng cần thống nhất múi giờ, ví dụ Asia/Ho_Chi_Minh. Mã đơn và mã vé mặc định là UUID. Enum lưu dạng chuỗi để không phụ thuộc thứ tự phần tử.

## Quy tắc cần thực hiện ở tầng service

- Ghế của vé phải thuộc xe của chuyến; nhân viên được phân công phải thuộc nhà xe sở hữu xe và đang hoạt động.
- Tạo đơn, giữ ghế và phát hành vé trong transaction. Khóa bản ghi chuyến (pessimistic write) trước khi kiểm tra và ghi vé; mọi luồng đặt/đổi/hủy vé phải dùng cùng quy ước khóa để tránh bán trùng ghế. Kiểm tra các vé RESERVED hoặc CONFIRMED và thời hạn giữ chỗ. Không đặt unique toàn bộ `(trip_id, seat_id)` vì cần giữ lịch sử vé đã hủy và cho phép đặt lại ghế đó.
- Tổng tiền đơn phải khớp các vé; số tiền thanh toán phải đúng số tiền cần thu. Không tin giá do client gửi lên.
- Số ghế đang hoạt động phải phù hợp sức chứa xe. Không đổi xe của chuyến đã có vé nếu chưa xử lý lại ghế.
- Chuyển trạng thái đơn, vé, chuyến và thanh toán theo nghiệp vụ; hết hạn giữ chỗ cần giải phóng vé. Callback thanh toán cần idempotent và xác thực trước khi xác nhận đơn.
- Dùng DTO cho API để kiểm soát dữ liệu trả về và tránh truy cập proxy LAZY ngoài transaction.

Project hiện chưa cấu hình datasource trong `application.properties`; cần cấu hình MySQL trước khi chạy ứng dụng hoặc integration test với cơ sở dữ liệu.
