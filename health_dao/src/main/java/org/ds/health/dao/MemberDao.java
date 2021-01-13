package org.ds.health.dao;

import org.ds.health.pojo.Member;

/**
 * @author :Deshan
 * @description:
 * @date :Created in 2021/1/12 17:16
 * @version: 1.0
 */
public interface MemberDao {
    Member findByTelephone(String telephone);

    void add(Member member);
}
