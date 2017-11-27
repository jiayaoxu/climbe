package cn.newcode.climb.Fight.net;

import cn.newcode.climb.Fight.tool.MLogger;
import cn.newcode.climb.Fight.tool.ResoveSocket;
import cn.newcode.climb.Fight.tool.UserCacheManager;
import cn.newcode.climb.Fight.tool.UserManager;
import cn.newcode.climb.po.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.List;

public class socketThread implements Runnable {

    private Socket socket;

    public socketThread(Socket socket) throws IOException {
        this.socket = socket;
    }

    @Override
    public void run() {
        ResoveSocket resove = new ResoveSocket();
        boolean connected = true;
        Integer uid = null;
        MLogger.info("new Player onlion....");
        DataInputStream input = null;
        while (connected){
            try {
                //获取数据长度
                input = new DataInputStream(socket.getInputStream());
                byte [] head = new byte[4];
                input.read(head);
                Integer Length = Integer.parseInt(new String(head));
                // 接受传来的数据,转为字符串
                byte[] b = new byte[Length];
                input.read(b);// 接收数据
                //抛出异常,注销房间，下线
                String s = new String(b, 0,Length);// 根据实际长度获取
                if (s.equals("") || s == null || !s.contains("@")) {
                    //截获空包
                    continue;
                } else if (s.split("@")[0].equals("onlion")) {
                    //截获上线包，存储uid
                    ObjectMapper obj = new ObjectMapper();
                    User user = obj.readValue(s.split("@")[1], User.class);
                    uid = user.getId();
                }
                // 转完后的字符串交给处理类进行处理
                //通过结束标志符号分割包
                String [] message = s.split("&");
                for(String str : message){
                    MLogger.info("resove:"+str);
                    resove.resove(socket, str);
                }
            } catch (Exception e) {
                connected = false;
                MLogger.info("player unlioning....");
                MLogger.error(e);
                //获取用户管理类实例
                UserManager userManager = UserManager.getInstance();
                //玩家下线
                userManager.removePlayer(uid);
                //普通房间处理
                //判断玩家是否创建房间,如果有,销毁房间,通知房间里的所有玩家roomDestroied@
                List<Integer> room =  userManager.getRoomMap(uid);
                //通知玩家
                if(room!=null) {
                    //遍历房间用户
                    for (Integer userId : room) {
                        Socket socket = userManager.getPlayer(userId);
                        DataOutputStream out = null;
                        try {
                            out = new DataOutputStream(socket.getOutputStream());
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        String message = "roomDestroied@";
                        try {
                            out.write(message.getBytes());
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                    //移除房间
                    userManager.removeRoom(uid);


                    //观战房间处理
                    //判断玩家是否创建房间,如果有,销毁房间,通知房间里的所有玩家roomDestroied@
                    List<Integer> Watchroom = userManager.getRoomMap(uid);
                    //通知玩家
                    if (Watchroom != null) {
                        //遍历房间用户
                        for (Integer userId : Watchroom) {
                            Socket socket = userManager.getPlayer(userId);
                            DataOutputStream out = null;
                            try {
                                out = new DataOutputStream(socket.getOutputStream());
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            String message = "WatchroomDestroied@";
                            try {
                                out.write(message.getBytes());
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                        //移除房间
                        userManager.removeWatchRoom(uid);
                    }
                }

                //玩家连接关闭
                try {
                    socket.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
                break;
            }
        }
    }
}
