package cn.newcode.climb.mapper;

import cn.newcode.climb.po.Rank_age;
import cn.newcode.climb.vo.Rank_ageVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Rank_ageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Rank_age record);

    int insertSelective(Rank_age record);

    Rank_age selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Rank_age record);

    int updateByPrimaryKey(Rank_age record);

    List<Rank_ageVo> seletcAgeRank(@Param("startPos") Integer startPos,@Param("pageSize") Integer pageSize);

    Integer selectCount();
}