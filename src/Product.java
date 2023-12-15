import javax.swing.*;
import java.io.*;
import java.util.*;
import java.util.regex.Pattern;

public class Product implements IProduct, Serializable {
    //private static final long serialVersionUID = -4183872234870655245L;
    private String id;
    private String name;
    private double improtPrice;
    private double exportPrice;
    private double profit;
    private String description;
    private boolean status;
    private int categoryId;

    public Product() {
    }

    public Product(String id, String name, double improtPrice, double exportPrice,
                   double profit, String description, boolean status, int categoryId) {
        this.id = id;
        this.name = name;
        this.improtPrice = improtPrice;
        this.exportPrice = exportPrice;
        this.profit = profit;
        this.description = description;
        this.status = status;
        this.categoryId = categoryId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getImprotPrice() {
        return improtPrice;
    }

    public void setImprotPrice(double improtPrice) {
        this.improtPrice = improtPrice;
    }

    public double getExportPrice() {
        return exportPrice;
    }

    public void setExportPrice(double exportPrice) {
        this.exportPrice = exportPrice;
    }

    public double getProfit() {
        return profit;
    }

    public void setProfit(double profit) {
        this.profit = profit;
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

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", Tên sản phẩm='" + name + '\'' +
                ", Mua vào =" + improtPrice +
                ", Bán ra=" + exportPrice +
                ", Lợi nhuận=" + profit +
                ", Mô tả ='" + description + '\'' +
                ", Trạng thái=" + status +
                ", Mã danh mục=" + categoryId +
                '}';
    }

    public String chackID(Scanner scanner) {
        Store.productList= Product.readDataFormFilee();
            System.out.println("Nhập vào mã sản phẩm: ");
            String id = scanner.nextLine();
            String regax = "P[a-zA-Z0-9]{3}";
            boolean result = Pattern.matches(regax, id);
            if (result == true) {
                // Kiểm tra xem mã sản phẩm đã tồn tại chưa
                for (int i = 0; i < Store.productList.size(); i++) {
                    if (Store.productList.get(i).getId().equals(id)) {
                        System.err.println("Mã đã tồn tại");
                       return null;
                    }
                }

            } else {
                System.out.println("Nhập đúng định dang bắt đầu bằng P gồm 4 kí tự");
            }
            return id;
    }

    public void sapXepTen(List<Product> productList) {
        Store.productList = Product.readDataFormFilee();
        Collections.sort(Store.productList, new Comparator<Product>() {
            @Override
            public int compare(Product o1, Product o2) {
                return o1.getName().compareTo(o2.getName());
            }
        });
    }

    public String checkName(Scanner scanner) {
        boolean checkout = false;// chưa tồn tại
        do {
            System.out.println("Nhập vào tên sản phẩm: ");
            String name = scanner.nextLine();
            if (name.trim().length() > 6 && name.trim().length() <= 30) {
                for (int i = 0; i < Store.productList.size(); i++) {
                    if (Store.productList.get(i).getName().equals(name)) {
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

    public double checkImportPrice(Scanner scanner) {
        do {
            System.out.println("Nhập vào giá tiền nhập :");
            this.improtPrice = Double.parseDouble(scanner.nextLine());
            if (this.improtPrice > 0) {
                return this.improtPrice;
            } else {
                System.out.println("Giá trị nhập vào nhỏ hơn 0 ");
            }
        } while (true);
    }

    public double checkExportPrice(Scanner scanner) {
        do {
            System.out.println("Nhập vào giá bán: ");
            this.exportPrice = Double.parseDouble(scanner.nextLine());
            if (this.exportPrice > this.improtPrice * MIN_INTEREST_RATE) {
                return this.exportPrice;
            } else {
                System.out.println("Giá trị bán nhỏ hơn giá trị nhập vào 0.2 lần ");
            }
        } while (true);
    }

    public String checkDescription(Scanner scanner) {
        do {
            System.out.println("Nhập vào mô tả sản phẩm: ");
            this.description = scanner.nextLine();
            if (this.description == "") {
                System.out.println("Khộng được bỏ trống");
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
                System.out.println("Chỉ nhận true hoặc false");
            }
        } while (true);
    }

    public int checkIdCatalogID(Scanner scanner, List<Category> categoryList) {
        do {
            System.out.println("Các danh mục:");
            for (Category category : categoryList) {
                System.out.printf("%d - %s\n", category.getId(), category.getName());
            }
            System.out.println("Nhập vào mã danh mục: ");
            try {
                this.categoryId = Integer.parseInt(scanner.nextLine());
            } catch (NumberFormatException e) {
                System.err.println("Mã danh mục không hợp lệ.");
                continue;
            }
            for (Category category : categoryList) {
                if (this.categoryId == category.getId()) {
                    System.out.println("Đã chọn danh mục: " + category.getName());
                    return this.categoryId;
                }
            }
            System.err.println("Không tìm thấy danh mục với mã: " + this.categoryId);
        } while (true);
    }
    public void searchProduct(Scanner scanner)
    {
        Store.productList=Product.readDataFormFilee();
        System.out.println("Nhập vào tên sản phẩm: ");
        try {
            String nameProduct=scanner.nextLine();
            for(int i=0;i<Store.productList.size();i++)
            {
                if(Store.productList.get(i).getName().equals(nameProduct))
                {
                    System.out.println("Trong file có sản phẩm này rồi");
                    return;
                }
            }
            System.out.println("Chưa có sản phẩm này");
            return;
        }catch (Exception e)
        {
            System.out.println("Lỗi "+e);
        }
    }
    public void update(Scanner scanner, List<Product> productList) {
        System.out.println("Nhập vào mã sản phẩm cần cập nhật: ");
        String id = scanner.nextLine();
        Store.productList = Product.readDataFormFilee();
        for (int i = 0; i < Store.productList.size(); i++) {
            if (Store.productList.get(i).getId().equals(id)) {
                boolean checkOut = true;
                do {
                    System.out.println("**** Cập nhật theo ********");
                    System.out.println("1. Tên sản phẩm");
                    System.out.println("2. Giá nhập vào: ");
                    System.out.println("3. Giá bán");
                    System.out.println("4. Mô tả sản phẩm");
                    System.out.println("5. Thoát cập nhât");
                    System.out.printf("Lựa chọn: \n");
                    int choice = Integer.parseInt(scanner.nextLine());
                    switch (choice) {
                        case 1:
                            System.out.println("Nhập vào tên sản phẩm mới");
                            String newName = scanner.nextLine();
                            Store.productList.get(i).setName(newName);
                            Product.writeDataToFile(Store.productList);
                            System.out.println("cập nhật thành công");
                            break;
                        case 2:
                            System.out.println("Nhập vào giá mới :  ");
                            double newImportPrice = Double.parseDouble(scanner.nextLine());
                            Store.productList.get(i).setImprotPrice(newImportPrice);
                            Product.writeDataToFile(Store.productList);
                            System.out.println("Cập nhật thành công");
                            break;
                        case 3:
                            System.out.println("Nhập vào giá bán mới :  ");
                            double newExportPrice = Double.parseDouble(scanner.nextLine());
                            Store.productList.get(i).setExportPrice(newExportPrice);
                            Product.writeDataToFile(Store.productList);
                            System.out.println("Cập nhật thành công");
                            break;
                        case 4:
                            System.out.println("Nhập vào mô tả danh mục: ");
                            String newDescription = scanner.nextLine();
                            Store.productList.get(i).setDescription(newDescription);
                            Product.writeDataToFile(Store.productList);
                            System.out.println("Cập nhật thành công");
                            break;
                        case 5:
                            checkOut = false;
                            break;
                    }
                } while (checkOut);
            } else {
                System.out.println("Không tìm thấy id này ");
                return;
            }
        }
    }

    //    public void sapXepLoiNhau()
//    {
//        Store.productList=Product.readDataFormFilee();
//        for(int i=0;i< Store.productList.size();i++)
//        {
//            for(int j=i+1;i< Store.productList.size();i++)
//            {
//                if(Store.productList.get(j).getProfit()<Store.productList.get(i).getProfit())
//                {
//                    Product temp = Store.productList.get(i);
//                    Store.productList.set(i, Store.productList.get(j));
//                    Store.productList.set(j, temp);
//
//                }
//            }
//        }
//    }
    public void deleteByID(Scanner scanner, List<Product> productList) {
        System.out.println("Nhập vào id cần xóa: ");
        try {
            String id = scanner.nextLine();
            Store.productList = Product.readDataFormFilee();
            for (int i = 0; i < Store.productList.size(); i++) {
                if (Store.productList.get(i).getId().equals(id)) {
                    Store.productList.remove(i);
                    Product.writeDataToFile(Store.productList);
                    System.out.println("Xóa thành công ");
                    return;
                }
            }
            System.out.println("Không tìm thấy mã cần xóa");
        } catch (Exception ex) {
            System.out.println("Có lỗi " + ex);
        }
    }

    @Override
    public void inputData() {
        Scanner scanner = new Scanner(System.in);
        this.id = chackID(scanner);
        this.name = checkName(scanner);
        this.improtPrice = checkImportPrice(scanner);
        this.exportPrice = checkExportPrice(scanner);
        this.description = checkDescription(scanner);
        this.profit = this.exportPrice - this.improtPrice;
        this.status = checkStatus(scanner);
        this.categoryId = checkIdCatalogID(scanner, Store.categoryList);
        Product product = new Product(this.id, this.name, this.improtPrice, this.exportPrice, this.profit, this.description,
                this.status, this.categoryId);
        Store.productList.add(product);
    }

    @Override
    public void displayData() {
        System.out.println("Mã sản phẩm: " + this.id);
        System.out.println("Tên sản phẩm: " + this.name);
        System.out.println("Giá nhập vào:  " + this.improtPrice);
        System.out.println("Giá bán ra: " + this.exportPrice);
        System.out.println("Lợi nhuận: " + this.profit);
        System.out.println("Mô tả sản phẩm: " + this.description);
        System.out.println("Mã danh mục: " + this.categoryId);
        System.out.println("Trạng thái: " + ((this.status == true) ? "Còn hàng" : "Hết hàng"));

    }

    @Override
    public void calProfit() {
    }

    public static void writeDataToFile(List<Product> productList) {
        // ghi dữ liệu ra file
        File file = new File("product.txt");
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = new FileOutputStream(file);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(productList);
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
    }

    // đọc dữ liệu từ file
    public static List<Product> readDataFormFilee() {
        List<Product> listProductRead = null;
        File file = new File("product.txt");
        if (!file.exists()) {
            System.err.println("File product.txt không tồn tại");
            return new ArrayList<>();
        }
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = new FileInputStream(file);
            ois = new ObjectInputStream(fis);
            listProductRead = (List<Product>) ois.readObject();
            return listProductRead;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
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
