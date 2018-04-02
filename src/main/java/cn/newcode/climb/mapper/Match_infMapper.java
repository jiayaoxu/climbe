package cn.newcode.climb.mapper;

import cn.newcode.climb.po.Match_inf;
import cn.newcode.climb.vo.Clear;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Match_infMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Match_inf record);

    int insertSelective(Match_inf record);

    Match_inf selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Match_inf record);

    int updateByPrimaryKey(Match_inf record);

    Match_inf selectMatchInf(Integer mid);

    void sign(Match_inf match_inf);

    Integer selectCommitCount(Integer mid);

    List<Clear> clear(Integer mid);
}