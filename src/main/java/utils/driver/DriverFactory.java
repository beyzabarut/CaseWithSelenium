package utils.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class DriverFactory {
    public static WebDriver getDriver(String driverName, WebDriver driver){
        Properties properties = new Properties();

        try{
            properties.load(new FileReader("src/main/resources/test.properties"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        if(driver == null)
            driverName = properties.getProperty("browser");

        switch (driverName.toLowerCase()){
            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/geckodriver");
                driver = new FirefoxDriver(firefoxOptions);
                break;
            default:
            case "chrome":
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("disable-notifications");
                System.setProperty("webdriver.chrome.driver", "src/main/resources/drivers/chromedriver");
                driver = new ChromeDriver(chromeOptions);
                break;
        }
        driver.manage().window().maximize();
        return driver;

    }
}
