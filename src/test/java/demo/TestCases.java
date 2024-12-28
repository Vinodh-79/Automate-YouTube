package demo;

import org.apache.commons.math3.geometry.partitioning.Side;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.v123.v123Domains;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import java.util.logging.Level;

import demo.utils.ExcelDataProvider;
// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;

public class TestCases extends ExcelDataProvider { // Lets us read the data
        ChromeDriver driver;
        /*
         * TODO: Write your tests here with testng @Test annotation.
         * Follow `testCase01` `testCase02`... format or what is provided in
         * instructions
         * Do not change the provided methods unless necessary, they will help in
         * automation and assessment
         */

        @Test(enabled = true)
        public void testCase01() throws InterruptedException {

                System.out.println("Beginning of Test Case 01");
                driver.get("https://www.youtube.com/");

                // Assert that we are on the correct URL
                String URLTEXT = "youtube";
                Assert.assertTrue(driver.getCurrentUrl().contains(URLTEXT), "URL does not contain 'youtube'");

                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

                WebElement Sidebarbutton = driver
                                .findElement(By.xpath("(//button[@id='button'])[7]"));
                Sidebarbutton.click();
                WebElement AboutBtn = wait
                                .until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='About']")));
                AboutBtn.click();

                Thread.sleep(2000);

                WebElement AboutBtnText = driver.findElement(By.xpath("//section[@class='ytabout__content']"));
                String Text = AboutBtnText.getText();
                System.out.println(Text);

                System.out.println("End of Test Case 01");
        }

        @Test(enabled = true)
        public void testCase02() throws InterruptedException {

                SoftAssert sa = new SoftAssert();

                System.out.println("Beginning of Test Case 02");

                driver.get("https://www.youtube.com/");

                WebElement Sidebarbutton = driver
                                .findElement(By.xpath("(//button[@id='button'])[7]"));
                Sidebarbutton.click();

                WebElement FilmsBtn = driver.findElement(By.xpath("//yt-formatted-string[text()='Movies']"));
                FilmsBtn.click();

                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

                Wrappers.clickTillUnclickable(driver, (By.xpath("//button[@aria-label='Next']")), 5);

                // Wait for the "Animation" movie element to be loaded and visible
                WebElement AnimationText = wait.until(ExpectedConditions.visibilityOfElementLocated(
                                By.xpath("//div[contains(@class,'yt-horizontal-list-renderer')]/ytd-grid-movie-renderer[16]//span[contains(@class,'grid-movie-renderer-metadata')]")));

                System.out.println("Animation Movie Text: " + AnimationText.getText().split(" ")[0]);

                // Wait for the "Mature Rating" (or another relevant element) to be loaded and
                // visible
                WebElement URatingText = wait.until(ExpectedConditions.visibilityOfElementLocated(
                                By.xpath("//div[contains(@class,'yt-horizontal-list-renderer')]/ytd-grid-movie-renderer[16]//div[contains(@class,'ytd-badge-supported-renderer')][2]/p")));
                System.out.println("Movie Rating Text: " + URatingText.getText());

                // Soft assert: Check that the movie category exists (like "Animation",
                // "Drama",// etc.)
                sa.assertTrue(AnimationText.getText().toLowerCase().contains("animation"),
                                "Movie is not in 'Animation' category.");
                System.out.println("End of Test Case 02");

                sa.assertAll();

        }

        @Test(enabled = true)
        public void testCase03() throws InterruptedException {
                SoftAssert sa = new SoftAssert();
                System.out.println("Beginning of Test Case 03");
                driver.get("https://www.youtube.com/");

                WebElement Sidebarbutton = driver
                                .findElement(By.xpath("(//button[@id='button'])[7]"));
                Sidebarbutton.click();

                Wrappers.findElementAndClick(driver, By.xpath("//yt-formatted-string[text()='Music']"));

                Wrappers.clickTillUnclickable(driver, By.xpath(
                                "(//span[contains(text(),'Biggest Hits')]/ancestor::div[@id='dismissible']//div[@id='right-arrow']//button)[1]"),
                                3);

                By Track_count = By.xpath(
                                "(//span[contains(text(),'Biggest Hits')]/ancestor::div[@id='dismissible'])[1]//div[@id='items']/yt-lockup-view-model//div[@class='badge-shape-wiz__text']");
                String result = Wrappers.findElementAndPrint(driver, Track_count,
                                driver.findElements(Track_count).size() - 1);
                System.out.println(result);
                Thread.sleep(2000);

                sa.assertTrue(Wrappers.convertToNumericValue(result.split(" ")[0]) <= 50);

                System.out.println("End of Test Case 03");
        }

        @Test(enabled = true)
        public void testCase04() throws InterruptedException {
                SoftAssert sa = new SoftAssert();
                System.out.println("Beginning of Test Case 04");
                driver.get("https://www.youtube.com/");

                WebElement Sidebarbutton = driver
                                .findElement(By.xpath("(//button[@id='button'])[7]"));
                Sidebarbutton.click();

                Wrappers.findElementAndClick(driver, By.xpath("//yt-formatted-string[text()='News']"));

                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

                WebElement ListOfLatestNews = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(
                                "//span[contains(text(),'Latest')]/ancestor::div[@id='dismissible']//ytd-rich-item-renderer")));

                long sumOfVotes = 0;
                for (int i = 0; i < 3; i++) {
                        System.out.println(Wrappers.findElementAndPrintWE(driver, By.xpath("//div[@id='header']"),
                                        ListOfLatestNews, i));
                        System.out.println(Wrappers.findElementAndPrintWE(driver, By.xpath("//div[@id='body']"),
                                        ListOfLatestNews, i));
                        try {
                                String Result = Wrappers.findElementAndPrintWE(driver,
                                                By.xpath("//span[@id='vote-count-middle']"), ListOfLatestNews, i);
                                sumOfVotes += Wrappers.convertToNumericValue(Result);

                        } catch (NoSuchElementException e) {
                                // TODO: handle exception
                                System.out.println("Votes Not Present " + e.getMessage());
                        }
                }
                System.out.println(sumOfVotes);
                System.out.println("End of Test Case 04");
        }

        @BeforeTest
        public void startBrowser() {
                System.setProperty("java.util.logging.config.file", "logging.properties");

                // NOT NEEDED FOR SELENIUM MANAGER
                // WebDriverManager.chromedriver().timeout(30).setup();

                ChromeOptions options = new ChromeOptions();
                LoggingPreferences logs = new LoggingPreferences();

                logs.enable(LogType.BROWSER, Level.ALL);
                logs.enable(LogType.DRIVER, Level.ALL);
                options.setCapability("goog:loggingPrefs", logs);
                options.addArguments("--remote-allow-origins=*");

                System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log");

                driver = new ChromeDriver(options);

                driver.manage().window().maximize();
        }

        @AfterTest
        public void endTest() {
                driver.close();
                driver.quit();

        }
}