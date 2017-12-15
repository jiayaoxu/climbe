package cn.newcode.climb.mapper;

import cn.newcode.climb.po.Rank_record;
import cn.newcode.climb.vo.Rank_recordVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Rank_recordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Rank_record record);

    int insertSelective(Rank_record record);

    Rank_record selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Rank_record record);

    int updateByPrimaryKey(Rank_record record);

    void addIntegral(Integer uid);

    List<Rank_recordVo> seletcRecordRank(@Param("startPos") Integer startPos,@Param("pageSize") Integer pageSize);

    Integer selectCount();
}