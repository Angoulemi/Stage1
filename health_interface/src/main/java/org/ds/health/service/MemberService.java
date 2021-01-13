package org.ds.health.service;

import org.ds.health.pojo.Member;

/**
 * @author :Deshan
 * @description:
 * @date :Created in 2021/1/12 20:13
 * @version: 1.0
 */
public interface MemberService {
    Member findByTelephone(String telephone);

    void add(Member member);
}
