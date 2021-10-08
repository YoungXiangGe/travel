package plus.wpx.travel.service.impl;

import plus.wpx.travel.dao.CategoryDao;
import plus.wpx.travel.dao.impl.CategoryDaoImpl;
import plus.wpx.travel.domain.Category;
import plus.wpx.travel.service.CategoryService;
import plus.wpx.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    private CategoryDao categoryDao = new CategoryDaoImpl();

    @Override
    public List<Category> query() {
        //定义分类数据的list集合
        List<Category> category = null;
        Jedis jedis = JedisUtil.getJedis();
        //首先从redis缓存中查询分类数据，包含cid
        Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, -1);
        if (categorys == null || categorys.size() == 0) {
            //当缓存中没有分类数据时,从数据库里查询
            //调用service查询所有的分类
            category = categoryDao.query();
            //遍历category将数据存入到redis中的sortedSet集合里
            int size = category.size();
            for (int i = 0; i < size; i++) {
                jedis.zadd("category", category.get(i).getCid(), category.get(i).getCname());
            }
        } else {
            //缓存中有数据,那么将sortedSet集合里数据存入到category集合中
            category = new ArrayList<Category>();
            for (Tuple tuple : categorys) {
                Category cs = new Category();
                cs.setCname(tuple.getElement());
                cs.setCid((int) tuple.getScore());
                category.add(cs);
            }
        }
        return category;
    }
}
