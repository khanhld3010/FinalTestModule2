package view;

import models.GenuinePhone;
import models.Phone;
import models.PortablePhone;
import service.PhoneService;
import service.PhoneServiceImpl;
import util.Validation;

import java.util.List;
import java.util.Scanner;

public class PhoneView {
    private PhoneService phoneService;
    private Scanner scanner;

    public PhoneView(PhoneService phoneService) {
        this.phoneService = phoneService;
        this.scanner = new Scanner(System.in);
    }

    // Hàm khởi chạy ứng dụng
    public void start() {
        boolean isRunning = true;
        while (isRunning) {
            displayMenu();
            System.out.print("Chọn chức năng: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    addNewPhone();
                    break;
                case "2":
                    deletePhone();
                    break;
                case "3":
                    displayAllPhones();
                    break;
                case "4":
                    searchPhone();
                    break;
                case "5":
                    System.out.println("Đã thoát chương trình. Hẹn gặp lại!");
                    isRunning = false;
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ. Vui lòng chọn từ 1 đến 5!");
            }
        }
    }

    private void displayMenu() {
        System.out.println("\n--CHƯƠNG TRÌNH QUẢN LÝ ĐIỆN THOẠI--");
        System.out.println("Chọn chức năng theo số (để tiếp tục):");
        System.out.println("1. Thêm mới");
        System.out.println("2. Xóa");
        System.out.println("3. Xem danh sách điện thoại");
        System.out.println("4. Tìm kiếm");
        System.out.println("5. Thoát");
    }

    private void addNewPhone() {
        System.out.println("\n--- THÊM MỚI ĐIỆN THOẠI ---");
        try {
            String name = Validation.getValidString("Nhập tên điện thoại: ", scanner);
            double price = Validation.getValidPositiveDouble("Nhập giá: ", scanner);
            int quantity = Validation.getValidPositiveInt("Nhập số lượng: ", scanner);
            String brand = Validation.getValidString("Nhập tên hãng: ", scanner);
            String typeChoice;
            while (true) {
                System.out.println("Chọn loại điện thoại: ");
                System.out.println("1. Điện thoại Chính Hãng");
                System.out.println("2. Điện thoại Xách Tay");
                System.out.print("Lựa chọn: ");
                typeChoice = scanner.nextLine().trim();
                if (typeChoice.equals("1") || typeChoice.equals("2")) {
                    break;
                }
                System.out.println("Lỗi: Vui lòng chỉ chọn 1 hoặc 2!");
            }
            Phone newPhone = typePhone(typeChoice, scanner, name, price, quantity, brand);
            // Gọi service để lưu xuống file
            phoneService.addPhone(newPhone);
            System.out.println("=> THÔNG BÁO: Thêm mới dữ liệu thành công vào file CSV!");
        } catch (Exception e) {
            System.out.println("Lỗi hệ thống: " + e.getMessage());
        }
    }

    private Phone typePhone(String typeChoice, Scanner scanner, String name, double price, int quantity, String brand) {
        if (typeChoice.equals("1")) {
            int warrantyPeriod = Validation.getValidWarrantyPeriod("Nhập thời gian bảo hành (số ngày): ", scanner, 730);
            String warrantyScope = Validation.getValidWarrantyScope("Nhập phạm vi bảo hành (Toan Quoc/Quoc Te): ", scanner);
            return new GenuinePhone(0, name, price, quantity, brand, warrantyPeriod, warrantyScope);
        } else {
            String country = Validation.getValidPortableCountry("Nhập quốc gia xách tay: ", scanner);
            String status = Validation.getValidPhoneStatus("Nhập trạng thái (Da sua chua/Chua sua chua): ", scanner);
            return new PortablePhone(0, name, price, quantity, brand, country, status);
        }
    }

    private void deletePhone() {
        System.out.println("\n--- XÓA ĐIỆN THOẠI ---");
        int id = Validation.getValidPositiveInt("Nhập ID điện thoại cần xóa: ", scanner);
        System.out.print("Bạn có chắc chắn muốn xóa không? (Y/N): ");
        String confirm = scanner.nextLine().trim();
        if (confirm.equalsIgnoreCase("Y")) {
            boolean isDeleted = phoneService.deletePhone(id);
            if (isDeleted) {
                System.out.println("=> THÔNG BÁO: Xóa thành công điện thoại có ID: " + id);
            } else {
                System.out.println("=> LỖI: ID điện thoại không tồn tại: " + id);
            }
        } else {
            System.out.println("Đã hủy thao tác xóa.");
        }
    }

    private void displayAllPhones() {
        System.out.println("\n--- DANH SÁCH ĐIỆN THOẠI ---");
        List<Phone> phones = phoneService.displayAllPhones();
        if (phones.isEmpty()) {
            System.out.println("Danh sách trống!");
        } else {
            for (Phone p : phones) {
                // Gọi hàm displayInformation đã được khai báo abstract ở Phone và triển khai ở các lớp con
                p.displayInformation();
            }
        }
    }

    private void searchPhone() {
        System.out.println("\n--- TÌM KIẾM ĐIỆN THOẠI ---");
        String keyword = Validation.getValidString("Nhập tên hoặc hãng cần tìm: ", scanner);
        List<Phone> results = phoneService.searchPhone(keyword);
        if (results.isEmpty()) {
            System.out.println("Không tìm thấy kết quả nào cho: '" + keyword + "'");
        } else {
            System.out.println("Tìm thấy " + results.size() + " kết quả:");
            for (Phone p : results) {
                p.displayInformation();
            }
        }
    }
}