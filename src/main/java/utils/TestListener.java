package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestListener;
import org.testng.ITestResult;
import pages.HomePage;

import java.io.File;
import java.io.IOException;

public class TestListener extends HomePage implements ITestListener {
    @Override
    public void onTestFailure(ITestResult result) {
        String methodName = result.getName();
        TakesScreenshot screenshot = (TakesScreenshot) driver;
        File file = screenshot.getScreenshotAs(OutputType.FILE);
        try{
            FileUtils.copyFile(file, new File("./failed-tests/" + methodName + ".png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        ITestListener.super.onTestFailure(result);
    }
}
