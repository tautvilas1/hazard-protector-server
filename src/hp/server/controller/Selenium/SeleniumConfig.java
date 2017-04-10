package hp.server.controller.Selenium;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.io.File;

/**
 * Created by Tautvilas on 09/04/2017.
 */
public class SeleniumConfig
{
    public WebDriver driver;

    public SeleniumConfig()
    {
        System.setProperty("webdriver.gecko.driver", "C:\\Users\\Tautvilas\\Documents\\Final Project\\Server v1\\src\\geckodriver\\geckodriver.exe");
        FirefoxProfile ffProfile = new FirefoxProfile();
        File adBlock = new File("C:\\Users\\Tautvilas\\Documents\\Final Project\\Server v1\\src\\geckodriver\\adblock.xpi");
        ffProfile.addExtension(adBlock);
        driver = new FirefoxDriver(ffProfile);
    }
}
