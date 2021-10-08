package plus.wpx.travel.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import plus.wpx.travel.dao.FavoriteDao;
import plus.wpx.travel.domain.Favorite;
import plus.wpx.travel.util.JDBCUtils;

import java.util.Date;


/**
 * @author wpx666
 */
public class FavoriteDaoImpl implements FavoriteDao {
    JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 根据rid和uid判断当前登录用户是否已经收藏该线路
     *
     * @param rid 线路id
     * @param uid 用户id
     * @return 收藏：true
     */
    @Override
    public Favorite isFavorite(int rid, int uid) {
        Favorite favorite = null;
        String sql = null;
        try {
            sql = "select * from favorite where rid = ? and uid = ?";
            favorite = template.queryForObject(sql, new BeanPropertyRowMapper<Favorite>(Favorite.class), rid, uid);
        } catch (Exception e) {
        }

        return favorite;
    }

    @Override
    public int queryCount(int rid) {
        String sql = "select count(*) from favorite where rid = ?";
        return template.queryForObject(sql, Integer.class, rid);
    }

    @Override
    public void addFavorite(int uid, int rid) {
        String sql = "insert into favorite values(?,?,?)";
        template.update(sql, rid, new Date(), uid);
    }
}
