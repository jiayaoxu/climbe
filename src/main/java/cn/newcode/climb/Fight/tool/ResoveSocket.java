package cn.newcode.climb.Fight.tool;

import cn.newcode.climb.Fight.vo.Invite;
import cn.newcode.climb.Fight.vo.Room;
import cn.newcode.climb.Fight.vo.UserCustom;
import cn.newcode.climb.LogUtil.MLogger;
import cn.newcode.climb.po.User;
import cn.newcode.climb.service.RankService;
import cn.newcode.climb.vo.PersonalInf;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

    private ObjectMapper obj = new ObjectMapper();

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
    public ResoveSocket(Socket socket) throws IOException{
        this.socket = socket;
        out = new DataOutputStream(socket.getOutputStream());
        /*ApplicationContext context = new ClassPathXmlApplicationContext("classpath:springmvc-servlet.xml");
        userService = (UserService) context.getBean("userService");*/

    }

    /**
     * 解析socket中的信息
     * @param
     * @param s
     * @throws IOException
     */
    public  void resove(String s) throws IOException {
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
            User user = null;
            try {
                user = obj.readValue(body,User.class);
            } catch (IOException e) {
                out.write(addCache("error@getUserError"));
            }
            /**
             *  登录校验:
             *  检测该uid是否有用户登录,如果有，通知该用户被顶替,然后进行上线操作
             */
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
            Room room = null;
            try {
                room = obj.readValue(body,Room.class);
            } catch (IOException e) {
                out.write(addCache("error:getRoomError"));
            }
            //通过用户id创建房间，需要提供密码
            userManager.createRoom(uid,room.getPassword(),room.getRoomName());
            //DataOutputStream out  = new DataOutputStream(socket.getOutputStream());
            String response = "CreateRoom@Success";
            out.write(addCache(response));
        }else if(head.equals("JoinRoom")){
            //获取房间id
            Room room = null;
            try {
                room = obj.readValue(body,Room.class);
            } catch (IOException e) {
                out.write(addCache("error:getRoomError"));
            }
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
            } if (!userManager.getRoomPassword(rid).split("@")[1].equals(userJoinPass)){
                //密码不正确
                //DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                String response = "JoinRoom@paswordError";
                out.write(addCache(response));
            } else{
                //房间添加用户
                //获取房主id
                Integer roomHolder = players.get(0);
                //获取房主socket
                Socket roomHolderSocket = null;
                try {
                    roomHolderSocket = userManager.getPlayer(roomHolder);
                    //提示房主有人加入
                    DataOutputStream Holderout = new DataOutputStream(roomHolderSocket.getOutputStream());
                    String joinMessage = "join@" + uid;
                    Holderout.write(addCache(joinMessage));

                    //DataOutputStream outwiriter = new DataOutputStream(socket.getOutputStream());
                    out.write(addCache(joinMessage));
                } catch (Exception e) {
                    out.write(addCache("error@getSocketError"));
                }
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
                try {
                    if(!p.equals(uid)){
                        Socket player = userManager.getPlayer(p);
                        DataOutputStream out = new DataOutputStream(player.getOutputStream());
                        String str = "fight@"+body;
                        out.write(addCache(str));
                        //out.writeUTF("fight@"+body);
                    }
                } catch (IOException e) {
                    out.write(addCache("error@noPlayerError"));
                }
            }
        }else if(head.equals("roomList")){
            //查询房间列表
            Map<Integer,List<Integer>> mapList = userManager.getRoomList();
            Map<Integer,String> passwordList = userManager.getRoomPassword();
            //获取用户socket
            //Socket client = userManager.getPlayer(uid);
            //DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            //返回信息数据头
            String roomList = "RoomList@";
            List<Room> room = new ArrayList<Room>();
            for(Map.Entry<Integer,List<Integer>> entry: mapList.entrySet()){
                Room r = new Room();
                r.setRid(entry.getKey());
                r.setRoomName(passwordList.get(entry.getKey()).split("@")[0]);
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
                    try {
                        Socket roomUser = userManager.getPlayer(r);
                        DataOutputStream roomOut = new DataOutputStream(roomUser.getOutputStream());
                        String response = "room@roomDestried";
                        roomOut.write(addCache(response));
                    } catch (IOException e) {
                        out.write(addCache("error@noPlayerError"));
                    }
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
            UserCustom user  = obj.readValue(body,UserCustom.class);
            //获取好友的socket
            Socket friendSocket = userManager.getPlayer(user.getId());
            if(friendSocket==null){
                //DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                String inviteMessage = "Invite@friendUnlion";
                out.write(addCache(inviteMessage));
            } else {
                String inviteMessage = null;
                try {
                    DataOutputStream FriendOut = new DataOutputStream(friendSocket.getOutputStream());
                    Room room = new Room();
                    room.setRid(rid);
                    inviteMessage = "invite@"+obj.writeValueAsString(user)+"@"+obj.writeValueAsString(room);
                    //向好友发送邀请
                    FriendOut.write(addCache(inviteMessage));
                } catch (IOException e) {
                    out.write(addCache("error@noPlayerError"));
                }
                //通知自己
                inviteMessage = "invite@Success";
                out.write(addCache(inviteMessage));
            }
        }else if(head.equals("ReplayInvite")){
            Invite i = obj.readValue(body,Invite.class);
            DataOutputStream outputStream = null;
            try {
                Socket ss = userManager.getPlayer(i.getId());
                outputStream = new DataOutputStream(ss.getOutputStream());
            } catch (IOException e) {
                out.write(addCache("error@noPlayerError"));
            }
            Invite invite = new Invite();
            invite.setId(uid);
            String joinMessage = "";
            if(i.getInvite()){
                invite.setInvite(true);
                //查找房间中的用户
                List<Integer> players = userManager.getRoomMap(i.getRid());
                if(players.size()>=2){
                    joinMessage = "ReplayInvite@roomFull";
                }else{
                    //房间添加玩家
                    players.add(uid);
                    //房间信息重新设回
                    userManager.playerJoinRoom(i.getRid(),players);
                    joinMessage = "ReplayInvite@Success";
                    rid = i.getRid();
                }
            }else{
                invite.setInvite(false);
                joinMessage = "ReplayInvite@Success";
            }
            String str = "ReplayInvite@"+obj.writeValueAsString(invite);
            outputStream.write(addCache(str));
            out.write(addCache(joinMessage));
        }else if(head.equals("JoinWatchRoom")){
            //观战房间加入,观战房间最多三十个人
            //获取房间id,房间密码
            Room room = null;
            try {
                room = obj.readValue(body,Room.class);
                rid = room.getRid();
            } catch (IOException e) {
                out.write(addCache("error:getRoomError"));
            }
            String userJoinPass = room.getPassword();
            //查找房间中的用户
            List<Integer> players = userManager.getWatchRoomMap(rid);
            //如果房间用户大于等于两人,向客户端返回false
            if(players.size()>=30){
                //DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                String flag = "JoinWatchRoom@false";
                out.write(addCache(flag));
            }else if(!userManager.getRoomPassword(rid).split("@")[1].equals(userJoinPass)){
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
                annon();
            }
        }else if(head.equals("CreateWatchRoom")){
            rid = uid;
            Room room = obj.readValue(body,Room.class);
            //通过用户id创建房间
            userManager.createWatchRoom(uid,room.getPassword(),room.getRoomName(),room.getWid(),room.getRoid());
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
            Map<Integer,String> passwordList = userManager.getRoomPassword();
            //获取用户socket
            //Socket client = userManager.getPlayer(uid);
            //DataOutputStream out = new DataOutputStream(client.getOutputStream());
            //返回信息数据头
            String roomList = "WatchRoomList@";
            List<Room> room = new ArrayList<Room>();
            for (Map.Entry<Integer, List<Integer>> entry : mapList.entrySet()) {
                Room r = new Room();
                r.setRoomName(passwordList.get(entry.getKey()));
                r.setRid(entry.getKey());
                room.add(r);
            }
            //返回数据
            String str = roomList + obj.writeValueAsString(room);
            out.write(addCache(str));
        }else if(head.equals("WatchFight")){
            try{
                //查询本房间
                List<Integer> RoomList =  userManager.getWatchRoomMap(rid);
                //遍历用户
                for(Integer p : RoomList){
                    //将自己的位置信息发送给对手
                    try{
                        if(!p.equals(uid)){
                            Socket player = userManager.getPlayer(p);
                            DataOutputStream out = new DataOutputStream(player.getOutputStream());
                            String str = "Watchfight@"+body;
                            //byte [] b = str.getBytes();
                            out.write(addCache(str));
                            //out.writeUTF("fight@"+body);
                        }
                    } catch (NullPointerException e){
                        out.write(addCache("WatchFight@NoThisPlayer:"+p));
                    }
                }
            } catch (NullPointerException pe){
                out.write(addCache("error@NoRoomError"));
            }
        }else if(head.equals("addPoint")){
            //点赞
            //Integer teacher = body!=null?Integer.parseInt(body):null;
            String str = "addPoint@teacherIsEmpty";
            if(rid!=null){
                rankService.addPoint(rid);
                str = "addPoint@success";
            }
            out.write(addCache(str));
        }else if(head.equals("WatchInvite")){
            //教学模块邀请好友
            String [] players = body.split(",");
            for(int i = 0;i<players.length-1;i++){
                String str = players[i];
                Socket playSoket = null;
                try{
                    playSoket   = userManager.getPlayer(Integer.parseInt(str));
                    DataOutputStream playerOut = new DataOutputStream(playSoket.getOutputStream());
                    String [] strs = UserManager.getInstance().getRoomPassword().get(rid).split("@");
                    String wid = strs[2];
                    String roid = strs[3];
                    String inviteMessage = "WatchInvite@"+rid+"@"+wid+"@"+roid;
                    playerOut.write(addCache(inviteMessage));
                    out.write(addCache("WatchInvite@Success"));
                } catch (NumberFormatException e){
                    out.write(addCache("WatchInvite@FriendUnlion"));
                }
            }
        }else if(head.equals("ReplayWatchInvite")){
            body = body.split(",")[0];
            Integer joinRoom = null;
            try {
                joinRoom = Integer.parseInt(body);
            } catch (NumberFormatException e) {
                out.write(addCache("error@NumberFormatException"));
            }
            rid = joinRoom;
            //同意才回复邀请,不同意直接屏蔽即可 回复房间号
            String ReplayMessage = "ReplayWatchInvite@"+uid;
            DataOutputStream roomHolder = null;
            try {
                roomHolder = new DataOutputStream(userManager.getPlayer(joinRoom).getOutputStream());
            } catch (IOException e) {
                out.write(addCache("error:noRoomHolder"));
            }
            //加入房间
            //查找房间中的用户
            List<Integer> players = userManager.getWatchRoomMap(joinRoom);
            //如果房间用户大于等于30,向客户端返回false
            if(players.size()>=30){
                String flag = "ReplayWatchInvite@roomFull";
                out.write(addCache(flag));
            }else{
                //提示房主有人加入
                String joinMessage = "WatchRoom@" + uid;
                roomHolder.write(addCache(joinMessage));
                //DataOutputStream outwiriter = new DataOutputStream(socket.getOutputStream());
                //提醒玩家
                out.write(addCache(joinMessage));
                //房间添加玩家
                players.add(uid);
                //房间信息重新设回
                userManager.WatchplayerJoinRoom(joinRoom,players);
                String flag = "ReplayWatchInvite@Success";
                out.write(addCache(flag));
                //告诉房间里的人有人加入
                annon();
            }
        }
    }

    /**
     * 有人加入通知房间里所有人
     * @throws IOException
     */
    private void annon() throws IOException{
        UserManager userManager = UserManager.getInstance();
        List<Integer> players = userManager.getWatchRoomMap(rid);
        List<PersonalInf> persons = new ArrayList<PersonalInf>();
        //查询本房间所有人的信息
        for(Integer p : players){
            try {
                PersonalInf personalInf = DataBaseUtil.dataBaseUtil.userService.seletcPersonalInf(p,p);
                persons.add(personalInf);
                MLogger.info(personalInf.toString());
            } catch (Exception e) {
                out.write(addCache("error:annonError"));
            }
        }
        //依次发送给所有用户
        for(Integer p : players){
            Socket s = userManager.getPlayer(p);
            DataOutputStream outputStream = null;
            try {
                outputStream = new DataOutputStream(s.getOutputStream());
                outputStream.write(addCache("newJoin@"+obj.writeValueAsString(persons)));
            } catch (IOException e){
                e.printStackTrace();
                out.write(addCache("error:annonError"));
            }
        }
    }


    /**
     * 将字符串封装入缓存数组中
     * @param value
     * @return
     * @throws Exception
     */
    public static byte[] addCache(String value){
        byte src [] = value.getBytes();
        byte response [] = new byte[1024];
        System.arraycopy(src,0,response,0,src.length);
        return response;
    }
}
