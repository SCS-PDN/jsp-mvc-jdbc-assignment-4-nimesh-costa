package com.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class CourseController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    
    @GetMapping("/courses")
    public String listCourses(Model model) {

        String sql = "SELECT * FROM courses";
        List<Map<String, Object>> courses = jdbcTemplate.queryForList(sql);

        model.addAttribute("courses", courses);
        return "courses";
    }

    
    @PostMapping("/register/{courseId}")
    public String registerCourse(@PathVariable("courseId") int courseId,
                                 HttpSession session) {

        
        String email = (String) session.getAttribute("email");

        if (email == null) {
            return "redirect:/login";
        }

        
        String getStudentSql = "SELECT student_id FROM students WHERE email = ?";
        int studentId = jdbcTemplate.queryForObject(getStudentSql, Integer.class, email);

        
        String insertSql = "INSERT INTO registrations (student_id, course_id, date) VALUES (?, ?, CURDATE())";
        jdbcTemplate.update(insertSql, studentId, courseId);

        return "success";
    }
}