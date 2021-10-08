package plus.wpx.travel.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import plus.wpx.travel.dao.RouteImgDao;
import plus.wpx.travel.domain.RouteImg;
import plus.wpx.travel.util.JDBCUtils;

import java.util.List;

public class RouteImgDaoImpl implements RouteImgDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 根据rid查询线路详情页，商品图片
     *
     * @param rid 线路id
     * @return 图片集合
     */
    @Override
    public List<RouteImg> queryImgByRid(int rid) {
        String sql = "select * from route_img where rid = ?";
        return jdbcTemplate.query(sql, new BeanPropertyRowMapper<RouteImg>(RouteImg.class), rid);
    }
}
