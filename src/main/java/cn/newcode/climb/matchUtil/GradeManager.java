package cn.newcode.climb.matchUtil;

import cn.newcode.climb.po.Match_grade;
import cn.newcode.climb.po.grade;

import java.lang.reflect.Array;
import java.util.*;

/**
 * @Description: 暂时存储成绩
 * @author: shine
 * @CreateDate: 2017/10/25 11:36
 * @Version: 1.0
 */
public class GradeManager {

    /**
     * 存储排名
     */
    private Map<Integer,List<Integer>> grade = new HashMap<Integer, List<Integer>>();

    /**
     * 存储对比成绩
     */
    private Map<Integer,Map<Integer,Match_grade>> playerGrade = new HashMap<Integer, Map<Integer, Match_grade>>();

    /**
     * 实例化内部类
     */
    private static  class Instance{
        private static final GradeManager gradeManager = new GradeManager();
    }

    private GradeManager(){}

    /**
     * 获取实例化
     * @return
     */
    public static GradeManager getInstance(){
        return Instance.gradeManager;
    }

    /**
     * 添加比赛排名
     * @param mid
     * @param ranking
     */
    public void addMathcRanking(Integer mid,List<Integer> ranking){
        grade.put(mid,ranking);
    }

    /**
     * 获取比赛排名
     * @param mid
     * @return
     */
    public List<Integer> getMatchList(Integer mid){
        return grade.get(mid);
    }

    /**
     * 添加对比成绩
     *
     * @param mid
     * @param matchGrade
     */
    public void setPlayerGrade(Integer mid,Match_grade matchGrade){
        Map<Integer,Match_grade> map = playerGrade.get(mid)==null?new HashMap<Integer, Match_grade>():playerGrade.get(mid);
        map.put(matchGrade.getUid(),matchGrade);
        playerGrade.put(mid,map);
    }

    /**
     * 获取对应赛事的对应玩家的成绩
     * @param mid
     * @param uid
     * @return
     */
    public Match_grade completeGrade(Integer mid,Integer uid){
        return  playerGrade.get(mid).get(uid);
    }

}
