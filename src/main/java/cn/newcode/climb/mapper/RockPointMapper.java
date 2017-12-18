package cn.newcode.climb.mapper;

import cn.newcode.climb.po.RockPoint;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RockPointMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RockPoint record);

    int insertSelective(RockPoint record);

    RockPoint selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RockPoint record);

    int updateByPrimaryKey(RockPoint record);

    /**
     * 获取岩壁下的所有岩点
     * @param wid
     * @return
     */
    List<RockPoint> selectPoints(Integer wid);

    /**
     * 删除指定岩壁下的所有岩点
     * @param wid
     */
    void deletePoints(Integer wid);
}