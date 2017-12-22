package cn.newcode.climb.service;

import cn.newcode.climb.po.Club;
import cn.newcode.climb.po.Club_member;
import cn.newcode.climb.po.Club_notice;
import cn.newcode.climb.vo.FindClubVo;

import java.util.List;

/**
 * @Description:俱乐部
 * @author: shine
 * @CreateDate: 下午7:18 17-12-21
 * @Version: 1.0
 */
public interface ClubService {

    /**
     * 创建俱乐部
     * @param club
     */
    void createClub(Club club,Integer uid);

    /**
     * 加入俱乐部
     * @param club_member
     */
    void joinClub(Club_member club_member);

    /**
     * 发公告
     * @param club_notice
     */
    void addNotice(Club_notice club_notice);

    /**
     * 通过俱乐部id查找俱乐部公告
     * @param clid
     * @return
     */
    String getNotice(Integer clid);

    /**
     * 通过俱乐部名字模糊查询俱乐部信息和加入人数
     * @param startPos
     * @param pageSize
     * @param clubName
     * @return
     */
    List<FindClubVo> listAllClub(Integer startPos,Integer pageSize,String clubName);

    /**
     * 查询俱乐部数量
     * @param clubName
     * @return
     */
    Integer count(String clubName);
}
