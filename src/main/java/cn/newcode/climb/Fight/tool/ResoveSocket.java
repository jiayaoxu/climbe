package cn.newcode.climb.Fight.tool;

import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.*;

import cn.newcode.climb.Fight.vo.Room;
import cn.newcode.climb.po.Rank_teacher;
import cn.newcode.climb.po.User;
import cn.newcode.climb.service.RankService;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Description: 解析socket中的信息
 * @author: shine
 * @CreateDate: 2017/10/27 16:44
 * @Version: 1.0
 */
public class ResoveSocket {

    /**
     * 用户id
     */
    private Integer uid;

    /**
     * 房间id
     */
    private Integer rid;

    /**
     * 用户套接字
     */
    private Socket socket;

    /**
     * 输出流
     */
    private DataOutputStream out;

    @Autowired
    private RankService rankService;

    /**
     * 初始化  接收socket 输入流
     * @param socket
     * @throws IOException
     */
    public ResoveSocket(Socket socket) throws Exception{
        this.socket = socket;
        out = new DataOutputStream(socket.getOutputStream());
    }

    /**
     * 解析socket中的信息
     * @param
     * @param s
     * @throws IOException
     */
    public  void resove(String s) throws Exception {
        ObjectMapper obj = new ObjectMapper();
        obj.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        UserManager userManager = UserManager.getInstance();
        String [] message = s.split("@");
        String head = message[0];
        String body = "";
        if(message.length>=2){
            body = message[1];
        }

        //根据头判断
        if(head.equals("onlion")){
            //获取用户id
            User user = obj.readValue(body,User.class);
            //用户id存入在线列表
            userManager.addPlayer(user.getId(),socket);
            //保存用户信息
            this.uid = user.getId();
            //DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            //返回响应
            String response = "onlion@Success";

            out.write(addCache(response));
        }else if(head.equals("CreateRoom")){
            rid = uid;
            //通过用户id创建房间，需要提供密码
            userManager.createRoom(uid,body);
            //DataOutputStream out  = new DataOutputStream(socket.getOutputStream());
            String response = "CreateRoom@Success";
            out.write(addCache(response));
        }else if(head.equals("JoinRoom")){
            //获取房间id
            Room room = obj.readValue(body,Room.class);
            //获取房间rid,获取房间密码
            rid = room.getRid();
            String userJoinPass = room.getPassword();
            //查找房间中的用户
            List<Integer> players = userManager.getRoomMap(rid);
            //如果房间用户大于等于两人,向客户端返回false
            if(players.size()>=2){
                //DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                String flag = "false@";
                out.write(addCache(flag));
            } if (!userManager.getRoomPassword(rid).equals(userJoinPass)){
                //密码不正确
                //DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                String response = "JoinRoom@paswordError";
                out.write(addCache(response));
            } else{
                //房间添加用户
                //获取房主id
                Integer roomHolder = players.get(0);
                //获取房主socket
                Socket roomHolderSocket = userManager.getPlayer(roomHolder);
                //提示房主有人加入
                DataOutputStream Holderout = new DataOutputStream(roomHolderSocket.getOutputStream());
                String joinMessage = "join@" + uid;
                Holderout.write(addCache(joinMessage));

                //DataOutputStream outwiriter = new DataOutputStream(socket.getOutputStream());
                out.write(addCache(joinMessage));

                //房间添加玩家
                players.add(uid);
                //房间信息重新设回
                userManager.playerJoinRoom(room.getRid(),players);
            }
        }else if(head.equals("fight")){
            //查询本房间
            List<Integer> RoomList =  userManager.getRoomMap(rid);
            //遍历用户
            for(Integer p : RoomList){
                //将自己的位置信息发送给对手
                if(!p.equals(uid)){
                    Socket player = userManager.getPlayer(p);
                    DataOutputStream out = new DataOutputStream(player.getOutputStream());
                    String str = "fight@"+body;

                    out.write(addCache(str));
                    //out.writeUTF("fight@"+body);
                }
            }
        }else if(head.equals("roomList")){
            //查询房间列表
            Map<Integer,List<Integer>> mapList = userManager.getRoomList();
            //获取用户socket
            //Socket client = userManager.getPlayer(uid);
            //DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            //返回信息数据头
            String roomList = "RoomList@";
            List<Room> room = new ArrayList<Room>();
            for(Map.Entry<Integer,List<Integer>> entry: mapList.entrySet()){
                Room r = new Room();
                r.setRid(entry.getKey());
                room.add(r);
            }
            //返回数据
            String str = roomList+obj.writeValueAsString(room);
            out.write(addCache(str));
        }else if(head.equals("Closed")){
            //移除用户
            userManager.removePlayer(uid);
            String response = "Closed@Success";
            out.write(addCache(response));
            socket.close();
        }else if(head.equals("ExitRoom")){
            //列出房间信息
            List<Integer> room = userManager.getRoomMap(rid);
            //创建遍历对象
            Iterator<Integer> it = room.iterator();
            while(it.hasNext()){
                Integer player = it.next();
                if(player.equals(uid)){
                    it.remove();
                }
            }
            //判断房间里是否还有人,没人直接销毁房间,有人通知销毁房间
            if(room.size()==0){
                userManager.removeRoom(rid);
            }else {
                for(Integer r :room){
                    Socket roomUser = userManager.getPlayer(r);
                    DataOutputStream roomOut = new DataOutputStream(roomUser.getOutputStream());
                    String response = "room@roomDestried";
                    roomOut.write(addCache(response));
                }
            }
            //反馈成功信息
            String response = "room@ExitedSuccess";
            //DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.write(addCache(response));
        }else if(head.equals("GameOver")){
            //游戏结束,提交对战数据
            /*SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = dateFormat.format(new Date());*/
        }else if(head.equals("Invite")){
            User user  = obj.readValue(body,User.class);
            //获取好友的socket
            Socket friendSocket = userManager.getPlayer(user.getId());
            if(friendSocket==null){
                //DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                String inviteMessage = "Invite@friendUnlion";
                out.write(addCache(inviteMessage));
            } else {
                DataOutputStream FriendOut = new DataOutputStream(friendSocket.getOutputStream());
                Room room = new Room();
                room.setRid(rid);
                String inviteMessage = "invite@"+obj.writeValueAsString(user)+"@"+obj.writeValueAsString(room);
                FriendOut.write(addCache(inviteMessage));
            }
        }else if(head.equals("JoinWatchRoom")){
            //观战房间加入,观战房间最多三十个人
            //获取房间id,房间密码
            Room room = obj.readValue(body,Room.class);
            rid = room.getRid();
            String userJoinPass = room.getPassword();
            //查找房间中的用户
            List<Integer> players = userManager.getWatchRoomMap(rid);
            //如果房间用户大于等于两人,向客户端返回false
            if(players.size()>=30){
                //DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                String flag = "JoinWatchRoom@false";
                out.write(addCache(flag));
            }else if(!userManager.getRoomPassword(rid).equals(userJoinPass)){
                String response = "JoinWatchRoom@passwordError";
                out.write(addCache(response));
            }else{
                //房间添加用户
                //获取房主id
                Integer roomHolder = players.get(0);
                //获取房主socket
                Socket roomHolderSocket = userManager.getPlayer(roomHolder);
                //提示房主有人加入
                DataOutputStream Holderout = new DataOutputStream(roomHolderSocket.getOutputStream());
                String joinMessage = "WatchRoom@" + uid;
                Holderout.write(addCache(joinMessage));

                //DataOutputStream outwiriter = new DataOutputStream(socket.getOutputStream());
                out.write(addCache(joinMessage));

                //房间添加玩家
                players.add(uid);
                //房间信息重新设回
                userManager.WatchplayerJoinRoom(room.getRid(),players);
            }
        }else if(head.equals("CreateWatchRoom")){
            rid = uid;
            //通过用户id创建房间
            userManager.createWatchRoom(uid,body);
            String response = "CreateWatchRoom@Success";
            out.write(addCache(response));
        }else if(head.equals("ExitWatchRoom")){
            //列出房间信息
            List<Integer> room = userManager.getWatchRoomMap(rid);
            //创建遍历对象
            Iterator<Integer> it = room.iterator();
            while(it.hasNext()){
                Integer player = it.next();
                if(player.equals(uid)){
                    it.remove();
                }
            }
            if(room.size()==0){
                userManager.removeWatchRoom(rid);
            }else {
                userManager.WatchplayerJoinRoom(rid,room);
            }
        }else if(head.equals("WatchroomList")) {
            //查询房间列表
            Map<Integer, List<Integer>> mapList = userManager.getWatchRoomList();
            //获取用户socket
            //Socket client = userManager.getPlayer(uid);
            //DataOutputStream out = new DataOutputStream(client.getOutputStream());
            //返回信息数据头
            String roomList = "WatchRoomList@";
            List<Room> room = new ArrayList<Room>();
            for (Map.Entry<Integer, List<Integer>> entry : mapList.entrySet()) {
                Room r = new Room();
                r.setRid(entry.getKey());
                room.add(r);
            }
            //返回数据
            String str = roomList + obj.writeValueAsString(room);
            out.write(addCache(str));
        }else if(head.equals("WatchFight")){
            //查询本房间
            List<Integer> RoomList =  userManager.getWatchRoomMap(rid);
            //遍历用户
            for(Integer p : RoomList){
                //将自己的位置信息发送给对手
                if(!p.equals(uid)){
                    Socket player = userManager.getPlayer(p);
                    DataOutputStream out = new DataOutputStream(player.getOutputStream());
                    String str = "Watchfight@"+body;
                    //byte [] b = str.getBytes();
                    out.write(addCache(str));
                    //out.writeUTF("fight@"+body);
                }
            }
        }else if(head.equals("addPoint")){
            //Integer teacher = body!=null?Integer.parseInt(body):null;
            String str = "addPoint@teacherIsEmpty";
            if(rid!=null){
                rankService.addPoint(rid);
                str = "addPoint@success";
            }
            out.write(addCache(str));
        }
    }

    /**
     * 将字符串封装入缓存数组中
     * @param value
     * @return
     * @throws Exception
     */
    public static byte[] addCache(String value) throws Exception{
        byte src [] = value.getBytes();
        byte response [] = new byte[1024];
        System.arraycopy(src,0,response,0,src.length);
        return response;
    }
}
