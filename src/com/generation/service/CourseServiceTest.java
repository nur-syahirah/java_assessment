package com.generation.service;

import com.generation.model.Course;
import com.generation.model.Module;
import com.generation.model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

    class CourseServiceTest {

        // We do not need to inject courses because it is already fulfilled when CourseService is instantiated
        private CourseService courseService;
        private Student student;

        @BeforeEach
        void setUp(){
            courseService = new CourseService();
            student = new Student("S01", "John Doe", "test@gmail.com", new Date());
        }

        @Test
        @DisplayName("getCourse() method should return the same course code - INTRO-CS-1")
        void getCourse() {
            assertEquals("INTRO-CS-1", courseService.getCourse("INTRO-CS-1").getCode());
        }

        @Test
        @DisplayName("showEnrolledStudents() should display enrolled students correctly")
        void testShowEnrolledStudents() {
            courseService.registerCourse(new Course("INTRO-CS-100", "Algorithm", 9, new Module("INTRO-CS",
                    "Introduction to Computer Science", "Introductory module for tech programs")));

            courseService.enrollStudent("INTRO-CS-100",student);

            // Capture console output
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            System.setOut(new PrintStream(outputStream));

            courseService.showEnrolledStudents("INTRO-CS-100");

            // Restore System.out
            System.setOut(System.out);

            // Verify console output
            String consoleOutput = outputStream.toString();
            assertTrue(consoleOutput.contains(student.getId()), "Student ID should be printed");
            assertTrue(consoleOutput.contains(student.getName()), "Student Name should be printed");
        }
    }