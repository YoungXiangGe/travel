package plus.wpx.travel.dao;

import plus.wpx.travel.domain.RouteImg;

import java.util.List;

public interface RouteImgDao {
    /**
     * 根据rid查询线路详情页，商品图片
     * @param rid 线路id
     * @return 图片集合
     */
    List<RouteImg> queryImgByRid(int rid);
}
