package repository;

import models.GenuinePhone;
import models.Phone;
import models.PortablePhone;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PhoneRepositoryImpl implements PhoneRepo {
    // Đường dẫn file CSV lưu trữ dữ liệu
    private final String FILE_PATH = "data/phones.csv";
    private final String COMMA_DELIMITER = ",";
    // Header của file CSV, định nghĩa cấu trúc các cột
    private final String HEADER = "id,phoneName,price,quantity,brand,type,extra1,extra2";

    public PhoneRepositoryImpl() {
        // Kiểm tra thư mục và file đã tồn tại chưa, nếu chưa thì tạo mới
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            try {
                File parent = file.getParentFile();
                if (parent != null && !parent.exists()) {
                    parent.mkdirs();
                }
                file.createNewFile();          // Tạo file CSV
                writeToFile(new ArrayList<>(), false); // Ghi dòng Header đầu tiên vào file
            } catch (IOException e) {
                System.out.println("Lỗi tạo file: " + e.getMessage());
            }
        }
    }

    /**
     * Hàm đọc toàn bộ dữ liệu từ file CSV
     */
    private List<Phone> readFromFile() {
        List<Phone> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            br.readLine(); // Đọc và bỏ qua dòng Header đầu tiên
            while ((line = br.readLine()) != null) {
                String[] data = line.split(COMMA_DELIMITER);
                // Đảm bảo dòng dữ liệu có đủ ít nhất 6 cột cơ bản (bao gồm cả type)
                if (data.length >= 6) {
                    // Đọc các thuộc tính chung của lớp Phone
                    int id = Integer.parseInt(data[0]);
                    String phoneName = data[1];
                    double price = Double.parseDouble(data[2]);
                    int quantity = Integer.parseInt(data[3]);
                    String brand = data[4];
                    String type = data[5]; // Cột phân loại
                    // Xử lý tạo đối tượng dựa trên Type
                    if (type.equals("Genuine") && data.length >= 8) {
                        int warrantyPeriod = Integer.parseInt(data[6]);
                        String warrantyScope = data[7];
                        list.add(new GenuinePhone(id, phoneName, price, quantity, brand, warrantyPeriod, warrantyScope));
                    } else if (type.equals("Portable") && data.length >= 8) {
                        String portableCountry = data[6];
                        String status = data[7];
                        list.add(new PortablePhone(id, phoneName, price, quantity, brand, portableCountry, status));
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("Không tìm thấy file: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Lỗi đọc file CSV: " + e.getMessage());
        }
        return list;
    }

    /**
     * Hàm ghi danh sách điện thoại ra file CSV
     *
     * !! @param append true nếu muốn ghi nối thêm vào cuối file, false nếu muốn ghi đè từ đầu
     */
    private void writeToFile(List<Phone> list, boolean append) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, append))) {
            // Nếu ghi đè (không phải append), cần ghi lại Header
            if (!append) {
                bw.write(HEADER + "\n");
            }
            for (Phone p : list) {
                StringBuilder line = new StringBuilder();
                // 1. Ghi các thuộc tính chung
                line.append(p.getId()).append(COMMA_DELIMITER)
                        .append(p.getPhoneName()).append(COMMA_DELIMITER)
                        .append(p.getPrice()).append(COMMA_DELIMITER)
                        .append(p.getQuantity()).append(COMMA_DELIMITER)
                        .append(p.getBrand()).append(COMMA_DELIMITER);
                // 2. Ép kiểu (Downcasting) để lấy và ghi các thuộc tính riêng của lớp con
                if (p instanceof GenuinePhone) {
                    GenuinePhone gp = (GenuinePhone) p;
                    line.append("Genuine").append(COMMA_DELIMITER)           // type
                            .append(gp.getWarrantyPeriod()).append(COMMA_DELIMITER) // extra1
                            .append(gp.getWarrantyScope());                         // extra2
                } else if (p instanceof PortablePhone) {
                    PortablePhone pp = (PortablePhone) p;
                    line.append("Portable").append(COMMA_DELIMITER)          // type
                            .append(pp.getPortableCountry()).append(COMMA_DELIMITER) // extra1
                            .append(pp.getStatus());                                 // extra2
                }
                bw.write(line.toString() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Lỗi ghi file CSV: " + e.getMessage());
        }
    }

    @Override
    public void save(Phone phone) {
        // CẬP NHẬT: Tự động tăng ID
        List<Phone> currentList = readFromFile();
        int newId = 1; // Mặc định ID = 1
        if (!currentList.isEmpty()) {
            // Tìm ID lớn nhất trong danh sách hiện tại và cộng thêm 1
            newId = currentList.stream()
                    .mapToInt(Phone::getId)
                    .max()
                    .orElse(0) + 1;
        }
        // Cập nhật ID mới cho đối tượng phone trước khi lưu
        phone.setId(newId);
        List<Phone> singleList = new ArrayList<>();
        singleList.add(phone);
        writeToFile(singleList, true);
    }

    @Override
    public List<Phone> findAll() {
        return readFromFile();
    }

    @Override
    public boolean deleteById(int id) {
        List<Phone> list = readFromFile();
        // Xóa phần tử có ID tương ứng ra khỏi danh sách tạm trên RAM
        boolean isRemoved = list.removeIf(p -> p.getId() == id);
        if (isRemoved) {
            // Ghi đè lại toàn bộ file (append = false) để cập nhật dữ liệu mới
            writeToFile(list, false);
        }
        return isRemoved;
    }

    @Override
    public List<Phone> search(String keyword) {
        // Lấy toàn bộ danh sách lên, sử dụng Java Stream để lọc (tìm theo tên hoặc hãng)
        return readFromFile().stream()
                .filter(p -> p.getPhoneName().toLowerCase().contains(keyword.toLowerCase()) ||
                        p.getBrand().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }
}
