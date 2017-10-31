package cn.newcode.climb.Fight.tool;

import cn.newcode.climb.Fight.vo.Room;
import cn.newcode.climb.po.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.*;

/**
 * @Description: 解析socket里的信息
 * @author: shine
 * @CreateDate: 2017/10/27 16:44
 * @Version: 1.0
 */
public class ResoveSocket {

    private Integer uid;

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
        String body = message[1];
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
            //获取房间中的人员信息
            List<Integer> players = userManager.getRoomMap(room.getRid());
            //添加本成员
            players.add(uid);
            //成员信息重新加入系统
            userManager.playerJoinRoom(room.getRid(),players);
        }else if(head.equals("fight")){
            //对战模块 获取rid
            Integer rid = obj.readValue(body,Room.class).getRid();
            //通过rid获取对手Socket
            List<Integer> RoomList =  userManager.getRoomMap(rid);
            //遍历对手
            for(Integer p : RoomList){
                //不传给自己,只传给对手
                if(!p.equals(uid)){
                    Socket player = userManager.getPlayer(p);
                    DataOutputStream out = new DataOutputStream(player.getOutputStream());
                    out.writeUTF("fight@"+body);
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
            Set<Integer> roomSet = mapList.keySet();
            //返回信息
            out.writeUTF(roomList+obj.writeValueAsString(mapList));
        }else if(head.equals("Closed")){
            //下线,清除用户
            userManager.removePlayer(uid);
        }
    }

}
