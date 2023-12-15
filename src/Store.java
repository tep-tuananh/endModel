import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Store {
    public static List<Category> categoryList = new ArrayList<>();
    public static List<Product> productList = new ArrayList<>();

    public static void main(String[] args) {
        boolean checkout = true;
        do {
            Scanner scanner = new Scanner(System.in);
            categoryList = Category.readDataFormFile();
            productList = Product.readDataFormFilee();
            System.out.println("**********Quản Lý Kho*********");
            System.out.println("1. Quản lý danh mục");
            System.out.println("2. Quản lý sản phẩm");
            System.out.println("3. Thoát");
            System.out.println("Lựa chọn của bạn: ");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    Store.MenuQLDM(scanner);
                    break;
                case 2:
                    Store.MenuProduct();
                    break;
                case 3:
                    checkout = false;
                    System.exit(0);
            }
        } while (checkout);
    }

    public static void MenuQLDM(Scanner scanner) {
        Category category = new Category();
        Store.categoryList = Category.writeDataToFile(Store.categoryList);
        boolean checkOut = true;
        do {
            System.out.println("--------Quản lý danh mục-----------");
            System.out.println("1. Thêm danh mục");
            System.out.println("2. Cập nhật danh mục");
            System.out.println("3. Xóa danh mục");
            System.out.println("4. Tìm kiếm danh mục theo tên danh mục file");
            System.out.println("5. Thống kê số lượng sản phẩm  đang có trong danh mục(Chưa làm đc)");
            System.out.println("8. Hiển thị danh mục lưu vào trong file");
            System.out.println("6. Ghi vào file");
            System.out.println("0. Quay lại");
            System.out.printf("Lựa chọn của ban: ");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    System.out.println("Nhập vào số danh mục cần thêm: ");
                    int n = Integer.parseInt(scanner.nextLine());
                    for (int i = 0; i < n; i++) {
                        category.inpoutData();
                        categoryList.add(category);
                    }
                    break;
                case 2:
                    category.update(scanner, categoryList);
                    System.out.println("Hoàn thành");
                    break;
                case 3:
                    category.deleteCategories(scanner, categoryList, productList);
                    System.out.println("hoàn thành");
                    break;
                case 4:
                    category.seachCategories(scanner, categoryList, productList);
                    System.out.println("Hoàn thành");
                    break;
                case 5:
                    category.thongKe();// chưa hoàn thành;
                    System.out.println("Hoàn thành");
                    break;
                case 6:
                    System.out.println("Đã dừng chương trình");
                    Category.writeDataToFile(categoryList);
                    break;
//                case 7:
//                    // đọc ra với list
//                    for (int i = 0; i < categoryList.size(); i++) {
//                        Category category1 = categoryList.get(i);
//                        category1.displayData();
//                    }
                //    break;
                case 8:// hiển thị thông tin trong file
                    List<Category> categoriesRead = Category.readDataFormFile();
                    if (categoriesRead == null) {
                        System.err.println("lôi đọc file");
                    } else {
                        categoriesRead.forEach(category1 -> System.out.println(category1));
                    }
                    break;
                case 0:
                    checkOut = false;
                    break;
            }
        } while (checkOut);
    }

    public static void MenuProduct() {
        Scanner scanner = new Scanner(System.in);
        Product product = new Product();
        boolean checkOut = true;
        do {
            System.out.println("===== QUẢN LÝ SẢN PHẨM =====");
            System.out.println("1. Thêm mới sản phẩm");
            System.out.println("2. Cập nhật sản phẩm trong file");
            System.out.println("3. Xóa sản phẩm trong file");
            System.out.println("4. Hiển thị sản phẩm theo tên A-Z");
            System.out.println("5. Hiển thị sản phẩm theo lợi nhuận từ cao-thấp (chưa làm được)");
            System.out.println("6. Tìm kiếm sản phẩm");
            System.out.println("7. Quay lại");
            System.out.println("9. hiện thị dữ liệu trong file");
            System.out.println("0. Để lưu thông tin vào file");
            System.out.printf("Lựa chọn của bạn: \n");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    System.out.println("Nhập số phân tử thêm: ");
                    try {
                        int m = Integer.parseInt(scanner.nextLine());
                        for (int i = 0; i < m; i++) {
                            product.inputData();
                        }
                        System.out.println("Hoàn thành");
                    } catch (Exception e) {
                        System.err.println("Có lỗi: " + e);
                    }

                    break;
                case 2:
                    product.update(scanner, productList);
                    System.out.println("Hoaàn thành");
                    break;
                case 3:
                    product.deleteByID(scanner, productList);
                    System.out.println("Thành công");
                    break;
                case 4:
                    product.sapXepTen(productList);
                    Product.writeDataToFile(Store.productList);
                    System.out.println("Hoàn thành");
                    break;
                case 5:
                   //  sắp xếp xong
                    //product.sapXepLoiNhau();
                    // ghi lại file
                    Product.writeDataToFile(Store.productList);

                    break;
                case 6:
                    product.searchProduct(scanner);
                    System.out.println("Hoàn thành");
                    break;
                case 7://đọc vào file
                    checkOut = false;
                    break;
                case 8:
                    // hiển thị là list
                    for (int i = 1; i < productList.size(); i++) {
                        Product product1 = productList.get(i);
                        product1.displayData();
                    }
                    System.out.println("Hoàn thành");
                    break;
                case 9://  lấy dữ liệu trong file ra
                    List<Product> productRead = Product.readDataFormFilee();
                    if (productRead == null) {
                        System.err.println("Lỗi đọc file");
                    } else if (productRead.isEmpty()) {
                        System.err.println("File đang trống");
                        return;
                    }
                    productRead.forEach(product1 -> System.out.println(product1));
                    break;
                case 0:
                    Product.writeDataToFile(productList);
                    System.out.println("Đã thêm vào file");
                    break;
            }
        } while (checkOut);
    }

}
