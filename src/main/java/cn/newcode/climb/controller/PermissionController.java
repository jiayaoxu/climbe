package cn.newcode.climb.controller;

import cn.newcode.climb.service.UserService;
import cn.newcode.climb.vo.UserLimitVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: shine
 * \* Date: 2018/1/13 0013
 * \* Time: 21:29
 * \* Description:
 * \  处理权限
 */
@Controller
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private UserService userService;

    /**
     * 查询用户列表
     * @param name
     * @return
     */
    @RequestMapping(value = "/selectUserLimit")
    public @ResponseBody List<UserLimitVo> selectUserLimit(String name){
        List<UserLimitVo> users = null;
        try{
            users = userService.selectUserLimit(name);
        } catch (Exception e){
            e.printStackTrace();
        }
        return users;
    }

}