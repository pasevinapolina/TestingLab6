import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by user on 19.11.2016.
 */
public class HomePage {

    private By loginLink = By.className("loginurl");
    private String siteToTest = "http://www.adukacyja.by/";

    private final WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public HomePage open() throws InterruptedException {
        driver.get(siteToTest);
        Thread.sleep(2000);
        return this;
    }

    public HomePage loginClick() throws InterruptedException {
        driver.findElement(loginLink).click();
        Thread.sleep(2000);
        return this;
    }

}
