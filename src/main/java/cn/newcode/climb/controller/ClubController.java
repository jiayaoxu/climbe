package cn.newcode.climb.controller;

import cn.newcode.climb.LoginCheck.net.UserRegister;
import cn.newcode.climb.page.pageBean;
import cn.newcode.climb.po.Club;
import cn.newcode.climb.po.Club_member;
import cn.newcode.climb.po.Club_notice;
import cn.newcode.climb.service.ClubService;
import cn.newcode.climb.vo.ClubMemberVo;
import cn.newcode.climb.vo.FindClubVo;
import cn.newcode.climb.vo.OnlionMember;
import cn.newcode.climb.vo.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;


import java.sql.SQLException;
import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: shine
 * \* Date: 17-12-21
 * \* Time: 下午7:46
 * \* Description:处理俱乐部
 * \
 */
@RequestMapping("/club")
@Controller
public class ClubController {

    @Autowired
    private ClubService clubService;

    /**
     * 创建俱乐部
     * @param club
     * @param uid
     * @return
     */
    @RequestMapping(value = "/createClub",produces = "text/json;charset=UTF-8")
    public @ResponseBody String createClub(Club club, Integer uid) throws Exception{
        Status status = null;
        ObjectMapper obj = new ObjectMapper();
        try{

            String clubName = club.getName();
            clubService.createClub(club,uid);
            status = new Status("Success","");
        } catch (Exception e){
            e.printStackTrace();
            return obj.writeValueAsString(new Status("","SystemError"));
        }
        return obj.writeValueAsString(status);
    }

    /**
     * 加入俱乐部
     * @param club_member
     * @return
     */
    @RequestMapping(value = "/joinClub",produces = "text/json;charset=UTF-8")
    public @ResponseBody String joinClub(Club_member club_member) throws Exception{
        ObjectMapper obj = new ObjectMapper();
        try{
            clubService.joinClub(club_member);
        } catch (Exception e){
            e.printStackTrace();
            return obj.writeValueAsString(new Status("","SystemError"));
        }
        return obj.writeValueAsString(new Status("Success",""));
    }

    /**
     * 添加公告
     * @param notice
     * @return
     */
    @RequestMapping(value = "/addNotice",produces = "text/json;charset=UTF-8")
    public @ResponseBody String addNotice(Club_notice notice) throws Exception {
        ObjectMapper obj = new ObjectMapper();
        try{
            clubService.addNotice(notice);
        } catch (Exception e){
            e.printStackTrace();
            return obj.writeValueAsString(new Status("","SystemError"));
        }
        return obj.writeValueAsString(new Status("Success",""));
    }

    /**
     * 获取俱乐部公告
     * @param cid
     * @return
     */
    @RequestMapping(value = "/getNotice")
    public @ResponseBody String getNotice(Integer cid){
        String notice = null;
        try{
            notice = clubService.getNotice(cid);
        } catch (Exception e){
            e.printStackTrace();
        }
        return notice;
    }

    /**
     * 分页查询俱乐部
     * @param pageNow
     * @param clubName
     * @return
     */
    @RequestMapping(value = "/listClub")
    public @ResponseBody List<FindClubVo> listClub(Integer pageNow,String clubName){
        List<FindClubVo> clubs = null;
        int now = 1;

        if(pageNow!=null){
            now = pageNow;
        }

        Integer t = 0;

        Integer total = (t = clubService.count(clubName))!=0?t:0;
        pageBean page = new pageBean(now,total);
        try{
            clubs = clubService.listAllClub(page.getStartPos(),page.getPageSize(),clubName);
        } catch (Exception e){
            e.printStackTrace();
        }

        return clubs;
    }

    /**
     * 分页查询俱乐部成员
     * @param pageNow
     * @param uid
     * @param name
     * @return
     */
    @RequestMapping("/selectMember")
    public @ResponseBody List<ClubMemberVo> selectMember(Integer pageNow,Integer uid,String name){
        List<ClubMemberVo> members = null;
        int now = 1;

        if(pageNow!=null){
            now = pageNow;
        }

        Integer t = 0;

        Integer total = (t = clubService.memberCount(uid, name))!=0?t:0;
        pageBean page = new pageBean(now,total);
        try{
            members = clubService.listMamber(page.getStartPos(),page.getPageSize(),uid,name);
        } catch (Exception e){
            e.printStackTrace();
        }

        return members;
    }

    /**
     * 退出俱乐部
     * @param uid
     * @return
     */
    @RequestMapping(value = "/quiteClub")
    public @ResponseBody Status quiteClub(Integer uid){
        try{
            clubService.quiteClub(uid);
        }catch (Exception e){
            e.printStackTrace();
            return new Status("","SystemError");
        }
        return new Status("Success","");
    }

    /**
     * 查询俱乐部成员在线人数
     * @param cid
     * @param uid
     * @return
     */
    @RequestMapping(value = "/selectOnlionMember")
    public @ResponseBody OnlionMember selectOnlionMember(Integer cid,Integer uid){
        OnlionMember onlionMember = new OnlionMember();
        try {
            onlionMember.setTotal(clubService.memberCount(uid,null));
//            onlionMember.setOnlion(UserRegister.getInstance().getClub().get(cid));
            OnlionMember onlionMember1 = clubService.selectOnlion(cid);
            onlionMember.setOnlion(onlionMember1.getOnlion());
        } catch (Exception e){
            e.printStackTrace();
        }
        return onlionMember;
    }

    /**
     * 通过uid查询cid
     * @param uid
     * @return
     */
    @RequestMapping(value = "/selectCidByUid")
    public @ResponseBody Integer selectCidByUid(Integer uid){
        Integer cid = null;
        try{
            cid = clubService.selectCidByUid(uid);
        }catch (Exception e){
            e.printStackTrace();
        }
        return cid;
    }
}