package org.example;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class StudentFileService {

    public void saveStudent(HashMap<Integer, Student> students) throws IOException {
        FileWriter writer = new FileWriter("students.txt");

        for (Map.Entry<Integer, Student> entry : students.entrySet()) {
            Student student = entry.getValue();

            writer.write(student.getId() + "," +
                    student.getName() + "," +
                    student.getEmail() + "," +
                    student.getMarks() + "," +
                    student.getAttendance() + "," +
                    student.getStatus() + "\n"
            );
        }

        writer.flush();
        writer.close();

        System.out.println("Student details saved successfully");
    }

    public void loadStudent(HashMap<Integer, Student> students) throws IOException {
        FileReader reader = new FileReader("students.txt");
        Scanner scanner = new Scanner(reader);

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            String[] data = line.split(",");

            int id = Integer.parseInt(data[0]);
            String name = data[1];
            String email = data[2];
            double marks = Double.parseDouble(data[3]);
            int attendance = Integer.parseInt(data[4]);
            StudentStatus status = StudentStatus.valueOf(data[5]);

            Student student = new Student(id, name, email, marks, attendance, status);

            students.put(id, student);
        }

        scanner.close();
        reader.close();

        System.out.println("Student details loaded successfully");
    }
}
