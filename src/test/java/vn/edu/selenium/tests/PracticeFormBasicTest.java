package vn.edu.selenium.tests;

import org.testng.Assert;
import org.testng.annotations.Test;
import vn.edu.selenium.base.BaseTest;
import vn.edu.selenium.pages.PracticeFormPage;

import java.io.File;
import java.io.IOException;
import java.util.Map;

/**
 * Lớp kiểm thử các yêu cầu cơ bản 1 đến 5 của Lab 6:
 * - Yêu cầu 1: Mở trang và kiểm tra tiêu đề, URL
 * - Yêu cầu 2: Điền thông tin cá nhân và in ra console
 * - Yêu cầu 3: Chọn ngày sinh bằng date picker và kiểm tra định dạng
 * - Yêu cầu 4: Tải tệp lên form và kiểm tra tên tệp
 * - Yêu cầu 5: Kiểm tra thông báo/modal xác nhận sau khi submit
 */
public class PracticeFormBasicTest extends BaseTest {

    /**
     * Yêu cầu 1: Mở trang và kiểm tra tiêu đề
     * a. Mở trình duyệt Chrome.
     * b. Truy cập trang https://demoqa.com/automation-practice-form.
     * c. Kiểm tra URL hiện tại có đúng với trang đã yêu cầu.
     * d. Kiểm tra tiêu đề trang không rỗng.
     */
    @Test(priority = 1, description = "Yêu cầu 1: Kiểm tra mở trang, URL và Tiêu đề")
    public void testRequirement1_OpenPageAndCheckTitleAndUrl() throws IOException {
        System.out.println("\n=======================================================");
        System.out.println(">>> [YÊU CẦU 1] KIỂM TRA MỞ TRANG, URL VÀ TIÊU ĐỀ");
        System.out.println("=======================================================");

        PracticeFormPage page = new PracticeFormPage(driver);
        page.open();

        String currentUrl = page.getCurrentUrl();
        String title = page.getPageTitle();

        System.out.println("URL hiện tại: " + currentUrl);
        System.out.println("Tiêu đề trang: " + title);

        // Kiểm tra URL
        Assert.assertTrue(currentUrl.contains("demoqa.com/automation-practice-form"),
                "URL không đúng với trang yêu cầu! URL thực tế: " + currentUrl);

        // Kiểm tra tiêu đề trang không rỗng
        Assert.assertNotNull(title, "Tiêu đề trang bị null!");
        Assert.assertFalse(title.trim().isEmpty(), "Tiêu đề trang không được rỗng!");

        captureScreen("YC1_OpenPageSuccess");
        System.out.println(">>> YÊU CẦU 1 HOÀN THÀNH THÀNH CÔNG!\n");
    }

    /**
     * Yêu cầu 2: Điền thông tin cá nhân
     * a. Mở trang form thực hành.
     * b. Điền các trường: Họ, Tên, Email, Số điện thoại.
     * c. Nhập dữ liệu hợp lệ vào từng trường.
     * d. In ra console nội dung đã nhập.
     */
    @Test(priority = 2, description = "Yêu cầu 2: Điền thông tin cá nhân và in ra console")
    public void testRequirement2_FillPersonalInfo() throws IOException {
        System.out.println("\n=======================================================");
        System.out.println(">>> [YÊU CẦU 2] ĐIỀN THÔNG TIN CÁ NHÂN VÀ IN RA CONSOLE");
        System.out.println("=======================================================");

        PracticeFormPage page = new PracticeFormPage(driver);
        page.open();

        String firstName = "Bao";
        String lastName = "Nguyen Duc";
        String email = "ducbao.student@example.com";
        String mobile = "0987654321";

        page.setFirstName(firstName)
            .setLastName(lastName)
            .setEmail(email)
            .setMobile(mobile);

        // Lấy lại giá trị đã điền để kiểm tra
        String actualFirstName = page.getFirstNameValue();
        String actualLastName = page.getLastNameValue();
        String actualEmail = page.getEmailValue();
        String actualMobile = page.getMobileValue();

        System.out.println("--- DỮ LIỆU ĐÃ NHẬP VÀO FORM ---");
        System.out.println("Tên (First Name): " + actualFirstName);
        System.out.println("Họ (Last Name) : " + actualLastName);
        System.out.println("Email           : " + actualEmail);
        System.out.println("Số điện thoại   : " + actualMobile);

        Assert.assertEquals(actualFirstName, firstName, "First Name nhập vào không khớp!");
        Assert.assertEquals(actualLastName, lastName, "Last Name nhập vào không khớp!");
        Assert.assertEquals(actualEmail, email, "Email nhập vào không khớp!");
        Assert.assertEquals(actualMobile, mobile, "Mobile nhập vào không khớp!");

        captureScreen("YC2_PersonalInfoFilled");
        System.out.println(">>> YÊU CẦU 2 HOÀN THÀNH THÀNH CÔNG!\n");
    }

