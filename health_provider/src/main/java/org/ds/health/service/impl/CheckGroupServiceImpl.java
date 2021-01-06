package org.ds.health.service.impl;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.ds.health.dao.CheckGroupDao;
import org.ds.health.entity.PageResult;
import org.ds.health.entity.QueryPageBean;
import org.ds.health.exception.HealthException;
import org.ds.health.pojo.CheckGroup;
import org.ds.health.service.CheckGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author :Deshan
 * @description:
 * @date :Created in 2021/1/6 20:03
 * @version: 1.0
 */
@Service(interfaceClass = CheckGroupService.class)
public class CheckGroupServiceImpl implements CheckGroupService {

    @Autowired
    private CheckGroupDao checkGroupDao;

    /**
    * @description: 添加检查组
    * @author: Deshan
    * @date: 2021/1/6 20:36
    * @param: [checkgroup, checkitemIds]
    * @return: void
    */
    @Override
    @Transactional(rollbackFor = HealthException.class)
    public void add(CheckGroup checkgroup, Integer[] checkitemIds) {
        //- 先添加检查组
        checkGroupDao.add(checkgroup);
        //- 获取检查组的id
        Integer checkgroupId = checkgroup.getId();
        //- 遍历选中的检查项id的数组
        if(null != checkitemIds){
            for (Integer checkitemId : checkitemIds) {
                //- 添加检查组与检查项的关系
                checkGroupDao.addCheckGroupCheckItem(checkgroupId, checkitemId);
            }
        }
        //- 添加事务控制
    }

    /**
    * @description: 分页查询
    * @author: Deshan
    * @date: 2021/1/6 20:36
    * @param: [queryPageBean]
    * @return: org.ds.health.entity.PageResult<org.ds.health.pojo.CheckGroup>
    */
    @Override
    public PageResult<CheckGroup> findPage(QueryPageBean queryPageBean) {
        // pageSize能无限大吗？参数是从前端来的，100W，后台限制大小
        PageHelper.startPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        // 条件查询
        if(StringUtils.isNotEmpty(queryPageBean.getQueryString())){
            // 有查询条件， 模糊查询
            queryPageBean.setQueryString("%" + queryPageBean.getQueryString() + "%");
        }
        // page extends arrayList
        Page<CheckGroup> page = checkGroupDao.findByCondition(queryPageBean.getQueryString());
        return new PageResult<CheckGroup>(page.getTotal(),page.getResult());
    }

    /**
    * @description: 通过ID查询选择组
    * @author: Deshan
    * @date: 2021/1/6 20:36
    * @param: [id]
    * @return: org.ds.health.pojo.CheckGroup
    */
    @Override
    public CheckGroup findById(int id) {
        return checkGroupDao.findById(id);
    }

    /**
    * @description: 根据检查组的Id查询所关联的检查项Id
    * @author: Deshan
    * @date: 2021/1/6 20:35
    * @param: [id]
    * @return: java.util.List<java.lang.Integer>
    */
    @Override
    public List<Integer> findCheckItemIdsByCheckGroupId(int id) {
        return checkGroupDao.findCheckItemIdsByCheckGroupId(id);
    }

    /**
    * @description: 修改检查组信息
    * @author: Deshan
    * @date: 2021/1/6 20:34
    * @param: [checkgroup 检查组信息, checkitemIds 关联的检查项Id]
    * @return: void
    */
    @Override
    @Transactional(rollbackFor = HealthException.class)
    public void update(CheckGroup checkgroup, Integer[] checkitemIds) {
        //- 先更新检查组
        checkGroupDao.update(checkgroup);
        //- 先删除旧关系
        checkGroupDao.deleteCheckGroupCheckItem(checkgroup.getId());
        //- 遍历选中的检查项id的数组
        if(null != checkitemIds){
            for (Integer checkitemId : checkitemIds) {
                //- 添加检查组与检查项的关系
                checkGroupDao.addCheckGroupCheckItem(checkgroup.getId(), checkitemId);
            }
        }
    }
}
