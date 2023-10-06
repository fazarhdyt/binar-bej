//package com.binar.binarfud.controller;
//
//import com.binar.binarfud.model.*;
//import com.binar.binarfud.service.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.stereotype.Component;
//
//import javax.annotation.PostConstruct;
//import java.text.ParseException;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.List;
//import java.util.Scanner;
//import java.util.stream.Collectors;
//
//@Component
//public class MenuController {
//
//    @Autowired
//    private IUserService userService;
//
//    @Autowired
//    private IMerchantService merchantService;
//
//    @Autowired
//    private IProductService productService;
//
//    @Autowired
//    private IOrderService orderService;
//
//    @Autowired
//    private IOrderDetailService orderDetailService;
//
//    @PostConstruct
//    public void init() throws ParseException {
//        this.mainMenu();
//    }
//
//    private Scanner scanner = new Scanner(System.in);
//
//    public void mainMenu() throws ParseException {
//        System.out.println("Welcome to Binarfud!!\n" +
//                "Silahkan pilih menu\n" +
//                "1. Get all user\n" +
//                "2. Create user\n" +
//                "3. Get all merchant\n" +
//                "4. Create merchant\n" +
//                "5. Edit merchant\n" +
//                "6. Get all product\n" +
//                "7. Create product\n" +
//                "8. Edit product\n" +
//                "9. Delete product\n" +
//                "10. Show Open Merchants\n" +
//                "11. Show Product with pagination\n" +
//                "12. Order products\n" +
//                "13. Show order\n" +
//                "14. delete user\n" +
//                "15. update user\n" +
//                "0. Keluar");
//        System.out.print("=> ");
//        int pilihan = scanner.nextInt();
//        scanner.nextLine();
//        switch(pilihan) {
//            case 1:
//                this.showUsers();
//                break;
//            case 2:
//                this.createUser();
//                break;
//            case 3:
//                this.showMerchants();
//                break;
//            case 4:
//                this.createMerchant();
//                break;
//            case 5:
//                this.editMerchant();
//                break;
//            case 6:
//                this.showProducts();
//                break;
//            case 7:
//                this.addProduct();
//                break;
//            case 8:
//                this.editProduct();
//                break;
//            case 9:
//                this.deleteProduct();
//                break;
//            case 10:
//                this.showOpenMerchants();
//                break;
//            case 11:
//                this.showProductsWithPagination();
//                break;
//            case 12:
//                this.orderProduct();
//                break;
//            case 13:
//                this.showOrders();
//                break;
//            case 14:
//                this.deleteUser();
//                break;
//            case 15:
//                this.updateUser();
//                break;
//            case 0:
//                System.exit(0);
//            default:
//                System.out.println("Pilihan dengan benar coy\n!!!!!!!!!!!");
//                this.mainMenu();
//        }
//    }
//
//    public void showUsers() throws ParseException {
//        System.out.println("Berikut adalah daftar user saat ini");
//        System.out.println("Username \t | \t Email");
//        List<User> users = userService.getUsers();
//        users.forEach(user -> {
//            System.out.println(user.getUsername() + " \t | \t " + user.getEmail());
//        });
//        this.mainMenu();
//    }
//
//    public void createUser() throws ParseException {
//        System.out.print("Username : ");
//        String username = scanner.nextLine();
//        System.out.print("Email : ");
//        String email = scanner.nextLine();
//        System.out.print("Password : ");
//        String password = scanner.nextLine();
//
//        User user = User.builder()
//                .username(username)
//                .email(email)
//                .password(password)
//                .build();
//        userService.createUser(user);
//        System.out.println("\nberhasil mendaftar akun!");
//        this.mainMenu();
//    }
//
//    public void deleteUser() throws ParseException {
//        System.out.print("username : ");
//        String username = scanner.nextLine();
//
//        userService.deleteUserByUsername(username);
//        System.out.println("\nberhasil menghapus user!");
//        this.mainMenu();
//    }
//
//    public void updateUser() throws ParseException {
//        System.out.print("Username : ");
//        String username = scanner.nextLine();
//        System.out.print("Email : ");
//        String email = scanner.nextLine();
//        System.out.print("Password : ");
//        String password = scanner.nextLine();
//
//        User user = User.builder()
//                .username(username)
//                .email(email)
//                .password(password)
//                .build();
//        userService.updateUserByUsername(username, user);
//        System.out.println("\nberhasil mengedit merchant!");
//        this.mainMenu();
//    }
//
//    public void showMerchants() throws ParseException {
//        System.out.println("Berikut adalah daftar merchant saat ini");
//        System.out.println("Merchant Code \t | \t Merchant Name \t | \t Merchant Location \t | \t is Open");
//        List<Merchant> merchants = merchantService.getMerchants();
//        merchants.forEach(merchant -> {
//            System.out.println(merchant.getMerchantCode() + " \t | \t " + merchant.getMerchantName() +
//                    " \t | \t " + merchant.getMerchantLocation() + " \t | \t " + merchant.isOpen());
//        });
//        System.out.print("Pilih merchant untuk melihat daftar produk => ");
//        int pilihan = scanner.nextInt();
//        scanner.nextLine();
//        Merchant selectedMerchant = merchants.get(pilihan-1);
//        this.showProductsOnMerchant(selectedMerchant);
//    }
//
//    public void showProductsOnMerchant(Merchant merchant) throws ParseException {
//        System.out.println("Berikut adalah daftar produk pada merchant ini");
//        System.out.println("Product Code \t | \t Product Name \t | \t Price \t | \t Merchant Code");
//        merchant.getProducts().forEach(order -> {
//            System.out.println(order.getProductCode() + " \t | \t " + order.getProductName() +
//                    " \t | \t " + order.getPrice() + " \t | \t " + order.getMerchant().getMerchantCode());
//        });
//
//        this.mainMenu();
//    }
//
//    public void showOpenMerchants() throws ParseException {
//        System.out.println("Berikut adalah daftar merchant yang buka saat ini");
//        System.out.println("Merchant Code \t | \t Merchant Name \t | \t Merchant Location \t | \t is Open");
//        List<Merchant> merchants = merchantService.getMerchants();
//        List<Merchant> isOpenMerchant = merchants.stream()
//                .filter(merchant -> merchant.isOpen() == true)
//                .collect(Collectors.toList());
//        if(isOpenMerchant.isEmpty()) {
//            throw new RuntimeException("is empty");
//        }
//        isOpenMerchant.forEach(merchant -> {
//            System.out.println(merchant.getMerchantCode() + " \t | \t " + merchant.getMerchantName() +
//                    " \t | \t " + merchant.getMerchantLocation() + " \t | \t " + merchant.isOpen());
//        });
//        this.mainMenu();
//    }
//
//    public void createMerchant() throws ParseException {
//        System.out.print("Merchant Code : ");
//        String merchantCode = scanner.nextLine();
//        System.out.print("Merchant Name : ");
//        String merchantName = scanner.nextLine();
//        System.out.print("Merchant Location : ");
//        String merchantLocation = scanner.nextLine();
//        System.out.print("Merchant is open : ");
//        boolean isOpen = scanner.nextBoolean();
//
//        Merchant merchant = Merchant.builder()
//                .merchantCode(merchantCode)
//                .merchantName(merchantName)
//                .merchantLocation(merchantLocation)
//                .open(isOpen)
//                .build();
//        merchantService.createMerchant(merchant);
//        System.out.println("\nberhasil mendaftar merchant!");
//        this.mainMenu();
//    }
//
//    public void editMerchant() throws ParseException {
//        System.out.print("Merchant Code : ");
//        String merchantCode = scanner.nextLine();
//        System.out.print("Merchant Name : ");
//        String merchantName = scanner.nextLine();
//        System.out.print("Merchant Location : ");
//        String merchantLocation = scanner.nextLine();
//        System.out.print("Merchant is open : ");
//        boolean isOpen = scanner.nextBoolean();
//
//        Merchant merchant = Merchant.builder()
//                .merchantName(merchantName)
//                .merchantLocation(merchantLocation)
//                .open(isOpen)
//                .build();
//        merchantService.updateMerchantByMerchantCode(merchantCode, merchant);
//        System.out.println("\nberhasil mengedit merchant!");
//        this.mainMenu();
//    }
//
//    public void showProducts() throws ParseException {
//        System.out.println("Berikut adalah daftar produk saat ini");
//        System.out.println("Product Code \t | \t Product Name \t | \t Price \t | \t Merchant Code");
//        List<Product> orders = productService.getProducts();
//        orders.forEach(order -> {
//            System.out.println(order.getProductCode() + " \t | \t " + order.getProductName() +
//                    " \t | \t " + order.getPrice() + " \t | \t " + order.getMerchant().getMerchantCode());
//        });
//        this.mainMenu();
//    }
//
//    public void showProductsWithPagination() throws ParseException{
//        System.out.print("Masukkan page: ");
//        int page = scanner.nextInt();
//        System.out.print("Masukkan size: ");
////        int size = scanner.nextInt();
////        Pageable pageable = PageRequest.of(page, size);
//        Page<Product> orders = productService.getProductsWithPagination(page);
//
//        orders.forEach(order -> {
//            System.out.println(order.getProductCode() + " \t | \t " + order.getProductName() +
//                    " \t | \t " + order.getPrice() + " \t | \t " + order.getMerchant().getMerchantCode());
//        });
//        this.mainMenu();
//    }
//
//    public void addProduct() throws ParseException {
//        System.out.print("Product Code : ");
//        String productCode = scanner.nextLine();
//        System.out.print("Product Name : ");
//        String productName = scanner.nextLine();
//        System.out.print("Price : ");
//        double price = scanner.nextDouble();
//        scanner.nextLine();
//        System.out.print("Merchant Code : ");
//        String merchantCode = scanner.nextLine();
//
//        Product order = Product.builder()
//                .productCode(productCode)
//                .productName(productName)
//                .price(price)
//                .build();
//        productService.createProduct(order, merchantCode);
//        System.out.println("\nberhasil menambah produk!");
//        this.mainMenu();
//    }
//
//    public void editProduct() throws ParseException {
//        System.out.print("Product Code : ");
//        String productCode = scanner.nextLine();
//        System.out.print("Product Name : ");
//        String productName = scanner.nextLine();
//        System.out.print("Price : ");
//        double price = scanner.nextDouble();
//        scanner.nextDouble();
//
//        Product order = Product.builder()
//                .productCode(productCode)
//                .productName(productName)
//                .price(price)
//                .build();
//        productService.updateProductByProductCode(productCode, order);
//        System.out.println("\nberhasil mengedit produk!");
//        this.mainMenu();
//    }
//
//    public void deleteProduct() throws ParseException {
//        System.out.print("Product Code : ");
//        String productCode = scanner.nextLine();
//
//        productService.deleteProductByProductCode(productCode);
//        System.out.println("\nberhasil menghapus produk!");
//        this.mainMenu();
//    }
//
//    public void orderProduct() throws ParseException{
//        System.out.print("Destination address : ");
//        String destinationAddress = scanner.nextLine();
//        System.out.print("username : ");
//        String username = scanner.nextLine();
//        System.out.print("productCode : ");
//        String productCode = scanner.nextLine();
//        System.out.print("Quantity : ");
//        int qty = scanner.nextInt();
//        Date orderTime = new Date();
//
//        OrderDetail orderDetail = OrderDetail.builder()
//                .product(productService.getProductByProductCode(productCode))
//                .quantity(qty)
//                .build();
//
//        Order oder = Order.builder()
//                .user(userService.getUserByUsername(username))
//                .orderTime(orderTime)
//                .destinationAddress(destinationAddress)
//                .completed(false)
//                .orderDetails(Arrays.asList(orderDetail))
//                .build();
//
//        orderService.orderProducts(oder);
//
//        this.mainMenu();
//    }
//
//    public void showOrders() throws ParseException {
//        System.out.println("Berikut adalah daftar order saat ini");
//        System.out.println("Order Id \t | \t Product Name \t | \t Quantity \t | \t Total Price" +
//                "\t | \t Destination Address");
//        List<OrderDetail> orders = orderDetailService.getOrderDetail();
//        orders.forEach(order -> {
//            System.out.println(order.getId() + " \t | \t " + order.getProduct().getProductName() +
//                    " \t | \t " + order.getTotalPrice() + " \t | \t " + order.getOrder().getDestinationAddress());
//        });
//        this.mainMenu();
//    }
//
//}
