package com.generation;

import com.generation.model.Course;
import com.generation.model.Student;
import com.generation.service.CourseService;
import com.generation.service.StudentService;
import com.generation.utils.PrinterHelper;

import java.text.ParseException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {

    public Main() {
    }

    public static void main(String[] args)
            throws ParseException {
        StudentService studentService = new StudentService();
        CourseService courseService = new CourseService();
        Scanner scanner = new Scanner(System.in);
        int option = 0;
        do {
            PrinterHelper.showMainMenu();
            option = scanner.nextInt();
            switch (option) {
                case 1:
                    registerStudent(studentService, scanner);
                    break;
                case 2:
                    findStudent(studentService, scanner);
                    break;
                case 3:
                    gradeStudent(studentService, scanner);
                    break;
                case 4:
                    enrollStudentToCourse(studentService, courseService, scanner);
                    break;
                case 5:
                    showStudentsSummary(studentService, scanner);
                    break;
                case 6:
                    showCoursesSummary(courseService, scanner);
                    break;
                case 7:
                    showStudentAverage(studentService, scanner);
                    break;
            }
        }
        while (option != 8);
    }

    private static void enrollStudentToCourse(StudentService studentService, CourseService courseService,
                                              Scanner scanner) {
        System.out.println("Insert student ID");
        String studentId = scanner.next();
        Student student = studentService.findStudent(studentId);
        if (student == null) {
            System.out.println("Invalid Student ID");
            return;
        }
        System.out.println("Hello, " + student.getName());
        System.out.println("Please insert the course ID you want to sign up:");
        String courseId = scanner.next();
        Course course = courseService.getCourse(courseId);
        if (course == null) {
            System.out.println("Invalid Course ID");
            return;
        }
        courseService.enrollStudent(courseId, student);

        boolean status = studentService.enrollToCourse(studentId, course);
        if (status) {
            System.out.println("You have successfully enrolled to " + courseId + "-"+ course.getName());

        } else
            System.out.println(student.getName()+ ", you have already enrolled to " + courseId + "-"+ course.getName());

    }

    private static void showCoursesSummary(CourseService courseService, Scanner scanner) {
        courseService.showSummary();
    }

    private static void showStudentsSummary(StudentService studentService, Scanner scanner) {
        studentService.showSummary();
    }

    private static void gradeStudent(StudentService studentService, Scanner scanner) {
        // Prompt for student ID
        System.out.println("Enter student ID to grade: ");
        String studentId = scanner.next();
        Student student = studentService.findStudent(studentId);
        if (student == null) {
            System.out.println("Student with ID" + studentId + " not found.");
            return;
        }
        // List courses enrolled by the student.
        List<Course> enrolledCourses = student.getCourses();
        if (enrolledCourses.isEmpty()) {
            System.out.println("Student is not enrolled in any courses.");
            return;
        }
        // Else list down the courses the student is attending
        System.out.println("Courses that Student "+studentId+ " is attending: ");
        for (Course course : enrolledCourses) {
            System.out.println(" " + course.getCode() + " : " + course.getName());
        }

        // Prompt for the course to grade
        System.out.println("Enter course code to grade: ");
        String courseCode = scanner.next();
        if (!student.isAttendingCourse(courseCode)) {   // Checks if the student is attending the specified course
            System.out.println("Student is not attending course " + courseCode);
            return;
        }

        double grade;
        while (true) {
            System.out.println("Enter grade for course " + courseCode + "(0-100): ");
            grade = scanner.nextDouble();
            if (grade >= 0 && grade <= 100) {
                break;
            } else {
                System.out.println("Invalid input. Grade must be between 0 and 100. Please re-enter.");
            }
        }

        // Add the double grade in student detail
        // Store the numeric grade in the student's record.
        student.registerGrade(courseCode, grade);

        // Categorise the grade according credit 9-0.
        int earnedCredits = 0;
        if (grade >= 90) {
            earnedCredits = 9;
        } else if (grade >= 80) {
            earnedCredits = 8;
        } else if (grade >= 70) {
            earnedCredits = 7;
        } else if (grade >= 60) {
            earnedCredits = 6;
        } else if (grade >= 50) {
            earnedCredits = 5;
        } else if (grade >= 40) {
            earnedCredits = 4;
        } else if (grade >= 30) {
            earnedCredits = 3;
        } else if (grade >= 20) {
            earnedCredits = 2;
        } else if (grade >= 10) {
            earnedCredits = 1;
        } else { // numericGrade below 20
            earnedCredits = 0;
        }

        // Print the grading details: student id, name, course id, course name, and letter grade.
        Course gradedCourse = null;
        for (Course c : enrolledCourses) {
            if (c.getCode().equals(courseCode)) {
                gradedCourse = c;
                break;
            }
        }

        if (gradedCourse != null) {
            System.out.println("\nGrade Recorded:");
            System.out.println("Student ID   : " + student.getId());
            System.out.println("Student Name : " + student.getName());
            System.out.println("Course ID    : " + gradedCourse.getCode());
            System.out.println("Course Name  : " + gradedCourse.getName());
            System.out.println("Grade(Credit): " + grade + " (" + earnedCredits + ")");
        }
    }

        private static void findStudent (StudentService studentService, Scanner scanner ){
            System.out.println("Enter student ID: ");
            String studentId = scanner.next();
            Student student = studentService.findStudent(studentId);
            if (student != null) {
                System.out.println("We found student with ID: " + studentId);
                System.out.println(student);
            } else {
                System.out.println("Sorry, student with ID: " + studentId + " is not found.");
            }
        }

        private static void registerStudent (StudentService studentService, Scanner scanner )
        throws ParseException {
            Student student = PrinterHelper.createStudentMenu(scanner);
            studentService.subscribeStudent(student);
        }

        private static void showStudentAverage (StudentService studentService, Scanner scanner){
            System.out.println("Enter student ID:");
            String studentId = scanner.next();
            Student student = studentService.findStudent(studentId);

            if (student == null){
                System.out.println("Student not found");
                return;
            }
            //Display the subjects and grades first
            System.out.println(student.getName()+"'s Courses and Grades =>7");
            Map<String, Double> courseGrades = student.getCourseGrades();

            if (courseGrades.isEmpty()){
                return;
            }
            for (Map.Entry<String,Double> entry:courseGrades.entrySet()){
                System.out.printf(" - %s: %.2f%n",entry.getKey(),entry.getValue());
            }
            // Display average
            double average = student.getAverage();
            System.out.printf("%s's average grades: %.1f%n", student.getName(), student.getAverage());
        }
    }
