package cn.newcode.climb.service.impl;

import cn.newcode.climb.mapper.MatchMapper;
import cn.newcode.climb.mapper.Match_gradeMapper;
import cn.newcode.climb.mapper.Match_infMapper;
import cn.newcode.climb.mapper.Match_signupMapper;
import cn.newcode.climb.matchUtil.GetInMatch;
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
     * 海选获取成绩排行榜
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
     * 海选用户报名比赛
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
     * 首先获取总参加的人数 通过报名并开始比赛的表中查询总人数
     * 通过与提交人数比较,等到全部提交完成之后,通过uid和mid获取自己的排名
     * 通过自己的排名和总参加的人数,判断自己是否晋级，并获取总晋级的人数
     * 通过总晋级的人数和自己的排名,获取对手的排名
     * 通过对手的排名,获取对手的id
     * 传给client 自己排名,自己是否晋级,自己的对手的id
     * @param matchGrade
     * @return
     * @throws Exception
     */
    @Override
    public Grade getGrade(Match_grade matchGrade) throws Exception {
        Integer mid = matchGrade.getMid();
        Integer totalPlayer = match_signupMapper.selectMatched(mid);
        //设置系统耐心值
        int i = 0;
        while(!totalPlayer.equals(match_gradeMapper.selectGradeCount(mid))){
            Thread.sleep(500);
            i++;
            //等待20秒后,不能再继续等待,说明有人掉线,忽略掉此人
            if(i>=40){
                break;
            }
        }

        Grade grade = match_gradeMapper.selectRank(matchGrade);
        hasIn hs = hadNext(totalPlayer,grade.getRanking());
        //是否进入下一场赛事
        grade.setHasNext(hs.getIN());
        //查询对手id
        rank r = new rank();
        r.setMid(matchGrade.getMid());
        r.setRank(hs.getTotal()-grade.getRanking()+1);
        Integer uid = match_gradeMapper.selectUserByRanking(r);
        //添加对手
        grade.setEqual(uid);


        //内存中添加进入复赛的人选
        ruleMethod(mid,hs.getTotal());
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
        GradeManager gradeManager = GradeManager.getInstance();
        //插入内存
        gradeManager.setPlayerGrade(matchGrade.getMid(),matchGrade);
        //插入数据库
        return match_gradeMapper.uploadGrade(matchGrade);
    }

    /**
     * 晋级赛查询成绩,提交uid,mid
     * 通过mid获取赛事晋级成员
     * 通过uid获取自己的上一次排名
     * 通过获取的排名获取对手uid
     * 通过对手uid获取这一次对手成绩
     * 对比这一次的成绩
     * @param matchGrade
     * @throws Exception
     */
    @Override
    public Boolean getGradeRise(Match_grade matchGrade,Integer degree) throws Exception {
        //判断自己是否还有资格
        Boolean OwnFlag = false;
        Integer uid = matchGrade.getUid();
        Integer mid = matchGrade.getMid();
        GradeManager gradeManager = GradeManager.getInstance();
        List<Integer> rank = gradeManager.getMatchList(mid);
        Integer MatchUid = null;
        int i = 0;
        for(i =0;i<rank.size();i++){
            if(uid.equals(rank.get(i))){
                //List中能找到自己,说明有资格
                OwnFlag = true;
                MatchUid = rank.get(rank.size()-i-1);
                break;
            }
        }
        //如果没找到自己,说明自己已经被PK,没有下一场参赛资格
        if(!OwnFlag){
            return false;
        }
        //找不到对手uid,说明对手被除名
        if(MatchUid==null){
            return true;
        }

        //获取成绩
        Match_grade OwnMg = null;
        Match_grade MatchMg = null;
        Integer OwnGrade = null;
        Integer MatchGrade = null;
        //获取自己的成绩
        OwnMg = gradeManager.completeGrade(mid,uid);
        //获取对手的成绩
        //设置系统耐心值
        int wait = 0;
        //判断收拾是否提交成绩
        while(MatchMg == null){
            Thread.sleep(500);
            MatchMg = gradeManager.completeGrade(mid,MatchUid);
            //如果对手超过20秒不提交信息,默认比赛成绩无效,本人胜出
            wait++;
            if(wait>40){
                return true;
            }
        }
        //判断第几次比赛
        if(degree==2){
            OwnGrade = OwnMg.getSgrade();
            MatchGrade = MatchMg.getSgrade();
        }else if(degree==3){
            OwnGrade = OwnMg.getTgrade();
            MatchGrade = MatchMg.getTgrade();
        }else if(degree==4){
            OwnGrade = OwnMg.getFgrade();
            MatchGrade = MatchMg.getFgrade();
        }else if(degree==5){
            OwnGrade = OwnMg.getFigrade();
            MatchGrade = MatchMg.getFigrade();
        }

        //如果自己的成绩大于对手的成绩(时间),说明自己输了,比赛结束,反之说明自己赢了
        if(OwnGrade>MatchGrade){
            //自己输了,将自己除名
            rank.remove(i);
            //重新设回内存
            gradeManager.addMathcRanking(mid,rank);
            return false;
        }else{
            //对手输了,对手除名
            rank.remove(rank.size()-i-1);
            gradeManager.addMathcRanking(mid,rank);
            return true;
        }
    }

    /**
     * 海选筛选
     * @param totalPlayer
     * @param ranking
     * @return
     */
    private hasIn hadNext(Integer totalPlayer,Integer ranking){
        Boolean flag = false;
        Integer total = 0;
        if(totalPlayer>16){
            if(ranking<=16){
                flag = true;
            }else{
                flag = false;
            }
            total = 16;
        }else if(totalPlayer>8){
            if(ranking<=8){
                flag = true;
            }else{
                flag = false;
            }
            total = 8;
        }else if(totalPlayer>4){
            if(ranking<=4){
                flag = true;
            }else {
                flag = false;
            }
            total = 4;
        }else if(totalPlayer>2){
            if(ranking<=2){
                flag = true;
            }else {
                flag = false;
            }
            total = 2;
        }else {
            flag = false;
        }

        hasIn hs = new hasIn();
        hs.setIN(flag);
        hs.setTotal(total);
        return hs;
    }

    /**
     * 添加晋级人员
     * @param mid
     * @param total
     * @throws Exception
     */
    @Override
    public void ruleMethod(Integer mid,Integer total) throws Exception {
        GradeManager gradeManager = GradeManager.getInstance();
        GetInMatch get = new GetInMatch();
        get.setMid(mid);
        get.setTotal(total);
        List<Integer> grades = match_gradeMapper.selectRankList(get);
        gradeManager.addMathcRanking(mid,grades);
    }
}
