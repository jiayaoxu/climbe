package cn.newcode.climb.mapper;

import cn.newcode.climb.po.User_email;

public interface User_emailMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User_email record);

    int insertSelective(User_email record);

    User_email selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User_email record);

    int updateByPrimaryKey(User_email record);
}