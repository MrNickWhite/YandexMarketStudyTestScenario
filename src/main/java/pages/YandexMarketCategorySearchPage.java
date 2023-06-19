package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
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
    @FindBy(xpath = "//div[contains(@data-zone-data,'Производитель')]//button")
    WebElement companyFilterSearchFieldRemove;
    @FindBy(xpath = "//div[contains(@data-zone-data,'Производитель')]//div[@data-zone-name='FilterValue']/label")
    WebElement filterCompanySelectCheckbox;
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
     public void waitForSearchResults(){
        WebDriverWait wait = new WebDriverWait(chromeDriver, Duration.ofSeconds(5));
        chromeDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(1));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-grabber='SearchSerp']/div[@data-auto='preloader']//span[@role='progressbar']")));
        wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath("//div[@data-grabber='SearchSerp']/div[@data-auto='preloader']//span[@role='progressbar']"),0));
        chromeDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }


    public void setCompanies(String[] companies){

        WebDriverWait wait = new WebDriverWait(chromeDriver, Duration.ofSeconds(5));
        waitForSearchResults();
        companyShowMore.click();
        for (String s : companies) {
            companyFilterSearchField.click();
            companyFilterSearchField.sendKeys(s);
            wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath("//div[contains(@data-zone-data,'Производитель')]//div[@data-zone-name='FilterValue']/label"),1));
            filterCompanySelectCheckbox.click();
            waitForSearchResults();
            companyFilterSearchFieldRemove.click();
            wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//div[contains(@data-zone-data,'Производитель')]//div[@data-zone-name='FilterValue']/label"),1));
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

    public int getPriceOnProductCard(WebElement productCard){
        String s = productCard.findElement(By.xpath(".//div[@data-zone-name='price']")).getText();
        String[] arrS = s.split("₽");
        s = arrS[0].replaceAll("[^0-9]", "");
        return Integer.parseInt(s);
    }

    public String getNameOnProductCard(WebElement productCard){
        String productname = productCard.findElement(By.xpath(".//h3[@data-zone-name='title']")).getText();
        return productname;
    }

    public boolean isContainCompanyNameOnGoodsPage(String[] companies, WebElement productCard) {
        productCard.findElement(By.xpath(".//h3[@data-zone-name='title']/a")).click();
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
    public boolean isContainCompanyName(String[] companies, WebElement productCard)  {
        String goodsCardHeader = getNameOnProductCard(productCard);
        for (String company : companies){
            if (goodsCardHeader.toLowerCase().contains(company.toLowerCase())) {
                return true;
            }
        }
        return isContainCompanyNameOnGoodsPage(companies, productCard);
    }

    public boolean isPriceInRange(int minPrice, int maxPrice, WebElement productCard){
        int goodsUnitPrice = getPriceOnProductCard(productCard);
        if (goodsUnitPrice>=minPrice && goodsUnitPrice<=maxPrice) return true;
        return false;
    }




}
