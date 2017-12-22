package cn.newcode.climb.mapper;

import cn.newcode.climb.po.User_fans;

public interface User_fansMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User_fans record);

    int insertSelective(User_fans record);

    User_fans selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User_fans record);

    int updateByPrimaryKey(User_fans record);
}