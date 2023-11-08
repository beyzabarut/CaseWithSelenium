package pages;

import org.openqa.selenium.support.PageFactory;
import utils.Utilities;

public class HomePage extends Utilities {

    public HomePage(){
        PageFactory.initElements(driver, this);
    }

}
