package cn.newcode.climb.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: shine
 * \* Date: 2017/12/15 0015
 * \* Time: 13:52
 * \* Description:向前端返回用户的注册年龄时间
 * \
 */
public class Rank_ageVo {
    private String username;
    private long year;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * 将long类型数据转换为String Date在返回
     * @return
     */
    public String getYear() {
       /* SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        java.util.Date date = new Date( year* 1000);
        String register = sdf.format(date);*/
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(year));
        //System.out.println(str);
        //return register;
    }

    public void setYear(long year) {
        this.year = year;
    }
}