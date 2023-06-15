package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.HashMap;
import java.util.List;

public class YandexMarketCategorySearchPage extends YandexMarketMainPage {

    @FindBy(xpath = "//div[@data-filter-id='glprice']//span[@data-auto='filter-range-min']//input")
    WebElement minPriceField;
    @FindBy(xpath = "//div[@data-filter-id='glprice']//span[@data-auto='filter-range-max']//input")
    WebElement maxPriceField;
    @FindBy(xpath = "//div[@data-zone-name='LoadFilterValues']//button//span[contains(text(),'Показать')]")
    WebElement companyShowMore;
    @FindBy(xpath = "//div[contains(@data-zone-data,'Производитель')]//input[@type='text']")
    WebElement companyFilterSearchField;
    @FindBy(xpath = "//div[contains(@data-zone-data,'Производитель')]//div[@data-zone-name='FilterValue']/label")
    WebElement filterCompanySelectCheckbox;
    @FindBy(xpath = "//span[@role='button' and contains(text(),'Найдено')]")
    WebElement searchShortResult;

    @FindBy(xpath = "//article[@data-autotest-id='product-snippet']")
    List<WebElement> searchGoodsResult;

    public YandexMarketCategorySearchPage(WebDriver chromeDriver){
        super(chromeDriver);
    }

    public void setPrices(int minPrice, int maxPrice){
        minPriceField.click();
        minPriceField.sendKeys(String.valueOf(minPrice));
        maxPriceField.click();
        maxPriceField.sendKeys(String.valueOf(maxPrice));
    }

    public boolean isResultReady(){
        System.out.println(searchShortResult.getText());
        return true;
    }
    public void setCompanies(String[] companies) throws InterruptedException {

        Thread.sleep(8000);
        companyShowMore.click();
        for (String s : companies) {
            companyFilterSearchField.click();
            companyFilterSearchField.sendKeys(s);
            Thread.sleep(3000);
            filterCompanySelectCheckbox.click();
            Thread.sleep(3000);
            companyFilterSearchField.clear();
        }

    }

    public List<WebElement> getResults(int estimatedCount){
        int goodsCount = searchGoodsResult.size();
        int previousGoodsCount = 0;
        while(goodsCount<estimatedCount && previousGoodsCount!=goodsCount){
            previousGoodsCount = goodsCount;
            Actions action = new Actions(chromeDriver);
            action.moveToElement(searchGoodsResult.get(goodsCount-1)).perform();
            goodsCount = searchGoodsResult.size();
        }
        return searchGoodsResult;
    }

    public int getPriceOnGoodsCard(WebElement goodsCard){
        String s = goodsCard.findElement(By.xpath(".//div[@data-zone-name='price']")).getText();
        String[] arrS = s.split("₽");
        s = arrS[0].replaceAll("[^0-9]", "");
        return Integer.parseInt(s);
    }

    public String getNameOnGoodsCard(WebElement goodsCard){
        String s = goodsCard.findElement(By.xpath(".//h3[@data-zone-name='title']")).getText();
        return s;
    }

    public boolean isContainCompanyNameOnGoodsPage(String[] companies, WebElement goodsCard) throws InterruptedException {
        goodsCard.findElement(By.xpath(".//h3[@data-zone-name='title']/a")).click();
        chromeDriver.switchTo().window(chromeDriver.getWindowHandles().toArray()[2].toString());
        YandexProductPage yandexProductPage = PageFactory.initElements(chromeDriver, YandexProductPage.class);
        yandexProductPage.openSpecsPage();
        YandexProductSpecsPage yandexProductSpecsPage = PageFactory.initElements(chromeDriver, YandexProductSpecsPage.class);
        HashMap<String, String> specsMap = yandexProductSpecsPage.getSpecs();
        for (String company : companies){
            if (specsMap.get("Производитель").toLowerCase().equals(company.toLowerCase())) {
                chromeDriver.close();
                chromeDriver.switchTo().window(chromeDriver.getWindowHandles().toArray()[1].toString());
                return true;
            }
        }
        return false;
    }
    public boolean isContainCompanyName(String[] companies, WebElement goodsCard) throws InterruptedException {
        String goodsCardHeader = getNameOnGoodsCard(goodsCard);
        for (String company : companies){
            if (goodsCardHeader.toLowerCase().contains(company.toLowerCase())) {
                return true;
            }
        }
        return isContainCompanyNameOnGoodsPage(companies, goodsCard);
    }

    public boolean isPriceInRange(int minPrice, int maxPrice, WebElement goodsCard){
        int goodsUnitPrice = getPriceOnGoodsCard(goodsCard);
        if (goodsUnitPrice>=minPrice && goodsUnitPrice<=maxPrice) return true;
        return false;
    }




}
