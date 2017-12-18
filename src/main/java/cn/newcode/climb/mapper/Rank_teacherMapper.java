package cn.newcode.climb.mapper;

import cn.newcode.climb.po.Rank_teacher;
import cn.newcode.climb.vo.Rank_teacherVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Rank_teacherMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Rank_teacher record);

    int insertSelective(Rank_teacher record);

    Rank_teacher selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Rank_teacher record);

    int updateByPrimaryKey(Rank_teacher record);

    void addPoint(Integer teacher);

    List<Rank_teacherVo> seletcPaging(@Param("startPos")Integer startPos, @Param("pageSize")Integer pageSize);

    Integer selectCount();
}