package main.java;

import main.java.model.Product;

import java.util.Scanner;

public class Validation {
    /**
     * method ini digunakan untuk memvalidasi input user dengan regex yang ditentukan
     * @param inputUser
     * @param regex
     * @return
     */
    public static boolean inputValidation(String inputUser, String regex) {
        return inputUser.matches(regex);
    }

    /**
     * method ini digunakan untuk mengambil input user yang di dalamnya sudah tersedia funcion validasi untuk input usernya
     * @param question
     * @param errorMessage
     * @param regex
     * @return
     */
    public static String inputUser(String question, String errorMessage, String regex) {
        Scanner input = new Scanner(System.in);
        String result;
        boolean isLooping = true;
        do {
            System.out.print("\n" + question);
            result = input.nextLine();
            if (!inputValidation(result, regex)) {
                System.out.println(errorMessage);
            } else {
                isLooping = false;
            }
        } while (isLooping);
        return result;
    }

    /**
     * method ini digunakan untuk mengecek id suatu makanan atau minuman yang diinput user tersedia atau tidak
     * @param products
     * @param id
     * @return
     */
    public static boolean checkAvailabilityOrderId(Product[] products, int id) {
        boolean result = false;
        for(int i=0; i<products.length; i++){
            if(i+1 == id){
                result = true;
            }
        }
        return result;
    }

    /**
     * method ini digunakan untuk mengambil input id dari makanan atau minuman yang akan dipesan
     * @param products
     * @return
     */
    public static int inputOrder(Product[] products) {
        String regexNumeric = "^[0-9]+$";
        boolean isLooping = true;
        int inputOrderId;
        do {
            inputOrderId = Integer.parseInt(inputUser("=> ", "masukkan hanya dengan angka", regexNumeric));
            if (inputOrderId == 0 || inputOrderId == 99 || inputOrderId == 90) {
                return inputOrderId;
            }
            boolean isValid = checkAvailabilityOrderId(products, inputOrderId);
            if (!isValid) {
                System.out.println("id pesanan tidak tersedia");
            } else {
                isLooping = false;
            }
        } while (isLooping);
        return inputOrderId;
    }


}
