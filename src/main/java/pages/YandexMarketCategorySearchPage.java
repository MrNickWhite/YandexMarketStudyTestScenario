package pages;

import helpers.Properties;
import helpers.TestProperties;
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

    /**
     * Переменная для хранения объекта поля ввода минимальной цены
     * @author Паничев Н.В.
     */
    @FindBy(xpath = "//div[@data-filter-id='glprice']//span[@data-auto='filter-range-min']//input")
    WebElement minPriceField;

    /**
     * Переменная для хранения объекта поля ввода максимального цены
     * @author Паничев Н.В.
     */
    @FindBy(xpath = "//div[@data-filter-id='glprice']//span[@data-auto='filter-range-max']//input")
    WebElement maxPriceField;

    /**
     * Переменная для открытия полного списка производителей в панели фильтра
     * @author Паничев Н.В.
     */
    @FindBy(xpath = "//div[@data-zone-name='LoadFilterValues']//button//span[contains(text(),'Показать')]")
    WebElement companyShowMore;

    /**
     * Переменная для элемента строки поиска производителя по названию
     * @author Паничев Н.В.
     */
    @FindBy(xpath = "//div[contains(@data-zone-data,'Производитель')]//input[@type='text']")
    WebElement companyFilterSearchField;

    /**
     * Переменная для элемента очистки строки поиска производителей
     * @author Паничев Н.В.
     */
    @FindBy(xpath = "//div[contains(@data-zone-data,'Производитель')]//button")
    WebElement companyFilterSearchFieldRemove;

    /**
     * Переменная для выбора производителя в фильтре
     * @author Паничев Н.В.
     */
    @FindBy(xpath = "//div[contains(@data-zone-data,'Производитель')]//div[@data-zone-name='FilterValue']/label")
    List<WebElement> filterCompanySelectCheckbox;

    /**
     * Переменная для хранения списка товаров в поисковой выдаче
     * @author Паничев Н.В.
     */
    @FindBy(xpath = "//article[@data-autotest-id='product-snippet']")
    List<WebElement> searchGoodsResult;

    /**
     * Конструктор класса YandexMarketCategorySearchPage
     * @author Паничев Н.В.
     */
    public YandexMarketCategorySearchPage(WebDriver chromeDriver){
        super(chromeDriver);
    }

    /**
     * Метод для установки необходимой цены в фильтр
     * @author Паничев Н.В.
     */
    public void setPrices(int minPrice, int maxPrice){
        minPriceField.click();
        minPriceField.sendKeys(String.valueOf(minPrice));
        maxPriceField.click();
        maxPriceField.sendKeys(String.valueOf(maxPrice));
        waitForSearchResults();
    }

    /**
     * Метод для ожидания прогрузки товаров
     * @author Паничев Н.В.
     */
     public void waitForSearchResults(){
        WebDriverWait wait = new WebDriverWait(chromeDriver, Duration.ofSeconds(5));
        chromeDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Properties.testProperties.fastWait()));
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@data-grabber='SearchSerp']/div[@data-auto='preloader']//span[@role='progressbar']")));
        wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath("//div[@data-grabber='SearchSerp']/div[@data-auto='preloader']//span[@role='progressbar']"),0));
        chromeDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Properties.testProperties.slowWait()));
    }

    /**
     * Метод для выбора необходимых производителей в фильтре
     * @author Паничев Н.В.
     */
    public void setCompanies(String[] companies){
        WebDriverWait wait = new WebDriverWait(chromeDriver, Duration.ofSeconds(5));

        companyShowMore.click();
        for (String searchedCompany : companies) {
            if (!chromeDriver.findElements(By.xpath("//div[contains(@data-zone-data,'Производитель')]//input[@type='text']")).isEmpty()){
                companyFilterSearchField.click();
                companyFilterSearchField.sendKeys(searchedCompany);
                wait.until(ExpectedConditions.numberOfElementsToBe(By.xpath("//div[contains(@data-zone-data,'Производитель')]//div[@data-zone-name='FilterValue']/label"),1));
                for (WebElement company : filterCompanySelectCheckbox){
                    if (company.getText().toLowerCase().contains(searchedCompany.toLowerCase())){
                        company.click();
                        waitForSearchResults();
                    }
                }
                companyFilterSearchFieldRemove.click();
                wait.until(ExpectedConditions.numberOfElementsToBeMoreThan(By.xpath("//div[contains(@data-zone-data,'Производитель')]//div[@data-zone-name='FilterValue']/label"),1));
            }
            else{

                for (WebElement company : filterCompanySelectCheckbox){
                    if (company.getText().toLowerCase().contains(searchedCompany.toLowerCase())){
                        company.click();
                        waitForSearchResults();
                    }
                }
            }
        }
    }

    /**
     * Метод для получения необходимого числа товаров из поисковой выдачи
     * @author Паничев Н.В.
     */
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

    /**
     * Метод возвращающий цену с карточки товара
     * @author Паничев Н.В.
     */
    public int getPriceOnProductCard(WebElement productCard){
        String s = productCard.findElement(By.xpath(".//div[@data-zone-name='price']")).getText();
        String[] arrS = s.split("₽");
        s = arrS[0].replaceAll("[^0-9]", "");
        return Integer.parseInt(s);
    }

    /**
     * Метод возвращающий название карточки товара
     * @author Паничев Н.В.
     */
    public String getNameOnProductCard(WebElement productCard){
        String productname = productCard.findElement(By.xpath(".//h3[@data-zone-name='title']")).getText();
        return productname;
    }

    /**
     * Метод для проверки наличия искомого производителя на странице товара в разделе характеристики
     * @author Паничев Н.В.
     */
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

    /**
     * Метод проверки наличия искомого производителя в заголовке товара
     * @author Паничев Н.В.
     */
    //Иногда Яндекс Маркет не выводит полное название товара, показывая только модель. Для таких случаев вызывается функция
    //поиска производителя на странице товара.
    public boolean isContainCompanyName(String[] companies, WebElement productCard)  {
        String goodsCardHeader = getNameOnProductCard(productCard);
        for (String company : companies){
            if (goodsCardHeader.toLowerCase().contains(company.toLowerCase())) {
                return true;
            }
        }
        return isContainCompanyNameOnGoodsPage(companies, productCard);
    }

    /**
     * Метод для проверки соответствия цены на товар цене установленной в фильтре
     * @author Паничев Н.В.
     */
    public boolean isPriceInRange(int minPrice, int maxPrice, WebElement productCard){
        int goodsUnitPrice = getPriceOnProductCard(productCard);
        if (goodsUnitPrice>=minPrice && goodsUnitPrice<=maxPrice) return true;
        return false;
    }




}
