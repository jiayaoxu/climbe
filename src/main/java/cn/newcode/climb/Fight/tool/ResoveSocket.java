package cn.newcode.climb.Fight.tool;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.newcode.climb.Fight.vo.Room;
import cn.newcode.climb.po.User;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @Description: 解析socket中的信息
 * @author: shine
 * @CreateDate: 2017/10/27 16:44
 * @Version: 1.0
 */
public class ResoveSocket {

    private Integer uid;

    private Integer rid;

    /**
     * 解析socket中的信息
     * @param socket
     * @param s
     * @throws IOException
     */
    public  void resove(Socket socket,String s) throws IOException {
        ObjectMapper obj = new ObjectMapper();
        UserManager userManager = UserManager.getInstance();
        String [] message = s.split("@");
        String head = message[0];
        String body = "";
        if(message.length>=2){
            body = message[1];
        }
        if(head.equals("onlion")){
            //获取用户id
            User user = obj.readValue(body,User.class);
            //用户id存入在线列表
            userManager.addPlayer(user.getId(),socket);
            //保存用户信息
            this.uid = user.getId();
        }else if(head.equals("CreateRoom")){
            rid = uid;
            //通过用户id创建房间
            userManager.createRoom(uid);

        }else if(head.equals("JoinRoom")){
            //获取房间id
            Room room = obj.readValue(body,Room.class);
            rid = room.getRid();
            //查找房间中的用户
            List<Integer> players = userManager.getRoomMap(rid);
            //如果房间用户大于等于两人,向客户端返回false
            if(players.size()>=2){
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                String flag = "false";
                out.write(flag.getBytes());
            }else{
                //房间添加用户
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
                    byte [] b = str.getBytes();
                    out.write(b);
                    //out.writeUTF("fight@"+body);
                }
            }
        }else if(head.equals("roomList")){
            //查询房间列表
            Map<Integer,List<Integer>> mapList = userManager.getRoomList();
            //获取用户socket
            Socket client = userManager.getPlayer(uid);
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
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
            out.write(str.getBytes());
        }else if(head.equals("Closed")){
            //移除用户
            userManager.removePlayer(uid);
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
            if(room.size()==0){
                userManager.removeRoom(rid);
            }else {
                userManager.playerJoinRoom(rid,room);
            }
        }else if(head.equals("unlion")){
            //进行下线操作
            userManager.removePlayer(uid);
            socket.close();
        }else if(head.equals("GameOver")){
            //游戏结束,提交对战数据
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String date = dateFormat.format(new Date());
        }else if(head.equals("Invite")){
            User user  = obj.readValue(body,User.class);
            //获取好友的socket
            Socket friendSocket = userManager.getPlayer(user.getId());
            if(friendSocket==null){
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                String inviteMessage = "unlion";
                out.write(inviteMessage.getBytes());
            } else {
                DataOutputStream out = new DataOutputStream(friendSocket.getOutputStream());
                Room room = new Room();
                room.setRid(rid);
                String inviteMessage = "invite@"+obj.writeValueAsString(user)+"@"+obj.writeValueAsString(room);
                out.write(inviteMessage.getBytes());
            }
        }
    }
}
