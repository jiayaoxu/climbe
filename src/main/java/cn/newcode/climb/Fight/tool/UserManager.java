package cn.newcode.climb.Fight.tool;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 存放用户的socket
 * @author: shine
 * @CreateDate: 2017/10/27 16:30
 * @Version: 1.0
 */
public class UserManager {

   /**
    * 用户上线存入这里
    */
   private Map<Integer,Socket> userMap = new HashMap<Integer, Socket>();

   /**
    * 房间Map
    * 房间号,列表存用户id
    */
   private Map<Integer,List<Integer>> roomMap = new HashMap<Integer, List<Integer>>();

   private static class Instance{
      private static final UserManager userManager = new UserManager();
   }

   private UserManager(){}

   //实例化
   public static UserManager getInstance(){
      return Instance.userManager;
   }

   //添加上线玩家
   public void addPlayer(Integer uid,Socket socket){
      userMap.put(uid,socket);
   }

   //玩家下线
   public void removePlayer(Integer uid){
      userMap.remove(uid);
   }

   //获取玩家信息
   public Socket getPlayer(Integer uid){
      return userMap.get(uid);
   }

   //获取用户列表
   public Map<Integer, Socket> getUserMap() {
      return userMap;
   }

   //创建房间
   public void createRoom(Integer uid){
      List<Integer> roomPlayers = new ArrayList<Integer>();
      roomPlayers.add(uid);
      roomMap.put(uid,roomPlayers);
   }

   //玩家加入房间
   public void playerJoinRoom(Integer rid,List<Integer> players){
      roomMap.put(rid,players);
   }

   //获取用户房间信息
   public List<Integer> getRoomMap(Integer rid){
      return roomMap.get(rid);
   }

   //获取所有房间
   public Map<Integer, List<Integer>> getRoomList() {
      return roomMap;
   }
}
