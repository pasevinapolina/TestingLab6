import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by user on 19.11.2016.
 */
public class HomePage {

    private By loginLink = By.id("link-head-login");
    private String siteToTest = "http://eda.ru/";

    private final WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    public HomePage open() {
        driver.get(siteToTest);
        return this;
    }

    public HomePage loginClick() {
        driver.findElement(loginLink).click();
        return this;
    }

}
