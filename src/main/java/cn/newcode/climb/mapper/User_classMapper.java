package cn.newcode.climb.mapper;

import cn.newcode.climb.po.User_class;
import org.springframework.stereotype.Repository;

@Repository
public interface User_classMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User_class record);

    int insertSelective(User_class record);

    User_class selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User_class record);

    int updateByPrimaryKey(User_class record);
}