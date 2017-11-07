package cn.newcode.climb.Fight.tool;

import cn.newcode.climb.Fight.vo.Room;
import cn.newcode.climb.po.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @Description: 解析socket里的信息
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
            //用户上线
            User user = obj.readValue(body,User.class);
            //系统添加上线用户
            userManager.addPlayer(user.getId(),socket);
            //系统保存当前用户id
            this.uid = user.getId();
        }else if(head.equals("CreateRoom")){
            //创建房间
            userManager.createRoom(uid);

        }else if(head.equals("JoinRoom")){
            //获取房间id
            Room room = obj.readValue(body,Room.class);
            //记录房间id
            rid = room.getRid();
            //获取房间中的人员信息
            List<Integer> players = userManager.getRoomMap(rid);
            //判断房间中人数,多余两人返回false
            if(players.size()>=2){
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                String flag = "false";
                out.write(flag.getBytes());
            }else{
                //添加本成员
                players.add(uid);
                //成员信息重新加入系统
                userManager.playerJoinRoom(room.getRid(),players);
            }
        }else if(head.equals("fight")){
            //通过rid获取对手Socket
            List<Integer> RoomList =  userManager.getRoomMap(rid);
            //遍历对手
            for(Integer p : RoomList){
                //不传给自己,只传给对手
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
            //查找所有房间
            Map<Integer,List<Integer>> mapList = userManager.getRoomList();
            //获取用户连接
            Socket client = userManager.getPlayer(uid);
            DataOutputStream out = new DataOutputStream(client.getOutputStream());
            //创建头信息
            String roomList = "RoomList@";
            List<Room> room = new ArrayList<Room>();
            for(Map.Entry<Integer,List<Integer>> entry: mapList.entrySet()){
                Room r = new Room();
                r.setRid(entry.getKey());
                room.add(r);
            }
            //返回信息
            String str = roomList+obj.writeValueAsString(room);
            out.write(str.getBytes());
        }else if(head.equals("Closed")){
            //下线,清除用户
            userManager.removePlayer(uid);
        }else if(head.equals("ExitRoom")){
            //退出房间,首先获取玩家指定房间
            List<Integer> room = userManager.getRoomMap(rid);
            //遍历删除玩家元素
            Iterator<Integer> it = room.iterator();
            while(it.hasNext()){
                Integer player = it.next();
                if(player.equals(uid)){
                    it.remove();
                }
            }
            //删除完之后重新设回房间Map,如果list长度为0,直接销毁房间
            if(room.size()==0){
                userManager.removeRoom(rid);
            }else {
                userManager.playerJoinRoom(rid,room);
            }
        }else if(head.equals("unlion")){
            //用户下线
            userManager.removePlayer(uid);
            socket.close();
        }else if(head.equals("GameOver")){
            //比赛完成,游戏结束,成绩存入数据库
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            //当前时间
            String date = dateFormat.format(new Date());
        }else if(head.equals("Invite")){
            User user  = obj.readValue(body,User.class);
            //获取好友的套接字
            Socket friendSocket = userManager.getPlayer(user.getId());
            //如果获取不到socket,说明用户不在线
            if(friendSocket==null){
                DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                String inviteMessage = "unlion";
                out.write(inviteMessage.getBytes());
            } else {
                //如果好友在线,向好友发送邀请信息
                DataOutputStream out = new DataOutputStream(friendSocket.getOutputStream());
                Room room = new Room();
                room.setRid(rid);
                String inviteMessage = "invite@"+obj.writeValueAsString(user)+"@"+obj.writeValueAsString(room);
                out.write(inviteMessage.getBytes());
            }
        }
    }
}
