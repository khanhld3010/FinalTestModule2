package util;

import java.util.Scanner;

public class Validation {
    public static String getValidString(String prompt, Scanner scanner) {
        while (true) {
            System.out.print(prompt);
            String input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            }
            System.out.println("Lỗi: Trường này là bắt buộc, không được để trống!");
        }
    }

    /**
     * Kiểm tra số thực dương (phù hợp cho giá tiền).
     */
    public static double getValidPositiveDouble(String prompt, Scanner scanner) {
        while (true) {
            System.out.print(prompt);
            try {
                double value = Double.parseDouble(scanner.nextLine().trim());
                if (value > 0) {
                    return value;
                }
                System.out.println("Lỗi: Giá trị phải lớn hơn 0!");
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Vui lòng nhập một số hợp lệ!");
            }
        }
    }

    /**
     * Kiểm tra số nguyên dương (phù hợp cho số lượng, ID).
     */
    public static int getValidPositiveInt(String prompt, Scanner scanner) {
        while (true) {
            System.out.print(prompt);
            try {
                int value = Integer.parseInt(scanner.nextLine().trim());
                if (value > 0) {
                    return value;
                }
                System.out.println("Lỗi: Giá trị phải là số nguyên lớn hơn 0!");
            } catch (NumberFormatException e) {
                System.out.println("Lỗi: Vui lòng nhập một số nguyên hợp lệ!");
            }
        }
    }
    // --- CÁC HÀM VALIDATION NGHIỆP VỤ RIÊNG BIỆT ---

    /**
     * Kiểm tra thời gian bảo hành (số dương và <= maxDays)
     */
    public static int getValidWarrantyPeriod(String prompt, Scanner scanner, int maxDays) {
        while (true) {
            int value = getValidPositiveInt(prompt, scanner);
            if (value <= maxDays) {
                return value;
            }
            System.out.println("Lỗi: Thời gian bảo hành không được vượt quá " + maxDays + " ngày!");
        }
    }

    /**
     * Kiểm tra phạm vi bảo hành ("Toan Quoc" hoặc "Quoc Te")
     */
    public static String getValidWarrantyScope(String prompt, Scanner scanner) {
        while (true) {
            String scope = getValidString(prompt, scanner);
            if (scope.equals("Toan Quoc") || scope.equals("Quoc Te")) {
                return scope;
            }
            System.out.println("Lỗi: Phạm vi bảo hành chỉ được nhập 'Toan Quoc' hoặc 'Quoc Te'!");
        }
    }

    /**
     * Kiểm tra quốc gia xách tay (Không được là "Viet Nam")
     */
    public static String getValidPortableCountry(String prompt, Scanner scanner) {
        while (true) {
            String country = getValidString(prompt, scanner);
            if (!country.equalsIgnoreCase("Viet Nam")) {
                return country;
            }
            System.out.println("Lỗi: Quốc gia xách tay không được là 'Viet Nam'!");
        }
    }

    /**
     * Kiểm tra trạng thái ("Da sua chua" hoặc "Chua sua chua")
     */
    public static String getValidPhoneStatus(String prompt, Scanner scanner) {
        while (true) {
            String status = getValidString(prompt, scanner);
            if (status.equals("Da sua chua") || status.equals("Chua sua chua")) {
                return status;
            }
            System.out.println("Lỗi: Trạng thái chỉ được nhập 'Da sua chua' hoặc 'Chua sua chua'!");
        }
    }
}
