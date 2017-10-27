package cn.newcode.climb.mapper;

import cn.newcode.climb.po.User_identity;
import org.springframework.stereotype.Repository;

@Repository
/**
 *@author:shine
 *@Description:用户角色mapper
 *@Date:15:52 2017/10/19
 */
public interface User_identityMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User_identity record);

    int insertSelective(User_identity record);

    User_identity selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User_identity record);

    int updateByPrimaryKey(User_identity record);
}