package main.java;

import main.java.model.CartItem;
import main.java.model.Product;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.Locale;

public class Menu {
    /**
     * method ini digunakan untuk mencetak tampilan dari sebuah menu
     * @param products
     * @param title
     */
    public static void printMenu(Product[] products, String title) {
        Locale localeId = new Locale("in", "ID");
        NumberFormat rupiah = NumberFormat.getCurrencyInstance(localeId);
        String spacing = "| %-4s | %-24s | %-14s |%n";
        String border = "+--------------------------------------------------+%n";
        System.out.printf(border);
        System.out.printf("| %-44s |%n", "\t\t" +title);
        System.out.printf(border);
        System.out.printf(spacing, "No", "Makanan/Minuman", "Harga");
        System.out.printf(border);
        for (Product product : products) {
            if(product == null){
                break;
            }
                System.out.printf(spacing, product.getId(), product.getProductName(), rupiah.format(product.getPrice()));
        }
        System.out.printf(border);
        System.out.printf("| %-4s | %-41s |%n", "99", "Pesan dan Bayar");
        System.out.printf("| %-4s | %-41s |%n", "0", "Keluar aplikasi");
        System.out.printf(border);
    }

    /**
     * method ini digunakan untuk mencetak tampilan dari menu konfirmasi dan pesan
     * @param product
     */
    public static void printConfirmOrder(Product product) {
        Locale localeId = new Locale("in", "ID");
        NumberFormat rupiah = NumberFormat.getCurrencyInstance(localeId);
        String border = "+------------------------------------+%n";
        System.out.printf(border);
        System.out.printf("| %-33s |%n", "\t" + "Berapa pesanan anda");
        System.out.printf(border);
        System.out.printf("| %-20s | %-11s |%n", product.getProductName(), rupiah.format(product.getPrice()));
        System.out.printf("| %-34s |%n","input 0 untuk kembali");
        System.out.printf(border);
    }

    /**
     * method ini digunakan untuk mencetak tampilan dari menu konfirmasi dan bayar
     * @param cartItems
     */
    public static void printConfirmAndPayment(CartItem[] cartItems) {
        Locale localeId = new Locale("in", "ID");
        NumberFormat rupiah = NumberFormat.getCurrencyInstance(localeId);
        int totalQty=0;
        double grandPrice=0;
        String border = "+----------------------------------------+%n";
        System.out.printf(border);
        System.out.printf("| %-37s |%n", "\t" + "Konfirmasi dan Pembayaran");
        System.out.printf(border);
        for (CartItem item: cartItems) {
            if(item == null){
                break;
            }
            System.out.printf("| %-16s | %-2s | %-14s |%n", item.getProduct().getProductName(), item.getQty(), rupiah.format(item.getTotalPrice()));
            totalQty += item.getQty();
            grandPrice += item.getTotalPrice();
        }
        System.out.printf(border);
        System.out.printf("| %-16s | %-2s | %-14s |%n","Total", totalQty, rupiah.format(grandPrice));
        System.out.printf(border);
        System.out.printf("| %-4s | %-31s |%n","1", "Konfirmasi dan Bayar");
        System.out.printf("| %-4s | %-31s |%n","2", "Kembali ke menu utama");
        System.out.printf("| %-4s | %-31s |%n","0", "Keluar aplikasi");
        System.out.printf(border);
    }

    /**
     * method ini digunakan untuk men-generate struk dari pesanan yang telah dibayar dalam bentuk .txt
     * @param cartItems
     * @param title
     */
    public static void generateReceipt(CartItem[] cartItems, String title){
        Locale localeId = new Locale("in", "ID");
        NumberFormat rupiah = NumberFormat.getCurrencyInstance(localeId);
        String spacing = "| %-24s | %-4s | %-14s |%n";
        try {
            double grandPrice = 0;
            int totalQty = 0;
            FileWriter fw = new FileWriter("C:/Users/Fazar/Documents/Binar/Challenge/StoreFazar/out/production/StoreFazar/receipt.txt");
            BufferedWriter bw = new BufferedWriter(fw);
            String border = "=====================================\n";
            String border2 = "-----------------------------------+\n";

            bw.write(border);
            bw.write( "\t\t" +title+"\n");
            bw.write(border);

            bw.write("\nTerima kasih sudah memesan\ndi " + title + "\n\nDi bawah ini adalah pesanan anda\n\n");
            for(CartItem item: cartItems){
                if(item == null){
                    break;
                }
                bw.write(item.getProduct().getProductName() + "\t\t" + item.getQty() + "\t" + rupiah.format(item.getTotalPrice())+"\n");
                totalQty += item.getQty();
                grandPrice += item.getTotalPrice();
            }
            bw.write(border2);
            bw.write("Total:\t\t\t" + totalQty + "\t" + rupiah.format(grandPrice) + "\n\n");
            bw.write("\nPembayaran: Tunai\n\n");
            bw.write(border);
            bw.write("Simpan struk ini sebagai\nbukti pembayaran\n");
            bw.write(border);
            bw.close();
            System.out.println("Transaksi berhasil!");
        }catch (IOException e){
            System.err.println("Error: " + e.getMessage());
        }
    }
}
