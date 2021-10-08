package plus.wpx.travel.service;

import plus.wpx.travel.domain.Category;

import java.util.List;

public interface CategoryService {
    /**
     * 查询导航栏所有的分类数据
     */
    List<Category> query();
}
