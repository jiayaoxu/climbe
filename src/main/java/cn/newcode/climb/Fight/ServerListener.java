package cn.newcode.climb.Fight;

import cn.newcode.climb.Fight.net.socketThread;
import cn.newcode.climb.LogUtil.MLogger;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Description: 监听Socket类
 * @author: shine
 * @CreateDate: 2017/10/27 16:11
 * @Version: 1.0
 */
public class ServerListener implements Runnable {

    private ServerSocket serverSocket;

    private Integer port = 10086;

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            MLogger.info("---------------------socketServer is starting---------------------");
            //System.out.println("---------------------socketServer is starting---------------------");
        } catch (IOException e) {
            e.printStackTrace();
        }

        try{
            while(true){
                try{
                    Socket s = serverSocket.accept();
                    Thread serverThread = new Thread(new socketThread(s));
                    serverThread.start();
                } catch (Exception e){
                    MLogger.info("---------------------player is Unline-------------------");
//                    System.out.println("---------------------player is Unline-------------------");
                    e.printStackTrace();
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
