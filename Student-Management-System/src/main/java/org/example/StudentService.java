package org.example;

import java.util.HashMap;
import java.util.Map;

public class StudentService {

    Map<Integer, Student> students = new HashMap<Integer, Student>();
    Map<Integer, Course> courses = new HashMap<>();

    public void createCourses() {

        courses.put(101, new Course(101, "Java", 3));
        courses.put(102, new Course(102, "SQL", 2));
        courses.put(103, new Course(103, "Spring Boot", 3));
        courses.put(104, new Course(104, "HTML & CSS", 2));
        courses.put(105, new Course(105, "DSA", 3));
    }

    public void addStudent(Student student) throws StudentException {
        if (students.containsKey(student.getId())) {
            throw  new StudentException("Student already exists!");
        } else {
            students.put(student.getId(), student);
            System.out.println("Student details saved successfully");
        }
    }

    public void displayAllStudent() {
        for (Student s : students.values()) {
            System.out.println(s);
        }

        if (students.isEmpty()) {
            System.out.println("No students found!");
        }
    }

    public Student searchByName(String name) throws StudentException {
        for (Student student : students.values()) {
            if (student.getName().equals(name)) {
                return student;
            }
        }

        throw new StudentException("Student not found!");
    }

    public Student searchByStudentId(int studentId) throws StudentException {
        if (students.containsKey(studentId)) {
            return students.get(studentId);
        } else {
            throw new StudentException("Student not found!");
        }
    }

    public void addCourseMarks(int studentId, int courseId, double marks)
            throws StudentException {

        Student student = searchByStudentId(studentId);

        if (!courses.containsKey(courseId)) {
            throw new StudentException("Course not found!");
        }

        if (student.getCourseMarks().containsKey(courseId)) {
            throw new StudentException("Marks already added for this course!");
        }

        if (marks < 0 || marks > 100) {
            throw new StudentException("Marks must be between 0 and 100!");
        }

        student.getCourseMarks().put(courseId, marks);

        System.out.println("Marks added successfully!");
    }

    public void updateMarks(int studentId, int courseId, double newMark) throws StudentException {
        Student student = searchByStudentId(studentId);

        if (!courses.containsKey(courseId)) {
            throw new StudentException("Course not found!");
        }

        if (newMark < 0 || newMark > 100) {
            throw new StudentException("Invalid marks!");
        }

        student.getCourseMarks().put(courseId, newMark);

        System.out.println("Marks updated!");
    }

    public void updateAttendance(int studentId, int newAttendance)
            throws StudentException {

        Student student = searchByStudentId(studentId);

        if (newAttendance < 0 || newAttendance > 100) {
            throw new StudentException("Attendance must be between 0 and 100!");
        }

        student.setAttendance(newAttendance);

        System.out.println("Attendance updated!");
    }

    public void calculateGrade(int studentId) throws StudentException {

        Student student = searchByStudentId(studentId);

        for (Integer courseId : courses.keySet()) {
            if (!student.getCourseMarks().containsKey(courseId)) {
                throw new StudentException(
                        "Marks are not available for all courses!"
                );
            }
        }

        double totalMarks = 0;

        for (double marks : student.getCourseMarks().values()) {
            totalMarks += marks;
        }

        double percentage = totalMarks / courses.size();

        student.setPercentage(percentage);

        String grade;

        if (percentage >= 90) {
            grade = "A";
        } else if (percentage >= 75) {
            grade = "B";
        } else if (percentage >= 60) {
            grade = "C";
        } else if (percentage >= 50) {
            grade = "D";
        } else {
            grade = "F";
        }

        student.setGrade(grade);
    }

    public void removeStudent(int studentId) throws StudentException {
        if (students.containsKey(studentId)) {
            students.remove(studentId);
            System.out.println("Student removed!");
        } else {
            throw new StudentException("Student not found!");
        }
    }

    public void displayTopThreeStudents() {

        students.values()
                .stream()
                .filter(student -> student.getPercentage() > 0)
                .sorted((s1, s2) ->
                        Double.compare(
                                s2.getPercentage(),
                                s1.getPercentage()
                        ))
                .limit(3)
                .forEach(student ->
                        System.out.println(
                                student.getId() + " | " +
                                        student.getName() + " | " +
                                        student.getPercentage() + "%"
                        )
                );
    }

    public void displayTopScorerByCourse() {

        for (Course course : courses.values()) {

            Student topStudent = students.values()
                    .stream()
                    .filter(student -> student.getCourseMarks().containsKey(course.getCourseId()))
                    .max((s1, s2) ->
                            Double.compare(
                                    s1.getCourseMarks().get(course.getCourseId()),
                                    s2.getCourseMarks().get(course.getCourseId())
                            ))
                    .orElse(null);

            if (topStudent != null) {

                double marks =
                        topStudent.getCourseMarks().get(course.getCourseId());

                System.out.println(
                        course.getCourseId() + " | " +
                                course.getCourseName() + " | " +
                                topStudent.getId() + " | " +
                                topStudent.getName() + " | " +
                                marks
                );
            }
        }
    }

    public boolean checkCourse(int courseId) {
        return courses.containsKey(courseId);
    }

    public boolean studentExists(int studentId) {
        return students.containsKey(studentId);
    }
}
