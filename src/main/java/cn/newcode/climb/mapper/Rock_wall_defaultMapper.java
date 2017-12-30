package cn.newcode.climb.mapper;

import cn.newcode.climb.po.Rock_wall_default;
import cn.newcode.climb.vo.routerClass;

import java.util.List;

public interface Rock_wall_defaultMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Rock_wall_default record);

    int insertSelective(Rock_wall_default record);

    Rock_wall_default selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Rock_wall_default record);

    int updateByPrimaryKey(Rock_wall_default record);

    /**
     * 查询指定类别下的线路
     * @param rock_wall_default
     * @return
     */
    List<routerClass> selectByClass(Rock_wall_default rock_wall_default);

    /**
     * 查询同一库中有没有相同的路线
     * @param rock_wall_default
     * @return
     */
    Integer selectRe(Rock_wall_default rock_wall_default);

    /**
     * 查询线路总数
     * @param rock_wall_default
     * @return
     */
    Integer selectCountContent(Rock_wall_default rock_wall_default);

    /**
     * 通过类别进行删除
     * @param rock_wall_default
     */
    void deleteByCl(Rock_wall_default rock_wall_default);
}