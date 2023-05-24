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

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SeleniumTests {
    private static WebDriver driver;

    private static WebDriverWait webDriverWait;

    private static Actions actions;

    @BeforeAll
    static void navigate() {
        System.setProperty("webdriver.chrome.driver", "/Users/sachinmehta/chromedriver_mac64_latest/chromedriver");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));

        webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(2));

        actions = new Actions(driver);

        driver.get("https://svtplay.se");

        WebElement cookieModel = driver.findElement(By.xpath("//*[@data-rt='cookie-consent-modal']"));
        webDriverWait.until(ExpectedConditions.visibilityOf(cookieModel));
        driver.findElement(By.xpath("//button[text()='Acceptera alla']")).click();
        webDriverWait.until(ExpectedConditions.invisibilityOf(cookieModel));
    }

    @Test
    void checkTitle() {
        
        String websiteTitle = driver.getTitle();
        assertEquals("SVT Play", websiteTitle);
    }

    @Test
    void checkWebsiteLogo() {
        boolean logoPresent = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[3]/div/header/div[2]/div/div/nav/a")).isDisplayed();
        Assertions.assertTrue(logoPresent);
    }

    @Test
    void checkMenuLinks() {
        
        WebElement startMenuElement = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[3]/div/header/div[2]/div/div/nav/ul/li[1]/a"));
        WebElement programMenuElement = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[3]/div/header/div[2]/div/div/nav/ul/li[2]/a"));
        WebElement kanalerMenuElement = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[3]/div/header/div[2]/div/div/nav/ul/li[3]/a"));
        Assertions.assertEquals("START", startMenuElement.getAccessibleName());
        Assertions.assertEquals("PROGRAM", programMenuElement.getAccessibleName());
        Assertions.assertEquals("KANALER", kanalerMenuElement.getAccessibleName());
    }

    @Test
    void checkTillganglighetLink() throws InterruptedException {
        Thread.sleep(1000);
        WebElement tillganglighetLink = webDriverWait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[3]/div/footer/div/div[5]/div[2]/p[1]/a"))));
        actions.moveToElement(tillganglighetLink).perform();
        Assertions.assertTrue(tillganglighetLink.isDisplayed());
        Assertions.assertEquals("Tillgänglighet i SVT Play\n" +
                ", öppnar annan webbplats", tillganglighetLink.getText());
    }

    @Test
    void checkClickOnTillganglighetLink() {
        WebElement tillganglighetLink = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[3]/div/footer/div/div[5]/div[2]/p[1]/a"));
        actions.moveToElement(tillganglighetLink);
        actions.perform();
        tillganglighetLink.click();
        String websiteHeading = driver.findElement(By.xpath("//*[@id=\"__next\"]/div[2]/main/div/div/div[1]/h1")).getText();
        assertEquals("Så arbetar SVT med tillgänglighet", websiteHeading);
        driver.navigate().back();
    }

    @Test
    void checkFindElementById() {
        
        WebElement element = webDriverWait.until(ExpectedConditions.elementToBeClickable(By.id("play_a11y-announcer")));
        assertEquals(true, element.isDisplayed());
    }

    @Test
    void checkFindElementByXPath() {
        
        WebElement element = webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"play_a11y-announcer\"]")));
        assertEquals(true, element.isDisplayed());
    }

    @Test
    void checkFindElementByTagName() {
        
        WebElement element = webDriverWait.until(ExpectedConditions.elementToBeClickable(By.tagName("header")));
        assertEquals(true, element.isDisplayed());
    }

    @Test
    void checkFindElementByCssSelector() {
        
        WebElement element = webDriverWait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("#play_a11y-announcer")));
        assertEquals(true, element.isDisplayed());
    }

    @Test
    void checkSearchTheProgram() throws InterruptedException {
        WebElement element = webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.name("q")));
        Thread.sleep(1000);
        element.clear();
        element.sendKeys("Agenda");
        element.sendKeys(Keys.ENTER);
        Thread.sleep(1000);
        WebElement articleElement = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"play_main-content\"]/section/div/ul/li[1]/article/a")));
        assertEquals("https://www.svtplay.se/agenda", articleElement.getDomProperty("href"));
    }

    @Test
    void checkNumberOfEpisodesForASearch() throws InterruptedException {
        
        WebElement element = webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.name("q")));
        Thread.sleep(1000);
        element.sendKeys("Pistvakt");
        element.sendKeys(Keys.ENTER);
        WebElement articleElement = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"play_main-content\"]/section/div/ul/li[1]/article/a")));
        articleElement.click();
        WebElement season2Element = webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//*[@id=\"play_main-content\"]/div/div[3]/section[2]/h2/a")));
        actions
                .scrollToElement(season2Element)
                .perform();
        season2Element.click();
        List<WebElement> season2Episodes = driver.findElements(By.xpath("//*[@id=\"season-2-jx3za5B\"]/article"));

        driver.navigate().back();
        element.clear();
        assertEquals(6, season2Episodes.size());
    }

    @AfterAll
    static void teardown() {
        driver.quit();
    }
}