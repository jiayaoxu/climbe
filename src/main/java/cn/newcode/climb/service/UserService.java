package cn.newcode.climb.service;

import cn.newcode.climb.po.*;
import cn.newcode.climb.vo.PersonalInf;

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
    PersonalInf seletcPersonalInf(Integer id) throws Exception;

    /**
     * 用户点赞/增粉操作
     * @param user_fans
     * @throws Exception
     */
    void addPoint(User_fans user_fans) throws Exception;


}
