import java.io.*;
import java.sql.SQLOutput;
import java.util.*;

public class Category implements ICategory, Serializable {
    private int id;
    private String name;
    private String description;
    private boolean status;

    public Category() {
    }

    public Category(int id, String name, String description, boolean status) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id=" + id +
                ", Tên danh mục='" + name + '\'' +
                ", Mô tả danh  mục='" + description + '\'' +
                ", Trạng thái=" + status +
                '}';
    }

    @Override
    public void inpoutData() {
        Scanner scanner = new Scanner(System.in);
        this.id = checkId(Store.categoryList);
        this.name = chackName(scanner);
        this.description = checkDescription(scanner);
        this.status = checkStatus(scanner);
    }

    public void seachCategories(Scanner scanner, List<Category> categoryList, List<Product> productList) {
        Store.categoryList = Category.readDataFormFile();
        System.out.println("Nhập vào tên danh cần tìm");
        try {
            String id = scanner.nextLine();
            for (int i = 0; i < Store.categoryList.size(); i++) {
                if (Store.categoryList.get(i).getName().equals(id)) {
                    System.out.println("Đã có danh mục này");
                    return;
                }
            }
            System.out.println("Không có danh mục này");
        } catch (Exception ex) {
            System.out.println(" có lỗi" + ex);
        }
    }

    public void deleteCategories(Scanner scanner, List<Category> categoryList, List<Product> productList) {
        System.out.println("Nhập vào id cần xóa: ");
        Store.productList = Product.readDataFormFilee();
        int deleteId = Integer.parseInt(scanner.nextLine());
        // Kiểm tra danh mục tồn tại
        if (!Store.categoryList.stream().anyMatch(category -> category.getId() == deleteId)) {
            System.out.println("Danh mục không tồn tại.");
            return;
        }
        // Xóa danh mục và cập nhật file
        Category categoryToRemove = Store.categoryList.stream()
                .filter(category -> category.getId() == deleteId)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Danh mục không tìm thấy."));
        for (int i = 0; i < productList.size(); i++) {
            if (productList.get(i).getCategoryId() == deleteId) {
                System.err.println("Đang có sản phẩm không thể xóa");
                return;
            }
        }
        Store.categoryList.remove(categoryToRemove);
        Category.writeDataToFile(Store.categoryList);
        // Hiển thị thông báo
        System.out.println("Đã xóa danh mục thành công.");
    }

    public void update(Scanner scanner, List<Category> categoryList) {
        Store.categoryList = Category.readDataFormFile();
        System.out.println("Nhập vào id cần cập nhật: ");
        int id = Integer.parseInt(scanner.nextLine());
        for (int i = 0; i <= Store.categoryList.size(); i++) {
            if (Store.categoryList.get(i).getId() == id) {
                boolean checkOut = true;
                do {
                    System.out.println("Các thông tin cập nhật ");
                    System.out.println("1. Tên danh mục");
                    System.out.println("2. Mô tả danh mục");
                    System.out.println("3. Thoát cập nhật");
                    System.out.printf("Lựa chọn của bạn : ");
                    int choice = Integer.parseInt(scanner.nextLine());
                    switch (choice) {
                        case 1:
                            System.out.println("Nhập vào tên danh mục mới: ");
                            String newName = scanner.nextLine();
                            Store.categoryList.get(i).setName(newName);
                            Category.writeDataToFile(Store.categoryList);
                            System.out.println("Cập nhật thành công");
                            break;
                        case 2:
                            System.out.println("Nhập vào mô tả danh mục mới: ");
                            String newDescription = scanner.nextLine();
                            Store.categoryList.get(i).setName(newDescription);
                            Category.writeDataToFile(Store.categoryList);
                            break;
                        case 3:
                            checkOut = false;
                            break;
                    }
                } while (checkOut);
            } else {
                System.out.println("Không tìm thấy ID cần cập nhật");
                return;
            }
        }
    }

    public int checkId(List<Category> categoryList) {
        if (categoryList.isEmpty()) {
            return 1;
        } else {
            int max = categoryList.get(0).getId();
            for (int i = 1; i < categoryList.size(); i++) {
                if (categoryList.get(i).getId() > max) {
                    max = categoryList.get(i).getId();
                }
            }
            return max + 1;
        }
    }

    public void thongKe() {
        Map<Integer, Integer> categoryCounts = new HashMap<>();
        for (Category category : Store.categoryList) {
            int categoryId = category.getId();
            for (Product product : Store.productList) {
                if (product.getCategoryId() == categoryId) {
                    categoryCounts.put(categoryId, categoryCounts.getOrDefault(categoryId, 0) + 1);
                    break;
                }
            }
        }

        for (Map.Entry<Integer, Integer> entry : categoryCounts.entrySet()) {
            System.out.println("Mã danh mục: " + entry.getKey() + ", Số lượng sản phẩm: " + entry.getValue());
        }
    }

    public String chackName(Scanner scanner) {
        boolean checkout = false;// chưa tồn tại
        do {
            System.out.println("Nhập vào tên loại: ");
            String name = scanner.nextLine();
            if (name.trim().length() > 6 && name.trim().length() <= 30) {
                for (int i = 0; i < Store.categoryList.size(); i++) {
                    if (Store.categoryList.get(i).getName().equals(name)) {
                        checkout = true;// đã tồn tại
                        break;
                    }
                }
                if (checkout == true) {
                    System.err.println("Đã tồn tại tên này. Nhập lại");
                } else {
                    return name;
                }
            } else {
                System.err.println("Tên danh mục phải từ 6 -30 ký tự");
            }
        } while (checkout);
        return null;
    }


    public String checkDescription(Scanner scanner) {
        boolean checkOut = false;
        do {
            System.out.println("Nhập vào mô tả danh mục:");
            this.description = scanner.nextLine();
            if (this.description == "") {
                System.err.println("Không được bỏ trông");
            } else {
                return this.description;
            }
        } while (true);
    }

    public boolean checkStatus(Scanner scanner) {
        do {
            System.out.println("Nhập vào trạng thái: ");
            String status = scanner.nextLine();
            if (status.equals("true") || status.equals("false")) {
                return this.status = Boolean.parseBoolean(status);
            } else {
                System.err.println("Chỉ nhận giá trị true hoặc false");
            }
        } while (true);
    }

    @Override
    public void displayData() {
        System.out.println("Mã danh mục: " + this.id);
        System.out.println("Tên danh mục: " + this.name);
        System.out.println("Mô tả: " + this.description);
        System.out.println("Trạng thái: " + ((this.status == true) ? "Hoạt động" : "Không hoạt động"));
    }

    public static List<Category> writeDataToFile(List<Category> categoryList) {
        // đọc dữ liệu ra file
        File file = new File("categories.txt");
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(categoryList);
            oos.flush();
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (oos != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return categoryList;
    }

    // đọc dữ liệu từ file ra
    public static List<Category> readDataFormFile() {
        List<Category> categoryListRead = null;
        File file = new File("categories.txt");
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            categoryListRead = (List<Category>) ois.readObject();
            return categoryListRead;
        } catch (FileNotFoundException e) {
            return new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            if (ois != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return null;
    }
}
