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
}
