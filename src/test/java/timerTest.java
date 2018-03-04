import cn.newcode.climb.matchUtil.timer;
import cn.newcode.climb.service.UserService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description: java类作用描述
 * @author: 作者姓名
 * @CreateDate: 2017/10/25 11:53
 * @Version: 1.0
 */
public class timerTest {

    public static void main(String [] args) throws ParseException {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = df.parse("2017-10-25 12:38:00");
        timer timer = new timer(date,1);
        Thread thread = new Thread(timer);
        thread.start();
        for(;;){

        }
    }

    @Test
    public void testTimer() throws ParseException {
        /*SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = df.parse("2017-10-25 12:38:00");
        timer timer = new timer(date);
        Thread thread = new Thread(timer);
        thread.start();
        for(;;){

        }*/
    }

}
