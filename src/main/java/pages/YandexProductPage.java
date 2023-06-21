package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class YandexProductPage extends YandexMarketMainPage{

    /**
     * Переменная для хранения кнопки перехода на страницу характеристик
     * @author Паничев Н.В.
     */
    @FindBy(xpath = "//div[@data-baobab-name='productActions']//div[@data-baobab-name='specsLink']/a")
    WebElement showSpecsButton;

    /**
     * Конструктор класса YandexProductPage
     * @author Паничев Н.В.
     */
    public YandexProductPage(WebDriver chromeDriver){super(chromeDriver);}

    /**
     * Метод для перехода на страницу характеристик
     * @author Паничев Н.В.
     */
    public void openSpecsPage(){
        showSpecsButton.click();
    }

}
