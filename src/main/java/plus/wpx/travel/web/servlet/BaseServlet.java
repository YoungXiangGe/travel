package plus.wpx.travel.web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BaseServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //完成方法的分发
        //获取请求路径
        String uri = req.getRequestURI();
        //获取方法名称
        String methodName = uri.substring(uri.lastIndexOf('/') + 1);
        //获取方法对象
        try {
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            method.invoke(this, req, resp);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    /**
     * 将对象序列化为json，并写回客户端
     */
    public void writeValue(HttpServletResponse response, Object obj) {
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json; charset=UTF-8");
        try {
            mapper.writeValue(response.getOutputStream(), obj);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 将对象序列化为json，并返回json的String类型
     */
    public String writeValueAsString(Object obj) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(obj);
    }
}
