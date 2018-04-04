package cn.newcode.climb.service;

import cn.newcode.climb.po.*;
import cn.newcode.climb.vo.FinalsMatchVo;
import cn.newcode.climb.vo.Grade;
import cn.newcode.climb.vo.MathVo;
import com.fasterxml.jackson.databind.deser.DataFormatReaders;

import java.util.List;

/**
 * @Description: java类作用描述
 * @author: 作者姓名
 * @CreateDate: 2017/10/18 6:13
 * @Version: 1.0
 */
public interface MatchService {

    /**
     * 查询 正在进行/已经结束比赛
     * @param status
     * @return List<Match>
     */
    List<Match> selectMatchs(Boolean status) throws Exception;

    /**
     * 添加比赛
     * @param match
     * @throws Exception
     * @return int
     */
    int  insertSelective(Match match, Match_inf match_inf,String date1,String date2,long timestamp) throws Exception;

    /**
     * 改变比赛状态(停止比赛)
     * @param match
     * @throws Exception
     * @return int
     */
    int updateByPrimaryKeySelective(Match match) throws Exception;

    /**
     * 选手提交成绩,向选手返回比赛结果
     * @param matchGrade
     * @return
     * @throws Exception
     */
    Integer insertSelective(Match_grade matchGrade) throws Exception;

    /**
     * 排行榜
     * @param mid
     * @return
     * @throws Exception
     */
    List<grade> seletcMatchList(Integer mid) throws Exception;

    /**
     * 用户报名比赛
     * @param recode
     * @return
     * @throws Exception
     */
    Integer signUpMatch(Match_signup recode) throws Exception;

    /**
     * 请求开始比赛
     * @param signup
     * @return
     * @throws Exception
     */
    Match_inf requiredStart(Match_signup signup) throws Exception;

    /**
     * 海选获取选手成绩
     * @param matchGrade
     * @return
     * @throws Exception
     */
    Grade getGrade(Match_grade matchGrade) throws Exception;

    /**
     * 复赛提交成绩
     * @param matchGrade
     * @return
     * @throws Exception
     */
    Integer uploadGradeS(Match_grade matchGrade) throws Exception;

    /**
     * 添加晋级人员
     * @param mid
     * @param total
     * @throws Exception
     */
    void ruleMethod(Integer mid,Integer total) throws Exception;

    /**
     * 晋级赛查询成绩
     * @param matchGrade
     * @throws Exception
     * @return Boolean
     */
    Boolean getGradeRise(Match_grade matchGrade,Integer degree) throws Exception;

    /**
     * 初赛获取比赛信息
     * @return
     * @throws Exception
     */
    MathVo getMatchInfo() throws Exception;

    /**
     * 查询初赛排名
     * @param match_grade
     * @return
     * @throws Exception
     */
    Grade  selectRank(Match_grade match_grade)throws Exception;

    /**
     * 查询复赛晋级人员
     * @param mid
     * @return
     * @throws Exception
     */
    List<FinalsMatchVo> selectFinal(Integer mid)throws Exception;

    /**
     * 决赛提交成绩
     * @param match_grade
     * @throws Exception
     */
    void submitGrade(Match_grade match_grade)throws Exception;

    /**
     * 查询自己是否胜出
     * @param uid
     * @throws Exception
     */
    Boolean isWin(Integer uid)throws Exception;

    /**
     * 比赛报名
     * @param match_inf
     */
    void sign(Match_inf match_inf);

    /**
     * 查询成绩
     * @param grade
     * @return
     */
    Match_grade selectGrade(Match_grade grade);

    /**
     * 查询参加这次比赛的所有人
     * @param mid
     * @return
     */
    List<Integer> getAllPlayersInThisMatch(Integer mid);

}
