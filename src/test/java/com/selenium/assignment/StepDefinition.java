package com.selenium.assignment;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.jupiter.api.Assertions;
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

import static org.junit.jupiter.api.Assertions.*;

public class StepDefinition {

    private static WebDriver driver;

    private static WebDriverWait webDriverWait;

    @Given("Visit SVT Play")
    public void svt_play_is_available() {
        System.setProperty("webdriver.chrome.driver", "/Users/sachinmehta/chromedriver_mac64_latest/chromedriver");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        driver = new ChromeDriver(options);
        driver.get("https://svtplay.se");
        webDriverWait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        WebElement cookieModel = driver.findElement(By.xpath("//*[@data-rt='cookie-consent-modal']"));
        webDriverWait.until(ExpectedConditions.visibilityOf(cookieModel));
        driver.findElement(By.xpath("//button[text()='Acceptera alla']")).click();
        webDriverWait.until(ExpectedConditions.invisibilityOf(cookieModel));
    }

    @Then("Verify the Page Title")
    public void the_title_should_be_available() {
        String title = driver.getTitle();
        String pageTitle = "SVT Play";
        assertEquals(pageTitle , title);
    }

    @Then("Verify the Page Logo")
    public void verify_the_page_logo() {
        driver.manage().window().maximize();
        boolean logoPresent = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[3]/div/header/div[2]/div/div/nav/a")).isDisplayed();
        Assertions.assertTrue(logoPresent);
    }

    @Then("Verify the Menu Links")
    public void the_links_should_be_available() {
        driver.manage().window().maximize();
        WebElement startMenuElement = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[3]/div/header/div[2]/div/div/nav/ul/li[1]/a"));
        WebElement programMenuElement = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[3]/div/header/div[2]/div/div/nav/ul/li[2]/a"));
        WebElement kanalerMenuElement = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[3]/div/header/div[2]/div/div/nav/ul/li[3]/a"));
        Assertions.assertEquals("START", startMenuElement.getAccessibleName());
        Assertions.assertEquals("PROGRAM", programMenuElement.getAccessibleName());
        Assertions.assertEquals("KANALER", kanalerMenuElement.getAccessibleName());
    }

    @Then("Verify the Tillgänglighet i SVT Play")
    public void the_link_should_be_available() {
        driver.manage().window().maximize();
        WebElement startMenuElement = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[3]/div/footer/div/div[5]/div[2]/p[1]/a/span[2]"));
        new Actions(driver)
                .scrollToElement(startMenuElement)
                .perform();
        Assertions.assertTrue(startMenuElement.isDisplayed());
        Assertions.assertEquals("Tillgänglighet i SVT Play", startMenuElement.getText());
    }

    @Then("Click the TillganglighetLink")
    public void click_tillganglighetlink() {
        driver.manage().window().maximize();
        WebElement startMenuElement = driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[3]/div/footer/div/div[5]/div[2]/p[1]/a/span[2]"));
        new Actions(driver)
                .scrollToElement(startMenuElement)
                .perform();

        new WebDriverWait(driver, Duration.ofSeconds(20)).until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"__next\"]/div/div[3]/div/footer/div/div[5]/div[2]/p[1]/a"))).click();
    }
    @Then("Verify the Click On TillganglighetLink")
    public void the_main_heading_should_be_available() {
        String websiteHeading = driver.findElement(By.xpath("//*[@id=\"__next\"]/div[2]/main/div/div/div[1]/h1")).getText();
        assertEquals("Så arbetar SVT med tillgänglighet", websiteHeading);
    }

    @Then("Verify the Nevigatation of Programs Page")
    public void the_program_should_be_clickable() {
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

    @When("Click the program tab")
    public void click_program_tab() {
        WebElement link = driver.findElement(By.linkText("PROGRAM"));
        link.click();
    }

    @Then("Verify the categories are displayed")
    public void verifyTheCategoriesAreDisplayed() {
        String pageTitle = "Program A-Ö | SVT Play";
        webDriverWait.until(ExpectedConditions.titleIs(pageTitle));
        String title = driver.getTitle();
        assertEquals(title, pageTitle);
        List<WebElement> categories = driver.findElements(By.xpath("//*[@data-rt='grid']//article"));
        assertEquals(categories.size(), 17);
    }

    @When("Click on the Search tab")
    public void Click_on_the_sök_tab() {
        WebElement link = driver.findElement(By.name("q"));
        link.click();
    }

    @When("Provide input as musik")
    public void provide_input_as_musik() {
        WebElement link = driver.findElement(By.name("q"));
        link.click();
        link.sendKeys("Musik");
    }

    @When("Click the enter")
    public void click_enter() {
        WebElement search = driver.findElement(By.name("q"));
        search.sendKeys(Keys.ENTER);
    }

    @Then("Verify the musik categories are displayed")
    public void verifyTheMusikCategoriesAreDisplayed() {
        WebElement article = webDriverWait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//*[@id=\"play_main-content\"]/section/div/ul/li[1]/article"))));
        assertTrue(article.isDisplayed());
    }

    @Then("Validates the title of the Humor category page")
    public void verify_the_humor_category() {
        String pageTitle = "Humor | SVT Play";
        webDriverWait.until(ExpectedConditions.titleIs(pageTitle));
        String title = driver.getTitle();
        assertEquals(title, pageTitle);
    }

    @When("Click on SVT logo on other Page")
    public void click_on_svt_logo_on_other_page() {
        WebElement svtLogo = webDriverWait.until(
                ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[3]/div/header/div[2]/div/div/nav/a"))));
        svtLogo.click();
    }

    @When("Click on Humor Link")
    public void click_on_the_humor_link() {
        WebElement humorLink = webDriverWait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[3]/div/footer/div/div[1]/nav/div[3]/div[2]/p/a"))));
        humorLink.click();
    }

    @When("Click on Next Arrow Button")
    public void click_on_article() {
        WebElement nextArrowButton = webDriverWait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//*[@id=\"play_main-content\"]/div/section[1]/div/div/div[1]/div[2]/button"))));
        nextArrowButton.click();
    }

    @Then("Back Arrow Button should be visible")
    public void back_button_should_be_displayed() {
        WebElement backArrowButton = webDriverWait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//*[@id=\"play_main-content\"]/div/section[1]/div/div/div[1]/div[1]/button"))));
        assertTrue(backArrowButton.isEnabled());
    }

    @When("Scroll to Footer")
    public void scroll_to_footer() {
        WebElement footerElement = webDriverWait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.tagName("Footer"))));
        new Actions(driver)
                .scrollToElement(footerElement)
                .perform();
    }

    @Then("Contact us should be visible in footer")
    public void contact_us_should_be_visible_on_footer() {
        WebElement contactLink = webDriverWait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[3]/div/footer/div/div[3]/a[2]"))));
        assertEquals("KONTAKT , ÖPPNAR ANNAN WEBBPLATS", contactLink.getAccessibleName());
    }

    @When("Click on contact us")
    public void click_on_contact_us() {
        WebElement contactLink = webDriverWait.until(ExpectedConditions.elementToBeClickable(driver.findElement(By.xpath("//*[@id=\"__next\"]/div/div[3]/div/footer/div/div[3]/a[2]"))));
        contactLink.click();
    }

    @Then("Verify title on contact us")
    public void verify_title_on_contact_us() {
        String title = driver.getTitle();
        String pageTitle = "Hej \uD83D\uDC4B Hur kan vi hjälpa dig? | SVT Kontakt";
        assertEquals(pageTitle , title);
    }


    @Then("close the driver")
    public void close_driver() {
        driver.close();
    }


}
