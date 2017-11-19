package cn.newcode.climb.controller;

import cn.newcode.climb.po.Rock;
import cn.newcode.climb.po.RockHall;
import cn.newcode.climb.po.RockPoint;
import cn.newcode.climb.po.RockWall;
import cn.newcode.climb.service.RockService;
import cn.newcode.climb.vo.Status;
import cn.newcode.climb.vo.rock;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Description: 定线相关接口
 * @author: shine
 * @CreateDate: 2017/11/18 23:28
 * @Version: 1.0
 */
@Controller
@RequestMapping("/rock")
public class RockController {

    @Autowired
    private RockService rockService;

    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 列出所有岩馆
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value="/listHalls")
    public @ResponseBody String ListHalls(HttpServletResponse response) throws Exception{
        response.setHeader("Access-Control-Allow-Origin","*");
        List<RockHall> halls = null;
        try{
            halls = rockService.ListHalls();
        } catch (Exception e){
            e.printStackTrace();
            return objectMapper.writeValueAsString(new Status("","SystemError"));
        }

        return objectMapper.writeValueAsString(halls);
    }

    /**
     * 列出岩馆下的所有岩壁
     * @param response
     * @param hid
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/listWalls")
    public @ResponseBody String listWalls(HttpServletResponse response,Integer hid) throws Exception{
        response.setHeader("Access-Control-Allow-Origin","*");
        List<RockWall> walls = null;
        try{
            walls = rockService.ListWalls(hid);
        } catch (Exception e){
            e.printStackTrace();
            return objectMapper.writeValueAsString(new Status("","SystemError"));
        }

        return objectMapper.writeValueAsString(walls);
    }

    /**
     * 创建岩壁
     * @param response
     * @param rockWall
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/createWall")
    public @ResponseBody String createWall(HttpServletResponse response,RockWall rockWall) throws Exception{
        response.setHeader("Access-Control-Allow-Origin","*");
        try{
            rockService.createWall(rockWall);
        } catch (Exception e){
            e.printStackTrace();
            return objectMapper.writeValueAsString(new Status("","SystemError"));
        }

        return objectMapper.writeValueAsString(new Status("Success",""));
    }

    /**
     * 添加岩点
     * @param response
     * @param points
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/addPoint")
    public @ResponseBody String addPoint(HttpServletResponse response, String points) throws Exception{
        response.setHeader("Access-Control-Allow-Origin","*");
        rock r = objectMapper.readValue(points,rock.class);
          try{
              List<RockPoint> rockPoints = r.getYanshi();
              rockService.addPoint(rockPoints);
        } catch (Exception e){
            e.printStackTrace();
            return objectMapper.writeValueAsString(new Status("","SystemError"));
        }
        return objectMapper.writeValueAsString(new Status("Success",""));
    }

    /**
     * 通过岩壁查询所有岩点
     * @param response
     * @param wid
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/listPoints")
    public @ResponseBody String listPoints(HttpServletResponse response,Integer wid) throws Exception{
        response.setHeader("Access-Control-Allow-Origin","*");
        List<RockPoint> points = null;
        try{
            points = rockService.selectPoint(wid);
        } catch (Exception e){
            e.printStackTrace();
            return objectMapper.writeValueAsString(new Status("","SystemError"));
        }

        return objectMapper.writeValueAsString(points);
    }

    /**
     * 查询所有岩石
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/listRocks")
    public @ResponseBody String listRocks(HttpServletResponse response) throws Exception{
        response.setHeader("Access-Control-Allow-Origin","*");
        List<Rock> rocks = null;
        try{
            rocks = rockService.selectRocks();
        } catch (Exception e){
            e.printStackTrace();
            return objectMapper.writeValueAsString(new Status("","SystemError"));
        }

        return objectMapper.writeValueAsString(rocks);
    }

}