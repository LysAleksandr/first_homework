package com.mipt.lysaleksandr.hometask_3.service;

import com.mipt.lysaleksandr.hometask_3.dto.PriorityCountDto;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Service
public class TaskStatisticsJdbcService {

    private final JdbcTemplate jdbcTemplate;

    public TaskStatisticsJdbcService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<PriorityCountDto> getTasksCountByPriority() {
        String sql = "SELECT priority, COUNT(*) as count FROM tasks GROUP BY priority ORDER BY priority";
        return jdbcTemplate.query(sql, new PriorityCountRowMapper());
    }

    private static class PriorityCountRowMapper implements RowMapper<PriorityCountDto> {

        public PriorityCountDto mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new PriorityCountDto(rs.getString("priority"), rs.getLong("count"));
        }
    }
}