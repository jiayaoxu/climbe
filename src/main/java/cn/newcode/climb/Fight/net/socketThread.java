package cn.newcode.climb.Fight.net;

import cn.newcode.climb.Fight.tool.MLogger;
import cn.newcode.climb.Fight.tool.ResoveSocket;
import cn.newcode.climb.Fight.tool.UserManager;
import cn.newcode.climb.po.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;

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
        MLogger.info("新玩家上线了");
        DataInputStream input = null;
        while (connected) {
            try {
                input = new DataInputStream(socket.getInputStream());
                // 接受传来的数据,转为字符串
                byte[] b = new byte[socket.getSendBufferSize()];
                int len = input.read(b);// 此处需要获取读取字节的实际长度
                String s = new String(b, 0, len);// 根据实际长度获取
                if (s.equals("") || s == null || !s.contains("@")) {
                    continue;
                } else if (s.split("@")[0].equals("onlion")) {
                    ObjectMapper obj = new ObjectMapper();
                    User user = obj.readValue(s.split("@")[1], User.class);
                    uid = user.getId();
                }
                //MLogger.info(s);
                // write("aa:"+s+":aa\r\n");
                // 转完后的字符串交给处理类进行处理
                //通过结束标志符号分割包
                String [] message = s.split("&");
                for(String str : message){
                    MLogger.info("resove"+str);
                    resove.resove(socket, str);
                }
            } catch (IOException e) {
                connected = false;
                MLogger.info("玩家已下线");
                // 系统移除该用户
                try {
                    socket.close();
                    UserManager.getInstance().removePlayer(uid);
                } catch (IOException e1) {
                    MLogger.error(e1);
                }
                MLogger.error(e);
            }
        }
    }
}
