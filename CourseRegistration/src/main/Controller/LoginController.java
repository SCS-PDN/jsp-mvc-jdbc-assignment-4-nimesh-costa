package com.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @GetMapping("/login")
    public String showLoginPage() {
        return "login";
    }

   
    @PostMapping("/login")
    public String validateLogin(@RequestParam("email") String email,
                                @RequestParam("password") String password,
                                HttpSession session,
                                Model model) {

        String sql = "SELECT COUNT(*) FROM students WHERE email = ? AND password = ?";
        int count = jdbcTemplate.queryForObject(sql, Integer.class, email, password);

        if (count == 1) {
            session.setAttribute("email", email);
            return "redirect:/courses";  // Login success
        } else {
            model.addAttribute("error", "Invalid email or password!");
            return "login";
        }
    }
}