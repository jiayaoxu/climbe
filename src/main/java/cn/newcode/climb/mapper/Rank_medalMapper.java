package cn.newcode.climb.mapper;

import cn.newcode.climb.po.Rank_medal;
import org.springframework.stereotype.Repository;

@Repository
public interface Rank_medalMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Rank_medal record);

    int insertSelective(Rank_medal record);

    Rank_medal selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Rank_medal record);

    int updateByPrimaryKey(Rank_medal record);
}