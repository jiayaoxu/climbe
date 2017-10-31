package cn.newcode.climb.service;

import cn.newcode.climb.po.Match_inf;
import cn.newcode.climb.po.Match_signup;
import cn.newcode.climb.po.User;
import cn.newcode.climb.po.User_inf;
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



}
