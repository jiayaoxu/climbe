package cn.newcode.climb.service.impl;

import cn.newcode.climb.mapper.*;
import cn.newcode.climb.po.*;
import cn.newcode.climb.service.RockService;
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
        return rockWallMapper.insertSelective(wall);
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
}
