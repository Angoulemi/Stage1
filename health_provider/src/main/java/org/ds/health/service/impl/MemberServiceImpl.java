package org.ds.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import org.ds.health.dao.MemberDao;
import org.ds.health.pojo.Member;
import org.ds.health.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;

@Service(interfaceClass = MemberService.class)
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;

    /**
    * @description: 通过手机号码判断是否为会员
    * @author: Deshan
    * @date: 2021/1/12 20:15
    * @param: [telephone]
    * @return: org.ds.health.pojo.Member
    */
    @Override
    public Member findByTelephone(String telephone) {
        return memberDao.findByTelephone(telephone);
    }

    /**
    * @description: 通过手机号码判断是否为会员
    * @author: Deshan
    * @date: 2021/1/12 20:15
    * @param: [member]
    * @return: void
    */
    @Override
    public void add(Member member) {
        memberDao.add(member);
    }
}