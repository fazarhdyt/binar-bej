import service.IMenuService;
import service.IShopService;
import service.IValidationService;
import service.impl.MenuService;
import service.impl.ShopService;
import service.impl.ValidationService;
import view.MainMenuView;

public class Main {
    public static void main(String[] args) {

        IMenuService menuService = new MenuService();
        IShopService shopService = new ShopService();
        IValidationService validationService = new ValidationService();

        MainMenuView mainMenuView = new MainMenuView(menuService, shopService, validationService);

        mainMenuView.runApp();

    }
}
