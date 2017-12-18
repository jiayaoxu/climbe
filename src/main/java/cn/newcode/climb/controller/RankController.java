package cn.newcode.climb.controller;

import cn.newcode.climb.page.pageBean;
import cn.newcode.climb.po.Rank_wall;
import cn.newcode.climb.service.RankService;
import cn.newcode.climb.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.SQLException;
import java.util.List;

@Controller
@RequestMapping("rank")
public class RankController {

    @Autowired
    private RankService rankService;

    private Status status;

    /*@RequestMapping("/addPoint")
    public @ResponseBody Status addPoint(Integer teacher){
        try{
            rankService.addPoint(teacher);
        } catch (Exception e){
            return new Status(null,"SystemError");
        }
        return new Status("Success",null);
    }*/

    @RequestMapping("/seletcRankTeacher")
    public @ResponseBody List<Rank_teacherVo> seletcRankTeacher(Integer pageNow){
        int now = 1;
        if(pageNow != null){
            now = pageNow;
        }

        Integer t = 0;

        Integer total = (t = rankService.selectCount())!=0?t:0;
        //total = rankService.selectCount();

        pageBean page = new pageBean(now,total);

        List<Rank_teacherVo> rankList = null;
        try{
            rankList = rankService.rankTeacherList(page.getStartPos(),page.getPageSize());
        } catch (Exception e){
            e.printStackTrace();
        }
        return rankList;
    }

    /**
     * 为线路送鲜花接口
     * @param rank_wall
     * @return
     */
    @RequestMapping("/sendFlover")
    public @ResponseBody Status sendFlover(Rank_wall rank_wall){
        Status status = new Status();
        try{
            if(rankService.addFlover(rank_wall))
                status.setSuccess("Success");
            else
                status.setError("AlreadySend");
        } catch (Exception e){
            status.setError("SystemError");
            e.printStackTrace();
        }
        return status;
    }

    /**
     * 分页查询名定榜
     * @param pageNow
     * @return
     */
    @RequestMapping("/selectRankWall")
    public @ResponseBody List<Rank_wallVo> selectRankWall(Integer pageNow){
        List<Rank_wallVo> walls = null;
        int now = 1;

        if(pageNow!=null){
            now = pageNow;
        }

        Integer t = 0;

        Integer total = (t = rankService.selectCountWall().size())!=0?t:0;
        pageBean page = new pageBean(now,total);
        try{
            walls = rankService.rankWallList(page.getStartPos(),page.getPageSize());
        } catch (Exception e){
            e.printStackTrace();
        }

        return walls;
    }

    /**
     * 查询攀龄榜
     * @param pageNow
     * @return
     */
    @RequestMapping("/selectRankAge")
    public @ResponseBody List<Rank_ageVo> selectRankAge(Integer pageNow){
        List<Rank_ageVo> ages = null;
        int now = 1;

        if(pageNow!=null){
            now = pageNow;
        }

        Integer t = 0;

        Integer total = (t = rankService.selectAgeCount())!=0?t:0;
        pageBean page = new pageBean(now,total);
        try{
            ages = rankService.rankAgeList(page.getStartPos(),page.getPageSize());
        } catch (Exception e){
            e.printStackTrace();
        }

        return ages;
    }

    /**
     * 查询积分榜
     * @param pageNow
     * @return
     */
    @RequestMapping("/selectRecordAge")
    public @ResponseBody List<Rank_recordVo> selectRecordAge(Integer pageNow){
        List<Rank_recordVo> records = null;
        int now = 1;

        if(pageNow!=null){
            now = pageNow;
        }

        Integer t = 0;

        Integer total = (t = rankService.selectRecordCount())!=0?t:0;
        pageBean page = new pageBean(now,total);
        try{
            records = rankService.rankRecordList(page.getStartPos(),page.getPageSize());
        } catch (Exception e){
            e.printStackTrace();
        }

        return records;
    }

}
