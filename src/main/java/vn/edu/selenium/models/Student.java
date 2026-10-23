package vn.edu.selenium.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Model biểu diễn thông tin sinh viên đăng ký trên DemoQA Practice Form.
 */
public class Student {
    private String firstName;
    private String lastName;
    private String email;
    private String gender; // Male, Female, Other
    private String mobile; // 10 chữ số
    private String birthDay; // e.g. "15"
    private String birthMonth; // e.g. "May"
    private String birthYear; // e.g. "2000"
    private List<String> subjects = new ArrayList<>();
    private List<String> hobbies = new ArrayList<>(); // Sports, Reading, Music
    private String picturePath;
    private String currentAddress;
    private String state;
    private String city;

    public Student() {}

    public Student(String firstName, String lastName, String email, String gender, String mobile,
                   String birthDay, String birthMonth, String birthYear,
                   List<String> subjects, List<String> hobbies,
                   String picturePath, String currentAddress, String state, String city) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.mobile = mobile;
        this.birthDay = birthDay;
        this.birthMonth = birthMonth;
        this.birthYear = birthYear;
        if (subjects != null) this.subjects = subjects;
        if (hobbies != null) this.hobbies = hobbies;
        this.picturePath = picturePath;
        this.currentAddress = currentAddress;
        this.state = state;
        this.city = city;
    }

    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }

    public String getFullName() { return (firstName != null ? firstName : "") + " " + (lastName != null ? lastName : ""); }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public String getMobile() { return mobile; }
    public void setMobile(String mobile) { this.mobile = mobile; }

    public String getBirthDay() { return birthDay; }
    public void setBirthDay(String birthDay) { this.birthDay = birthDay; }

    public String getBirthMonth() { return birthMonth; }
    public void setBirthMonth(String birthMonth) { this.birthMonth = birthMonth; }

    public String getBirthYear() { return birthYear; }
    public void setBirthYear(String birthYear) { this.birthYear = birthYear; }

    public List<String> getSubjects() { return subjects; }
    public void setSubjects(List<String> subjects) { this.subjects = subjects; }

    public List<String> getHobbies() { return hobbies; }
    public void setHobbies(List<String> hobbies) { this.hobbies = hobbies; }

    public String getPicturePath() { return picturePath; }
    public void setPicturePath(String picturePath) { this.picturePath = picturePath; }

    public String getCurrentAddress() { return currentAddress; }
    public void setCurrentAddress(String currentAddress) { this.currentAddress = currentAddress; }

    public String getState() { return state; }
    public void setState(String state) { this.state = state; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + getFullName() + '\'' +
                ", email='" + email + '\'' +
                ", gender='" + gender + '\'' +
                ", mobile='" + mobile + '\'' +
                ", dob='" + birthDay + " " + birthMonth + "," + birthYear + '\'' +
                ", subjects=" + subjects +
                ", hobbies=" + hobbies +
                ", address='" + currentAddress + '\'' +
                ", stateCity='" + state + " " + city + '\'' +
                '}';
    }
}
