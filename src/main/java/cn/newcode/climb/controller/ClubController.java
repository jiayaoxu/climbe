package cn.newcode.climb.controller;

import cn.newcode.climb.page.pageBean;
import cn.newcode.climb.po.Club;
import cn.newcode.climb.po.Club_member;
import cn.newcode.climb.po.Club_notice;
import cn.newcode.climb.service.ClubService;
import cn.newcode.climb.vo.FindClubVo;
import cn.newcode.climb.vo.Rank_recordVo;
import cn.newcode.climb.vo.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: shine
 * \* Date: 17-12-21
 * \* Time: 下午7:46
 * \* Description:处理俱乐部
 * \
 */
@Controller
@RequestMapping("/club")
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
    public @ResponseBody Status createClub(Club club,Integer uid){
        try{
            String clubName = club.getName();
            clubService.createClub(club,uid);
        } catch (Exception e){
            e.printStackTrace();
            return new Status("","SystemError");
        }
        return new Status("Success","");
    }

    /**
     * 加入俱乐部
     * @param club_member
     * @return
     */
    @RequestMapping(value = "/joinClub")
    public @ResponseBody Status joinClub(Club_member club_member){
        try{
            clubService.joinClub(club_member);
        } catch (Exception e){
            e.printStackTrace();
            return new Status("","SystemError");
        }
        return new Status("Success","");
    }

    /**
     * 添加公告
     * @param notice
     * @return
     */
    @RequestMapping(value = "/addNotice",produces = "text/json;charset=UTF-8")
    public @ResponseBody Status addNotice(Club_notice notice){
        try{
            clubService.addNotice(notice);
        } catch (Exception e){
            e.printStackTrace();
            return new Status("","SystemError");
        }
        return new Status("Success","");
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
            clubService.getNotice(cid);
        } catch (Exception e){
            e.printStackTrace();
        }
        return notice;
    }

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
}