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

        for (Student student : students.values()) {
            StringBuilder courseMarks = new StringBuilder();

            for (Map.Entry<Integer, Double> entry : student.getCourseMarks().entrySet()) {
                if (courseMarks.length() > 0) {
                    courseMarks.append("|");
                }
                courseMarks.append(entry.getKey()).append(":").append(entry.getValue());
            }

            writer.write(
                    student.getId() + "," +
                            student.getName() + "," +
                            student.getEmail() + "," +
                            student.getAttendance() + "," +
                            student.getStatus() + "," +
                            courseMarks + "," +
                            student.getPercentage() + "," +
                            student.getGrade() +
                            "\n"
            );
        }

        writer.flush();
        writer.close();

        System.out.println("Student details saved successfully!");
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
            int attendance = Integer.parseInt(data[3]);

            StudentStatus status = StudentStatus.valueOf(data[4]);

            Student student = new Student(id, name, email, attendance, status);

            // Course marks
            String[] courseData = data[5].split("\\|");
            for (String course : courseData) {
                if (!course.isEmpty()) {
                    String[] courseDetails = course.split(":");
                    int courseId = Integer.parseInt(courseDetails[0]);
                    double marks = Double.parseDouble(courseDetails[1]);
                    student.getCourseMarks().put(courseId, marks);
                }
            }

            // Percentage
            double percentage = Double.parseDouble(data[6]);
            student.setPercentage(percentage);

            // Grade
            student.setGrade(data[7]);

            students.put(id, student);
        }

        scanner.close();
        reader.close();

        System.out.println("Student details loaded successfully!");
    }
}
