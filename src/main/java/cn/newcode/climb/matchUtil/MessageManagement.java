package cn.newcode.climb.matchUtil;

import cn.newcode.climb.DataBaseUtil.DataBaseUtil;
import cn.newcode.climb.Fight.tool.UserManager;
import cn.newcode.climb.po.Match_grade;
import cn.newcode.climb.service.MatchService;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Timer;
import java.util.TimerTask;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: shine
 * \* Date: 2018/4/1 0001
 * \* Time: 15:53
 * \* Description:复赛通知选手输赢
 * \
 */
public class MessageManagement implements Runnable {

    private Integer uid;

    private Integer mid;

    private String flag;

    private Integer grade;

    private Long timestamp = 20000L;

    public MessageManagement(Integer uid,Integer mid,String flag,Integer grade)
    {
        this.uid = uid;
        this.mid = mid;
        this.flag = flag;
        this.grade = grade;
    }

    @Override
    public void run() {
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                Boolean isWin = false;

                MatchService matchService = DataBaseUtil.dataBaseUtil.matchService;
                GradeManager g = GradeManager.getInstance();
                Integer equal = g.getEquals(uid);
                //如果通过自己的id找不到对手,说明自己已经输了
                if(equal==null){

                    isWin = false;

                }else {

                    //查询对手成绩
                    Match_grade grades = new Match_grade();
                    grades.setMid(mid);
                    grades.setUid(equal);
                    Match_grade match_grade = matchService.selectGrade(grades);

                    //通过成绩判断输赢
                    if(flag.equals("s")){
                        isWin = grade>match_grade.getSgrade()? true:false;
                    }else if(flag.equals("t")){
                        isWin = grade>match_grade.getTgrade()? true:false;
                    }else if (flag.equals("f")){
                        isWin = grade>match_grade.getFgrade()? true:false;
                    } else if (flag.equals("fi")){
                        isWin = grade>match_grade.getFigrade()? true:false;
                    }
                }

                //判断出输赢之后移除输的人的信息
                if(isWin)
                    g.removeEqual(equal);
                else
                    g.removeEqual(uid);

                UserManager userManager = UserManager.getInstance();
                Socket socket = userManager.getPlayer(uid);
                try {
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());
                    out.write(addCache("match_tw@"+isWin));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Integer size = g.getEqualsSize();

                //收集排名
                if(size<=8){
                    // 如果是自己赢了对手就是第九名 否则自己就是第九名
                    if(isWin){
                        g.addMedalList(equal);
                    }else{
                        g.addMedalList(uid);
                    }
                }

                if(size==1){
                    //如果自己赢了说明自己是冠军  否则自己是季军
                    if(isWin){
                        g.addMedalList(uid);
                    }else{
                        g.addMedalList(equal);
                    }
                }
            }
        };
        timer.schedule(timerTask,timestamp);
    }

    public static byte[] addCache(String value){
        byte src [] = value.getBytes();
        byte response [] = new byte[1024];
        System.arraycopy(src,0,response,0,src.length);
        return response;
    }
}