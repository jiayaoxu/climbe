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

    /**
     * 实例化
     * @return
     */
   public static UserManager getInstance(){
      return Instance.userManager;
   }

    /**
     * 添加上线玩家
     * @param uid
     * @param socket
     */
   public void addPlayer(Integer uid,Socket socket){
      userMap.put(uid,socket);
   }

    /**
     * 玩家下线
     * @param uid
     */
   public void removePlayer(Integer uid){
      userMap.remove(uid);
   }

    /**
     * 获取玩家信息
     * @param uid
     * @return
     */
   public Socket getPlayer(Integer uid){
      return userMap.get(uid);
   }

    /**
     * 获取用户列表
     * @return
     */
   public Map<Integer, Socket> getUserMap() {
      return userMap;
   }

    /**
     * 创建房间s
     * @param uid
     */
   public void createRoom(Integer uid){
      List<Integer> roomPlayers = new ArrayList<Integer>();
      roomPlayers.add(uid);
      roomMap.put(uid,roomPlayers);
   }

    /**
     * 玩家加入房间
     * @param rid
     * @param players
     */
   public void playerJoinRoom(Integer rid,List<Integer> players){
      roomMap.put(rid,players);
   }

    /**
     * 获取用户房间信息
     * @param rid
     * @return
     */
   public List<Integer> getRoomMap(Integer rid){
      return roomMap.get(rid);
   }

    /**
     * 获取所有房间
     * @return
     */
   public Map<Integer, List<Integer>> getRoomList() {
      return roomMap;
   }

    /**
     * 销毁房间
     * @param rid
     */
   public void removeRoom(Integer rid){
      roomMap.remove(rid);
   }
}
