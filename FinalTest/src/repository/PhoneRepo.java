package repository;

import models.Phone;

import java.util.List;

public interface PhoneRepo {
    /**
     * Thêm mới một đối tượng Phone vào hệ thống lưu trữ (File CSV).
     */
    void save(Phone phone);

    /**
     * Lấy danh sách toàn bộ điện thoại đang có trong hệ thống.
     */
    List<Phone> findAll();

    /**
     * Xóa một điện thoại khỏi hệ thống dựa vào ID.
     *
     * @return true nếu xóa thành công, false nếu không tìm thấy ID
     */
    boolean deleteById(int id);

    /**
     * Tìm kiếm điện thoại theo từ khóa.
     * Có thể tìm theo tên điện thoại hoặc tên hãng (brand).
     *
     * @return Danh sách các điện thoại khớp với từ khóa
     */
    List<Phone> search(String keyword);
}

