package plus.wpx.travel.service;

import plus.wpx.travel.domain.User;

public interface UserService {
    /**
     * 注册方法
     */
    boolean register(User user);

    /**
     * 激活账号
     */
    boolean active(String code);

    //登录方法

    User login(User user);
}
