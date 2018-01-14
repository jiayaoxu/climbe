package cn.newcode.climb.LoginCheck.net;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: shine
 * \* Date: 17-12-24
 * \* Time: 下午5:18
 * \* Description:存储用户上线信息
 * \
 */
public class UserRegister {

    //存储用户
    private Map<Integer,Socket> user = new HashMap<Integer, Socket>();

    //存储俱乐部人数
    private Map<Integer,Integer> club = new HashMap<Integer, Integer>();

    private static class Instance{
        private static final UserRegister userRegister = new UserRegister();
    }

    public static  UserRegister getInstance(){
        return Instance.userRegister;
    }

    public UserRegister(){
    }

    //获取用户socket
    public Socket getUser(Integer uid){
        return user.get(uid);
    }

    //添加用户
    public void addUser(Integer uid,Socket socket){
        user.put(uid,socket);
    }

    //移除用户
    public void removeUser(Integer uid){
        user.remove(uid);
    }

    public Integer getClub(Integer cid) {
        return club.get(cid);
    }

    public Map<Integer, Integer> getClub() {
        return club;
    }

    public void setClub(Map<Integer, Integer> club) {
        this.club = club;
    }

    public void addonlion(Integer cid){
        Integer i = club.get(cid);
        if(i!=null){
            club.put(cid,i+1);
        }else {
            club.put(cid,1);
        }
    }
}