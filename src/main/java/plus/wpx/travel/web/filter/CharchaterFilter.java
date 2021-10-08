package plus.wpx.travel.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 解决全站乱码问题，处理所有的post请求
 */
@WebFilter("/*")
public class CharchaterFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse rep, FilterChain filterChain) throws IOException, ServletException {
        //将父接口转为子接口
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) rep;
        //获取请求方式
        String method = request.getMethod();
        //获取请求路径
        String uri = request.getRequestURI();
        //解决post请求中文数据乱码问题
        if (method.equalsIgnoreCase("post")) {
            //css,js,img,font等静态资源直接放行
            if (uri.contains("/css/") || uri.contains("/js/") || uri.contains("/img/") || uri.contains("/font/")) {
                filterChain.doFilter(request, response);
            }
            //处理请求乱码
            request.setCharacterEncoding("utf-8");
            //处理响应乱码
            response.setCharacterEncoding("utf-8");
        }
        //放行
        filterChain.doFilter(request, response);
    }
}
