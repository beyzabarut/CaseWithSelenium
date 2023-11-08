package tests;

import pages.HomePage;
import pages.QualityAssurancePage;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class TestBase {
    public void startSession(String pageName) {
        HomePage homePage = new HomePage();
        homePage.initialize();
        homePage.predeterminedNavigate(pageName);
    }

    public void startQAPageSession(String pageName) {
        QualityAssurancePage qualityAssurancePageTest = new QualityAssurancePage();
        qualityAssurancePageTest.initialize();
        qualityAssurancePageTest.predeterminedNavigate(pageName);
    }

    public void waitFor(double seconds) {
        try {
            Thread.sleep((long) seconds * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}

