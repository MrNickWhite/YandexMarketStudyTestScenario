package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class YandexSearchPage {

    WebDriver chromeDriver;

    @FindBy(xpath = "//input[contains(@class,'search') and @role='combobox']")
    WebElement searchField;

    @FindBy(xpath = "//a[contains(@role,'button') and contains(@class,'services')]")
    WebElement allServicesButton;
    @FindBy(xpath = "//div[contains(@class,'popup2 services')]//span//a[@aria-label='Маркет']")
    WebElement marketServiceButton;


    public YandexSearchPage(WebDriver chromeDriver){this.chromeDriver = chromeDriver;}


    public void openMarketPage(){
        searchField.click();
        allServicesButton.click();
        marketServiceButton.click();
    }
}
