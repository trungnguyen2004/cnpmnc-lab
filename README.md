# cnpmnc-lab

## Lab 1

### Giải thích lỗi `UNIQUE constraint failed`

Lỗi UNIQUE constraint failed xảy ra khi thao tác thêm dữ liệu vi phạm ràng buộc khóa chính (Primary Key) của bảng trong cơ sở dữ liệu. Trong bảng students, cột id được khai báo là PRIMARY KEY, nên mỗi giá trị id phải là duy nhất và không được trùng lặp. Khi cố tình insert một sinh viên mới có id trùng với id đã tồn tại trong bảng, SQLite sẽ tự động chặn thao tác này và trả về lỗi UNIQUE constraint failed. Cơ chế này giúp đảm bảo tính toàn vẹn dữ liệu, tránh việc có nhiều bản ghi đại diện cho cùng một sinh viên.
