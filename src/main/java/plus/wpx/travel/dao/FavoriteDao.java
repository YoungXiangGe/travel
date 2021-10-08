package plus.wpx.travel.dao;

import plus.wpx.travel.domain.Favorite;

public interface FavoriteDao {

    /**
     * 根据rid和uid判断当前登录用户是否已经收藏该线路
     *
     * @param rid 线路id
     * @param uid 用户id
     * @return 收藏：true
     */
    Favorite isFavorite(int rid, int uid);

    /**
     * 根据rid查询收藏次数
     * @param rid
     * @return
     */
    int queryCount(int rid);

    void addFavorite(int uid, int rid);
}
