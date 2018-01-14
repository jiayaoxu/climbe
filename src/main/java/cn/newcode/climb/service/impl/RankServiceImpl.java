package cn.newcode.climb.service.impl;

import cn.newcode.climb.mapper.*;
import cn.newcode.climb.po.Rank_medal;
import cn.newcode.climb.po.Rank_wall;
import cn.newcode.climb.service.RankService;
import cn.newcode.climb.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("rankService")
public class RankServiceImpl implements RankService {

    @Autowired
    private Rank_teacherMapper rank_teacherMapper;

    @Autowired
    private Rank_ageMapper rank_ageMapper;

    @Autowired
    private Rank_medalMapper rank_medalMapper;

    @Autowired
    private Rank_recordMapper rank_recordMapper;

    @Autowired
    private Rank_wallMapper rank_wallMapper;

    /**
     * 点赞操作
     * @param teacher
     */
    @Override
    public void addPoint(Integer teacher) {
        rank_teacherMapper.addPoint(teacher);
    }

    /**
     * 分页查询排名
     * @param startPos
     * @param pageSize
     * @return
     */
    @Override
    public List<Rank_teacherVo> rankTeacherList(Integer startPos, Integer pageSize) {
        return rank_teacherMapper.seletcPaging(startPos,pageSize);
    }

    @Override
    public Integer selectCount() {
        return rank_teacherMapper.selectCount();
    }

    @Override
    public Boolean addFlover(Rank_wall rank_wall) {
        Rank_wall wall = rank_wallMapper.selectByWallAndFlover(rank_wall);
        if(wall!=null)
            return false;
        rank_wallMapper.insertSelective(rank_wall);
        return true;
    }

    @Override
    public List<Integer> selectCountWall() {
        return rank_wallMapper.selectCount();
    }

    @Override
    public List<Rank_wallVo> rankWallList(Integer startPos, Integer pageSize) {
        return rank_wallMapper.selectRank(startPos,pageSize);
    }

    @Override
    public List<Rank_ageVo> rankAgeList(Integer startPos, Integer pageSize) {
        return rank_ageMapper.seletcAgeRank(startPos, pageSize);
    }

    @Override
    public Integer selectAgeCount() {
        return rank_ageMapper.selectCount();
    }

    @Override
    public void addIntegral(Integer uid) {
        rank_recordMapper.addIntegral(uid);
    }

    @Override
    public List<Rank_recordVo> rankRecordList(Integer startPos, Integer pageSize) {
        return rank_recordMapper.seletcRecordRank(startPos, pageSize);
    }

    @Override
    public Integer selectRecordCount() {
        return rank_recordMapper.selectCount();
    }

    @Override
    public List<Rank_medalVo> rankMedalList(Integer startPos, Integer pageSize, Rank_medal rank_medal) {
        return rank_medalMapper.seletcRank(startPos, pageSize, rank_medal);
    }

    @Override
    public Integer selectMedalCount(String medal) {
        return rank_medalMapper.selectCount(medal);
    }

    @Override
    public Integer selectMyMedalRank(Rank_medal rank_medal) {
        return rank_medalMapper.selectMyRank(rank_medal);
    }

    @Override
    public Integer selectMyAgeRank(Integer uid) {
        return rank_ageMapper.selectMyRank(uid);
    }

    @Override
    public Integer selectMyRecordRank(Integer uid) {
        return rank_recordMapper.selectMyRank(uid);
    }

    @Override
    public Integer selectMyTeacherRank(Integer uid) {
        return rank_teacherMapper.selectMyRank(uid);
    }
}
