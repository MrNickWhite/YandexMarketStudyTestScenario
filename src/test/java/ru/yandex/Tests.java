package ru.yandex;

import helpers.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import pages.*;
import steps.StepsAll;

import java.util.List;



public class Tests extends BaseTest {



    @ParameterizedTest
    @CsvSource({"10000, 90000, Huawei Lenovo, 12, Ноутбуки и компьютеры, Ноутбуки"})
    public void yandexMarketTest(int minPrice, int maxPrice, String companies, int estimatedCount, String categoryName, String subCategoryName){
        String[] companiesArr = companies.split(" ");
        StepsAll.openSite("https://ya.ru/",chromeDriver);
        StepsAll.openYandexMarket();
        StepsAll.openMarketCategory(categoryName, subCategoryName);
        YandexMarketCategorySearchPage yandexMarketCategorySearchPage = PageFactory.initElements(chromeDriver,YandexMarketCategorySearchPage.class);
        StepsAll.setPrices(yandexMarketCategorySearchPage, minPrice, maxPrice);
        StepsAll.setCompanies(yandexMarketCategorySearchPage, companiesArr);
        List<WebElement> goodsResult = StepsAll.getResults(yandexMarketCategorySearchPage, estimatedCount);
        Assertions.assertTrue(goodsResult.size()>=estimatedCount, String.format("Колличество товаров в поисковой выдаче меньше чем %s", estimatedCount) );
        for (int i = 0; i < estimatedCount; i++) {
            Assertions.assertTrue(yandexMarketCategorySearchPage.isContainCompanyName(companiesArr, goodsResult.get(i)) == true, "В результатах поисковой выдачи найдена карточка товара без требуемых производителей");
            Assertions.assertTrue(yandexMarketCategorySearchPage.isPriceInRange(minPrice, maxPrice, goodsResult.get(i)) == true, "В результатах поисковой выдачи найдена карточка товара с несоответсвующей фильтру ценой");
        }
        String searchedProduct = StepsAll.searchProduct(yandexMarketCategorySearchPage, goodsResult);
        goodsResult = StepsAll.getResults(yandexMarketCategorySearchPage, estimatedCount);
        boolean isContent = false;
        for (int i = 0; i < estimatedCount; i++){
            if(yandexMarketCategorySearchPage.getNameOnProductCard(goodsResult.get(i)).equals(searchedProduct)){
                isContent = true;
                break;
            }
        }
        Assertions.assertTrue(isContent==true,"Искомого товара в результатах поиска не обнаружено");
    }

}
