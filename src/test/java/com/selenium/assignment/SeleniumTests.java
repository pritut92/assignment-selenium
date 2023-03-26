package com.selenium.assignment;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SeleniumTests {
    private static WebDriver driver;

    @BeforeAll
    static void setup() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        System.setProperty("webdriver.chrome.driver", "/Users/sachinmehta/chromedriver_mac64/chromedriver");
        driver = new ChromeDriver(options);

    }

    @BeforeEach
    void navigate() {
        driver.get("https://svtplay.se");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        WebElement cookie = new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"__next\"]/div[2]/div/div[2]/button[2]")));
        if (cookie.isDisplayed()) {
            cookie.click();
        }
    }

    @Test
    void checkWebsiteTitle() {
        driver.manage().window().maximize();
        String websiteTitle = driver.getTitle();
        assertEquals("SVT Play", websiteTitle);
    }

    @Test
    void checkWebsiteLogo() {
        driver.manage().window().maximize();
        boolean logoPresent = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[3]/div/header/div[2]/div/div/nav/a")).isDisplayed();
        Assertions.assertTrue(logoPresent);
    }

    @Test
    void checkMenuLinks() {
        driver.manage().window().maximize();
        WebElement startMenuElement = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[3]/div/header/div[2]/div/div/nav/ul/li[1]/a"));
        WebElement programMenuElement = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[3]/div/header/div[2]/div/div/nav/ul/li[2]/a"));
        WebElement kanalerMenuElement = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[3]/div/header/div[2]/div/div/nav/ul/li[3]/a"));
        Assertions.assertEquals("START", startMenuElement.getAccessibleName());
        Assertions.assertEquals("PROGRAM", programMenuElement.getAccessibleName());
        Assertions.assertEquals("KANALER", kanalerMenuElement.getAccessibleName());
    }

    @Test
    void checkTillganglighetLink() {
        driver.manage().window().maximize();
        WebElement startMenuElement = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[3]/div/footer/div/div[5]/div[2]/p[1]/a/span[2]"));
        new Actions(driver)
                .scrollToElement(startMenuElement)
                .perform();
        Assertions.assertTrue(startMenuElement.isDisplayed());
        Assertions.assertEquals("Tillgänglighet i SVT Play", startMenuElement.getText());
    }

    @Test
    void checkClickOnTillganglighetLink() {
        driver.manage().window().maximize();
        new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"__next\"]/div[2]/div/div[2]/button[2]"))).click();
        WebElement linkElement = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[3]/div/footer/div/div[5]/div[2]/p[1]/a/span[2]"));
        new Actions(driver)
                .scrollToElement(linkElement)
                .perform();

        new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"__next\"]/div/div[3]/div/footer/div/div[5]/div[2]/p[1]/a"))).click();
        String websiteHeading = driver.findElement(By.xpath("//*[@id=\"__next\"]/div[2]/main/div/div/div[1]/h1")).getText();
        assertEquals("Så arbetar SVT med tillgänglighet", websiteHeading);
    }

    @Test
    void shouldFindElementById() {
        driver.manage().window().maximize();
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions.elementToBeClickable(By.id("play_a11y-announcer")));
        assertEquals(true, element.isDisplayed());
    }

    @Test
    void shouldFindElementByClass() {
        driver.manage().window().maximize();
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions.elementToBeClickable(By.className("sc-e5bd8c40-2")));
        assertEquals(true, element.isDisplayed());
    }

    @Test
    void shouldFindElementByXPath() {
        driver.manage().window().maximize();
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"play_a11y-announcer\"]")));
        assertEquals(true, element.isDisplayed());
    }

    @Test
    void shouldFindElementByTagName() {
        driver.manage().window().maximize();
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions.elementToBeClickable(By.tagName("header")));
        assertEquals(true, element.isDisplayed());
    }

    @Test
    void shouldFindElementByCssSelector() {
        driver.manage().window().maximize();
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions.elementToBeClickable(By.cssSelector("#play_a11y-announcer")));
        assertEquals(true, element.isDisplayed());
    }

    @Test
    void shouldSearchTheProgram() throws InterruptedException {
        driver.manage().window().maximize();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("q")));
        Thread.sleep(1000);
        element.sendKeys("Agenda");
        element.sendKeys(Keys.ENTER);
        WebElement articleElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"play_main-content\"]/section/div/ul/li[1]/article/a")));
        assertEquals("https://www.svtplay.se/agenda", articleElement.getDomProperty("href"));
    }

    @Test
    void shouldCheckNumberOfEpisodesForASearch() throws InterruptedException {
        driver.manage().window().maximize();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.name("q")));
        Thread.sleep(1000);
        element.sendKeys("Pistvakt");
        element.sendKeys(Keys.ENTER);
        WebElement articleElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"play_main-content\"]/section/div/ul/li[1]/article/a")));
        articleElement.click();
        WebElement season2Element = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"play_main-content\"]/div/div[3]/section[2]/h2/a")));
        new Actions(driver)
                .scrollToElement(season2Element)
                .perform();
        season2Element.click();
        List<WebElement> season2Episodes = driver.findElements(By.xpath("//*[@id=\"season-2-jx3za5B\"]/article"));

        assertEquals(6, season2Episodes.size());
    }

    @AfterAll
    static void teardown() {
        driver.close();
        driver.quit();
    }
}