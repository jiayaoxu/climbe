package cn.newcode.climb.Fight.net;

import cn.newcode.climb.LogUtil.MLogger;
import cn.newcode.climb.Fight.tool.ResoveSocket;
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

        ResoveSocket resove = null;

        try{
            resove = new ResoveSocket(socket);
        } catch (Exception e){
            MLogger.error(e);
        }

        boolean connected = true;
        Integer uid = null;
        MLogger.info("new Player onlion....");
        DataInputStream input = null;
        while (connected){
            try {
                //获取数据长度
                input = new DataInputStream(socket.getInputStream());
                //创建用于接收数据的byte数组
                byte[] b = new byte[1024];
                input.read(b);// 接收数据
                //抛出异常,注销房间，下线
                String s = new String(b, 0,1024);// 根据实际长度获取
                //截获特殊包
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
                    resove.resove(str);
                }

            } catch (Exception e) {
                connected = false;
                MLogger.info("player unlioning...." + uid);
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
                        try {
                            //遍历房间用户
                            for (Integer userId : room) {
                                Socket socket = userManager.getPlayer(userId);
                                DataOutputStream out = null;
                                out = new DataOutputStream(socket.getOutputStream());
                                String message = "roomDestroied@";
                                out.write(message.getBytes());
                            }
                        } catch (Exception ee) {
                            MLogger.error(ee);
                        } finally {
                            //移除房间
                            userManager.removeRoom(uid);
                        }
                    }

                    //观战房间处理
                    //判断玩家是否创建房间,如果有,销毁房间,通知房间里的所有玩家roomDestroied@
                    List<Integer> Watchroom = userManager.getRoomMap(uid);
                    //通知玩家
                    if (Watchroom != null) {
                        try{
                            //遍历房间用户
                            for (Integer userId : Watchroom) {
                                Socket socket = userManager.getPlayer(userId);
                                DataOutputStream out = null;
                                out = new DataOutputStream(socket.getOutputStream());
                                String message = "WatchroomDestroied@";
                                out.write(message.getBytes());
                            }
                        } catch (Exception eee){
                            MLogger.error(eee);
                        } finally {
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
/*    public static int byteToInt(byte[] src, int offset) {
        int value;
        value = (int) ((src[offset] & 0xFF)
                | ((src[offset+1] & 0xFF)<<8)
                | ((src[offset+2] & 0xFF)<<16)
                | ((src[offset+3] & 0xFF)<<24));
        return value;
    }

    public static int byteToInt2(byte [] src){
        int value = 0;
        int count = 0;
        for(int i : src){
             if(i>256){
                 count++;
             }
        }
        return 256*count + src[count];
    }

    public static byte [] intTobyte(int i){
        byte [] b = new byte[4];
        return null;
    }*/
}
