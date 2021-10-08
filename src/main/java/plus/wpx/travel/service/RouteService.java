package plus.wpx.travel.service;

import plus.wpx.travel.domain.PageBean;
import plus.wpx.travel.domain.Route;

public interface RouteService {


    /**
     * 根据分类id进行分页查询
     *
     * @param cid         分类ID
     * @param currentPage 当前页码
     * @param pageSize    每页显示的条数
     * @param rname 线路名称
     * @return PageBean对象
     */
    PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname);

    /**
     * 根据rid查询线路详情页的信息
     * @param rid 线路id
     * @return 线路对象
     */
    Route queryByRid(String rid);
}
