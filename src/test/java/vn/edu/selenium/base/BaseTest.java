package vn.edu.selenium.base;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * BaseTest quản lý vòng đời WebDriver, cấu hình trình duyệt Chrome,
 * explicit wait và hỗ trợ chụp ảnh màn hình theo đúng mẫu của đề bài.
 */
public class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;
    protected static final String BASE_URL = "https://demoqa.com/automation-practice-form";

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("--disable-notifications");
        options.addArguments("--disable-popup-blocking");
        // Kiểm tra biến môi trường hoặc system property headless nếu cần
        String headless = System.getProperty("headless", "false");
        if ("true".equalsIgnoreCase(headless)) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
        }

        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    @AfterMethod
    public void tearDown(ITestResult result) {
        if (driver != null) {
            // Tự động chụp màn hình khi test thất bại
            if (result.getStatus() == ITestResult.FAILURE) {
                try {
                    String path = captureScreen("FAILURE_" + result.getName());
                    System.err.println("[BaseTest] Test thất bại! Đã chụp màn hình lỗi tại: " + path);
                } catch (IOException e) {
                    System.err.println("[BaseTest] Lỗi khi chụp màn hình: " + e.getMessage());
                }
            }
            driver.quit();
        }
    }

    /**
     * Mẫu code chụp màn hình theo đúng yêu cầu đề bài Lab 6.
     * Lưu ảnh vào thư mục screenshots/ với tiền tố và dấu thời gian.
     */
    public String captureScreen(String prefix) throws IOException {
        File dir = new File("screenshots");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
        File dest = new File("screenshots/" + prefix + "_" + time + ".png");
        FileUtils.copyFile(src, dest);
        System.out.println("[Screenshot] Đã lưu ảnh: " + dest.getAbsolutePath());
        return dest.getAbsolutePath();
    }

    /**
     * Loại bỏ các banner quảng cáo cố định (fixedban) và footer của DemoQA
     * nhằm tránh tình trạng ElementClickInterceptedException.
     */
    public void cleanAdBanners() {
        try {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript(
                "const ad = document.getElementById('fixedban'); if(ad) ad.remove(); " +
                "const foot = document.querySelector('footer'); if(foot) foot.remove(); " +
                "const adContainers = document.querySelectorAll('iframe[id^=\"google_ads\"], [id*=\"google_ads\"]'); " +
                "adContainers.forEach(e => e.remove());"
            );
        } catch (Exception ignored) {
        }
    }
}
