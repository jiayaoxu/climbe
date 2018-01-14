package cn.newcode.climb.mapper;

import cn.newcode.climb.po.User_mood;
import org.springframework.stereotype.Repository;

@Repository
public interface User_moodMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User_mood record);

    int insertSelective(User_mood record);

    User_mood selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User_mood record);

    int updateByPrimaryKey(User_mood record);
}