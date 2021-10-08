package plus.wpx.travel.web.servlet;

import org.apache.commons.beanutils.BeanUtils;
import plus.wpx.travel.domain.ResultInfo;
import plus.wpx.travel.domain.User;
import plus.wpx.travel.service.UserService;
import plus.wpx.travel.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {
    private UserService service = new UserServiceImpl();

    /**
     * 注册
     */
    public void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取用户填写的验证码
        String check = request.getParameter("check");
        //从session中获取生成的验证码
        HttpSession session = request.getSession();
        String checkcode = (String) session.getAttribute("CHECKCODE_SERVER");
        //保证验证码只能使用一次
        session.removeAttribute("CHECKCODE_SERVER");
        //校验验证码是否正确，验证码正确执行下面的操作，不对直接结束
        if ((!checkcode.equalsIgnoreCase(check)) || checkcode == null || check == null) {
            //验证码错误,做出响应
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码错误");
            //将info对象序列化为json
            String json = writeValueAsString(info);
            //json响应到客户端
            //设置响应返回数据的类型为json
            response.setContentType("application/json; charset=UTF-8");
            response.getWriter().write(json);
            return;
        }
        //获取数据
        Map<String, String[]> map = request.getParameterMap();
        //封装对象
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //调用service完成注册
        boolean flag = service.register(user);
        //做出响应
        ResultInfo info = new ResultInfo();
        if (flag) {
            //注册成功
            info.setFlag(true);
        } else {
            info.setFlag(false);
            info.setErrorMsg("用户名已存在，注册失败，请换一个用户名注册");
        }
        //将info对象序列化为json
        String json = writeValueAsString(info);
        //json响应到客户端
        //设置响应返回数据的类型为json
        response.setContentType("application/json; charset=UTF-8");
        response.getWriter().write(json);
    }

    /**
     * 登录
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取用户名和密码
        Map<String, String[]> map = request.getParameterMap();
        //封装User对象，BeanUtils用于封装JavaBean对象
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        //调用Service查询
        User u = service.login(user);
        ResultInfo info = new ResultInfo();
        //判断用户是否存在
        if (u == null) {
            info.setFlag(false);
            info.setErrorMsg("用户名或密码错误");
        } else if ("Y".equals(u.getStatus())) {
            //用户存在且已激活账号
            info.setFlag(true);
            //把登录的用户存入到session中
            request.getSession().setAttribute("user", u);
        } else {
            //用户尚未激活账号
            info.setFlag(false);
            info.setErrorMsg("您尚未激活账号，请前往您的注册邮箱进行账号激活");
        }
        //响应信息,将对象序列化为json，并写回客户端
        writeValue(response, info);
    }

    /**
     * 注销登录
     */
    public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //销毁session
        request.getSession().invalidate();
        //跳转到登录页面
        response.sendRedirect(request.getContextPath() + "/login.html");
    }

    /**
     * 激活账号
     */
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        if (code != null) {
            //调用service完成邮箱激活账号
            boolean flag = service.active(code);
            String msg;
            String contextPath = request.getContextPath();
            if (flag) {
                msg = "激活成功请<a href='" + contextPath + "/login.html'>登录</a>";
            } else {
                msg = "激活失败，请联系管理员";
            }
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
        }
    }

    /**
     * 查询用户,用于回显用户名到index.html
     */
    public void showMsg(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        //将对象序列化为json，并写回客户端
        writeValue(response, user);
    }


}
