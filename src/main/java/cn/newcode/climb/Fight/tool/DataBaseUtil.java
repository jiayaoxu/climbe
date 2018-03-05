package cn.newcode.climb.Fight.tool;

import cn.newcode.climb.service.UserService;
import cn.newcode.climb.vo.PersonalInf;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: shine
 * \* Date: 2018/3/4 0004
 * \* Time: 22:29
 * \* Description:
 * \
 */
@Component
public class DataBaseUtil {

    @Autowired
    public UserService userService;

    public static DataBaseUtil dataBaseUtil;

    @PostConstruct
    public void init(){
        dataBaseUtil = this;
        dataBaseUtil.userService = this.userService;
    }

    public static void main(String [] args) throws Exception {
        PersonalInf p = DataBaseUtil.dataBaseUtil.userService.seletcPersonalInf(1,1);
        System.out.println(p.getClub());
    }
}