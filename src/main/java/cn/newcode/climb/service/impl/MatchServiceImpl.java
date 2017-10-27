package cn.newcode.climb.service.impl;

import cn.newcode.climb.mapper.MatchMapper;
import cn.newcode.climb.mapper.Match_gradeMapper;
import cn.newcode.climb.mapper.Match_infMapper;
import cn.newcode.climb.mapper.Match_signupMapper;
import cn.newcode.climb.matchUtil.GradeManager;
import cn.newcode.climb.matchUtil.timer;
import cn.newcode.climb.po.*;
import cn.newcode.climb.service.MatchService;
import cn.newcode.climb.vo.Grade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @Description: 比赛service具体实现
 * @author: shine
 * @CreateDate: 2017/10/18 6:18
 * @Version: 1.0
 */
@Service("matchService")
@CacheConfig(cacheNames = "match")
public class MatchServiceImpl implements MatchService {

    @Autowired
    private MatchMapper matchMapper;

    @Autowired
    private Match_gradeMapper match_gradeMapper;

    @Autowired
    private Match_signupMapper match_signupMapper;

    @Autowired
    private Match_infMapper match_infMapper;

    @Override
    @Cacheable
    public List<Match> selectMatchs(Boolean status) throws Exception {
        return matchMapper.selectMatchs(status);
    }

    /**
     * 管理员添加比赛场次
     * @param match
     * @return
     * @throws Exception
     */
    @Override
    @CachePut
    public int insertSelective(Match match, Match_inf match_inf) throws Exception {
        matchMapper.insertSelective(match);

        //通过比赛名称查询刚插入的比赛id
        int mid = matchMapper.selectBymatchName(match.getName());
        match_inf.setMid(mid);
        //添加比赛其他信息
        match_infMapper.insertSelective(match_inf);
        //开启比赛计时器
        timer timer = new timer(match_inf.getStarttime(),mid);
        //进入线程
        Thread thread = new Thread(timer);
        thread.start();
        return 1;
    }

    @Override
    @CachePut
    public int updateByPrimaryKeySelective(Match match) throws Exception {
        return matchMapper.updateByPrimaryKeySelective(match);
    }

    /**
     * 选手提交成绩
     * @param matchGrade
     * @return
     * @throws Exception
     */
    @Override
    @CachePut
    public Integer insertSelective(Match_grade matchGrade) throws Exception {
        //成绩插入数据库
        match_gradeMapper.insertSelective(matchGrade);
        return 1;
    }

    /**
     * 获取成绩排行榜
     * @param mid
     * @return
     * @throws Exception
     */
    @Override
    @Cacheable
    public List<grade> seletcMatchList(Integer mid) throws Exception {
        return match_gradeMapper.selectGrade(mid);
    }

    /**
     * 用户报名比赛
     * @param recode
     * @return
     * @throws Exception
     */
    @Override
    @CachePut
    public Integer signUpMatch(Match_signup recode) throws Exception {
        recode.setIsmatchd(false);
        return match_signupMapper.insertSelective(recode);
    }

    /**
     * 用户请求开始比赛
     * @param signup
     * @return
     * @throws Exception
     */
    @Override
    @CachePut
    public Match_inf requiredStart(Match_signup signup) throws Exception {
        signup.setIsmatchd(true);
        match_signupMapper.updateSignUp(signup);
        return match_infMapper.selectMatchInf(signup.getMid());
    }

    /**
     * 获取选手成绩
     * @param matchGrade
     * @return
     * @throws Exception
     */
    @Override
    public Grade getGrade(Match_grade matchGrade) throws Exception {
        Integer mid = matchGrade.getMid();
        Integer totalPlayer = match_signupMapper.selectMatched(mid);
        while(!totalPlayer.equals(match_gradeMapper.selectGradeCount(mid))){
            Thread.sleep(500);
        }
        //返回名次
        Integer uid = matchGrade.getUid();
        List<grade> grades = match_gradeMapper.selectGrade(matchGrade.getMid());
        int ranking = 0;
        for(grade g : grades){
            ranking ++;
            Integer gUid = g.getUid();
            if(gUid.equals(uid)){
                break;
            }
        }

        //海选结束,选拔进入半决赛
        Grade grade = new Grade();
        grade.setRanking(ranking);

        if(totalPlayer>16){
            if(ranking<=16){
                grade.setHasNext(true);
            }else{
                grade.setHasNext(false);
            }
            //grade.setForwardIn(16);
        }else if(totalPlayer>8){
            if(ranking<=8){
                grade.setHasNext(true);
            }else{
                grade.setHasNext(false);
            }
            //grade.setForwardIn(8);
        }else if(totalPlayer>4){
            if(ranking<=4){
                grade.setHasNext(true);
            }else {
                grade.setHasNext(false);
            }
            //grade.setForwardIn(4);
        }else if(totalPlayer>2){
            if(ranking<=2){
                grade.setHasNext(true);
            }else {
                grade.setHasNext(false);
            }
            //grade.setForwardIn(2);
        }else {
            grade.setHasNext(false);
        }

        return grade;
    }

    /**
     * 复赛  复复赛  决赛  提交成绩
     * @param matchGrade
     * @return
     * @throws Exception
     */
    @Override
    public Integer uploadGradeS(Match_grade matchGrade) throws Exception {
        return match_gradeMapper.uploadGrade(matchGrade);
    }


}
