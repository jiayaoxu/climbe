package cn.newcode.climb.mapper;

import cn.newcode.climb.po.RockWall;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RockWallMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RockWall record);

    /**
     * 添加岩壁
     * @param record
     * @return
     */
    int insertSelective(RockWall record);

    RockWall selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RockWall record);

    int updateByPrimaryKey(RockWall record);

    /**
     * 通过岩馆id查询所有岩壁
     * @param hid
     * @return
     */
    List<RockWall> selectWalls(Integer hid);
}