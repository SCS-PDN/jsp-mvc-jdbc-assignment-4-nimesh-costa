package com.university.dao;

import com.university.model.Course;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class CourseDAO {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    // Maps a DB row to a Course object using YOUR table columns
    private static final RowMapper<Course> MAPPER = (rs, rowNum) -> {
        Course c = new Course();
        c.setCourseId(rs.getInt("course_id"));
        c.setName(rs.getString("name"));
        c.setInstructor(rs.getString("instructor"));
        c.setCredits(rs.getInt("credits"));
        return c;
    };

    public List<Course> findAll() {
        return jdbcTemplate.query("SELECT * FROM courses ORDER BY course_id", MAPPER);
    }

    public Course findById(int id) {
        List<Course> list = jdbcTemplate.query(
            "SELECT * FROM courses WHERE course_id = ?", MAPPER, id);
        return list.isEmpty() ? null : list.get(0);
    }
}
