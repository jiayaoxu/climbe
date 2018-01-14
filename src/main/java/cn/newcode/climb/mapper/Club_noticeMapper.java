package cn.newcode.climb.mapper;

import cn.newcode.climb.po.Club_notice;
import org.springframework.stereotype.Repository;

@Repository
public interface Club_noticeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Club_notice record);

    int insertSelective(Club_notice record);

    Club_notice selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Club_notice record);

    int updateByPrimaryKey(Club_notice record);

    String selectNoticeByCid(Integer cid);
}