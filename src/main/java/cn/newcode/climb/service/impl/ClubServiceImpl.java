package cn.newcode.climb.service.impl;

import cn.newcode.climb.mapper.ClubMapper;
import cn.newcode.climb.mapper.Club_memberMapper;
import cn.newcode.climb.mapper.Club_noticeMapper;
import cn.newcode.climb.po.Club;
import cn.newcode.climb.po.Club_member;
import cn.newcode.climb.po.Club_notice;
import cn.newcode.climb.service.ClubService;
import cn.newcode.climb.vo.ClubMemberVo;
import cn.newcode.climb.vo.FindClubVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: shine
 * \* Date: 17-12-21
 * \* Time: 下午7:23
 * \* Description:
 * \
 */
@Service("clubService")
public class ClubServiceImpl implements ClubService {

    @Autowired
    private ClubMapper clubMapper;

    @Autowired
    private Club_memberMapper club_memberMapper;

    @Autowired
    private Club_noticeMapper club_noticeMapper;

    @Override
    @Transactional
    public void createClub(Club club,Integer uid) {
        //创建俱乐部
        clubMapper.insertSelective(club);
        //获取俱乐部的id
        Integer cid = clubMapper.selectByCname(club.getName());
        //创建人加入俱乐部
        Club_member member = new Club_member();
        member.setMember(uid);
        member.setPosition("会长");
        member.setCid(cid);
        joinClub(member);
    }

    @Override
    public void joinClub(Club_member club_member) {
        club_memberMapper.insertSelective(club_member);
    }

    @Override
    public void addNotice(Club_notice club_notice) {
        club_noticeMapper.insertSelective(club_notice);
    }

    @Override
    public String getNotice(Integer clid) {
        return club_noticeMapper.selectNoticeByCid(clid);
    }

    @Override
    public List<FindClubVo> listAllClub(Integer startPos, Integer pageSize, String clubName) {
        return clubMapper.ListByName(startPos, pageSize, clubName);
    }

    @Override
    public Integer count(String clubName) {
        return clubMapper.selectCount(clubName);
    }

    @Override
    public List<ClubMemberVo> listMamber(Integer startPos, Integer pageSize, Integer uid,String name) {
        Integer cid = club_memberMapper.selectClub(uid);
        return club_memberMapper.selectMember(startPos, pageSize, cid,name,uid);
    }

    @Override
    public Integer memberCount(Integer uid, String name) {
        Integer cid = club_memberMapper.selectClub(uid);
        return club_memberMapper.selectCount(cid, name);
    }

    @Override
    public void quiteClub(Integer uid) {
        club_memberMapper.quiteClub(uid);
    }
}