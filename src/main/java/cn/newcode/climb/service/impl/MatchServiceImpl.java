package cn.newcode.climb.service.impl;

import cn.newcode.climb.Fight.tool.UserManager;
import cn.newcode.climb.mapper.*;
import cn.newcode.climb.matchUtil.GetInMatch;
import cn.newcode.climb.matchUtil.GradeManager;
import cn.newcode.climb.matchUtil.MessageManagement;
import cn.newcode.climb.matchUtil.timer;
import cn.newcode.climb.po.*;
import cn.newcode.climb.service.MatchService;
import cn.newcode.climb.vo.Clear;
import cn.newcode.climb.vo.FinalsMatchVo;
import cn.newcode.climb.vo.Grade;
import cn.newcode.climb.vo.MathVo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description: 比赛service具体实现
 * @author: shine
 * @CreateDate: 2017/10/18 6:18
 * @Version: 1.0
 */
@Service("matchService")
//@CacheConfig(cacheNames = "match")
public class MatchServiceImpl implements MatchService {

    @Autowired
    private MatchMapper matchMapper;

    @Autowired
    private Match_gradeMapper match_gradeMapper;

    @Autowired
    private Match_signupMapper match_signupMapper;

    @Autowired
    private Match_infMapper match_infMapper;

    @Autowired
    private Rock_wall_defaultMapper rock_wall_defaultMapper;

