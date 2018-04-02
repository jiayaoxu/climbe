package cn.newcode.climb.mapper;

import cn.newcode.climb.po.Rock_record;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Rock_recordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Rock_record record);

    int insertSelective(Rock_record record);

    Rock_record selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Rock_record record);

    int updateByPrimaryKey(Rock_record record);

    /**
     * 查询今天所有线路的最高成绩
     * @return
     */
    List<Rock_record> selectMaxRecords();
}