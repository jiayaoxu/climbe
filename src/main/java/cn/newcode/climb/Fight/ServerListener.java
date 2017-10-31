package cn.newcode.climb.Fight;

import cn.newcode.climb.Fight.net.socketThread;

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
public class ServerListener implements ServletContextListener{

    private ServerSocket serverSocket;

    private Integer port = 10086;


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            serverSocket = new ServerSocket(port);
            System.out.println("---------------------socketServer is starting---------------------");
        } catch (IOException e) {
            e.printStackTrace();
        }

        while(true){
            try{
                Socket s = serverSocket.accept();
                Thread serverThread = new Thread(new socketThread(s));
                serverThread.start();
            } catch (Exception e){
                System.out.println("---------------------player is Unline-------------------");
                e.printStackTrace();
            }
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
       if(serverSocket!=null){
           try {
               serverSocket.close();
               System.out.println("---------------------socketServer is closed---------------------");
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
    }
}
