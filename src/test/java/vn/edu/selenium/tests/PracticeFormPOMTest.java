package vn.edu.selenium.tests;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import vn.edu.selenium.base.BaseTest;
import vn.edu.selenium.models.Student;
import vn.edu.selenium.pages.PracticeFormPage;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

/**
 * Lớp kiểm thử nâng cao hoàn chỉnh theo các yêu cầu 6, 7 và 8 của Lab 6:
 * - Yêu cầu 6: Áp dụng chặt chẽ mô hình Page Object Model (POM).
 * - Yêu cầu 7: Kiểm thử Data-Driven với ít nhất 3 bộ dữ liệu đầu vào khác nhau.
 * - Yêu cầu 8: Tự động hóa toàn bộ quy trình của form, sử dụng Explicit Wait,
 *   chụp ảnh trước & sau submit, in kết quả và đối chiếu chi tiết.
 */
public class PracticeFormPOMTest extends BaseTest {

    private static final String UPLOAD_FILE_PATH = new File("src/test/resources/sample_upload.txt").getAbsolutePath();

    /**
     * Yêu cầu 7: DataProvider cung cấp ít nhất 3 bộ dữ liệu khác nhau.
     */
    @DataProvider(name = "studentDataProvider")
    public Object[][] getStudentData() {
        // Bộ dữ liệu 1: Nam, ngành Khoa học Tự nhiên
        Student student1 = new Student(
                "Duc",
                "Nguyen",
                "duc.nguyen@testmail.com",
                "Male",
                "0988112233",
                "10",
                "January",
                "2001",
                Arrays.asList("Maths", "Computer Science"),
                Arrays.asList("Sports", "Reading"),
                UPLOAD_FILE_PATH,
                "123 Nguyen Hue, Quan 1, TP Ho Chi Minh",
                "NCR",
                "Delhi"
        );

        // Bộ dữ liệu 2: Nữ, ngành Kinh tế & Nghệ thuật
        Student student2 = new Student(
                "Linh",
                "Tran Thi",
                "linh.tran@testmail.com",
                "Female",
                "0977445566",
                "25",
                "August",
                "2002",
                Arrays.asList("Commerce", "Accounting"),
                Arrays.asList("Reading", "Music"),
                UPLOAD_FILE_PATH,
                "456 Le Duan, Quan Hai Chau, Da Nang",
                "Uttar Pradesh",
                "Agra"
        );

        // Bộ dữ liệu 3: Giới tính Khác, ngành Xã hội
        Student student3 = new Student(
                "Minh",
                "Le Quang",
                "minh.le@testmail.com",
                "Other",
                "0911889900",
                "05",
                "November",
                "2000",
                Collections.singletonList("Social Studies"),
                Collections.singletonList("Music"),
                UPLOAD_FILE_PATH,
                "789 Hoang Hoa Tham, Ba Dinh, Ha Noi",
                "Haryana",
                "Karnal"
        );

        return new Object[][]{
                {"Test Case 1 - Sinh viên Nam (NCR/Delhi)", student1},
                {"Test Case 2 - Sinh viên Nữ (Uttar Pradesh/Agra)", student2},
                {"Test Case 3 - Sinh viên Khác (Haryana/Karnal)", student3}
        };
    }

