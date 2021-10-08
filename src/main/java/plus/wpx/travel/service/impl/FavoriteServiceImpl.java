package plus.wpx.travel.service.impl;

import plus.wpx.travel.dao.FavoriteDao;
import plus.wpx.travel.dao.impl.FavoriteDaoImpl;
import plus.wpx.travel.domain.Favorite;
import plus.wpx.travel.service.FavoriteService;

public class FavoriteServiceImpl implements FavoriteService {
    private FavoriteDao favoriteDao = new FavoriteDaoImpl();

    /**
     * 根据rid和uid判断当前登录用户是否已经收藏该线路
     *
     * @param rid 线路id
     * @param uid 用户id
     * @return 收藏：true
     */
    @Override
    public boolean isFavorite(int rid, int uid) {
        boolean flag = false;
        Favorite favorite = favoriteDao.isFavorite(rid, uid);
        if (favorite != null) {
            flag = true;
        }

        return flag;
    }

    @Override
    public void addFavorite(int uid, int rid) {
        favoriteDao.addFavorite(uid,rid);
    }
}
