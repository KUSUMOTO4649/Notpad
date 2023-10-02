package memotyou;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class TaskListDao {
    private final static String TABLE_NAME = "tasklist";
    private final JdbcTemplate jdbcTemplate;
    public int add(HomeController.TaskItem item){
        SqlParameterSource param = new BeanPropertySqlParameterSource(item);
        SimpleJdbcInsert insert = new SimpleJdbcInsert(this.jdbcTemplate)
                .withTableName(TABLE_NAME);
        return insert.execute(param);
    }


    }