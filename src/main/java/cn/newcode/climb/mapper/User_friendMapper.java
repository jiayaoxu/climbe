package cn.newcode.climb.mapper;

import cn.newcode.climb.po.User_friend;

public interface User_friendMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User_friend record);

    int insertSelective(User_friend record);

    User_friend selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User_friend record);

    int updateByPrimaryKey(User_friend record);
}