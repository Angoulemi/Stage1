package org.ds.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import org.ds.health.dao.CheckItemDao;
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

    @Override
    public List<CheckItem> findAll() {
        return checkItemDao.findAll();
    }
}
