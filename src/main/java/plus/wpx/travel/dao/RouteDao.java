package plus.wpx.travel.dao;

import plus.wpx.travel.domain.Route;

import java.util.List;

public interface RouteDao {

    /**
     * 根据cid查询总的记录数
     *
     * @param cid   分类id
     * @param rname
     * @return 总记录数
     */
    int queryTotalCount(int cid, String rname);

    /**
     * 查询当前页的数据集合
     *
     * @param cid      分类id
     * @param start    查询开始的索引
     * @param pageSize 查询几条记录
     * @param rname    线路名
     * @return 数据集合List
     */
    List<Route> queryByPage(int cid, int start, int pageSize, String rname);

    /**
     * 根据rid查询线路详情页的信息
     * @param rid 线路id
     * @return 线路对象
     */
    Route queryByRid(int rid);
}
