package cn.newcode.climb.service;

import cn.newcode.climb.po.Rock;
import cn.newcode.climb.po.RockHall;
import cn.newcode.climb.po.RockPoint;
import cn.newcode.climb.po.RockWall;

import java.util.List;

/**
 * @Description: 定线service
 * @author: shine
 * @CreateDate: 2017/11/18 22:58
 * @Version: 1.0
 */
public interface RockService {

    /**
     * 列出所有的岩馆
     */
    List<RockHall> ListHalls() throws Exception;

    /**
     * 通过岩馆id查找岩馆下所有岩壁
     * @param hid
     * @return
     * @throws Exception
     */
    List<RockWall> ListWalls(Integer hid) throws Exception;

    /**
     * 在指定岩馆上创建岩壁,返回岩壁id
     * @param wall
     * @return
     * @throws Exception
     */
    Integer createWall(RockWall wall) throws Exception;

    /**
     * 在指定岩壁上添加岩点
     * @param points
     * @return
     * @throws Exception
     */
    Integer addPoint(List<RockPoint> points) throws Exception;

    /**
     * 通过指定岩壁查询岩点
     * @param wid
     * @return
     * @throws Exception
     */
    List<RockPoint> selectPoint(Integer wid) throws Exception;

    /**
     * 列出所有岩石
     * @return
     * @throws Exception
     */
    List<Rock> selectRocks() throws Exception;

    /**
     * 通过id查询岩壁的具体信息
     * @param wid
     * @return
     * @throws Exception
     */
    RockWall seletcRockWallById(Integer wid) throws Exception;
}
