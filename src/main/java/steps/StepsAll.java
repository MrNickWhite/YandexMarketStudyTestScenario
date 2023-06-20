package steps;

import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import pages.YandexMarketCategorySearchPage;
import pages.YandexMarketMainPage;
import pages.YandexSearchPage;

import java.util.List;

public class StepsAll {

    private static WebDriver chromeDriver;
    @Step("Переходим на сайт {url}")
    public static void openSite(String url, WebDriver driver){
        chromeDriver = driver;
        chromeDriver.get(url);
    }

    @Step("Открываем Яндекс Маркет")
    public static void openYandexMarket(){
        YandexSearchPage yandexSearchPage = PageFactory.initElements(chromeDriver, YandexSearchPage.class);
        yandexSearchPage.openMarketPage();
        chromeDriver.switchTo().window(chromeDriver.getWindowHandles().toArray()[1].toString());
    }

    @Step("Открываем категорию товаров \"{subCategoryName}\"")
    public  static void openMarketCategory(String categoryName, String subCategoryName){
        YandexMarketMainPage yandexMarketMainPage = PageFactory.initElements(chromeDriver, YandexMarketMainPage.class);
        yandexMarketMainPage.openCategory(categoryName, subCategoryName);
    }

    @Step("Указываем в панели фильтров диапазон цен")
    public static void setPrices(YandexMarketCategorySearchPage yandexMarketCategorySearchPage, int minPrice, int maxPrice){
        yandexMarketCategorySearchPage.setPrices(minPrice, maxPrice);
    }

    @Step("В панели фильтров выбираем необходимых производителей")
    public static void setCompanies(YandexMarketCategorySearchPage yandexMarketCategorySearchPage, String[] companies){
        yandexMarketCategorySearchPage.setCompanies(companies);
    }

    @Step("Получаем список результатов")
    public static List<WebElement> getResults(YandexMarketCategorySearchPage yandexMarketCategorySearchPage, int quantity){
        return yandexMarketCategorySearchPage.getResults(quantity);
    }

    @Step("Получение модели ноутбука и поиск")
    public static String searchProduct(YandexMarketCategorySearchPage yandexMarketCategorySearchPage, List<WebElement> goodsResult){
        String searchedProduct = yandexMarketCategorySearchPage.getNameOnProductCard(goodsResult.get(0));
        yandexMarketCategorySearchPage.findProduct(searchedProduct);
        return searchedProduct;
    }
}
