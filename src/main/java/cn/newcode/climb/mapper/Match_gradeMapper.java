package cn.newcode.climb.mapper;

import cn.newcode.climb.matchUtil.GetInMatch;
import cn.newcode.climb.po.Match_grade;
import cn.newcode.climb.po.grade;
import cn.newcode.climb.po.rank;
import cn.newcode.climb.vo.FinalsMatchVo;
import cn.newcode.climb.vo.Grade;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *@author:shine
 *@Description:比赛成绩Mapper
 *@Date:6:09 2017/10/18
 */
@Repository
public interface Match_gradeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Match_grade record);

    int insertSelective(Match_grade record);

    Match_grade selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Match_grade record);

    int updateByPrimaryKey(Match_grade record);

    List<grade> selectGrade(Integer mid);

    Integer selectGradeCount(Integer mid);

    Integer uploadGrade(Match_grade record);

    Integer selectIn(Integer mid);

    Grade selectRank(Match_grade matchGrade);

    Integer selectUserByRanking(rank r);

    List<Integer> selectRankList(GetInMatch getInMatch);

    Integer selectCountByMid(Integer mid);

    List<FinalsMatchVo> selectFinal(@Param("finalPlayer")Integer finalPalyer, @Param("match")Match_grade match_grade);

    Match_grade selectGrades(Match_grade grade);

}