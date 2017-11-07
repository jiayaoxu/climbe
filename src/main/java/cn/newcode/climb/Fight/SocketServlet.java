package cn.newcode.climb.Fight;

import cn.newcode.climb.Fight.net.socketThread;
import org.apache.log4j.net.SocketServer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Description: http启动时同时启动socket
 * @author: shine
 * @CreateDate: 2017/11/2 0:15
 * @Version: 1.0
 */
public class SocketServlet{

    private ServerSocket serverSocket;

    private Integer port = 10086;

    public void init() throws ServletException {
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

    public void run(){
        ServerSocket server = null;

        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        while(true){
                try{
                    Socket s = server.accept();
                    ExecutorService cachedThreadPool = Executors.newCachedThreadPool();
                    cachedThreadPool.execute(new Thread((new socketThread(s))));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }



    }
}
