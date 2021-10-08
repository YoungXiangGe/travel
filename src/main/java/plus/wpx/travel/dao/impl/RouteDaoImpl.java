package plus.wpx.travel.dao.impl;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import plus.wpx.travel.dao.RouteDao;
import plus.wpx.travel.domain.Route;
import plus.wpx.travel.util.JDBCUtils;

import java.util.ArrayList;
import java.util.List;


public class RouteDaoImpl implements RouteDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 根据cid查询总的记录数
     *
     * @param cid   分类id
     * @param rname
     * @return 总记录数
     */
    @Override
    public int queryTotalCount(int cid, String rname) {
        String sql = "select count(*) from route where 1 = 1 ";
        StringBuilder sb = new StringBuilder(sql);
        //参数的值的集合
        List params = new ArrayList();
        //判断参数是否存在，存在拼接sql语句
        if (cid != 0) {
            sb.append(" and cid = ? ");
            params.add(cid);
        }
        if (rname != null && rname.length() > 0 && !"null".equals(rname)) {
            sb.append(" and rname like ? ");
            params.add("%" + rname + "%");
        }
        sql = sb.toString();
        return template.queryForObject(sql, Integer.class, params.toArray());
    }

    /**
     * 查询当前页的数据集合
     *
     * @param cid      分类id
     * @param start    查询开始的索引
     * @param pageSize 查询几条记录
     * @param rname    线路名
     * @return 数据集合List
     */
    @Override
    public List<Route> queryByPage(int cid, int start, int pageSize, String rname) {

        String sql = "select * from route where 1 = 1 ";
        StringBuilder sb = new StringBuilder(sql);
        //参数的值的集合
        List params = new ArrayList();
        //判断参数是否存在，存在拼接sql语句
        if (cid != 0) {
            sb.append(" and cid = ? ");
            params.add(cid);
        }
        if (rname != null && rname.length() > 0 && !"null".equals(rname)) {
            sb.append(" and rname like ? ");
            params.add("%" + rname + "%");
        }
        sb.append(" limit ? , ? ");
        sql = sb.toString();
        params.add(start);
        params.add(pageSize);
        return template.query(sql, new BeanPropertyRowMapper<Route>(Route.class), params.toArray());
    }

    /**
     * 根据rid查询线路详情页的信息
     *
     * @param rid 线路id
     * @return 线路对象
     */
    @Override
    public Route queryByRid(int rid) {
        String sql = "select * from route where rid = ?";
        return template.queryForObject(sql, new BeanPropertyRowMapper<Route>(Route.class), rid);
    }

}
