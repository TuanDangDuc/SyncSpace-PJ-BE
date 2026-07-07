# SyncSpace: A Real-time Cloud-native Workspace Allocation Platform

**SyncSpace** là một nền tảng quản lý và đặt chỗ không gian làm việc (Co-working Space) được thiết kế theo kiến trúc Microservices hiện đại, tập trung vào khả năng chịu tải cao, tính nhất quán dữ liệu và trải nghiệm thời gian thực (Real-time).

---

##   Tính năng nổi bật
*   **Real-time Synchronization:** Sử dụng SSE (Server-Sent Events) để cập nhật trạng thái phòng/bàn cho người dùng ngay lập tức mà không cần làm mới trang.
*   **High Performance:** Tối ưu hóa truy vấn dữ liệu thông qua chiến lược Caching (Redis) - giảm độ trễ phản hồi từ ~100ms xuống < 5ms.
*   **Distributed Architecture:** Đảm bảo tính toàn vẹn dữ liệu trong môi trường phân tán bằng các kỹ thuật Optimistic Locking và Message Queue (Kafka).
*   **Scalability:** Đóng gói toàn bộ hệ thống bằng Docker và quản lý trên Kubernetes, hỗ trợ khả năng tự động mở rộng (Auto-scaling).

##   Tech Stack
*   **Backend:** Java 21, Spring Boot 3.x
*   **Database & Cache:** PostgreSQL, Redis
*   **Message Broker:** Apache Kafka
*   **DevOps/Infrastructure:** Docker, Kubernetes (K8s), Helm
*   **CI/CD:** GitHub Actions
*   **Monitoring:** Prometheus, Grafana

##  Architecture Overview

*   **Booking Service:** Xử lý nghiệp vụ đặt chỗ chính, giao tiếp với Kafka để giải quyết bài toán tranh chấp (Race Condition).
*   **Notification Service:** Chịu trách nhiệm đẩy trạng thái thời gian thực tới Client thông qua SSE.
*   **Caching Strategy:** Sử dụng mô hình Cache-Aside để tối ưu hóa hiệu suất đọc cho các tài nguyên hot-data.
