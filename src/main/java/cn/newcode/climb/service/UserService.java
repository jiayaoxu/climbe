package cn.newcode.climb.service;

import cn.newcode.climb.po.*;
import cn.newcode.climb.vo.FriendsVo;
import cn.newcode.climb.vo.IndexVo;
import cn.newcode.climb.vo.PersonalInf;
import cn.newcode.climb.vo.UserLimitVo;

import java.util.List;

/**
 *@author:shine
 *@Description:用户信息service
 *@Date:15:40 2017/10/17
 */

public interface UserService {

    /**
     * 用户登录
     * @param username
     * @return
     * @throws Exception
     */
    User selectByUsername(String username) throws Exception;

    /**
     * 用户注册
     * @param user
     * @param userInf
     * @throws Exception
     */
    void insertRegist(User user, User_inf userInf) throws Exception;

    /**
     * 修改用户密码
     * @param user
     * @return Intger
     * @throws Exception
     */
    Integer updateUserPassword(User user) throws Exception;

    /**
     * 通过用户id查询用户信息
     * @param id
     * @return
     * @throws Exception
     */
    PersonalInf seletcPersonalInf(Integer id,Integer p) throws Exception;

    /**
     * 用户点赞/增粉操作
     * @param user_fans
     * @throws Exception
     */
    Boolean addPoint(User_fans user_fans) throws Exception;

    /**
     * 查询游戏首页需要的信息
     * @param uid
     * @return
     * @throws Exception
     */
    IndexVo selectIndex(Integer uid) throws Exception;

    /**
     * 分页查询好友
     * @param uid
     * @return
     * @throws Exception
     */
    List<FriendsVo> selectFriends(Integer uid,String name) throws Exception;

    /**
     * 查询好友总数
     * @param uid
     * @return
     * @throws Exception
     */
    Integer seletcFrinedCount(Integer uid,String name) throws Exception;

    /**
     * 查询好友用来进行对战邀请
     */
    List<FriendsVo> selectFriendsWithFight(Integer uid) throws Exception;

    /**
     * 修改用户心情
     * @param user_mood
     * @return
     * @throws Exception
     */
    Boolean updateUserMood(User_mood user_mood) throws Exception;

    /**
     * 设置电话隐藏
     * @param user_identity
     * @return
     * @throws Exception
     */
    Boolean setIdentity(User_identity user_identity) throws Exception;

    /**
     * 查询用户权限
     * @param name
     * @return
     * @throws Exception
     */
    List<UserLimitVo> selectUserLimit(String name) throws Exception;

    /**
     * 设置用户权限
     * @param user_limit
     * @return
     * @throws Exception
     */
    Boolean setUserLimit(User_limit user_limit,Integer p) throws Exception;
}
