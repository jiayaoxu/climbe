package cn.newcode.climb.Fight.net;

import cn.newcode.climb.Fight.tool.ResoveSocket;
import cn.newcode.climb.Fight.tool.UserManager;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.util.Map;

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
        System.out.println("新玩家上线了");
        try{
            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            while(connected){
                resove.resove(socket,input.readUTF());
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
