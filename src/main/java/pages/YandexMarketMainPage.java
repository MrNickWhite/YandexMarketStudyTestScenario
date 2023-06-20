package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import java.util.List;


public class YandexMarketMainPage {

    WebDriver chromeDriver;

    @FindBy(xpath = "//div[not(@style='display:none')]/div[@data-zone-name='search_block']//input[@type='text']")
    WebElement searchField;
    @FindBy(xpath = "//div[not(@style='display:none')]/div[@data-zone-name='search_block']//button[@type='submit']")
    WebElement searchButton;
    @FindBy(xpath = "//button[@id='catalogPopupButton']")
    WebElement catalogButton;
    @FindBy(xpath = "//div[@data-zone-name='catalog-content']//li//span")
    List<WebElement> catalogCategory;
    @FindBy(xpath = "//div[@role='tabpanel']//ul//a")
    List<WebElement> catalogLaptopCategory;

    public YandexMarketMainPage(WebDriver chromeDriver){this.chromeDriver = chromeDriver;}

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

    public void findProduct(String productName){
        searchField.click();
        searchField.sendKeys(productName);
        searchButton.click();
    }

}
