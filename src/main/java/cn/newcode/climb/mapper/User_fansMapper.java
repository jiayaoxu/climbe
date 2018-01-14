package cn.newcode.climb.mapper;

import cn.newcode.climb.po.User_fans;
import cn.newcode.climb.vo.FriendsVo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface User_fansMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User_fans record);

    int insertSelective(User_fans record);

    User_fans selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User_fans record);

    int updateByPrimaryKey(User_fans record);

    List<FriendsVo> selectFriends(@Param("uid")Integer uid,@Param("name")String name);

    Integer selectCount(@Param("uid") Integer uid,@Param("name")String name);

    List<FriendsVo> selectFriendsWithFight(Integer uid);

    /**
     * 校验是否关注过某人
     * @param user_fans
     * @return
     */
    Integer verify(User_fans user_fans);

    /**
     * 查询添加粉丝/关注条件
     * @param user_fans
     * @return
     */
    Integer selectCondition(User_fans user_fans);
}