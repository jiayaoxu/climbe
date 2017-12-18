package cn.newcode.climb.mapper;

import cn.newcode.climb.po.RockHall;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RockHallMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RockHall record);

    int insertSelective(RockHall record);

    RockHall selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RockHall record);

    int updateByPrimaryKey(RockHall record);

    /**
     * 查询所有岩馆
     * @return
     * @throws Exception
     */
    List<RockHall> selectHalls() throws Exception;
}