package com.generation.model;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Student
    extends Person
    implements Evaluation
{
    private double average;     // Student's overall average score

    // A list that keeps track of the courses in which the student is enrolled.
    private final List<Course> courses = new ArrayList<>();
    // A map that stores courses the student has passed or have been approved, keyed by the course code
    private final Map<String, Course> approvedCourses = new HashMap<>();
    // A map that stores the grades for each student
    private final Map<String, Double> courseGrades = new HashMap<>();

    // A constructor
    // From parent Person class
    // It sets up the student’s personal details.
    public Student( String id, String name, String email, Date birthDate ) {
        super( id, name, email, birthDate );
    }

    // Returns the list of courses in which the student is enrolled
    public List<Course> getCourses() {
        return courses;
    }

    // Method to enroll student
    public boolean enrollToCourse( Course course )
    {
        if(courses.contains(course)) {
            return false;
        } else {
            this.courses.add(course); // Add the student to the attribute courses (ArrayList)
            return true;
    }}


    public void registerApprovedCourse( Course course ) {
        approvedCourses.put( course.getCode(), course );
    }

    public boolean isCourseApproved( String courseCode ) {
        return approvedCourses.containsKey(courseCode);
    }

    public void registerGrade(String courseCode, double grade) {
        courseGrades.put(courseCode, grade);
    }

    public Map <String, Double> getCourseGrades() {
        return courseGrades;
    }

    public List<Course> findPassedCourses( ) {
    List<Course> passedCourses = new ArrayList<>();
    for (Course course: courses){
        Double grade = courseGrades.get(course.getCode());
        if (grade != null && grade >= 50){
            passedCourses.add(course);
        }
    }
    return passedCourses;
}

    public boolean isAttendingCourse( String courseCode ) {
        for (Course c : courses) {
            if (c.getCode().equals(courseCode)) {
                return true;
            }
        } return false;
    }

    @Override
    public double getAverage() {
        if (courseGrades.isEmpty()){
            System.out.println("There are not grades recorded.");
            return -1; // To indicate that no grades are avail.
        }
        double total = 0;
        for (Double grade : courseGrades.values()){
            total += grade;
        }
        return average = total / courseGrades.size();
    }

    @Override
    public List<Course> getApprovedCourses() {
        return new ArrayList<>(approvedCourses.values());
    }

    @Override
    public String toString() {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-1993"); // Format birth date
        return String.format("Particulars:\n ID: %s\n Name: %s\n Email: %s\n Date of Birth: %s",
                getId(), getName(), getEmail(), dateFormat.format(getBirthDate()));
    }
}
