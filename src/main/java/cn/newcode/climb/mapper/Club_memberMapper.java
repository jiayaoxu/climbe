package cn.newcode.climb.mapper;

import cn.newcode.climb.po.Club_member;

public interface Club_memberMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Club_member record);

    int insertSelective(Club_member record);

    Club_member selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Club_member record);

    int updateByPrimaryKey(Club_member record);
}