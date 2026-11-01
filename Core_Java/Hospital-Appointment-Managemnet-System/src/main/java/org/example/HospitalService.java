package org.example;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class HospitalService {

    Scanner sc = new Scanner(System.in);

    Map<Integer, Patient> patients = new HashMap<>();
    Map<Integer, Doctor> doctors = new HashMap<>();
    Map<String, Appointment> appointments = new HashMap<>();

    public void addDoctor() {
        System.out.print("Enter Doctor ID: ");
        int doctorId = sc.nextInt();

        if (doctors.containsKey(doctorId)) {
            throw new IllegalArgumentException("Doctor ID " + doctorId + " already exists");
        }
        sc.nextLine();

        System.out.print("Enter Doctor Name: ");
        String doctorName = sc.nextLine();

        System.out.print("Enter Specialization: ");
        String specialization = sc.nextLine();

        doctors.put(doctorId, new Doctor(doctorId, doctorName, specialization));
        System.out.println("Doctor added");
    }

    public boolean doctorExists(int doctorId) {
        return doctors.containsKey(doctorId);
    }

    public Doctor searchDoctorById(int doctorId) {
        if (doctorExists(doctorId)) {
            return doctors.get(doctorId);
        }
        return null;
    }

    public void registerPatient() {
        System.out.print("Enter Patient ID: ");
        int patientId = sc.nextInt();

        if (patientExists(patientId)) {
            throw new IllegalArgumentException("Patient ID already exists");
        }
        sc.nextLine();

        System.out.print("Enter Patient Name: ");
        String patientName = sc.nextLine();

        System.out.print("Enter age: ");
        int age = sc.nextInt();
        sc.nextLine();

        System.out.print("Enter gender: ");
        String gender = sc.nextLine();

        System.out.print("Enter phone: ");
        String phone = sc.nextLine();


        patients.put(patientId, new Patient(patientId, patientName, age, gender, phone));
        System.out.println("Patient Registered Successfully");
    }

    public boolean patientExists(int patientId) {
        return patients.containsKey(patientId);
    }

    public Patient searchPatientById(int patientId) {
        if (patientExists(patientId)) {
            return patients.get(patientId);
        }
        return null;
    }

    public void bookAppointment() throws HospitalException {
        System.out.print("Enter patient ID: ");
        int patientId = sc.nextInt();

        if (!patientExists(patientId)) {
            throw new HospitalException("Patient does not exist");
        }

        System.out.print("Enter Doctor ID: ");
        int doctorId = sc.nextInt();

        if (!doctorExists(doctorId)) {
            throw new HospitalException("Doctor does not exist");
        }

        sc.nextLine();

        System.out.print("Enter Appointment Date (yyyy-MM-dd): ");
        LocalDate appointmentDate = LocalDate.parse(sc.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        System.out.print("Enter Appointment Time (HH:mm): ");
        LocalTime appointmentTime = LocalTime.parse(sc.nextLine(), DateTimeFormatter.ofPattern("HH:mm"));

        String slotKey = doctorId + "_" + appointmentDate + "_" + appointmentTime;

        if (appointments.containsKey(slotKey)) {
            throw new HospitalException("Slot Already Exists");
        }

        int appointmentId = appointments.size() + 1;

        Appointment appointment = new Appointment(appointmentId, patientId, doctorId, appointmentTime, appointmentDate);

        appointments.put(slotKey, appointment);

        System.out.println("Appointment Booked Successfully");
        System.out.println("Appointment ID: " + appointmentId);
    }

    public void cancelAppointment() {

    }

    public void viewAvailableSlots() {

    }
    public void viewDoctorSchedule() {

    }
    public void viewPatientHistory() {

    }


}
