package vn.edu.selenium.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import vn.edu.selenium.models.Student;

import java.io.File;
import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Page Object đại diện cho trang DemoQA Student Registration Form.
 * Đóng gói toàn bộ Locators và Actions của form theo chuẩn Page Object Model (POM).
 */
public class PracticeFormPage {
    private final WebDriver driver;
    private final WebDriverWait wait;
    private final JavascriptExecutor js;

    public static final String PAGE_URL = "https://demoqa.com/automation-practice-form";

    // --- Locators ---
    // Thông tin cá nhân
    private final By firstNameInput = By.id("firstName");
    private final By lastNameInput = By.id("lastName");
    private final By emailInput = By.id("userEmail");
    private final By mobileInput = By.id("userNumber");

    // Giới tính
    private final By genderMaleLabel = By.xpath("//label[@for='gender-radio-1']");
    private final By genderFemaleLabel = By.xpath("//label[@for='gender-radio-2']");
    private final By genderOtherLabel = By.xpath("//label[@for='gender-radio-3']");

    // Ngày sinh (Datepicker)
    private final By dateOfBirthInput = By.id("dateOfBirthInput");
    private final By monthSelect = By.className("react-datepicker__month-select");
    private final By yearSelect = By.className("react-datepicker__year-select");

    // Môn học & Sở thích
    private final By subjectsInput = By.id("subjectsInput");
    private final By hobbySportsLabel = By.xpath("//label[@for='hobbies-checkbox-1']");
    private final By hobbyReadingLabel = By.xpath("//label[@for='hobbies-checkbox-2']");
    private final By hobbyMusicLabel = By.xpath("//label[@for='hobbies-checkbox-3']");

    // Upload file
    private final By uploadPictureInput = By.id("uploadPicture");

    // Địa chỉ, Tỉnh/Thành phố
    private final By currentAddressInput = By.id("currentAddress");
    private final By stateContainer = By.id("state");
    private final By stateInput = By.id("react-select-3-input");
    private final By cityContainer = By.id("city");
    private final By cityInput = By.id("react-select-4-input");

    // Submit
    private final By submitButton = By.id("submit");

    // Modal xác nhận sau submit
    private final By modalDialog = By.className("modal-content");
    private final By modalTitle = By.id("example-modal-sizes-title-lg");
    private final By modalTableRows = By.xpath("//div[@class='modal-body']//table//tbody//tr");
    private final By closeModalButton = By.id("closeLargeModal");

