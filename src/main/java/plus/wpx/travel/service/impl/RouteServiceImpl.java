package plus.wpx.travel.service.impl;

import plus.wpx.travel.dao.FavoriteDao;
import plus.wpx.travel.dao.RouteDao;
import plus.wpx.travel.dao.RouteImgDao;
import plus.wpx.travel.dao.SellerDao;
import plus.wpx.travel.dao.impl.FavoriteDaoImpl;
import plus.wpx.travel.dao.impl.RouteDaoImpl;
import plus.wpx.travel.dao.impl.RouteImgDaoImpl;
import plus.wpx.travel.dao.impl.SellerDaoImpl;
import plus.wpx.travel.domain.PageBean;
import plus.wpx.travel.domain.Route;
import plus.wpx.travel.domain.RouteImg;
import plus.wpx.travel.domain.Seller;
import plus.wpx.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    private RouteDao dao = new RouteDaoImpl();
    private RouteImgDao imgDao = new RouteImgDaoImpl();
    private SellerDao sellerDao = new SellerDaoImpl();
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();

    /**
     * 根据分类id进行分页查询
     *
     * @param cid         分类ID
     * @param currentPage 当前页码
     * @param pageSize    每页显示的条数
     * @param rname       线路名称
     * @return PageBean对象
     */
    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname) {
        //封装PageBean对象返回
        PageBean<Route> pb = new PageBean<Route>();
        //设置当前页码
        pb.setCurrentPage(currentPage);
        //设置每页显示的记录数
        pb.setPageSize(pageSize);
        //设置总的记录数
        int totalCount = dao.queryTotalCount(cid, rname);
        pb.setTotalCount(totalCount);
        //设置每页显示的数据集合
        int start = (currentPage - 1) * pageSize;
        pb.setList(dao.queryByPage(cid, start, pageSize, rname));
        //设置总页数
        int totalPage = (int) (Math.ceil((double) totalCount / pageSize));
        pb.setTotalPage(totalPage);
        return pb;
    }

    /**
     * 根据rid查询线路详情页的信息
     *
     * @param rid 线路id
     * @return 线路对象
     */
    @Override
    public Route queryByRid(String rid) {
        //查询线路的一些基本信息,如名称，价格等...
        Route route = dao.queryByRid(Integer.parseInt(rid));
        //查询线路的详情图集合
        List<RouteImg> routeImgs = imgDao.queryImgByRid(Integer.parseInt(rid));
        route.setRouteImgList(routeImgs);
        //根据商家的id-sid
        Seller seller = sellerDao.queryBySid(route.getSid());
        route.setSeller(seller);
        //查询收藏次数
        int count = favoriteDao.queryCount(route.getRid());
        route.setCount(count);
        return route;
    }
}
