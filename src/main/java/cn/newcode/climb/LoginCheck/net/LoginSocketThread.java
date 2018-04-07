package cn.newcode.climb.LoginCheck.net;


import cn.newcode.climb.DataBaseUtil.DataBaseUtil;
import cn.newcode.climb.service.ClubService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * 用户登录处理socket类
 */
public class LoginSocketThread implements Runnable {

    private Socket socket;

//    @Autowired
//    private ClubService clubService;

    public LoginSocketThread(Socket socket) throws IOException {
        this.socket = socket;
    }

    @Override
    public void run() {
        try{
            Boolean connected = true;
            DataInputStream input = new DataInputStream(socket.getInputStream());
            DataOutputStream output = new DataOutputStream(socket.getOutputStream());
            while (connected) {
                Integer uid = null;
                try{
                    byte [] b = new byte[1024];
                    int len = input.read(b);
                    String login = new String(b,0,len);
                    uid = Integer.parseInt(login.trim());
                    //检查用户是否上线过
                    Socket checkUser = UserRegister.getInstance().getUser(uid);
                    if(checkUser!=null){
                        DataOutputStream out = new DataOutputStream(checkUser.getOutputStream());
                        String message = "replace";
                        out.write(message.getBytes());
                    }
                    //添加用户
                    UserRegister.getInstance().addUser(uid,socket);
                    //查询属于哪个俱乐部
                    Integer cid = 0;
                    try{
                        cid = DataBaseUtil.dataBaseUtil.clubService.selectBelong(uid);
                    } catch (NullPointerException ee){
                        ee.printStackTrace();
                    }
                    if(cid!=null){
                        UserRegister.getInstance().addonlion(cid);
                    }
                } catch (IOException e){
                    //socket断开链接抛出异常
                    UserRegister.getInstance().removeUser(uid);
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}
