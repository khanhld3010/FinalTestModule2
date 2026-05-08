package service;

import models.Phone;
import repository.PhoneRepo;
import repository.PhoneRepositoryImpl;

import java.util.List;

public class PhoneServiceImpl implements PhoneService {
    private PhoneRepo phoneRepository;

    public PhoneServiceImpl(PhoneRepo phoneRepository) {
        // Khởi tạo implementation của Repository
        this.phoneRepository = phoneRepository;
    }

    @Override
    public void addPhone(Phone phone) throws Exception {
        // Nếu ID hợp lệ (không trùng), gọi Repository để lưu xuống CSV
        phoneRepository.save(phone);
    }

    @Override
    public boolean deletePhone(int id) {
        return phoneRepository.deleteById(id);
    }

    @Override
    public List<Phone> displayAllPhones() {
        // Lấy dữ liệu từ Repo lên
        List<Phone> list = phoneRepository.findAll();
        // list.sort((p1, p2) -> Double.compare(p1.getPrice(), p2.getPrice()));
        return list;
    }

    @Override
    public List<Phone> searchPhone(String keyword) {
        // Xử lý logic chuẩn hóa chuỗi tìm kiếm trước khi đẩy xuống Repo (nếu cần)
        if (keyword == null || keyword.trim().isEmpty()) {
            return displayAllPhones(); // Nếu không nhập gì, trả về tất cả
        }
        return phoneRepository.search(keyword.trim());
    }
}
