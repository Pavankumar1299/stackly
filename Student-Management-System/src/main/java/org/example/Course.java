package org.example;

public class Course {
    private String courseId;
    private String courseName;
    private int duration;

    public Course(String courseId, String courseName, int duration) {
        this.courseId = courseId;
        this.courseName = courseName;
        this.duration = duration;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
}
