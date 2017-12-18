package cn.newcode.climb.matchUtil;


import cn.newcode.climb.po.Match;
import cn.newcode.climb.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Description: 比赛开始计时器
 * @author: 作者姓名
 * @CreateDate: 2017/10/25 11:47
 * @Version: 1.0
 */
public class timer implements Runnable {

    private Date startDate;

    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private Integer mid;

    @Autowired
    private MatchService matchService;

    public timer(Date startDate,Integer mid){

        this.startDate = startDate;
        this.mid = mid;
    }

    @Override
    public void run() {
        String sDate = dateFormat.format(startDate);
        Match match = new Match();
        match.setId(mid);
        match.setStatus(true);
        while(true){
            String nowDate = dateFormat.format(new Date());
            if(nowDate.equals(sDate)){
                try {
                    matchService.updateByPrimaryKeySelective(match);
                } catch (Exception e) {
                    e.printStackTrace();
                    throw  new RuntimeException("faild");
                }
            }
        }
    }
}
