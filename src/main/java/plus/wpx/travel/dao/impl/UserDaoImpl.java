package plus.wpx.travel.dao.impl;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import plus.wpx.travel.dao.UserDao;
import plus.wpx.travel.domain.User;
import plus.wpx.travel.util.JDBCUtils;


public class UserDaoImpl implements UserDao {
    private JdbcTemplate jdbcTemplate = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 根据用户名查询用户是否存在
     * 查询到直接返回User对象，查询不到返回null
     */
    @Override
    public User queryByUsername(String username) {
        User user = null;
        String sql = "select * from user where username  = ?";
        try {
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), username);
        } catch (Exception e) {

        }
        return user;
    }

    /**
     * 注册，添加用户的功能
     */
    @Override
    public void add(User user) {
        String sql = "insert into user(username,password,name,birthday,sex,telephone,email,status,code) values(?,?,?,?,?,?,?,?,?)";
        jdbcTemplate.update(sql,
                user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getBirthday(),
                user.getSex(),
                user.getTelephone(),
                user.getEmail(),
                user.getStatus(),
                user.getCode()
        );
    }

    /**
     * 根据激活码查询对应用户是否存在
     */
    @Override
    public User findUserByCode(String code) {
        User user = null;
        String sql = "select * from user where code = ? ";
        try {
            user = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), code);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return user;
    }

    /**
     * 更新用户的激活状态
     */
    @Override
    public void updateStatus(User user) {
        String sql = "update user set status ='Y' where uid=?";
        jdbcTemplate.update(sql, user.getUid());


    }

    //登录方法，根据账号和密码查询用户是否存在
    @Override
    public User login(User user) {
        User u = null;
        String sql = "select * from user where username = ? and password = ?";
        try {
            u = jdbcTemplate.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), user.getUsername(), user.getPassword());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return u;
    }
}
