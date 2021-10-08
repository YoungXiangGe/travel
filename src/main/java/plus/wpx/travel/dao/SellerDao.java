package plus.wpx.travel.dao;

import plus.wpx.travel.domain.Seller;

public interface SellerDao {
    /**
     * 根据sid查询商家
     * @param sid
     * @return
     */
    Seller queryBySid(int sid);
}
