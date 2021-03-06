import cn.newcode.climb.DataBaseUtil.DataBaseUtil;
import cn.newcode.climb.mapper.UserMapper;
import cn.newcode.climb.service.MatchService;
import cn.newcode.climb.service.UserService;
import cn.newcode.climb.service.impl.UserServiceImpl;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

import java.util.List;

import static cn.newcode.climb.DataBaseUtil.DataBaseUtil.dataBaseUtil;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: shine
 * \* Date: 2017/12/15 0015
 * \* Time: 13:20
 * \* Description:测试时间转换为long类型
 * \
 */
@Component
public class timeUtil {

    @Test
    public void test() throws Exception{
        //Date date = new Date(2015,1,1);
        //Date date2 = new Date(2016,1,1);
//        System.out.println(date.toString());
        /*System.out.println("2016年"+date2.getTime());
        System.out.println("2015年"+date.getTime());*/
//        userMapper.seletcByUsername("shine");
//        userService.seletcPersonalInf(1,1);
//        UserController userController = new UserController();
       //userController.personalInf(1);
        ApplicationContext context = new ClassPathXmlApplicationContext("classpath:springMVC/springmvc-servlet.xml");
        UserService userService = (UserServiceImpl) context.getBean("userService");
        userService.seletcPersonalInf(1,1);
    }

    @Autowired
    private MatchService matchService;


    @Test
    public void testMapper(){
        List<Integer> l = matchService.getAllPlayersInThisMatch(41);
    }
}