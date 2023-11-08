package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import utils.TestListener;
import utils.driver.Driver;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Listeners(TestListener.class)
public class HomePageTest{
    TestBase testBase = new TestBase();
    Properties prop = new Properties();
    String expectedUrl;
    String homePageName = "home";

    @BeforeClass
    public void getProperty(){
        try {
            prop.load(new FileInputStream("src/main/resources/test.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        expectedUrl = prop.getProperty("url.home");
    }

    @Test
    public void isPageOpened(){
        testBase.startSession(homePageName);
        try{
            Assert.assertEquals(expectedUrl, Driver.driver.getCurrentUrl());
            System.out.println("Navigated to correct webpage");
        }
        catch(Throwable pageNavigationError){
            System.out.println("Didn't navigate to correct webpage");
        }

        WebElement companyDropdown = Driver.driver.findElement(By.xpath("//*[@id=\"navbarNavDropdown\"]/ul[1]/li[6]"));
        companyDropdown.click();
        testBase.waitFor(2);

        WebElement careers = Driver.driver.findElement(By.xpath("//*[@id=\"navbarNavDropdown\"]/ul[1]/li[6]/div/div[2]/a[2]"));
        careers.click();
        testBase.waitFor(1);

        WebElement seeAllTeamsButton = Driver.driver.findElement(By.xpath("//*[@id=\"career-find-our-calling\"]/div/div/a"));
        ((JavascriptExecutor) Driver.driver).executeScript("arguments[0].scrollIntoView(true);", seeAllTeamsButton);
        testBase.waitFor(2);

        WebElement ourLocationText = Driver.driver.findElement(By.xpath("//*[@id=\"career-our-location\"]/div/div/div/div[1]/h3"));
        ((JavascriptExecutor) Driver.driver).executeScript("arguments[0].scrollIntoView(true);", ourLocationText);
        testBase.waitFor(2);

        WebElement lifeAtInsiderText = Driver.driver.findElement(By.xpath("/html/body/div[1]/section[4]/div/div/div/div[1]/div/h2"));
        ((JavascriptExecutor) Driver.driver).executeScript("arguments[0].scrollIntoView(true);", lifeAtInsiderText);
        testBase.waitFor(2);
    }

    @AfterTest
    public void closeSession(){
        Driver.driver.close();
    }
}