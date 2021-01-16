package org.ds.health.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import org.ds.health.dao.MemberDao;
import org.ds.health.pojo.Member;
import org.ds.health.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

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

    @Override
    public List<Integer> getMemberReport(List<String> months) {
        List<Integer> list = new ArrayList<Integer>();
        if(null != months){
            // 2020-02
            for (String month : months) {
                month+="-31";
                list.add(memberDao.findMemberCountBeforeDate(month));
            }
        }
        return list;
    }
}