    /**
     * Yêu cầu 6, 7 & 8: Chạy kiểm thử tự động toàn diện với từng bộ dữ liệu.
     * Áp dụng Page Object Model hoàn chỉnh.
     */
    @Test(dataProvider = "studentDataProvider", description = "Kiểm thử form hoàn chỉnh với nhiều bộ dữ liệu (Data-Driven)")
    public void testCompletePracticeFormFlow(String testCaseName, Student student) throws IOException {
        System.out.println("\n=======================================================");
        System.out.println(">>> BẮT ĐẦU: " + testCaseName);
        System.out.println("Dữ liệu đầu vào: " + student);
        System.out.println("=======================================================");

        PracticeFormPage practiceForm = new PracticeFormPage(driver);

        // 1. Mở trang form thực hành
        practiceForm.open();

        // 2. Điền thông tin cá nhân
        practiceForm.setFirstName(student.getFirstName())
                    .setLastName(student.getLastName())
                    .setEmail(student.getEmail())
                    .selectGender(student.getGender())
                    .setMobile(student.getMobile());

        // 3. Chọn ngày sinh bằng date picker
        practiceForm.selectDateOfBirth(student.getBirthDay(), student.getBirthMonth(), student.getBirthYear());

        // 4. Chọn môn học (Subjects)
        practiceForm.addSubjects(student.getSubjects());

        // 5. Chọn sở thích (Hobbies)
        practiceForm.selectHobbies(student.getHobbies());

        // 6. Tải tệp lên form (File upload)
        practiceForm.uploadPicture(student.getPicturePath());

        // 7. Nhập địa chỉ hiện tại và chọn State & City
        practiceForm.setCurrentAddress(student.getCurrentAddress())
                    .selectStateAndCity(student.getState(), student.getCity());

        // 8. Chụp ảnh màn hình THỜI ĐIỂM 1: Trước khi Submit
        String prefixBefore = "BeforeSubmit_" + student.getFirstName();
        String pathBefore = captureScreen(prefixBefore);
        System.out.println("[Yêu cầu 8] Ảnh chụp TRƯỚC submit: " + pathBefore);

        // 9. Nhấn nút Submit
        practiceForm.submit();

        // 10. Chờ modal xác nhận xuất hiện (Explicit Wait)
        Assert.assertTrue(practiceForm.isModalDisplayed(),
                "Lỗi: Modal xác nhận không xuất hiện sau khi Submit cho " + testCaseName);

        String modalTitle = practiceForm.getModalTitle();
        System.out.println("Tiêu đề modal hiển thị: " + modalTitle);
        Assert.assertEquals(modalTitle, "Thanks for submitting the form", "Tiêu đề modal không đúng!");

        // 11. Chụp ảnh màn hình THỜI ĐIỂM 2: Sau khi Submit thành công
        String prefixAfter = "AfterSubmit_" + student.getFirstName();
        String pathAfter = captureScreen(prefixAfter);
        System.out.println("[Yêu cầu 8] Ảnh chụp SAU submit: " + pathAfter);

        // 12. Trích xuất và kiểm tra toàn bộ dữ liệu xác nhận trong modal
        Map<String, String> tableData = practiceForm.getModalTableResults();

        System.out.println("\n--- [KẾT QUẢ XÁC NHẬN TRÊN MODAL] ---");
        tableData.forEach((key, value) -> System.out.printf("%-25s : %s\n", key, value));
        System.out.println("-------------------------------------\n");

        // Đối chiếu dữ liệu xác nhận
        Assert.assertEquals(tableData.get("Student Name"), student.getFullName(), "Tên sinh viên không khớp!");
        Assert.assertEquals(tableData.get("Student Email"), student.getEmail(), "Email không khớp!");
        Assert.assertEquals(tableData.get("Gender"), student.getGender(), "Giới tính không khớp!");
        Assert.assertEquals(tableData.get("Mobile"), student.getMobile(), "Số điện thoại không khớp!");

        // Đối chiếu ngày sinh (Modal format: "10 January,2001")
        String expectedDob = student.getBirthDay() + " " + student.getBirthMonth() + "," + student.getBirthYear();
        Assert.assertEquals(tableData.get("Date of Birth"), expectedDob, "Ngày sinh không khớp!");

        // Đối chiếu State and City
        String expectedStateCity = student.getState() + " " + student.getCity();
        Assert.assertEquals(tableData.get("State and City"), expectedStateCity, "State and City không khớp!");

        // Đối chiếu tên file upload
        Assert.assertTrue(tableData.get("Picture").contains("sample_upload.txt"), "Tên ảnh upload không khớp!");

        // Đóng modal để hoàn tất quy trình
        practiceForm.closeModal();

        System.out.println(">>> HOÀN THÀNH XUẤT SẮC: " + testCaseName + "\n");
    }
}
