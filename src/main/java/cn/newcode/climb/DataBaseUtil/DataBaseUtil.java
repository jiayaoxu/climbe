package cn.newcode.climb.DataBaseUtil;

import cn.newcode.climb.service.MatchService;
import cn.newcode.climb.service.RankService;
import cn.newcode.climb.service.RecordService;
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
 * \    针对在socket传输中无法使用mapper而创建的类
 */
@Component
public class DataBaseUtil {

    @Autowired
    public UserService userService;

    @Autowired
    public RecordService recordService;

    @Autowired
    public RankService rankService;

    @Autowired
    public MatchService matchService;

    public static DataBaseUtil dataBaseUtil;

    @PostConstruct
    public void init(){
        dataBaseUtil = this;
        dataBaseUtil.userService = this.userService;
        dataBaseUtil.recordService = this.recordService;
        dataBaseUtil.rankService = this.rankService;
        dataBaseUtil.matchService = this.matchService;
    }

    public static void main(String [] args) throws Exception {
        PersonalInf p = DataBaseUtil.dataBaseUtil.userService.seletcPersonalInf(1,1);
        System.out.println(p.getClub());
    }
}