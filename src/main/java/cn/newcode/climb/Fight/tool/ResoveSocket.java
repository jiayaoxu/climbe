package cn.newcode.climb.Fight.tool;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.Socket;

/**
 * @Description: 解析socket
 * @author: shine
 * @CreateDate: 2017/10/27 16:44
 * @Version: 1.0
 */
public class ResoveSocket {

    /**
     * 解析socket中的信息
     * @param socket
     * @param s
     * @throws IOException
     */
    public static void resove(Socket socket,String s) throws IOException {
        UserManager userManager = UserManager.getInstance();
        String [] messages = s.split(",");
        String  message = messages[0];
        String  head = message.split(":")[0];
        if("id".equals(head)){
            Integer uid = Integer.parseInt(message.split(":")[1]);
            userManager.addPlayer(uid,socket);
        }else if("createRoom".equals(head)){
            Integer uid = Integer.parseInt(message.split(":")[1]);
            userManager.createRoom(uid);
        }else if("joinRoom".equals(head)){
            Integer rid = Integer.parseInt(message.split(":")[1]);
            userManager.getRoomMap(rid);
        }
    }

}
