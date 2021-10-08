package plus.wpx.travel.service.impl;

import plus.wpx.travel.dao.UserDao;
import plus.wpx.travel.dao.impl.UserDaoImpl;
import plus.wpx.travel.domain.User;
import plus.wpx.travel.service.UserService;
import plus.wpx.travel.util.MailUtils;
import plus.wpx.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {
    private UserDao userDao = new UserDaoImpl();

    /**
     * 注册方法
     */
    @Override
    public boolean register(User user) {
        //根据用户名查询该用户是否存在
        User u = userDao.queryByUsername(user.getUsername());
        if (u != null) {
            //用户存在，注册失败
            return false;
        } else {


            //设置激活码，唯一字符串
            user.setCode(UuidUtil.getUuid());
            //设置激活状态
            user.setStatus("N");
            //保存用户信息
            userDao.add(user);
            //发送激活邮件，设置邮件正文
            String content = "<a href='http://localhost/travel/user/active?code=" + user.getCode() + "'>激活账号</a>";
            MailUtils.sendMail(user.getEmail(), content, "激活邮件");
            return true;
        }
    }

    /**
     * 激活账号
     */
    @Override
    public boolean active(String code) {
        //根据激活码查看对应用户是否存在
        User user = userDao.findUserByCode(code);
        if (user != null) {
            //用户存在更改激活状态
            userDao.updateStatus(user);
            return true;
        } else {
            return false;
        }
    }
    //登录方法
    @Override
    public User login(User user) {
        return userDao.login(user);
    }
}
