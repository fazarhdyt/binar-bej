package main.java;

import main.java.service.IMenuService;
import main.java.service.IShopService;
import main.java.service.IValidationService;
import main.java.service.impl.MenuService;
import main.java.service.impl.ShopService;
import main.java.service.impl.ValidationService;
import main.java.view.MainMenuView;

public class Main {
    public static void main(String[] args) {

        IMenuService menuService = new MenuService();
        IShopService shopService = new ShopService();
        IValidationService validationService = new ValidationService();

        MainMenuView mainMenuView = new MainMenuView(menuService, shopService, validationService);

        mainMenuView.runApp();

    }
}
