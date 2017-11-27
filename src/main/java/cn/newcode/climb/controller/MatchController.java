package cn.newcode.climb.controller;

import cn.newcode.climb.Fight.SocketServlet;
import cn.newcode.climb.po.Match;
import cn.newcode.climb.po.Match_grade;
import cn.newcode.climb.po.Match_inf;
import cn.newcode.climb.po.Match_signup;
import cn.newcode.climb.service.MatchService;
import cn.newcode.climb.vo.Grade;
import cn.newcode.climb.vo.Status;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * @Description: 比赛接口
 * @author: shine
 * @CreateDate: 2017/10/18 5:51
 * @Version: 1.0
 */
@Controller
@RequestMapping("/match")
public class MatchController {

    @Autowired
    private MatchService matchService;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Status statusMessage ;

    /**
     * 查找正在进行中比赛
     * @param response
     * @param status
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/selectMatchStatus",method= RequestMethod.POST,
            produces = "text/json;charset=UTF-8")
    public @ResponseBody String selectMatchStatus(HttpServletResponse response,Boolean status) throws JsonProcessingException {
        response.setHeader("Access-Control-Allow-Origin","*");
        List<Match> matchList = null;
        String matchs = "";
        statusMessage = new Status();
        try {
            matchList = matchService.selectMatchs(status);
        } catch (Exception e) {
            e.printStackTrace();
            statusMessage.setError("SystemError");
            return objectMapper.writeValueAsString(statusMessage);
        }

        matchs = objectMapper.writeValueAsString(matchList);

        return matchs;
    }

    /**
     * 管理员添加比赛
     * @param response
     * @param match
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/addMatch",produces = "text/json;charset=UTF-8")
    public @ResponseBody String addMatch(
            HttpServletResponse response, HttpServletRequest request, Match match, Match_inf match_inf,String date) throws JsonProcessingException, ParseException {
        response.setHeader("Access-Control-Allow-Origin","*");
        statusMessage = new Status();
        String matchName = null;
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startdate = df.parse(date);
        match_inf.setStarttime(startdate);
        //字符串转码
        try{
            matchName = new String(
                    match.getName().getBytes("ISO-8859-1"),"UTF-8");
        } catch (Exception e){
            e.printStackTrace();
        }

        //转码后的字符串重新设置回po
        match.setName(matchName);

        try {
            int status = matchService.insertSelective(match,match_inf);
            statusMessage.setSuccess(""+status);
        } catch (Exception e) {
            e.printStackTrace();
            statusMessage.setError("SystemError");
        }

        return objectMapper.writeValueAsString(statusMessage);
    }

    /**
     * 改变比赛状态(停止比赛)
     * @param response
     * @param match
     * @return status
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/stopMatch")
    public @ResponseBody String stopMatch(HttpServletResponse response,Match match) throws JsonProcessingException {
        response.setHeader("Access-Control-Allow-Origin","*");
        statusMessage = new Status();
        try {
            int status = matchService.updateByPrimaryKeySelective(match);
            statusMessage.setSuccess(""+status);
        } catch (Exception e) {
            e.printStackTrace();
            statusMessage.setError("SystemError");
        }

        return objectMapper.writeValueAsString(statusMessage);
    }

    /**
     * 提交选手成绩
     * @param response
     * @param matchGrade
     * @return grade
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/commitGrade",produces = "text/json;charset=UTF-8")
    public @ResponseBody String commitGrade(HttpServletResponse response, Match_grade matchGrade) throws JsonProcessingException {
        response.setHeader("Access-Control-Allow-Origin","*");
        statusMessage = new Status();
        try {
             matchService.insertSelective(matchGrade);
            statusMessage.setSuccess("Success");
        } catch (Exception e) {
            e.printStackTrace();
            statusMessage.setError("SystemError");

        }
        return objectMapper.writeValueAsString(statusMessage);
    }

    /**
     * 请求开始比赛,返回赛场信息
     * @param response
     * @return
     */
    @RequestMapping(value = "/requiedMatch",method = RequestMethod.POST)
    public @ResponseBody String requiedMatch(HttpServletResponse response,Match_signup signup) throws JsonProcessingException {
        response.setHeader("Access-Control-Allow-Origin","*");
        //查询是否报名,并将status置为true
        Match_inf matchInf = null;
        try {
            matchInf = matchService.requiredStart(signup);
        } catch (Exception e) {
            e.printStackTrace();
            return "false";
        }
        return objectMapper.writeValueAsString(matchInf);
    }


