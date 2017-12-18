package cn.newcode.climb.mapper;

import cn.newcode.climb.po.Match;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 *@author:shine
 *@Description:比赛mapper
 *@Date:6:10 2017/10/18
 */
@Repository
public interface MatchMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Match record);

    int insertSelective(Match record);

    Match selectByPrimaryKey(Integer id);

    int selectBymatchName(String name);

    int updateByPrimaryKeySelective(Match record);

    int updateByPrimaryKey(Match record);

    List<Match> selectMatchs(Boolean status);
}