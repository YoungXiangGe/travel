package plus.wpx.travel.service;

public interface FavoriteService {

    /**
     * 根据rid和uid判断当前登录用户是否已经收藏该线路
     *
     * @param rid 线路id
     * @param uid 用户id
     * @return 收藏：true
     */
    boolean isFavorite(int rid, int uid);

    /**
     * 添加收藏
     * @param uid
     * @param rid
     */
    void addFavorite(int uid, int rid);
}
