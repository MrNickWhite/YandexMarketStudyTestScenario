package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;

import javax.swing.*;

public class YandexMarketMainPage {

    WebDriver chromeDriver;

    @FindBy(xpath = "//input[@type='text']")
    WebElement searchField;

    @FindBy(xpath = "//button[@id='catalogPopupButton']")
    WebElement catalogButton;
    @FindBy(xpath = "//div[@data-zone-name='catalog-content']//li//span[text()='Ноутбуки и компьютеры']")
    WebElement catalogCategory;
    @FindBy(xpath = "//div[@role='tabpanel']//ul//a[text()='Ноутбуки']")
    WebElement catalogLaptopCategory;

    public YandexMarketMainPage(WebDriver chromeDriver){this.chromeDriver = chromeDriver;}

    public void openCategory() throws InterruptedException {
        catalogButton.click();
        Actions action = new Actions(chromeDriver);
        action.moveToElement(catalogCategory).perform();
        action.moveToElement(catalogLaptopCategory).click().perform();
    }

}
