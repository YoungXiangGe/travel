package plus.wpx.travel.dao;

import plus.wpx.travel.domain.Category;

import java.util.List;

public interface CategoryDao {
    /**
     * 查询导航栏所有的分类信息
     */
    List<Category> query();
}