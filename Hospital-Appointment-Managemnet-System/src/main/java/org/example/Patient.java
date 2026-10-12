package org.example;

public class Patient {
    private int patientId;
    private String patientName;
    private int age;
    private String gender;
    private String phoneNumber;

    public Patient(int age, String gender, int patientId, String patientName, String phoneNumber) {
        this.age = age;
        this.gender = gender;
        this.patientId = patientId;
        this.patientName = patientName;
        this.phoneNumber = phoneNumber;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getPatientId() {
        return patientId;
    }

    public void setPatientId(int patientId) {
        this.patientId = patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "age=" + age +
                ", patientId=" + patientId +
                ", patientName='" + patientName + '\'' +
                ", gender='" + gender + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
