package cn.newcode.climb.mapper;

import cn.newcode.climb.po.Rank_medal;
import cn.newcode.climb.vo.Rank_medalVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Rank_medalMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Rank_medal record);

    int insertSelective(Rank_medal record);

    Rank_medal selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Rank_medal record);

    int updateByPrimaryKey(Rank_medal record);

    List<Rank_medalVo> seletcRank(@Param("startPos") Integer startPos,
                                  @Param("pageSize") Integer pageSize,@Param("Rank_medal") Rank_medal rank_medal);

    Integer selectCount(String medal);
}