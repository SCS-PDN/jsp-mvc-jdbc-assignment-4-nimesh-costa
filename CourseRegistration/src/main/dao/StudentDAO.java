package com.university.dao;

import com.university.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class StudentDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Maps a DB row to a Student object using YOUR table columns
    private static final RowMapper<Student> MAPPER = (rs, rowNum) -> {
        Student s = new Student();
        s.setStudentId(rs.getInt("student_id"));
        s.setName(rs.getString("name"));
        s.setEmail(rs.getString("email"));
        s.setPassword(rs.getString("password"));
        return s;
    };

    // Login: match by email + password
    public Student authenticate(String email, String password) {
        String sql = "SELECT * FROM students WHERE email = ? AND password = ?";
        List<Student> list = jdbcTemplate.query(sql, MAPPER, email, password);
        return list.isEmpty() ? null : list.get(0);
    }

    public Student findById(int id) {
        String sql = "SELECT * FROM students WHERE student_id = ?";
        List<Student> list = jdbcTemplate.query(sql, MAPPER, id);
        return list.isEmpty() ? null : list.get(0);
    }
}
