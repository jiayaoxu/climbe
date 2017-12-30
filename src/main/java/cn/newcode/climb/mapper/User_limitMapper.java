package cn.newcode.climb.mapper;

import cn.newcode.climb.po.User_limit;

public interface User_limitMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User_limit record);

    int insertSelective(User_limit record);

    User_limit selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User_limit record);

    int updateByPrimaryKey(User_limit record);
}