package main.java.service.impl;

import main.java.model.Product;
import main.java.service.IValidationService;

import java.util.List;
import java.util.Scanner;

public class ValidationService implements IValidationService {

    /**
     * method ini digunakan untuk memvalidasi input user dengan regex yang ditentukan
     *
     * @param inputUser
     * @param regex
     * @return
     */
    public boolean inputValidation(String inputUser, String regex) {
        return inputUser.matches(regex);
    }

    /**
     * method ini digunakan untuk mengambil input user yang di dalamnya sudah tersedia method validasi untuk input usernya
     *
     * @param question
     * @param errorMessage
     * @param regex
     * @return
     */
    public String inputUser(String question, String errorMessage, String regex) {
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
     *
     * @param products
     * @param id
     * @return
     */
    public boolean checkAvailabilityOrderId(List<Product> products, int id) {
        boolean result = false;
        for (Product product : products) {
            if (product.getId() == id) {
                result = true;
            }
        }
        return result;
    }

    /**
     * method ini digunakan untuk mengambil input id dari makanan atau minuman yang akan dipesan
     *
     * @param products
     * @return
     */
    public int inputOrder(List<Product> products) {
        String regexNumeric = "^[0-9]+$";
        boolean isLooping = true;
        int inputOrderId;
        do {
            inputOrderId = Integer.parseInt(inputUser("=> ", "masukkan hanya dengan angka positif", regexNumeric));
            if (inputOrderId == 0 || inputOrderId == 99) {
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
