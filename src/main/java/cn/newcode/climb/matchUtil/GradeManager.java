package cn.newcode.climb.matchUtil;

import cn.newcode.climb.DataBaseUtil.DataBaseUtil;
import cn.newcode.climb.LogUtil.MLogger;
import cn.newcode.climb.po.Match_grade;
import cn.newcode.climb.po.Rank_medal;
import cn.newcode.climb.po.grade;
import cn.newcode.climb.service.RankService;

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
     * 比赛成员成绩存储器

     */
    Map<Integer,List<Integer>> grade = new HashMap<Integer, List<Integer>>();

    List<Integer> rank = new ArrayList<Integer>();

    public List<Integer> getRank() {
        return rank;
    }

    public void setRank(List<Integer> rank) {
        this.rank = rank;
    }

    /**
     * 存储自己的对手是谁
     */
    Map<Integer,Integer> equls = new HashMap<Integer, Integer>();

    public Integer getEquals(Integer uid){
        return equls.get(uid);
    }

    public void setEquls(Integer uid,Integer e){
        equls.put(uid,e);
    }

    /**
     * 移除信息
     * @param uid
     */
    public void removeEqual(Integer uid){
        equls.remove(uid);
    }

    public Integer getEqualsSize(){
        return equls.size();
    }
    /**
     * 实例化内部类
     */
    private static  class Instance{
        private static final GradeManager gradeManager = new GradeManager();
    }

    private GradeManager(){}

    public Map<Integer, List<Integer>> getGrade() {
        return grade;
    }

    public void setGrade(Map<Integer, List<Integer>> grade) {
        this.grade = grade;
    }

    /**

     * 获取实例化
     * @return
     */
    public static GradeManager getInstance(){
        return Instance.gradeManager;
    }

    private ArrayList<Integer> medalList = new ArrayList<Integer>();

    public List<Integer> getMedalList(){
        return medalList;
    }

    public void addMedalList(Integer uid){
        medalList.add(uid);
    }

    public void clearMedalList(){
        try{
            int flag = 0;
            for(Integer p : medalList){
                flag++;
                Rank_medal rank_medal = new Rank_medal();
                if(flag<=3){
                    rank_medal.setGoldMedal(1);
                    rank_medal.setUid(p);
                }else if(flag>3&&flag<=6){
//                    Rank_medal rank_medal = new Rank_medal();
                    rank_medal.setSilverMedal(1);
                    rank_medal.setUid(p);
                }else if(flag>6){
//                    Rank_medal rank_medal = new Rank_medal();
                    rank_medal.setBronzeMedal(1);
                    rank_medal.setUid(p);
                }
                RankService rankService = DataBaseUtil.dataBaseUtil.rankService;
                rankService.addMedal(rank_medal);
            }

        } catch (Exception e){
//            e.printStackTrace();
            MLogger.error(e);
        }finally {
            medalList.clear();
        }
    }

}
