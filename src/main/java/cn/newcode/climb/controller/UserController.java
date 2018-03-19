package cn.newcode.climb.controller;

import cn.newcode.climb.Fight.tool.DataBaseUtil;
import cn.newcode.climb.po.*;
import cn.newcode.climb.service.UserService;
import cn.newcode.climb.vo.IndexVo;
import cn.newcode.climb.vo.PersonalInf;
import cn.newcode.climb.vo.Status;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/user")
/**
 *@author:shine
 *@Description:用户controller接口
 *@Date:15:43 2017/10/17
 */
public class UserController {

    @Autowired
    private UserService userService;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Status status ;

    /**
     * 登录接口
     * @param response
     * @param clientUser
     * @return String
     */
    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public @ResponseBody String login(HttpServletResponse response,User clientUser){
        response.setHeader("Access-Control-Allow-Origin", "*");

        User user = null;

//      返回的json字段
        String userJson = "";

        try {
             //查询数据库用户名密码
             user = userService.selectByUsername(clientUser.getUsername());
             if(user==null){
                 userJson = "UsernameError";
             }
             if(user.getPassword().equals(clientUser.getPassword())){
//                 ObjectMapper objectMap per = new ObjectMapper();
                 userJson = objectMapper.writeValueAsString(user);
             }else {
                 userJson = "PasswordError";
             }
        } catch (Exception e) {
            e.printStackTrace();
            return "SystemError";
        }

        return userJson;
    }

    /**
     * 用户注册接口
     * @param response
     * @param user
     * @param userInf
     * @return String
     */
    @RequestMapping(value = "/regist",method = RequestMethod.POST)
    public @ResponseBody String regist(HttpServletResponse response, User user, User_inf userInf){
        response.setHeader("Access-Control-Allow-Origin","*");
        Map<String,String> map = new HashMap<String, String>(16);
        String str = "";

        try{
            //注册  用户名,昵称 ,密码,性别
            userService.insertRegist(user,userInf);
            map.put("status","Success");
        }catch (Exception e){
            e.printStackTrace();
            map.put("status","SystemFaild");
        }

        //map 转 json
        try {
            str = objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return str;
    }

    /**
     * 用户修改 密码/手机号 接口
     * @param response
     * @param user
     * @return
     */
    @RequestMapping(value = "/updateUserPassword",method = RequestMethod.POST)
    public @ResponseBody String updateUserPassword(
            HttpServletResponse response,User user) throws JsonProcessingException {
        response.setHeader("Access-Control-Allow-Origin","*");

        status = new Status();

        try {
            //此处添加验证用户身份
            Integer statusNum = userService.updateUserPassword(user);
            status.setSuccess(statusNum+"");
        } catch (Exception e) {
            e.printStackTrace();
            status.setError(e.toString());
        }

        return objectMapper.writeValueAsString(status);
    }

    /**
     * 根据用户id查询用户信息
     * @param uid
     * @return
     */
    @RequestMapping(value = "/selectUserInfByUid")
    public @ResponseBody PersonalInf selectUserInfByUid(Integer uid,Integer p){
        PersonalInf person = null;
        try{
            person = userService.seletcPersonalInf(uid,p);
        } catch (Exception e){
            e.printStackTrace();
        }
        return person;
    }

    /**
     * 添加关注或者粉丝
     * @param userFans
     * @return
     */
    @RequestMapping(value = "/addPoint")
    public @ResponseBody Status addPoint(User_fans userFans){
        Boolean flag = false;
        try{
            flag  = userService.addPoint(userFans);
        } catch (Exception e){
            e.printStackTrace();
            return new Status("","SystemError");
        }
        return  new Status(flag+"","");
    }

    /**
     * 查询游戏首页需要的信息
     * @param uid
     * @return
     */
    @RequestMapping(value = "/selectIndex")
    public @ResponseBody IndexVo selectIndex(Integer uid){
        IndexVo index = null;
        try{
            index = userService.selectIndex(uid);
        } catch (Exception e){
            e.printStackTrace();
        }
        return index;
    }

    /**
     * 修改用户心情接口
     * @param user_mood
     * @return
     */
    @RequestMapping("/updateUserMood")
    public @ResponseBody Status updateUserMood(User_mood user_mood){
        try{
            userService.updateUserMood(user_mood);
        } catch (Exception e) {
            e.printStackTrace();
            return new Status("","SystemError");
        }
        return new Status("Success","");
    }

    /**
     * 用户设置是否显示用户电话号码是否显示
     * @param user_identity
     * @return
     */
    @RequestMapping("/setIdentity")
    public @ResponseBody Status setIdentity(User_identity user_identity){
        try {
            if(user_identity.getUid()==null)
                return new Status("","NullPointer");
            userService.setIdentity(user_identity);
        } catch (Exception e){
            e.printStackTrace();
            return new Status("","SystemError");
        }
        return new Status("Success","");
    }

    /**
     * 更改用户信息
     * @param user_inf
     * @return
     */
    @RequestMapping("/updateUserInf")
    public @ResponseBody Status updateUserInf(User_inf user_inf){
        try{
            userService.updateUserRockHall(user_inf);
        } catch (Exception e){
            e.printStackTrace();
            return new Status("","SystemError");
        }
        return new Status("Success","");
    }

    /**
     * 查询房间内学生信息
     * @param stus
     * @return
     */
    @RequestMapping("/selectStuInf")
    public @ResponseBody String selectStuInf(String stus){
        String str = null;
        try{
            str = userService.selectStuInf(stus);
        } catch (Exception e){
            e.printStackTrace();
        }
        return str;
    }

    @RequestMapping("/test")
    public @ResponseBody Status test() throws Exception{
        PersonalInf p = DataBaseUtil.dataBaseUtil.userService.seletcPersonalInf(1,1);
        System.out.println(p.getAttention());
        return new Status("","");
    }

}
