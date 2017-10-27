package cn.newcode.climb.mapper;

import cn.newcode.climb.po.Match_grade;
import cn.newcode.climb.po.grade;
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
}