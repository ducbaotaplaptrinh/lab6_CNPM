package vn.edu.selenium;

import org.testng.TestNG;
import java.io.File;
import java.util.Collections;

/**
 * Lớp TestRunner giúp bạn chạy toàn bộ TestNG Suite trực tiếp trong Eclipse
 * bằng cách: Chuột phải vào TestRunner.java -> Run As -> Java Application
 * (Không cần cài thêm plugin TestNG cho Eclipse).
 */
public class TestRunner {
    public static void main(String[] args) {
        System.out.println("=================================================");
        System.out.println("   KHỞI ĐỘNG TESTNG SUITE TRÊN ECLIPSE");
        System.out.println("=================================================");

        File suiteFile = new File("testng.xml");
        if (!suiteFile.exists()) {
            System.err.println("Không tìm thấy tệp testng.xml tại: " + suiteFile.getAbsolutePath());
            return;
        }

        TestNG testng = new TestNG();
        testng.setTestSuites(Collections.singletonList(suiteFile.getAbsolutePath()));
        testng.run();

        System.out.println("\n=================================================");
        System.out.println("   ĐÃ CHẠY XONG TESTNG SUITE TRÊN ECLIPSE!");
        System.out.println("   Xem ảnh chụp màn hình tại thư mục: screenshots/");
        System.out.println("=================================================");
    }
}
