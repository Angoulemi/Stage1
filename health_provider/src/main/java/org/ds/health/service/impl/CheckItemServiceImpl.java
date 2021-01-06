package org.ds.health.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.ds.health.dao.CheckItemDao;
import org.ds.health.entity.PageResult;
import org.ds.health.entity.QueryPageBean;
import org.ds.health.exception.HealthException;
import org.ds.health.pojo.CheckItem;
import org.ds.health.service.CheckItemService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author :Deshan
 * @description:
 * @date :Created in 2021/1/5 18:05
 * @version: 1.0
 */
@Service(interfaceClass = CheckItemService.class)
public class CheckItemServiceImpl implements CheckItemService {

    @Autowired
    private CheckItemDao checkItemDao;

    /**
    * @description: 查询所有
    * @author: Deshan
    * @date: 2021/1/6 20:42
    * @param: []
    * @return: java.util.List<org.ds.health.pojo.CheckItem>
    */
    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }

    /**
    * @description: 添加检查项
    * @author: Deshan
    * @date: 2021/1/6 20:42
    * @param: [checkItem]
    * @return: void
    */
    @Override
    public void add(CheckItem checkItem) {
        checkItemDao.add(checkItem);
    }

    /**
    * @description: 删除选择项
    * @author: Deshan
    * @date: 2021/1/6 20:42
    * @param: [id]
    * @return: void
    */
    @Override
    public void deleteById(int id) {
        int count = checkItemDao.findCountByCheckItemId(id);
        if (count > 0) {
            throw new HealthException("该检查项被使用了，不能删除!");
        }

        checkItemDao.deleteById(id);
    }

    /**
    * @description: 改
    * @author: Deshan
    * @date: 2021/1/6 20:43
    * @param: [checkItem]
    * @return: void
    */
    @Override
    public void update(CheckItem checkItem) {
        checkItemDao.update(checkItem);
    }

    @Override
    public CheckItem findById(int id) {
        return checkItemDao.findById(id);
    }

    /**
    * @description: 分页
    * @author: Deshan
    * @date: 2021/1/6 20:43
    * @param: [queryPageBean]
    * @return: org.ds.health.entity.PageResult<org.ds.health.pojo.CheckItem>
    */
    @Override
    public PageResult<CheckItem> findPage(QueryPageBean queryPageBean) {
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());

        if (StringUtils.isNotEmpty(queryPageBean.getQueryString())) {
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }

        Page<CheckItem> page = checkItemDao.findByCondition(queryPageBean.getQueryString());

        return new PageResult<>(page.getTotal(), page.getResult());
    }

}
