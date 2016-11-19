import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * Created by user on 19.11.2016.
 */
public class HomePage {

    private By loginLink = By.className("loginurl");
    private By coursesLink = By.xpath(".//*[@id='expandable_branch_0_courses']/a");
    private By searchBox = By.id("coursesearchbox");
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

    public HomePage coursesClick() throws InterruptedException {
        driver.findElement(coursesLink).click();
        Thread.sleep(2000);
        return this;
    }

    public HomePage searchForCourse(String course) throws InterruptedException {
        coursesClick();
        driver.findElement(searchBox).sendKeys(course);
        driver.findElement(searchBox).submit();
        Thread.sleep(2000);
        return this;
    }

}
