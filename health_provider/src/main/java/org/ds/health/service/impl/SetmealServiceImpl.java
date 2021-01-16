package org.ds.health.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.ds.health.dao.SetmealDao;
import org.ds.health.entity.PageResult;
import org.ds.health.entity.QueryPageBean;
import org.ds.health.exception.HealthException;
import org.ds.health.pojo.CheckGroup;
import org.ds.health.pojo.CheckItem;
import org.ds.health.pojo.Setmeal;
import org.ds.health.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
* @description:
* @author: Deshan
* @date: 2021/1/8 19:44
*/
@Service(interfaceClass = SetmealService.class)
public class SetmealServiceImpl implements SetmealService {

    @Autowired
    private SetmealDao setmealDao;

    /**
    * @description: 添加套餐
    * @author: Deshan
    * @date: 2021/1/8 19:45
    * @param: [setmeal, checkgroupIds]
    * @return: void
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        //- 先 添加套餐
        setmealDao.add(setmeal);
        //- 获取套餐的id
        Integer setmealId = setmeal.getId();
        //- 遍历checkgroupIds数组
        if(null != checkgroupIds){
            //- 添加套餐与检查组的关系
            for (Integer checkgroupId : checkgroupIds) {
                setmealDao.addSetmealCheckGroup(setmealId,checkgroupId);
            }
        }
        //- 事务控制
    }

    /**
    * @description: 分页
    * @author: Deshan
    * @date: 2021/1/8 19:45
    * @param: [queryPageBean]
    * @return: org.ds.health.entity.PageResult<org.ds.health.pojo.Setmeal>
    */
    @Override
    public PageResult<Setmeal> findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());

        if(StringUtils.isNotEmpty(queryPageBean.getQueryString())){
            queryPageBean.setQueryString("%"+queryPageBean.getQueryString()+"%");
        }
        Page<Setmeal> setmealPage = setmealDao.findByCondition(queryPageBean.getQueryString());
        return new PageResult<>(setmealPage.getTotal(),setmealPage.getResult());
    }

    /**
    * @description: 查询
    * @author: Deshan
    * @date: 2021/1/8 19:46
    * @param: [id]
    * @return: org.ds.health.pojo.Setmeal
    */
    @Override
    public Setmeal findById(int id) {
        return setmealDao.findById(id);
    }

    /**
    * @description: 关联的检查组
    * @author: Deshan
    * @date: 2021/1/8 19:46
    * @param: [id]
    * @return: java.util.List<java.lang.Integer>
    */
    @Override
    public List<Integer> findCheckGroupIdsBySetmealId(int id) {
        return setmealDao.findCheckGroupIdsBySetmealId(id);
    }

    /**
    * @description: 修改套餐
    * @author: Deshan
    * @date: 2021/1/8 19:43
    * @param: [setmeal, checkgroupIds]
    * @return: void
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Setmeal setmeal, Integer[] checkgroupIds) {
        // 更新套餐
        setmealDao.update(setmeal);
        // 删除旧关系
        setmealDao.deleteSetmealCheckGroup(setmeal.getId());
        // 遍历添加 新关系
        if(null != checkgroupIds){
            for (Integer checkgroupId : checkgroupIds) {
                setmealDao.addSetmealCheckGroup(setmeal.getId(), checkgroupId);
            }
        }
        // 事务控制
    }

    /**
    * @description: 删除
    * @author: Deshan
    * @date: 2021/1/8 19:46
    * @param: [id]
    * @return: void
    */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteById(int id) {
        // 先判断 是否被订单使用了
        int count = setmealDao.findCountBySetmealId(id);

        if(count > 0){
            throw new HealthException("该套餐被订单使用了，不能删除");
        }
        // 没使用，则要先删除套餐与检查组的关系
        setmealDao.deleteSetmealCheckGroup(id);
        // 再删除套餐
        setmealDao.deleteById(id);
    }

    @Override
    public List<String> findImgs() {
        return setmealDao.findImgs();
    }

    @Override
    public List<Setmeal> findAll() {
        return setmealDao.findAll();
    }

    @Override
    public Setmeal findDetailById(int id) {
        return setmealDao.findDetailById(id);
    }

    @Override
    public List<Map<String, Object>> getSetmealReport() {
        return setmealDao.getSetmealReport();
    }
}
