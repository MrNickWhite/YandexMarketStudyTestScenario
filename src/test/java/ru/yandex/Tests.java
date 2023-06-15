package ru.yandex;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.*;

import java.time.Duration;
import java.util.Arrays;
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
            Assertions.assertTrue(yandexMarketCategorySearchPage.isContainCompanyName(companiesArr, goodsResult.get(i))==true,"В результатах поисковой выдачи найдена карточка товара без требуемых производителей");
            Assertions.assertTrue(yandexMarketCategorySearchPage.isPriceInRange(minPrice, maxPrice, goodsResult.get(i))==true, "В результатах поисковой выдачи найдена карточка товара с несоответсвующей фильтру ценой");
        }
    }

}
