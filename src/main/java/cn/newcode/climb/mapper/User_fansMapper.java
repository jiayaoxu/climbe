package cn.newcode.climb.mapper;

import cn.newcode.climb.po.User_fans;
import cn.newcode.climb.vo.FriendsVo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface User_fansMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User_fans record);

    int insertSelective(User_fans record);

    User_fans selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User_fans record);

    int updateByPrimaryKey(User_fans record);

    List<FriendsVo> selectFriends(@Param("startPos") Integer startPos,
                                  @Param("pageSize")Integer PageSize,@Param("uid")Integer uid,@Param("name")String name);

    Integer selectCount(@Param("uid") Integer uid,@Param("name")String name);
}