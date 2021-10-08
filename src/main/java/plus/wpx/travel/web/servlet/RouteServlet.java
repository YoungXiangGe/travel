package plus.wpx.travel.web.servlet;

import plus.wpx.travel.domain.PageBean;
import plus.wpx.travel.domain.Route;
import plus.wpx.travel.domain.User;
import plus.wpx.travel.service.FavoriteService;
import plus.wpx.travel.service.RouteService;
import plus.wpx.travel.service.impl.FavoriteServiceImpl;
import plus.wpx.travel.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {
    private RouteService service = new RouteServiceImpl();
    private FavoriteService favoriteService = new FavoriteServiceImpl();

    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        String cidStr = request.getParameter("cid");
        String rname = request.getParameter("rname");
        if (rname != null && rname.length() > 0 && !"null".equals(rname)) {
            rname = new String(rname.getBytes("iso-8859-1"), "utf-8");
        }
        //当前页码,如果没接收到默认为第一页
        int currentPage = 1;
        if (currentPageStr != null && currentPageStr.length() > 0) {
            currentPage = Integer.parseInt(currentPageStr);
        }
        //每页显示的条数，没接收到默认显示5条记录
        int pageSize = 5;
        if (pageSizeStr != null && pageSizeStr.length() > 0) {
            pageSize = Integer.parseInt(pageSizeStr);
        }
        //分类id，没接收到默认展示分类id为1的数据
        int cid = 1;
        if (cidStr != null && cidStr.length() > 0 && !"null".equals(cidStr)) {

            cid = Integer.parseInt(cidStr);
        }
        //调用service查询PageBean对象
        PageBean<Route> routePageBean = service.pageQuery(cid, currentPage, pageSize, rname);
        //将PageBean对象序列化为json
        writeValue(response, routePageBean);
    }

    //根据rid查询线路详情页的信息
    public void queryByRid(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rid = request.getParameter("rid");
        Route route = service.queryByRid(rid);
        writeValue(response, route);
    }

    //根据rid和uid查询当前登录用户是否收藏该线路
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取rid
        String ridStr = request.getParameter("rid");
        int rid = Integer.parseInt(ridStr);
        //获取当前登录的对象
        User user = (User) request.getSession().getAttribute("user");
        int uid = 0;
        if (user != null) {
            //用户已登录
            uid = user.getUid();
        }
        //查询是否收藏
        boolean flag = favoriteService.isFavorite(rid, uid);
        writeValue(response, flag);
    }

    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //获取rid
        String ridStr = request.getParameter("rid");
        int rid = Integer.parseInt(ridStr);
        //获取当前登录的对象
        User user = (User) request.getSession().getAttribute("user");
        int uid = 0;
        if (user != null) {
            //用户已登录
            uid = user.getUid();
        } else {
            return;
        }
        //添加收藏
        favoriteService.addFavorite(uid,rid);

    }
}
