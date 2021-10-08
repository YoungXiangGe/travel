package plus.wpx.travel.dao;

import plus.wpx.travel.domain.User;

public interface UserDao {
    /**
     * 根据用户名查询用户是否存在
     */
    User queryByUsername(String username);

    /**
     * 注册，添加用户的功能
     */
    void add(User user);

    /**
     * 根据激活码查询对应用户是否存在
     */
    User findUserByCode(String code);

    /**
     * 更新用户的激活状态
     */
    void updateStatus(User user);

    //登录方法，根据账号和密码查询用户是否存在

    User login(User user);
}
