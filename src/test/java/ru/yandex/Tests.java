package ru.yandex;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import pages.*;
import java.util.List;


public class Tests extends BaseTest {



    @ParameterizedTest
    @CsvSource({"10000, 90000, Huawei Lenovo, 12"})
    public void yandexMarketTest(int minPrice, int maxPrice, String companies, int estimatedCount) throws InterruptedException {
        chromeDriver.get("https://ya.ru/");
        YandexSearchPage yandexSearchPage = PageFactory.initElements(chromeDriver, YandexSearchPage.class);
        yandexSearchPage.openMarketPage();
        chromeDriver.switchTo().window(chromeDriver.getWindowHandles().toArray()[1].toString());
        YandexMarketMainPage yandexMarketMainPage = PageFactory.initElements(chromeDriver, YandexMarketMainPage.class);
        yandexMarketMainPage.openCategory();
        YandexMarketCategorySearchPage yandexMarketCategorySearchPage = PageFactory.initElements(chromeDriver, YandexMarketCategorySearchPage.class);
        yandexMarketCategorySearchPage.setPrices(minPrice, maxPrice);
        String[] companiesArr = companies.split(" ");
        yandexMarketCategorySearchPage.setCompanies(companiesArr);
        Thread.sleep(10000);
        List<WebElement> goodsResult = yandexMarketCategorySearchPage.getResults(estimatedCount);
        Assertions.assertTrue(goodsResult.size()>=estimatedCount, String.format("Колличество товаров в поисковой выдаче меньше чем %s", estimatedCount) );
        for (int i = 0; i < estimatedCount; i++) {
            Assertions.assertTrue(yandexMarketCategorySearchPage.isContainCompanyName(companiesArr, goodsResult.get(i)) == true, "В результатах поисковой выдачи найдена карточка товара без требуемых производителей");
            Assertions.assertTrue(yandexMarketCategorySearchPage.isPriceInRange(minPrice, maxPrice, goodsResult.get(i)) == true, "В результатах поисковой выдачи найдена карточка товара с несоответсвующей фильтру ценой");
        }
        String searchedProduct = yandexMarketCategorySearchPage.getNameOnGoodsCard(goodsResult.get(0));
        yandexMarketCategorySearchPage.findProduct(searchedProduct);
        Thread.sleep(10000);
        goodsResult = yandexMarketCategorySearchPage.getResults(estimatedCount);
        boolean isContent = false;
        for (int i = 0; i < estimatedCount; i++){
            if(yandexMarketCategorySearchPage.getNameOnGoodsCard(goodsResult.get(i)).equals(searchedProduct)){
                isContent = true;
                break;
            }
        }
        Assertions.assertTrue(isContent==true,"Искомого товара в результатах поиска не обнаружено");
    }

}
