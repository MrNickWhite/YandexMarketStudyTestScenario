package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class YandexProductPage extends YandexMarketMainPage{

    @FindBy(xpath = "//div[@data-baobab-name='productActions']//div[@data-baobab-name='specsLink']/a")
    WebElement showSpecsButton;

    public YandexProductPage(WebDriver chromeDriver){super(chromeDriver);}

    public void openSpecsPage(){
        showSpecsButton.click();
    }

}
