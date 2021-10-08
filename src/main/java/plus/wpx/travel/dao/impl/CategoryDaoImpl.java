package plus.wpx.travel.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import plus.wpx.travel.dao.CategoryDao;
import plus.wpx.travel.domain.Category;
import plus.wpx.travel.util.JDBCUtils;

import java.util.List;

public class CategoryDaoImpl implements CategoryDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 查询导航栏所有的分类信息
     */
    @Override
    public List<Category> query() {
        String sql = "select * from category";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<Category>(Category.class));
    }
}