    /**
     * Yêu cầu 3: Chọn ngày sinh bằng date picker
     * a. Mở trang form thực hành.
     * b. Chọn trường ngày sinh bằng giao diện date picker.
     * c. Chọn đúng năm, tháng và ngày theo dữ liệu cho trước.
     * d. Kiểm tra giá trị hiển thị sau khi chọn có đúng định dạng không.
     */
    @Test(priority = 3, description = "Yêu cầu 3: Chọn ngày sinh bằng date picker")
    public void testRequirement3_SelectDateOfBirthDatePicker() throws IOException {
        System.out.println("\n=======================================================");
        System.out.println(">>> [YÊU CẦU 3] CHỌN NGÀY SINH BẰNG DATE PICKER");
        System.out.println("=======================================================");

        PracticeFormPage page = new PracticeFormPage(driver);
        page.open();

        String day = "15";
        String month = "May";
        String year = "2000";

        page.selectDateOfBirth(day, month, year);

        String dobValue = page.getDateOfBirthValue();
        System.out.println("Dữ liệu chọn: Ngày=" + day + ", Tháng=" + month + ", Năm=" + year);
        System.out.println("Giá trị hiển thị trong ô Ngày sinh: " + dobValue);

        // Kiểm tra hiển thị định dạng ngày tháng: ví dụ "15 May 2000"
        Assert.assertTrue(dobValue.contains(day), "Giá trị hiển thị không chứa đúng ngày!");
        Assert.assertTrue(dobValue.contains(month), "Giá trị hiển thị không chứa đúng tháng!");
        Assert.assertTrue(dobValue.contains(year), "Giá trị hiển thị không chứa đúng năm!");

        captureScreen("YC3_DatePickerSelected");
        System.out.println(">>> YÊU CẦU 3 HOÀN THÀNH THÀNH CÔNG!\n");
    }

    /**
     * Yêu cầu 4: Tải tệp lên form
     * a. Mở một trang web có chức năng upload file.
     * b. Chọn một tệp ảnh hoặc tệp văn bản từ máy tính.
     * c. Gửi đường dẫn file vào ô upload.
     * d. Kiểm tra tên file đã được tải lên thành công.
     */
    @Test(priority = 4, description = "Yêu cầu 4: Tải tệp lên form")
    public void testRequirement4_UploadFile() throws IOException {
        System.out.println("\n=======================================================");
        System.out.println(">>> [YÊU CẦU 4] TẢI TỆP LÊN FORM");
        System.out.println("=======================================================");

        PracticeFormPage page = new PracticeFormPage(driver);
        page.open();

        File sampleFile = new File("src/test/resources/sample_upload.txt");
        Assert.assertTrue(sampleFile.exists(), "Tệp kiểm thử upload không tồn tại: " + sampleFile.getAbsolutePath());

        System.out.println("Đường dẫn file tải lên: " + sampleFile.getAbsolutePath());
        page.uploadPicture(sampleFile.getAbsolutePath());

        String uploadedFileName = page.getUploadedFileName();
        System.out.println("Tên file ghi nhận trong trường upload: " + uploadedFileName);

        Assert.assertEquals(uploadedFileName, "sample_upload.txt", "Tên file tải lên không chính xác!");

        captureScreen("YC4_FileUploadSuccess");
        System.out.println(">>> YÊU CẦU 4 HOÀN THÀNH THÀNH CÔNG!\n");
    }

    /**
     * Yêu cầu 5: Kiểm tra thông báo xác nhận sau submit
     * a. Điền đầy đủ thông tin hợp lệ vào form.
     * b. Nhấn nút Submit.
     * c. Chờ thông báo hoặc modal xác nhận xuất hiện.
     * d. Kiểm tra nội dung xác nhận có đúng với dữ liệu đã nhập.
     * e. Ghi kết quả kiểm tra ra console.
     */
    @Test(priority = 5, description = "Yêu cầu 5: Kiểm tra thông báo xác nhận sau submit")
    public void testRequirement5_SubmitAndVerifyConfirmationModal() throws IOException {
        System.out.println("\n=======================================================");
        System.out.println(">>> [YÊU CẦU 5] KIỂM TRA THÔNG BÁO XÁC NHẬN SAU SUBMIT");
        System.out.println("=======================================================");

        PracticeFormPage page = new PracticeFormPage(driver);
        page.open();

        // 1. Điền thông tin
        String firstName = "Bao";
        String lastName = "Nguyen";
        String email = "nguyenbao@test.com";
        String mobile = "0912345678";
        String gender = "Male";

        page.setFirstName(firstName)
            .setLastName(lastName)
            .setEmail(email)
            .selectGender(gender)
            .setMobile(mobile)
            .selectDateOfBirth("20", "September", "2002");

        captureScreen("YC5_BeforeSubmit");

        // 2. Nhấn nút Submit
        page.submit();

        // 3. Chờ modal xuất hiện (explicit wait bên trong isModalDisplayed)
        boolean modalShown = page.isModalDisplayed();
        Assert.assertTrue(modalShown, "Modal xác nhận không xuất hiện sau khi submit!");

        String modalTitleText = page.getModalTitle();
        System.out.println("Tiêu đề modal xác nhận: " + modalTitleText);
        Assert.assertEquals(modalTitleText, "Thanks for submitting the form", "Tiêu đề modal không đúng!");

        // 4. Kiểm tra nội dung xác nhận có đúng với dữ liệu đã nhập
        Map<String, String> tableData = page.getModalTableResults();

        System.out.println("\n--- BẢNG XÁC NHẬN THÔNG TIN TỪ MODAL ---");
        tableData.forEach((label, val) -> System.out.printf("%-25s : %s\n", label, val));
        System.out.println("----------------------------------------\n");

        Assert.assertEquals(tableData.get("Student Name"), firstName + " " + lastName);
        Assert.assertEquals(tableData.get("Student Email"), email);
        Assert.assertEquals(tableData.get("Gender"), gender);
        Assert.assertEquals(tableData.get("Mobile"), mobile);

        captureScreen("YC5_AfterSubmit_Success");
        System.out.println(">>> YÊU CẦU 5 HOÀN THÀNH THÀNH CÔNG!\n");
    }
}
