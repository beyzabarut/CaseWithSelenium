package pages;

import org.openqa.selenium.support.PageFactory;
import utils.Utilities;

public class QualityAssurancePage extends Utilities {

    public QualityAssurancePage() {
        PageFactory.initElements(driver, this);
    }
}