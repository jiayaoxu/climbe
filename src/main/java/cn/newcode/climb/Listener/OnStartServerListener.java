package cn.newcode.climb.Listener;

import cn.newcode.climb.Fight.ServerListener;
import cn.newcode.climb.Fight.net.socketThread;
import cn.newcode.climb.LoginCheck.LoginServer;
import cn.newcode.climb.LoginCheck.net.LoginSocketThread;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @Description: 监听容器启动类
 * @author: shine
 * @CreateDate: 2017/10/27 16:11
 * @Version: 1.0
 */
public class OnStartServerListener implements ServletContextListener{

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        //启动监听登录线程
        new Thread(new LoginServer()).start();
        //启动对战监听线程
        new Thread(new ServerListener()).start();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
