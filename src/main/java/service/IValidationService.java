package service;

import model.Product;

import java.util.List;

public interface IValidationService {

    boolean inputValidation(String input, String regex);

    String inputUser(String question, String errorMessage, String regex);

    boolean checkAvailabilityOrderId(List<Product> products, int id);

    int inputOrder(List<Product> products);
}
