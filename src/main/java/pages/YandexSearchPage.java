package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class YandexSearchPage {

    /**
     * Переменная для хранения драйвера Chrome
     * @author Паничев Н.В.
     */
    WebDriver chromeDriver;

    /**
     * Переменная для хранения элемента поисковой строки
     * @author Паничев Н.В.
     */
    @FindBy(xpath = "//input[contains(@class,'search') and @role='combobox']")
    WebElement searchField;

    /**
     * Переменная для хранения элемента открытия панели всех сервисов Яндекс
     * @author Паничев Н.В.
     */
    @FindBy(xpath = "//a[contains(@role,'button') and contains(@class,'services')]")
    WebElement allServicesButton;

    /**
     * Переменная для хранения кнопки перехода на Яндекс Маркет
     * @author Паничев Н.В.
     */
    @FindBy(xpath = "//div[contains(@class,'popup2 services')]//span//a[@aria-label='Маркет']")
    WebElement marketServiceButton;

    /**
     * Конструктор класса YandexSearchPage
     * @author Паничев Н.В.
     */
    public YandexSearchPage(WebDriver chromeDriver){this.chromeDriver = chromeDriver;}

    /**
     * Метод для перехода на страницу Яндекс Маркет
     * @author Паничев Н.В.
     */
    public void openMarketPage(){
        searchField.click();
        allServicesButton.click();
        marketServiceButton.click();
    }
}
