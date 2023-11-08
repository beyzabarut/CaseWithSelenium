package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.TestListener;
import utils.driver.Driver;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


import static utils.driver.Driver.driver;

@Listeners(TestListener.class)
public class QualityAssurancePageTest {
    Properties prop = new Properties();
    TestBase testBase = new TestBase();
    String expectedUrl;
    Boolean isNewTab = false;
    String QAPageName = "qa";

    @BeforeClass
    public void getProperty() {
        try {
            prop.load(new FileInputStream("src/main/resources/test.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        expectedUrl = prop.getProperty("url.qa");
    }


    @Test
    public void qualityAssuranceCases() {
        testBase.startQAPageSession(QAPageName);
        WebElement acceptButton = driver.findElement(By.cssSelector("[id='wt-cli-accept-btn']"));
        acceptButton.click();
        try {
            Assert.assertEquals(expectedUrl, Driver.driver.getCurrentUrl());
        } catch (Throwable pageNavigationError) {
            System.out.println("Didn't navigate to correct webpage");
        }

        WebElement seeAllQAjobs = driver.findElement(By.xpath("//*[@id=\"page-head\"]/div/div/div[1]/div/div/a"));
        seeAllQAjobs.click();
        testBase.waitFor(2);

        WebElement allJobs = driver.findElement(By.xpath("//*[@id=\"select2-filter-by-location-container\"]"));
        allJobs.click();
        testBase.waitFor(2);

        List<WebElement> elementsInTheList = driver.findElements(By.cssSelector("[class='select2-results__option'"));
        for (WebElement element : elementsInTheList) {
            if (element.getText().equalsIgnoreCase("Istanbul, Turkey")) {
                try {
                    element.click();
                    testBase.waitFor(2);
                    break;
                } catch (StaleElementReferenceException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        List<WebElement> positions = driver.findElements(By.cssSelector("[class='position-title font-weight-bold'"));
        for (WebElement position : positions) {
            if (!position.getText().contains("Quality Assurance")) {
                System.out.println("Every text does not contain Quality Assurance");
                break;
            }
        }


        List<WebElement> departments = driver.findElements(By.cssSelector("[class='position-department text-large font-weight-600 text-primary']"));
        for (WebElement department : departments) {
            if (!department.getText().contains("Quality Assurance")) {
                System.out.println("Every text does not contain Quality Assurance");
                break;
            }
        }

        List<WebElement> locations = driver.findElements(By.cssSelector("[class='position-location text-large']"));
        for (WebElement location : locations) {
            if (!location.getText().contains("Istanbul, Turkey")) {
                System.out.println("Every text does not contain Istanbul, Turkey");
                break;
            }
        }

        WebElement viewRoleButton = driver.findElement(By.cssSelector("[class='btn btn-navy rounded pt-2 pr-5 pb-2 pl-5']"));
        WebElement openPositionText = driver.findElement(By.xpath("//*[@id=\"career-position-list\"]/div/div/div[1]/h3"));
        testBase.waitFor(1);

        ((JavascriptExecutor) Driver.driver).executeScript("arguments[0].scrollIntoView(true);", openPositionText);
        testBase.waitFor(1);

        WebElement viewButton = driver.findElement(By.cssSelector("[class='position-location text-large']"));
        Actions action = new Actions(Driver.driver);
        WebElement viewButtonElement = new WebDriverWait(driver, Duration.ofSeconds(1L)).until(ExpectedConditions.visibilityOf(viewButton));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView();", viewButtonElement);
        action.moveToElement(viewRoleButton).build().perform();
        viewRoleButton.click();
        testBase.waitFor(1);

        String currentPageHandle = driver.getWindowHandle();
        ArrayList<String> tabHandles = new ArrayList<>(driver.getWindowHandles());

        for (String eachHandle : tabHandles) {
            driver.switchTo().window(eachHandle);
            if (driver.getCurrentUrl().contains("https://jobs.lever.co/")) {
                driver.close();
                driver.switchTo().window(currentPageHandle);
                isNewTab = true;
            }
        }
        if (isNewTab) {
            System.out.println("New page opened");
        }
    }

    @AfterTest
    public void closeSession() {
        driver.close();
    }
}