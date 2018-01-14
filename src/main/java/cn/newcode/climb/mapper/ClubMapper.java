package cn.newcode.climb.mapper;

import cn.newcode.climb.po.Club;
import cn.newcode.climb.vo.FindClubVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClubMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Club record);

    int insertSelective(Club record);

    Club selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Club record);

    int updateByPrimaryKey(Club record);

    //通过名字查询俱乐部id
    Integer selectByCname(String name);

    //查询俱乐部列表
    List<FindClubVo> ListByName(@Param("startPos")Integer startPos,
                                @Param("pageSize")Integer pageSize,@Param("name")String name);

    //查询俱乐部数量
    Integer selectCount(@Param("name") String name);
}