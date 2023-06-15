package ru.yandex;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.concurrent.TimeUnit;

public class BaseTest {

    /**
     * Поле для хранения драйвера Chrome
     * @author Паничев Н.В.
     */
    protected WebDriver chromeDriver;
    /**
     * Метод для подготовки тестового окружения для каждого теста
     * @author Паничев Н.В.
     */
    @BeforeEach
    public void before(){
        System.setProperty("webdriver.chrome.driver", System.getenv("CHROME_DRIVER"));
        chromeDriver = new ChromeDriver();
        chromeDriver.manage().window().maximize();
        chromeDriver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }
    /**
     * Метод для закрытия окна браузера после выполнения теста
     * @author Паничев Н.В.
     */
    @AfterEach
    public void after(){
        chromeDriver.quit();
    }
}

