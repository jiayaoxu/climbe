package cn.newcode.climb.service.impl;

import cn.newcode.climb.mapper.*;
import cn.newcode.climb.po.*;
import cn.newcode.climb.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 *@author:shine
 *@Description:用户信息实现类
 *@Date:15:42 2017/10/17
 */
@Service("userService")
@CacheConfig(cacheNames = "user")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private User_infMapper user_infMapper;

    @Autowired
    private User_packageMapper user_packageMapper;

    @Autowired
    private User_identityMapper user_identityMapper;

    @Autowired
    private User_classMapper user_classMapper;

    @Autowired
    private Rank_ageMapper rank_ageMapper;


    @Override
    //@Cacheable
    public User selectByUsername(String username) throws Exception {
        return userMapper.seletcByUsername(username);
    }

    @Override
    //@CachePut
    public void insertRegist(User user, User_inf userInf) throws Exception {

        //插入user
        userMapper.insertSelective(user);
        //查询刚插入的id
        Integer uid = userMapper.seletcByUsername(user.getUsername()).getId();
        //通过id插入user_inf
        userInf.setUid(uid);
        user_infMapper.insertSelective(userInf);

        //向其他表中插入用户信息
        roleProperties(uid);
    }

    @Override
    //@CachePut
    public Integer updateUserPassword(User user) throws Exception {
        return userMapper.updateByPrimaryKeySelective(user);
    }



    /**
     * 创建角色时创角色钱包、角色身份、角色等级 支持事物回滚
     * @param uid
     */
    private void roleProperties(Integer uid){
        //创建角色钱包
        User_package userPackage = new User_package();
        //角色初始化,金币为0
        userPackage.setCoin(0);
        //钻石为0
        userPackage.setDiamond(0);
        userPackage.setUid(uid);
        user_packageMapper.insertSelective(userPackage);

        //创建角色身份
        User_identity userIdentity = new User_identity();
        userIdentity.setUid(uid);
        userIdentity.setIdentity("平民");
        user_identityMapper.insertSelective(userIdentity);

        //创建角色等级
        User_class userClass = new User_class();
        userClass.setUid(uid);
        userClass.setOrder(1);
        user_classMapper.insertSelective(userClass);

        //插入创建用户时间
        //Date now = new Date();
        Rank_age rank_age = new Rank_age();
        rank_age.setUid(uid);
        rank_age.setYear(new Date().getTime());
        rank_ageMapper.insertSelective(rank_age);
    }

}
