package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import java.util.List;


public class YandexMarketMainPage {

    /**
     * Переменная для хранения драйвера Chrome
     * @author Паничев Н.В.
     */
    WebDriver chromeDriver;

    /**
     * Переменная для хранения элемента поисковой строки
     * @author Паничев Н.В.
     */
    @FindBy(xpath = "//div[not(@style='display:none')]/div[@data-zone-name='search_block']//input[@type='text']")
    WebElement searchField;

    /**
     * Переменная для хранения кнопки выполнения поиска
     * @author Паничев Н.В.
     */
    @FindBy(xpath = "//div[not(@style='display:none')]/div[@data-zone-name='search_block']//button[@type='submit']")
    WebElement searchButton;

    /**
     * Переменная для хранения кнопки открытия каталога
     * @author Паничев Н.В.
     */
    @FindBy(xpath = "//button[@id='catalogPopupButton']")
    WebElement catalogButton;

    /**
     * Переменная для хранения списка категорий в каталоге
     * @author Паничев Н.В.
     */
    @FindBy(xpath = "//div[@data-zone-name='catalog-content']//li//span")
    List<WebElement> catalogCategory;

    /**
     * Переменная для списка подкатегорий в категории
     * @author Паничев Н.В.
     */
    @FindBy(xpath = "//div[@role='tabpanel']//ul//a")
    List<WebElement> catalogLaptopCategory;

    /**
     * Конструктор класса YandexMarketMainPage
     * @author Паничев Н.В.
     */
    public YandexMarketMainPage(WebDriver chromeDriver){this.chromeDriver = chromeDriver;}

    /**
     * Метод для открытия нужной подкатегории
     * @author Паничев Н.В.
     */
    public void openCategory(String categoryName, String subCategoryName){
        catalogButton.click();
        Actions action = new Actions(chromeDriver);
        for (WebElement category : catalogCategory){
            if(category.getText().equals(categoryName)){
                action.moveToElement(category).perform();
                for (WebElement subCategory : catalogLaptopCategory){
                    if (subCategory.getText().equals(subCategoryName)){
                        action.moveToElement(subCategory).click().perform();
                        break;
                    }
                }
                break;
            }
        }

    }

    /**
     * Метод для выполнения поиска в поисковой строке
     * @author Паничев Н.В.
     */
    public void findProduct(String productName){
        searchField.click();
        searchField.sendKeys(productName);
        searchButton.click();
    }

}
