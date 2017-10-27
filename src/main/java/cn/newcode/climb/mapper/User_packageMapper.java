package cn.newcode.climb.mapper;

import cn.newcode.climb.po.User_package;
import org.springframework.stereotype.Repository;

@Repository
public interface User_packageMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User_package record);

    int insertSelective(User_package record);

    User_package selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User_package record);

    int updateByPrimaryKey(User_package record);
}