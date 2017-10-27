package cn.newcode.climb.Fight.tool;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Description: 放用户的socket
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

   public static UserManager getInstance(){
      return Instance.userManager;
   }

   public void addPlayer(Integer uid,Socket socket){
      userMap.put(uid,socket);
   }

   public void removePlayer(Integer uid){
      userMap.remove(uid);
   }

   public void createRoom(Integer uid){
      List<Integer> roomPlayers = new ArrayList<Integer>();
      roomPlayers.add(uid);
      roomMap.put(uid,roomPlayers);
   }

   public List<Integer> getRoomMap(Integer rid){
      return roomMap.get(rid);
   }

}
