package cn.newcode.climb.LoginCheck;

import cn.newcode.climb.LogUtil.MLogger;
import cn.newcode.climb.LoginCheck.net.LoginSocketThread;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * \* Created with IntelliJ IDEA.
 * \* User: shine
 * \* Date: 17-12-24
 * \* Time: 下午5:51
 * \* Description:
 * \
 */
public class LoginServer implements Runnable {

    private ServerSocket serverSocket;

    private Integer port = 10087;

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            MLogger.info("---------------------LoginSocketServer is starting---------------------");
            //System.out.println("---------------------LoginSocketServer is starting---------------------");
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(true){
            try{
                Socket s = serverSocket.accept();
                Thread serverThread = new Thread(new LoginSocketThread(s));
                serverThread.start();
            } catch (Exception e){
                //System.out.println("---------------------player is Unline-------------------");
                MLogger.info("---------------------player is Unline-------------------");
                e.printStackTrace();
            }
        }
    }
}