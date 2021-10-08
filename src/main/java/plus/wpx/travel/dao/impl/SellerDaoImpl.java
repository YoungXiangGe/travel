package plus.wpx.travel.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import plus.wpx.travel.dao.SellerDao;
import plus.wpx.travel.domain.Seller;
import plus.wpx.travel.util.JDBCUtils;

public class SellerDaoImpl implements SellerDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    @Override
    public Seller queryBySid(int sid) {
        String sql = "select * from seller where sid = ?";
        return template.queryForObject(sql, new BeanPropertyRowMapper<Seller>(Seller.class), sid);
    }
}
