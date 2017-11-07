package cn.newcode.climb.Fight.net;

import cn.newcode.climb.Fight.tool.ResoveSocket;
import cn.newcode.climb.Fight.tool.UserManager;
import cn.newcode.climb.po.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.Socket;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @Description: 处理每个用户的socket
 * @author: shine
 * @CreateDate: 2017/10/27 16:18
 * @Version: 1.0
 */
public class socketThread implements Runnable{

    private Socket socket;

    public socketThread(Socket socket) throws IOException {
        this.socket = socket;
    }

    @Override
    public void run() {
        ResoveSocket resove = new ResoveSocket();
        boolean connected = true;
        Integer uid = null;
        System.out.println("新玩家上线了");
        DataInputStream input = null;
        while (connected) {
        try {
                    input = new DataInputStream(socket.getInputStream());
                    //接受传来的数据,转为字符串
                    byte[] b = new byte[socket.getSendBufferSize()];
                    input.read(b);
                    String s = new String(b);
                    if(s.equals("")||s==null||!s.contains("@")){
                        continue;
                    }else if(s.split("@")[0].equals("onlion")){
                        ObjectMapper obj = new ObjectMapper();
                        User user = obj.readValue(s.split("@")[1],User.class);
                        uid = user.getId();
                    }
                    System.out.println(s);
                    //转完后的字符串交给处理类进行处理
                    resove.resove(socket, s);
            }catch(IOException e){
                connected = false;
                System.out.println("玩家已下线");
                //系统移除该用户
                    try {
                    socket.close();
                    UserManager.getInstance().removePlayer(uid);
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            e.printStackTrace();
            }
        }
    }
}
