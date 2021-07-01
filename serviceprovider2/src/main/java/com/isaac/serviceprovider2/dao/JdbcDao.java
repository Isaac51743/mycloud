package com.isaac.serviceprovider2.dao;

import com.isaac.serviceprovider2.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class JdbcDao {
    @Autowired
    JdbcTemplate jdbcTemplate;

    public List<User> getList() {
        String sql = "select* from user where email = 'a@a';";
        List<User> user = jdbcTemplate.query(sql, (resultSet, i) -> {
            User em = new User();
            em.setAddress(resultSet.getString("address"));
            em.setEmail(resultSet.getString("email"));
            em.setFirstName(resultSet.getString("first_name"));
            em.setLastName(resultSet.getString("last_name"));
            em.setPassword(resultSet.getString("password"));
            em.setPhone(resultSet.getString("phone"));
            em.setStatus(resultSet.getString("status"));

            return em;
        });
        return user;
    }

    public User getObject() {
        String sql = "select* from user where email = 'a@a';";
        RowMapper<User> rowMapper = (resultSet, i) -> {
            User em = new User();
            em.setAddress(resultSet.getString("address"));
            em.setEmail(resultSet.getString("email"));
            em.setFirstName(resultSet.getString("first_name"));
            em.setLastName(resultSet.getString("last_name"));
            em.setPassword(resultSet.getString("password"));
            em.setPhone(resultSet.getString("phone"));
            em.setStatus(resultSet.getString("status"));
            return em;
        };
        User user = jdbcTemplate.queryForObject(sql, rowMapper);
        return user;
    }
}
