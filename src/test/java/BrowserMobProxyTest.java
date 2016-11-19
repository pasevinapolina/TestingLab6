import net.lightbody.bmp.core.har.Har;
import net.lightbody.bmp.proxy.ProxyServer;
import org.junit.*;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * Created by user on 19.11.2016.
 */
public class BrowserMobProxyTest {

    private String driverProp = "webdriver.chrome.driver";
    private String pathToDriver = "C:\\Work\\chromedriver.exe";
    private String browserType = "chrome";

    @Test
    public void testMobProxyServer() throws Exception {
        System.setProperty(driverProp, pathToDriver);
        // запуск прокси сервера
        ProxyServer server = new ProxyServer(4444);
        server.start();

        // получение Selenium proxy
        Proxy proxy = server.seleniumProxy();

        // конфигурация для использования прокси
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.PROXY, proxy);


        WebDriver driver = new ChromeDriver(capabilities);

        // создание HAR с меткой "yandex.ru"
        server.newHar("yandex.ru");

        // открытие страницы
        driver.get("http://yandex.ru");

        // получение данных HAR
        Har har = server.getHar();

        // обработка полученных данных
        try {
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
        catch (IOException e) {
            // обработка ошибки
            e.printStackTrace();
        }
        finally {
            driver.quit();
            server.stop();
        }

        driver.quit();
        server.stop();
    }
}