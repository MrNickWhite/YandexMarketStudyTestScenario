package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;


public class YandexMarketMainPage {

    WebDriver chromeDriver;

    @FindBy(xpath = "//div[not(@style='display:none')]/div[@data-zone-name='search_block']//input[@type='text']")
    WebElement searchField;
    @FindBy(xpath = "//div[not(@style='display:none')]/div[@data-zone-name='search_block']//button[@type='submit']")
    WebElement searchButton;
    @FindBy(xpath = "//button[@id='catalogPopupButton']")
    WebElement catalogButton;
    @FindBy(xpath = "//div[@data-zone-name='catalog-content']//li//span[text()='Ноутбуки и компьютеры']")
    WebElement catalogCategory;
    @FindBy(xpath = "//div[@role='tabpanel']//ul//a[text()='Ноутбуки']")
    WebElement catalogLaptopCategory;

    public YandexMarketMainPage(WebDriver chromeDriver){this.chromeDriver = chromeDriver;}

    public void openCategory(){
        catalogButton.click();
        Actions action = new Actions(chromeDriver);
        action.moveToElement(catalogCategory).perform();
        action.moveToElement(catalogLaptopCategory).click().perform();
    }

    public void findProduct(String productName){
        searchField.click();
        searchField.sendKeys(productName);
        searchButton.click();
    }

}
