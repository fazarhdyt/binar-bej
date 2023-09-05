package main.java.repository;

import main.java.model.Product;

import java.util.Arrays;
import java.util.List;

public class ProductRepository {

    /**
     * method yang digunakan untuk mengambil semua data makanan dan minuman yang ada di repository
     *
     * @return
     */
    public static List<Product> getProducts() {
        List<Product> listProducts;

        Product food1 = new Product(1, "Mie Goreng", 7000);
        Product food2 = new Product(2, "Mie + Telur", 10000);
        Product food3 = new Product(3, "Roti Bakar", 10000);
        Product food4 = new Product(4, "Kue Pancong", 12000);
        Product beverage1 = new Product(5, "Teh Manis", 3000);
        Product beverage2 = new Product(6, "Es Jeruk", 5000);
        Product beverage3 = new Product(7, "Kubim Susu", 8000);
        Product beverage4 = new Product(8, "Kopi Hitam", 4000);

        listProducts = Arrays.asList(food1, food2, food3, food4, beverage1, beverage2, beverage3, beverage4);
        return listProducts;
    }

}
