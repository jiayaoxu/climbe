package cn.newcode.climb.service.impl;

import cn.newcode.climb.mapper.*;
import cn.newcode.climb.po.*;
import cn.newcode.climb.service.RockService;
import cn.newcode.climb.vo.routerClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Description: 定线Service
 * @author: shine
 * @CreateDate: 2017/11/18 22:58
 * @Version: 1.0
 */
@Service("rockService")
public class RockServiceImpl implements RockService {

    @Autowired
    private RockHallMapper rockHallMapper;

    @Autowired
    private RockWallMapper rockWallMapper;

    @Autowired
    private RockPointMapper rockPointMapper;

    @Autowired
    private RockMapper rockMapper;

    @Autowired
    private RockWallSysMapper rockWallSysMapper;

    @Autowired
    private Rock_wall_defaultMapper rock_wall_defaultMapper;

    @Override
    public List<RockHall> ListHalls() throws Exception {
        return rockHallMapper.selectHalls();
    }

    @Override
    public List<RockWall> ListWalls(Integer rsid) throws Exception {
        return rockWallMapper.selectWalls(rsid);
    }

    @Override
    public Integer createWall(RockWall wall) throws Exception {
        rockWallMapper.insertSelective(wall);
        return rockWallMapper.selectByName(wall.getName());
    }

    @Override
    public Integer addPoint(List<RockPoint> points) throws Exception {
        rockPointMapper.deletePoints(points.get(0).getWid());
        //在添加之前删除以前的所有岩点
        for(RockPoint p : points){
            rockPointMapper.insertSelective(p);
        }
        return 1;
    }

    @Override
    public List<RockPoint> selectPoint(Integer wid) throws Exception {
        return rockPointMapper.selectPoints(wid);
    }

    @Override
    public List<Rock> selectRocks() throws Exception {
        return rockMapper.selectRocks();
    }

    @Override
    public RockWall seletcRockWallById(Integer wid) throws Exception {
        return rockWallMapper.selectByPrimaryKey(wid);
    }

    @Override
    public List<RockWallSys> listAllWalls() throws Exception {
        return rockWallSysMapper.selectAllWalls();
    }

    @Override
    public List<routerClass> listWallsInHall(Integer hid) throws Exception {
        return rockWallMapper.selectByHid(hid);
    }

    @Override
    public List<routerClass> listWallsInHallNoAccess(Integer hid) throws Exception {
        return rockWallMapper.selectByHidNoAccess(hid);
    }

    @Override
    public Boolean addClass(Rock_wall_default rock_wall_default) throws Exception {
        //判断一下库中是否有重复的
        Boolean flag = false;

        if(rock_wall_default.getCl()==1&&rock_wall_default.getType()!=null){
            //比赛限制只有一条
            Integer count = rock_wall_defaultMapper.selectCountContent(rock_wall_default);
            if(count>=1){
                rock_wall_defaultMapper.deleteByCl(rock_wall_default);
            }
        }

        Integer id = rock_wall_defaultMapper.selectRe(rock_wall_default);
        if(id==null){
            rock_wall_defaultMapper.insertSelective(rock_wall_default);
            flag = true;
        }

        return flag;
    }

    @Override
    public List<routerClass> listWallsInClass(Rock_wall_default rock_wall_default) throws Exception {
        return rock_wall_defaultMapper.selectByClass(rock_wall_default);
    }

    @Override
    public void removeFromClass(Integer id) {
        rock_wall_defaultMapper.deleteByPrimaryKey(id);
    }

    @Override
    public void accessRockWall(Integer wid, Integer access) {
        RockWall rockWall = new RockWall();
        rockWall.setId(wid);
        rockWall.setAccess(access);
        rockWallMapper.updateByPrimaryKeySelective(rockWall);
    }

    @Override
    public List<routerClass> selectMyRockWall(Integer hid, Integer uid) {
        return rockWallMapper.selectMyRockWall(hid, uid);
    }

    @Override
    public void deleteRockWall(Integer id) {
        rockWallMapper.deleteByPrimaryKey(id);
    }

    @Override
    public List<routerClass> selectMyRockWallByHidAndWallSys(Integer rsid, Integer hid) {
        return rockWallMapper.selectMyRockWallByHidAndWallSys(hid,rsid);
    }
}