    @Override
    //@Cacheable
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
    //@CachePut
    public int insertSelective(Match match, Match_inf match_inf, String date1, String date2, final long timestamp) throws Exception {
        matchMapper.insertSelective(match);
        //通过比赛名称查询刚插入的比赛id
        final int mid = matchMapper.selectBymatchName(match.getName());
        match_inf.setMid(mid);
        Integer hid = match_inf.getVenue();
        Rock_wall_default r = new Rock_wall_default();
        r.setCl(1);
        r.setType(1);
        r.setHid(hid);
        Integer router = rock_wall_defaultMapper.selectByClass(r).get(0).getWid();
        match_inf.setRoute(router);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date d1 = simpleDateFormat.parse(date1);
        match_inf.setStarttime(d1);
        Date d2 = simpleDateFormat.parse(date2);
        match_inf.setStarttimeT(d2);
        //添加比赛其他信息
        match_infMapper.insertSelective(match_inf);

        //开启计时器,计算开始时间，到开始时间才能开始比赛
        Timer timer = new Timer();
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                //开启比赛
                Match m = new Match();
                m.setId(mid);
                m.setStatus(true);
                matchMapper.updateByPrimaryKeySelective(m);
                Timer timer = new Timer();
                //开启计时器，计算比赛结束,整理选手成绩
                TimerTask task = new TimerTask() {
                    @Override
                    public void run() {
                        //Match m = new Match();
                        //m.setId(mid);
                        //m.setStatus(false);
                        //matchMapper.updateByPrimaryKeySelective(m);
                        try {
                            clear(mid);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        //初赛结束，存储进入决赛的人员
//                        Integer finalPlayer = 0;
//                        Integer count = match_gradeMapper.selectGradeCount(mid);
//                        if(count>=16){
//                            finalPlayer = 16;
//                        }else if(count>=8){
//                            finalPlayer = 8;
//                        }else if(count>=4){
//                            finalPlayer = 4;
//                        }else if(count>=2){
//                            finalPlayer = 2;
//                        }else if(count<2){
//                            finalPlayer = 1;
//                        }
//                        //查询进入决赛的成员
//                        GradeManager gradeManager = GradeManager.getInstance();
//                        Match_grade match_grade = new Match_grade();
//                        match_grade.setMid(mid);
//                        List<FinalsMatchVo> finalsMatchVosList = match_gradeMapper.selectFinal(finalPlayer,match_grade);
//                        //进入决赛的成员设进成绩管理器
//                        Map<Integer,List<Integer>> players = new HashMap<Integer, List<Integer>>();
//                        //排名设入排名管理器
//                        List<Integer> rank = new ArrayList<Integer>();
//                        for(FinalsMatchVo f : finalsMatchVosList){
//                            List<Integer> grade = new ArrayList<Integer>();
//                            grade.add(f.getGrade());
//                            players.put(f.getUid(),grade);
//                            rank.add(f.getUid());
//                        }
//                        gradeManager.setGrade(players);
//                        gradeManager.setRank(rank);
                    }
                };
                timer.schedule(task,timestamp);
            }
        };
        timer.schedule(task,d1);
        return 1;
    }

    /**
     * 管理员关掉比赛，分析排名，存储玩家奖牌
     * @param match
     * @return
     * @throws Exception
     */
    @Override
    //@CachePut
    public int updateByPrimaryKeySelective(Match match) throws Exception {
        //return matchMapper.updateByPrimaryKeySelective(match);
        return 0;
    }

    /**
     * 选手提交成绩
     * @param matchGrade
     * @return
     * @throws Exception
     */
    @Override
    //@CachePut
    public Integer insertSelective(Match_grade matchGrade) throws Exception {
        //成绩插入数据库
//        Match_grade match_grade = match_gradeMapper.selectReapet(matchGrade);
//        if(match_grade.getId()!=null){
//            return 0;
//        }
        match_gradeMapper.insertSelective(matchGrade);
//        Integer count = match_infMapper.selectMatchInf(matchGrade.getMid()).getCount();
//        Integer nowCount = match_infMapper.selectCommitCount(matchGrade.getMid());
        /*if(count == nowCount){
            //向所有人分发成绩
            clear(matchGrade.getMid());
        }*/
        return 1;
    }

    /**
     * 海选获取成绩排行榜
     * @param mid
     * @return
     * @throws Exception
     */
    @Override
    //@Cacheable
    public List<grade> seletcMatchList(Integer mid) throws Exception {
        //return match_gradeMapper.selectGrade(mid);
        return null;
    }

    /**
     * 暂停不用
     * 海选用户报名比赛
     * @param recode
     * @return
     * @throws Exception
     */
    @Override
    //@CachePut
    public Integer signUpMatch(Match_signup recode) throws Exception {
        recode.setIsmatchd(false);
        return match_signupMapper.insertSelective(recode);
    }

    /**暂停不用
     * 用户请求开始比赛
     * @param signup
     * @return
     * @throws Exception
     */
    @Override
    //@CachePut
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
        /*Integer mid = matchGrade.getMid();
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
        return grade;*/
        return null;
    }

    /**
     * 复赛  复复赛  决赛  提交成绩
     * @param matchGrade
     * @return
     * @throws Exception
     */
    @Override
    public Integer uploadGradeS(Match_grade matchGrade) throws Exception {
        /*GradeManager gradeManager = GradeManager.getInstance();
        //插入内存
        gradeManager.setPlayerGrade(matchGrade.getMid(),matchGrade);
        //插入数据库
        return match_gradeMapper.uploadGrade(matchGrade);
    */
        return null;
    }

    @Override
    public void ruleMethod(Integer mid, Integer total) throws Exception {

    }

    /**
     * 晋级赛查询成绩,提交uid,mid
     * 通过mid获取赛事晋级成员
     * 通过uid获取自己的上一次排名
     * 通过获取的排名获取对手uid
     * 通过对手uid获取这一次对手成绩
     * 对比这一次的成绩
     * @param
     * @throws Exception
     */

    public Boolean getGradeRise(Match_grade matchGrade,Integer degree) throws Exception {
        /*//判断自己是否还有资格
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
        }*/
        return null;
    }

    /**
     * 获取比赛信息
     * @return
     * @throws Exception
     */
    @Override
    public MathVo getMatchInfo() throws Exception {
        return matchMapper.selectMatch();
    }

    /**
     * 查询选手的成绩排名
     * @param match_grade
     * @return
     * @throws Exception
     */
    @Override
    public Grade selectRank(Match_grade match_grade) throws Exception {
        //Grade grade = new Grade();
        Grade grade = match_gradeMapper.selectRank(match_grade);
        Integer totalPlayer = match_gradeMapper.selectCountByMid(match_grade.getMid());
        grade.setHasNext(hadNext(totalPlayer,grade.getRanking()).getIN());
        return grade;
    }

    /**
     * 查询晋级人员
     * @param mid
     * @return
     * @throws Exception
     */
    @Override
    public List<FinalsMatchVo> selectFinal(Integer mid) throws Exception {
        Integer count = match_gradeMapper.selectGradeCount(mid);
        Match_grade match_grade = new Match_grade();
        match_grade.setMid(mid);
        int i = 0;
        if(count>=16){
            i=16;
        }else if(count>=8){
            i=8;
        }else if(count>=4){
            i=4;
        }else if(count>=2){
            i=2;
        }else {
            i=1;
        }
        return match_gradeMapper.selectFinal(i,match_grade);
    }

    /**
     * 决赛提交成绩
     * @param match_grade
     * @throws Exception
     */
    @Override
    public void submitGrade(Match_grade match_grade) throws Exception {
//        GradeManager gradeManager = GradeManager.getInstance();
//        Integer uid = match_grade.getUid();
//        //判断这是第几次提交成绩
//        List<Integer> gradeList = gradeManager.getGrade().get(uid);
//        Integer i = gradeList.size();
//        Integer grade = match_grade.getGrade();
//        if(i==2){
//            match_grade.setSgrade(grade);
//        }else if(i==3){
//            match_grade.setTgrade(grade);
//        }else if(i==4){
//            match_grade.setFgrade(grade);
//        }else if(i==5){
//            match_grade.setFigrade(grade);
//        }
//        gradeList.add(grade);
//        //添加到成绩管理器中
//        Map<Integer,List<Integer>> gManager = gradeManager.getGrade();
//        gManager.put(uid,gradeList);
//        gradeManager.setGrade(gManager);
//        match_grade.setGrade(null);
        //成绩插入数据库
        match_gradeMapper.updateByPrimaryKeySelective(match_grade);
        String flag = "";
        if(match_grade.getSgrade()!=null){
            flag = "s";
        }else if(match_grade.getTgrade()!=null){
            flag = "t";
        }else if(match_grade.getFgrade()!=null){
            flag = "f";
        }else if(match_grade.getFigrade()!=null){
            flag = "fi";
        }
        //启动消息推送异步
        MessageManagement management = new MessageManagement(
                match_grade.getUid(),match_grade.getMid(),flag,match_grade.getGrade());
        Thread thread =  new Thread(management);
        thread.start();
    }

    @Override
    public Boolean isWin(Integer uid) throws Exception {
        GradeManager gradeManager = GradeManager.getInstance();
        List<Integer> rank = gradeManager.getRank();
        Map<Integer,List<Integer>> grades = gradeManager.getGrade();
        int count = 0;
        for(int i = 0;i<rank.size();i++){
            count++;
            if(rank.get(i)==uid){
                break;
            }
        }
        //获取对手的uid
        Integer equal = rank.get(rank.size()-(count-1));
        //判断是第几次成绩比较
        Integer t = grades.get(uid).size();
        Integer mg = grades.get(uid).get(t-1);
        Integer yg = grades.get(equal).get(t-1);
        Boolean flag = true;
        if(yg==null){
            flag = true;
        } else if(mg==null){
            flag = false;
        }else if(mg>yg){
            flag = true;
        }else if(mg<yg){
            flag = false;
        }

        return flag;
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

    /*public void ruleMethod(Integer mid,Integer total) throws Exception {
        GradeManager gradeManager = GradeManager.getInstance();
        GetInMatch get = new GetInMatch();
        get.setMid(mid);
        get.setTotal(total);
        List<Integer> grades = match_gradeMapper.selectRankList(get);
        gradeManager.addMathcRanking(mid,grades);
    }*/
    public void sign(Match_inf match_inf){
        match_infMapper.sign(match_inf);
    }

    @Override
    public Match_grade selectGrade(Match_grade grade) {
        return match_gradeMapper.selectGrades(grade);
    }

    @Override
    public List<Integer> getAllPlayersInThisMatch(Integer mid) {
        return match_gradeMapper.selectPlayersInThisMatch(mid);
    }

    public void clear(Integer mid) throws IOException {
        ObjectMapper obj = new ObjectMapper();
        List<Clear> clears = match_infMapper.clear(mid);
        int count = clears.size();
        Integer total = 0;
        if(count>=16){
            total=16;
        }else if(count>=8){
            total=8;
        }else if(count>=4){
            total=4;
        }else if(count>=2){
            total=2;
        }else {
            total=1;
        }
        for(int i = 0;i<clears.size();i++){
            Integer uid = clears.get(i).getUid();
            Grade grade = new Grade();
            grade.setRanking(i+1);
            //判断自己是否晋级
            if(i<total){
                Integer equal = clears.get(total-i-1).getUid();
                grade.setEqual(equal);
                grade.setHasNext(true);

                //添加对手到成绩管理器
                GradeManager g = GradeManager.getInstance();
                g.setEquls(uid,equal);
            }
            grade.setTotalIn(total);
            UserManager userManager = UserManager.getInstance();
            Socket s = userManager.getPlayer(uid);
            DataOutputStream out = new DataOutputStream(s.getOutputStream());
            out.write(addCache("matchGrade@"+obj.writeValueAsString(grade)));
        }
    }

    public static byte[] addCache(String value){
        byte src [] = value.getBytes();
        byte response [] = new byte[1024];
        System.arraycopy(src,0,response,0,src.length);
        return response;
    }
}
