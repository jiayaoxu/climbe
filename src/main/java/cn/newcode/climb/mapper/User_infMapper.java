package cn.newcode.climb.mapper;

import cn.newcode.climb.po.User_inf;
import org.springframework.stereotype.Repository;

@Repository
/**
 *@author:shine
 *@Description:用户信息mapper接口
 *@Date:15:42 2017/10/17
 */
public interface User_infMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User_inf record);

    int insertSelective(User_inf record);

    User_inf selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User_inf record);

    int updateByPrimaryKey(User_inf record);
}