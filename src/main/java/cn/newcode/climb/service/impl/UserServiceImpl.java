package cn.newcode.climb.service.impl;

import cn.newcode.climb.Fight.tool.UserManager;
import cn.newcode.climb.mapper.*;
import cn.newcode.climb.page.pageBean;
import cn.newcode.climb.po.*;
import cn.newcode.climb.service.UserService;
import cn.newcode.climb.vo.FriendsVo;
import cn.newcode.climb.vo.IndexVo;
import cn.newcode.climb.vo.PersonalInf;
import cn.newcode.climb.vo.UserLimitVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Autowired
    private User_moodMapper user_moodMapper;

    @Autowired
    private User_fansMapper user_fansMapper;

    @Autowired
    private User_limitMapper user_limitMapper;

    @Override
    //@Cacheable
    public User selectByUsername(String username) throws Exception {
        return userMapper.seletcByUsername(username);
    }

    @Override
    //@CachePut
    @Transactional
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

    @Override
    public PersonalInf seletcPersonalInf(Integer id,Integer pp) throws Exception {
        PersonalInf p = userMapper.seletcPersonalInf(id);
        //查询用户是否允许显示电话号码
        String i = user_identityMapper.selectByPrimaryKey(id).getIdentity();
        if(!i.equals("1")){
            p.setUsername("***");
        }
        //查询自己是否关注此人
        User_fans user_fans = new User_fans();
        user_fans.setUid(pp);
        user_fans.setAttention(id);
        Boolean flag = user_fansMapper.selectCondition(user_fans)==null?false:true;
        p.setAttented(flag);
        return p;
    }

    /**
     * 关注操作，关注某人，数据库添加两条数据，关注者添加attention 被关注者添加fens
     * @param user_fans
     * @throws Exception
     */
    @Override
    @Transactional
    public Boolean addPoint(User_fans user_fans) throws Exception {
        //此处校验本用户是否已经关注此用户,如果没有关注，就进行关注并返回true否则返回false
        Integer v = user_fansMapper.verify(user_fans);
        //关注者
        Integer user = user_fans.getUid();
        //被关注者
        Integer BeUser =user_fans.getAttention();

        if(v==null){
            //这里查询我要关注的用户是否关注过我(查询我是否有该粉丝)，如果关注过，直接修改该记录，否则插入记录
            User_fans f1 = new User_fans();
            f1.setUid(user);
            f1.setFens(BeUser);
            Integer a = user_fansMapper.selectCondition(f1);
            if(a!=null){
                user_fans.setId(a);
                user_fansMapper.updateByPrimaryKeySelective(user_fans);
            }else {
                user_fansMapper.insertSelective(user_fans);
            }
            //此处添加被关注用户a的粉丝，查询a是否关注过自己，如果关注过直接修改该条记录否则插入一条新的记录
            User_fans f2 = new User_fans();
            f2.setUid(BeUser);
            f2.setAttention(user);

            User_fans fans = new User_fans();
            fans.setUid(BeUser);
            fans.setFens(user);

            Integer f = user_fansMapper.selectCondition(f2);

            if(f!=null){
                fans.setId(f);
                user_fansMapper.updateByPrimaryKeySelective(fans);
            }else {
                user_fansMapper.insertSelective(fans);
            }
            return true;
        }else{
            return false;
        }
    }

    @Override
    public IndexVo selectIndex(Integer uid) throws Exception {
        return userMapper.selectIndex(uid);
    }

    @Override
    public List<FriendsVo> selectFriends(Integer uid,String name) throws Exception {
        List<FriendsVo> friends = new ArrayList<FriendsVo>();
        //判断name中是否有东西,有就查询所有人，没有就查询好友列表
        Integer total = 0;
        /*Integer t = 0;
          Integer total = (t=userService.seletcFrinedCount(uid,name))!=0?t:0;
          pageBean page = new pageBean(now,total);*/
        if (name != null) {
           friends =  userMapper.selectAddFriends(name,uid);
        }else {
            friends = user_fansMapper.selectFriends(uid,name);
        }

        //判断好友是否在线
        for(FriendsVo f : friends){
            Boolean flag = UserManager.getInstance().getPlayer(f.getId())==null?false:true;
            f.setOnlion(flag);
        }
        return friends;
    }

    @Override
    public Integer seletcFrinedCount(Integer uid,String name) throws Exception {
        return user_fansMapper.selectCount(uid,name);
    }

    @Override
    public List<FriendsVo> selectFriendsWithFight(Integer uid) throws Exception {
        return user_fansMapper.selectFriendsWithFight(uid);
    }

    @Override
    public Boolean updateUserMood(User_mood user_mood) throws Exception {
       user_moodMapper.updateByPrimaryKeySelective(user_mood);
        return true;
    }

    @Override
    public Boolean setIdentity(User_identity user_identity) throws Exception {
        user_identityMapper.updateByPrimaryKeySelective(user_identity);
        return true;
    }

    @Override
    public List<UserLimitVo> selectUserLimit(String name) throws Exception {
        return user_limitMapper.selectLimit(name);
    }

    @Override
    public Boolean setUserLimit(User_limit user_limit,Integer p) throws Exception {
        //查询是否为GM权限
        String limit = user_limitMapper.isGm(p);
        if(!limit.equals("GM"))
            return false;
        user_limitMapper.updateByPrimaryKeySelective(user_limit);
        return true;
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
        userIdentity.setIdentity("1");
        user_identityMapper.insertSelective(userIdentity);

        //创建角色等级
        User_class userClass = new User_class();
        userClass.setUid(uid);
        userClass.setOrder(1);
        user_classMapper.insertSelective(userClass);

        //创建用户心情
        User_mood userMood = new User_mood();
        userMood.setUid(uid);
        userMood.setMood("这家伙很懒,这里什么都没有留下");
        user_moodMapper.insertSelective(userMood);

        //插入创建用户时间
        //Date now = new Date();
        Rank_age rank_age = new Rank_age();
        rank_age.setUid(uid);
        rank_age.setYear(new Date().getTime());
        rank_ageMapper.insertSelective(rank_age);

        //创建用户权限
        User_limit user_limit = new User_limit();
        user_limit.setUid(uid);
        user_limit.setLimit("普");
        user_limitMapper.insertSelective(user_limit);
    }
}
