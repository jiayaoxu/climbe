package cn.newcode.climb.mapper;

import cn.newcode.climb.po.User;
import cn.newcode.climb.po.Version;
import cn.newcode.climb.vo.FriendsVo;
import cn.newcode.climb.vo.IndexVo;
import cn.newcode.climb.vo.PersonalInf;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    User seletcByUsername(@Param("username") String username);

    PersonalInf seletcPersonalInf(Integer id);

    IndexVo selectIndex(Integer uid);

    /**
     * 通过查询手机号码/昵称添加好友
     */
    List<FriendsVo> selectAddFriends(@Param("name") String name,@Param("uid")Integer uid);

    Integer selectCount(@Param("name") String name);

    /**
     * 查询版本号
     * @return
     */
    Version selectVersion();
}