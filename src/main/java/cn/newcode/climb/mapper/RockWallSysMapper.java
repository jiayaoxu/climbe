package cn.newcode.climb.mapper;

import cn.newcode.climb.po.RockWallSys;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RockWallSysMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RockWallSys record);

    int insertSelective(RockWallSys record);

    RockWallSys selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RockWallSys record);

    int updateByPrimaryKey(RockWallSys record);

    List<RockWallSys> selectAllWalls();
}