package cn.newcode.climb.controller;

import cn.newcode.climb.page.pageBean;
import cn.newcode.climb.service.UserService;
import cn.newcode.climb.vo.FriendsVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: shine
 * \* Date: 17-12-24
 * \* Time: 下午10:55
 * \* Description:
 * \
 */
@Controller
@RequestMapping("/friends")
public class FriendController {

    @Autowired
    private UserService userService;

    /**
     * 分页查询好友
     * @param pageNow
     * @param uid
     * @return
     */
    @RequestMapping(value = "/selectFriends")
    public @ResponseBody List<FriendsVo> selectFriends(Integer pageNow,Integer uid,String name){
        List<FriendsVo> friends = null;
        Integer now = 1;
        if(pageNow!=null){
            now = pageNow;
        }
        try{
            /*Integer t = 0;
            Integer total = (t=userService.seletcFrinedCount(uid,name))!=0?t:0;
            pageBean page = new pageBean(now,total);*/
            friends = userService.selectFriends(uid,name);
        } catch (Exception e){
            e.printStackTrace();
        }
        return friends;
    }

    /**
     * 查询好用用来对战邀请
     * @param uid
     * @return
     */
    @RequestMapping(value = "/selectFriendsWithFight")
    public @ResponseBody List<FriendsVo> selectFriendsByName(Integer uid){
        List<FriendsVo> friendsVos = null;
        try{
           friendsVos = userService.selectFriendsWithFight(uid);
        } catch (Exception e){
            e.printStackTrace();
        }
        return friendsVos;
    }
}