package com.generation.service;

import com.generation.model.Course;
import com.generation.model.Student;

import java.util.HashMap;
import java.util.Map;

public class StudentService
{
    private final Map<String, Student> students = new HashMap<>();

    // Register student and input them into students
    public void subscribeStudent( Student student ) {
        students.put( student.getId(), student );
    }

    public Student findStudent( String studentId ) {
        if ( students.containsKey( studentId ) ) {
            return students.get( studentId );
        } return null;
    }

    // Checks if the student is registered
    public boolean isSubscribed( String studentId ) {
        return students.containsKey(studentId);
    }

    public void showSummary() {
        System.out.println("Existing students");
        System.out.println("-----------------");

        // For each student, show the student's details (id, name, email)
        for (String key:students.keySet()){
            Student student = students.get(key);
            System.out.println(student);

            // Show the courses tht each student is taking
            System.out.println("\nCourses taken by " +student.getName()+ "(" +student.getId()+")");
            for (Course course : student.getCourses()){
                System.out.println(course);
            }
        }
    }

    public boolean enrollToCourse( String studentId, Course course ) {
        boolean status = false;
        if (students.containsKey( studentId ) ) {
            status = students.get(studentId).enrollToCourse(course);
        }
        return status;
    }
}
