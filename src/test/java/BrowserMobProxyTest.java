import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.proxy.ProxyServer;
import org.junit.*;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import pages.HomePage;
import pages.LoginPage;

import java.io.File;
import java.io.FileOutputStream;

/**
 * Created by user on 19.11.2016.
 */
public class BrowserMobProxyTest {

    private static String driverProp = "webdriver.chrome.driver";
    private static String pathToDriver = "C:\\Work\\chromedriver.exe";

    private static ProxyServer server;
    private static Proxy proxy;

    WebDriver driver;

    @BeforeClass
    public static void startProxyServer() throws Exception {
        System.setProperty(driverProp, pathToDriver);
        // запуск прокси сервера
        server = new ProxyServer(4444);
        server.start();

        // получение Selenium proxy
        proxy = server.seleniumProxy();
    }

    @Before
    public void createNewHar() {
        server.newHar("adukacyja.by");
    }

    @Test
    public void testMobProxyLogin() throws Exception {

        // конфигурация для использования прокси
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.PROXY, proxy);

        driver = new ChromeDriver(capabilities);

        String login = "";
        String password = "";

        HomePage homePage = new HomePage(driver);
        homePage.open().loginClick();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.loginAs(login, password);

        driver.quit();
    }

    @Test
    public void testMobProxySearch() throws Exception {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.PROXY, proxy);

        driver = new ChromeDriver(capabilities);

        HomePage homePage = new HomePage(driver);
        homePage.open();
        homePage.searchForCourse("ux");

        driver.quit();
    }

    @After
    public void saveHAR() throws Exception {
        Har har = server.getHar();
        File file = new File("results\\Test.har");
        if (!file.exists()) {
            file.createNewFile();
        }
        FileOutputStream fos = new FileOutputStream(file);
        try {
            har.writeTo(fos);
        }
        finally {
            fos.close();
        }
    }

    @AfterClass
    public static void stopProxyServer() throws Exception {
        server.stop();
    }

}