    /**
     * 比赛报名 提交选手id 场次id
     * @param response
     * @return
     */
    @RequestMapping(value = "/signUp",method = RequestMethod.POST)
    public @ResponseBody String signUp(HttpServletResponse response, Match_signup signup) throws JsonProcessingException {
        response.setHeader("Access-Control-Allow-Origin","*");
        statusMessage = new Status();
        try {
            matchService.signUpMatch(signup);
            statusMessage.setSuccess("SignUpSuccess");
        } catch (Exception e) {
            e.printStackTrace();
            statusMessage.setError("SignUpFaild");
        }

        return objectMapper.writeValueAsString(statusMessage);
    }

    /**
     * 海选获取名次
     * @param response
     * @param matchGrade
     * @return Grade
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/getGrade",method = RequestMethod.POST)
    public @ResponseBody String getGrade(HttpServletResponse response,Match_grade matchGrade) throws JsonProcessingException {
        response.setHeader("Access-Control-Allow-Origin","*");
        Grade grade;
        statusMessage = new Status();
        try {
            grade = matchService.getGrade(matchGrade);
        } catch (Exception e) {
            e.printStackTrace();
            statusMessage.setError("SystemError");
            return objectMapper.writeValueAsString(statusMessage);
        }

        return objectMapper.writeValueAsString(grade);
    }

    /**
     * 淘汰赛提交成绩
     * @param response
     * @param matchGrade
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/uploadGrade",method = RequestMethod.POST)
    public @ResponseBody String uploadGradeS(HttpServletResponse response,Match_grade matchGrade) throws JsonProcessingException {
        response.setHeader("Access-Control-Allow-Origin","*");
        statusMessage = new Status();
        try {
            matchService.uploadGradeS(matchGrade);
            statusMessage.setSuccess("Success");
        } catch (Exception e) {
            statusMessage.setError("SystemError");
            e.printStackTrace();
        }
        return objectMapper.writeValueAsString(statusMessage);
    }

    /**
     * 查询自己是否进入下一场比赛
     * @param response
     * @param matchGrade
     * @param degree
     * @return
     * @throws JsonProcessingException
     */
    @RequestMapping(value = "/getGradeRise",method = RequestMethod.POST)
    public @ResponseBody String getGradeRise(HttpServletResponse response,Match_grade matchGrade,Integer degree) throws JsonProcessingException {
        response.setHeader("Access-Control-Allow-Origin","*");
        statusMessage = new Status();
        try {
            Boolean flag = matchService.getGradeRise(matchGrade,degree);
            statusMessage.setSuccess(flag+"");
        } catch (Exception e) {
            statusMessage.setError("SystemError");
            e.printStackTrace();
        }

        return objectMapper.writeValueAsString(statusMessage);
    }

    /**
     * 启动对战模块端口
     * @param response
     */
    @RequestMapping(value = "/startFight")
    public void startFight(HttpServletResponse response) throws Exception {
        response.setHeader("Access-Control-Allow-Origin","*");
        SocketServlet socketServlet = new SocketServlet();
        statusMessage = new Status();
        PrintWriter writer = response.getWriter();
        try {
            statusMessage.setSuccess("Staring Fighting....");
            writer.write(objectMapper.writeValueAsString(statusMessage));
            socketServlet.init();
        } catch (Exception e) {
            statusMessage.setError("SystemError");
            writer.write(objectMapper.writeValueAsString(statusMessage));
            e.printStackTrace();
        }
    }
}
