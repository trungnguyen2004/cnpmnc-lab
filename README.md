# cnpmnc-lab

## Lab 1

### Giải thích lỗi `UNIQUE constraint failed`

Lỗi UNIQUE constraint failed xảy ra khi thao tác thêm dữ liệu vi phạm ràng buộc khóa chính (Primary Key) của bảng trong cơ sở dữ liệu. Trong bảng students, cột id được khai báo là PRIMARY KEY, nên mỗi giá trị id phải là duy nhất và không được trùng lặp. Khi cố tình insert một sinh viên mới có id trùng với id đã tồn tại trong bảng, SQLite sẽ tự động chặn thao tác này và trả về lỗi UNIQUE constraint failed. Cơ chế này giúp đảm bảo tính toàn vẹn dữ liệu, tránh việc có nhiều bản ghi đại diện cho cùng một sinh viên.

### Giải thích về vấn đề Toàn vẹn dữ liệu (Constraints)

Khi thử `INSERT` một sinh viên nhưng để trống cột `name` (giá trị `NULL`), cơ sở dữ liệu **không báo lỗi**. Nguyên nhân là do trong cấu trúc bảng `students`, cột `name` **không được khai báo ràng buộc `NOT NULL`**, nên Database cho phép lưu giá trị `NULL`.

Sự thiếu chặt chẽ này có thể gây ảnh hưởng khi code Java đọc dữ liệu lên. Cụ thể, nếu trong chương trình giả định rằng tên sinh viên luôn tồn tại, việc gặp giá trị `NULL` có thể dẫn đến lỗi logic hoặc xảy ra lỗi `NullPointerException`. Ngoài ra, dữ liệu không đầy đủ cũng làm giảm tính nhất quán và độ tin cậy của hệ thống.

Do đó, để đảm bảo toàn vẹn dữ liệu, cần thiết lập các ràng buộc phù hợp ở tầng Database (ví dụ `NOT NULL`) và kết hợp kiểm tra dữ liệu ở tầng Service trong ứng dụng.

### Giải thích về cấu hình Hibernate (`ddl-auto`)

Mỗi lần tắt ứng dụng và chạy lại, dữ liệu cũ trong Database bị mất do cấu hình sau trong file `application.properties`:

```properties
spring.jpa.hibernate.ddl-auto=create
```

Giá trị create có nghĩa là mỗi khi ứng dụng khởi động, Hibernate sẽ:

1. Xóa (drop) toàn bộ các bảng đã tồn tại trong Database.

2. Tạo lại bảng mới dựa trên các Entity trong code Java.

3. Không giữ lại dữ liệu cũ.

Vì vậy, mặc dù file student.db vẫn tồn tại, nhưng các bảng bên trong đã bị xóa và tạo lại, dẫn đến toàn bộ dữ liệu trước đó bị mất.
