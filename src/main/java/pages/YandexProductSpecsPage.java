package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.HashMap;
import java.util.List;

public class YandexProductSpecsPage extends YandexMarketMainPage{
    /**
     * Переменная для хранения списка строк таблицы характеристик
     * @author Паничев Н.В.
     */
    @FindBy(xpath = "//div[@data-auto='offer-specs']/div")
    List<WebElement> specs;
    public YandexProductSpecsPage(WebDriver chromeDriver){super(chromeDriver);}

    /**
     * Метод возвращающий характеристики товара в виде HashMap
     * @author Паничев Н.В.
     */
    public HashMap<String, String> getSpecs(){
        HashMap<String, String> specsMap = new HashMap<>();
        for (WebElement x : specs) {
            specsMap.put(x.findElement(By.xpath(".//dt/span")).getText(),x.findElement(By.xpath(".//dd")).getText());
        }
        return specsMap;
    }


}
