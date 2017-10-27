package cn.newcode.climb.matchUtil;

import cn.newcode.climb.po.Match_grade;
import cn.newcode.climb.po.grade;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 暂时存储成绩
 * @author: shine
 * @CreateDate: 2017/10/25 11:36
 * @Version: 1.0
 */
public class GradeManager {

    //所有比赛
    private Map<Integer,Integer> GradeList = new HashMap<Integer, Integer>();



    public  void addGrade(Integer mid,Integer players){
        GradeList.put(mid,players);
    }

    public Integer getPlayers(Integer mid){
        return GradeList.get(mid);
    }

    public Integer total(Integer mid){
        return GradeList.size();
    }

    public static GradeManager getInstance(){
        return new GradeManager();
    }
}