    public PracticeFormPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        this.js = (JavascriptExecutor) driver;
    }

    // --- Actions ---

    /**
     * Mở trang DemoQA Practice Form và xóa các banner quảng cáo gây che khuất.
     */
    public PracticeFormPage open() {
        driver.get(PAGE_URL);
        cleanAds();
        // Explicit Wait 1: Đợi ô nhập First Name hiển thị sẵn sàng tương tác
        wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameInput));
        return this;
    }

    public void cleanAds() {
        try {
            js.executeScript(
                "const ad = document.getElementById('fixedban'); if(ad) ad.remove(); " +
                "const foot = document.querySelector('footer'); if(foot) foot.remove(); " +
                "const adContainers = document.querySelectorAll('iframe[id^=\"google_ads\"], [id*=\"google_ads\"]'); " +
                "adContainers.forEach(e => e.remove());"
            );
        } catch (Exception ignored) {
        }
    }

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }

    public String getPageTitle() {
        return driver.getTitle();
    }

    // Điền thông tin cá nhân (Yêu cầu 2)
    public PracticeFormPage setFirstName(String firstName) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(firstNameInput));
        element.clear();
        element.sendKeys(firstName);
        return this;
    }

    public String getFirstNameValue() {
        return driver.findElement(firstNameInput).getAttribute("value");
    }

    public PracticeFormPage setLastName(String lastName) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(lastNameInput));
        element.clear();
        element.sendKeys(lastName);
        return this;
    }

    public String getLastNameValue() {
        return driver.findElement(lastNameInput).getAttribute("value");
    }

    public PracticeFormPage setEmail(String email) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(emailInput));
        element.clear();
        element.sendKeys(email);
        return this;
    }

    public String getEmailValue() {
        return driver.findElement(emailInput).getAttribute("value");
    }

    public PracticeFormPage setMobile(String mobile) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(mobileInput));
        element.clear();
        element.sendKeys(mobile);
        return this;
    }

    public String getMobileValue() {
        return driver.findElement(mobileInput).getAttribute("value");
    }

    public PracticeFormPage selectGender(String gender) {
        By targetLabel;
        if ("Female".equalsIgnoreCase(gender)) {
            targetLabel = genderFemaleLabel;
        } else if ("Other".equalsIgnoreCase(gender)) {
            targetLabel = genderOtherLabel;
        } else {
            targetLabel = genderMaleLabel;
        }
        WebElement label = wait.until(ExpectedConditions.presenceOfElementLocated(targetLabel));
        scrollIntoView(label);
        clickElement(label);
        return this;
    }

    // Chọn ngày sinh bằng date picker (Yêu cầu 3)
    public PracticeFormPage selectDateOfBirth(String day, String month, String year) {
        WebElement dobInput = wait.until(ExpectedConditions.elementToBeClickable(dateOfBirthInput));
        scrollIntoView(dobInput);
        clickElement(dobInput);

        // Explicit Wait: Đợi datepicker hiển thị
        wait.until(ExpectedConditions.visibilityOfElementLocated(monthSelect));

        // Chọn năm
        Select selectYear = new Select(driver.findElement(yearSelect));
        selectYear.selectByValue(year);

        // Chọn tháng (tháng có thể là text như "May" hoặc index)
        Select selectMonth = new Select(driver.findElement(monthSelect));
        try {
            selectMonth.selectByVisibleText(month);
        } catch (Exception e) {
            selectMonth.selectByValue(month);
        }

        // Chọn ngày trong tháng hiện tại (tránh ngày của tháng trước/sau)
        int dayNum = Integer.parseInt(day);
        By dayLocator = By.cssSelector(
            ".react-datepicker__day--0" + (dayNum < 10 ? "0" + dayNum : dayNum) +
            ":not(.react-datepicker__day--outside-month)"
        );

        WebElement dayElement = wait.until(ExpectedConditions.elementToBeClickable(dayLocator));
        clickElement(dayElement);

        return this;
    }

    public String getDateOfBirthValue() {
        return driver.findElement(dateOfBirthInput).getAttribute("value");
    }

    // Nhập môn học (Subjects)
    public PracticeFormPage addSubject(String subject) {
        if (subject == null || subject.trim().isEmpty()) return this;
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(subjectsInput));
        scrollIntoView(element);
        element.sendKeys(subject);
        element.sendKeys(Keys.ENTER);
        return this;
    }

    public PracticeFormPage addSubjects(List<String> subjects) {
        if (subjects != null) {
            for (String sub : subjects) {
                addSubject(sub);
            }
        }
        return this;
    }

    // Chọn sở thích (Hobbies)
    public PracticeFormPage selectHobby(String hobby) {
        if (hobby == null) return this;
        By targetHobby = null;
        if ("Sports".equalsIgnoreCase(hobby)) {
            targetHobby = hobbySportsLabel;
        } else if ("Reading".equalsIgnoreCase(hobby)) {
            targetHobby = hobbyReadingLabel;
        } else if ("Music".equalsIgnoreCase(hobby)) {
            targetHobby = hobbyMusicLabel;
        }

        if (targetHobby != null) {
            WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(targetHobby));
            scrollIntoView(element);
            clickElement(element);
        }
        return this;
    }

    public PracticeFormPage selectHobbies(List<String> hobbies) {
        if (hobbies != null) {
            for (String h : hobbies) {
                selectHobby(h);
            }
        }
        return this;
    }

    // Upload file (Yêu cầu 4)
    public PracticeFormPage uploadPicture(String filePath) {
        if (filePath != null && !filePath.isEmpty()) {
            File file = new File(filePath);
            String absolutePath = file.getAbsolutePath();
            WebElement uploadElement = driver.findElement(uploadPictureInput);
            uploadElement.sendKeys(absolutePath);
        }
        return this;
    }

    public String getUploadedFileName() {
        String fullPath = driver.findElement(uploadPictureInput).getAttribute("value");
        if (fullPath == null || fullPath.isEmpty()) return "";
        // Giá trị trình duyệt trả về dạng C:\fakepath\filename.ext
        return fullPath.substring(fullPath.lastIndexOf('\\') + 1);
    }

    // Địa chỉ hiện tại
    public PracticeFormPage setCurrentAddress(String address) {
        if (address != null && !address.isEmpty()) {
            WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(currentAddressInput));
            element.clear();
            element.sendKeys(address);
        }
        return this;
    }

    // Chọn State & City
    public PracticeFormPage selectStateAndCity(String state, String city) {
        if (state != null && !state.isEmpty()) {
            WebElement stateEl = wait.until(ExpectedConditions.presenceOfElementLocated(stateContainer));
            scrollIntoView(stateEl);
            WebElement stateInputEl = driver.findElement(stateInput);
            stateInputEl.sendKeys(state);
            stateInputEl.sendKeys(Keys.ENTER);
        }
        if (city != null && !city.isEmpty()) {
            WebElement cityEl = wait.until(ExpectedConditions.presenceOfElementLocated(cityContainer));
            scrollIntoView(cityEl);
            WebElement cityInputEl = driver.findElement(cityInput);
            cityInputEl.sendKeys(city);
            cityInputEl.sendKeys(Keys.ENTER);
        }
        return this;
    }

    // Gửi form (Submit)
    public PracticeFormPage submit() {
        cleanAds();
        WebElement submitEl = wait.until(ExpectedConditions.presenceOfElementLocated(submitButton));
        scrollIntoView(submitEl);
        clickElement(submitEl);
        return this;
    }

    // Kiểm tra modal xác nhận sau submit (Yêu cầu 5)
    public boolean isModalDisplayed() {
        try {
            // Explicit Wait 2: Chờ modal xác nhận xuất hiện sau khi submit
            wait.until(ExpectedConditions.visibilityOfElementLocated(modalDialog));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getModalTitle() {
        WebElement titleEl = wait.until(ExpectedConditions.visibilityOfElementLocated(modalTitle));
        return titleEl.getText();
    }

    /**
     * Trích xuất toàn bộ dữ liệu xác nhận trong bảng kết quả modal.
     * @return Map lưu trữ (Tên trường -> Giá trị)
     */
    public Map<String, String> getModalTableResults() {
        Map<String, String> results = new LinkedHashMap<>();
        List<WebElement> rows = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(modalTableRows));
        for (WebElement row : rows) {
            List<WebElement> cols = row.findElements(By.tagName("td"));
            if (cols.size() >= 2) {
                String label = cols.get(0).getText().trim();
                String value = cols.get(1).getText().trim();
                results.put(label, value);
            }
        }
        return results;
    }

    public void closeModal() {
        try {
            WebElement closeBtn = wait.until(ExpectedConditions.elementToBeClickable(closeModalButton));
            scrollIntoView(closeBtn);
            clickElement(closeBtn);
        } catch (Exception ignored) {
        }
    }

    /**
     * Điền toàn bộ thông tin từ đối tượng Student và submit form.
     */
    public void fillAndSubmitForm(Student student) {
        setFirstName(student.getFirstName());
        setLastName(student.getLastName());
        setEmail(student.getEmail());
        selectGender(student.getGender());
        setMobile(student.getMobile());
        if (student.getBirthDay() != null && student.getBirthMonth() != null && student.getBirthYear() != null) {
            selectDateOfBirth(student.getBirthDay(), student.getBirthMonth(), student.getBirthYear());
        }
        addSubjects(student.getSubjects());
        selectHobbies(student.getHobbies());
        if (student.getPicturePath() != null) {
            uploadPicture(student.getPicturePath());
        }
        setCurrentAddress(student.getCurrentAddress());
        selectStateAndCity(student.getState(), student.getCity());
        submit();
    }

    // Helper cuộn màn hình
    private void scrollIntoView(WebElement element) {
        js.executeScript("arguments[0].scrollIntoView({behavior: 'instant', block: 'center'});", element);
    }

    // Helper click an toàn tránh click bị cản bởi ad/overlay
    private void clickElement(WebElement element) {
        try {
            element.click();
        } catch (Exception e) {
            js.executeScript("arguments[0].click();", element);
        }
    }
}
