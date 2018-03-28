package cn.newcode.climb.mapper;

import cn.newcode.climb.po.Rank_wall;
import cn.newcode.climb.vo.Rank_wallVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Rank_wallMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Rank_wall record);

    int insertSelective(Rank_wall record);

    Rank_wall selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Rank_wall record);

    int updateByPrimaryKey(Rank_wall record);

    Rank_wall selectByWallAndFlover(Rank_wall rank_wall);

    List<Rank_wallVo> selectRank(@Param("startPos")Integer startPos, @Param("pageSize")Integer pageSize);

    List<Integer> selectCount();

    /**
     * 查询点赞过的线路
     * @param uid
     * @return
     */
    List<Rank_wall> points(Integer uid);
}