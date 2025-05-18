package com.generation.service;

import com.generation.model.Student;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class StudentServiceTest {
    private StudentService studentService;
    private Student student;

    @BeforeEach
    void setUp(){
        // Register students into  studentService
        studentService = new StudentService();
        student = new Student("S01", "John Doe", "test@example.com", new Date());
        studentService.subscribeStudent(student);
    }

    @AfterEach
    void tearDown(){
        // Remove all students from studentService
        studentService = new StudentService();
    }

    @Test
    @DisplayName("Method findStudent() should return null for non-existent student")
    void findNonExistenceStudent() {
        assertNull(studentService.findStudent("S02"));
    }

    @Test
    @DisplayName("Method findStudent should return correct student object when found")
        void foundStudent(){
            assertEquals(student,studentService.findStudent("S01"));
        }
}