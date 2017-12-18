package cn.newcode.climb.mapper;

import cn.newcode.climb.po.Rock;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RockMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Rock record);

    int insertSelective(Rock record);

    Rock selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Rock record);

    int updateByPrimaryKey(Rock record);

    /**
     * 列出所有的岩石
     * @return
     */
    List<Rock> selectRocks();
}