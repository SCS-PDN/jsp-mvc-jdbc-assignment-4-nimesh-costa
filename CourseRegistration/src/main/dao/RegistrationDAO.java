package com.university.dao;

import com.university.model.Registration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class RegistrationDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Maps a joined row to a Registration object
    private static final RowMapper<Registration> MAPPER = (rs, rowNum) -> {
        Registration r = new Registration();
        r.setRegId(rs.getInt("reg_id"));
        r.setStudentId(rs.getInt("student_id"));
        r.setCourseId(rs.getInt("course_id"));
        r.setDate(rs.getString("date"));
        try { r.setCourseName(rs.getString("course_name")); } catch (Exception ignored) {}
        try { r.setStudentName(rs.getString("student_name")); } catch (Exception ignored) {}
        return r;
    };

    // Check if student already registered for a course (prevents duplicate)
    public boolean exists(int studentId, int courseId) {
        String sql = "SELECT COUNT(*) FROM registrations WHERE student_id = ? AND course_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, studentId, courseId);
        return count != null && count > 0;
    }

    // Enroll a student in a course
    public int register(int studentId, int courseId) {
        String sql = "INSERT INTO registrations (student_id, course_id, date) VALUES (?, ?, CURDATE())";
        return jdbcTemplate.update(sql, studentId, courseId);
    }

    // Get all registrations for a student (with course name via JOIN)
    public List<Registration> findByStudentId(int studentId) {
        String sql = "SELECT r.*, c.name AS course_name " +
                     "FROM registrations r " +
                     "JOIN courses c ON r.course_id = c.course_id " +
                     "WHERE r.student_id = ? ORDER BY r.date DESC";
        return jdbcTemplate.query(sql, MAPPER, studentId);
    }
}
