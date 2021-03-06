package cn.newcode.climb.service;

import cn.newcode.climb.po.*;
import cn.newcode.climb.vo.routerClass;

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

    /**
     * 列出所有的系统岩壁
     * @return
     * @throws Exception
     */
    List<RockWallSys> listAllWalls() throws Exception;

    /**
     * 列出岩馆下的所有所有岩线
     * @param hid
     * @return
     * @throws Exception
     */
    List<routerClass> listWallsInHall(Integer hid) throws Exception;

    /**
     * 列出岩馆下的所有所有岩线(待审核)
     * @param hid
     * @return
     * @throws Exception
     */
    List<routerClass> listWallsInHallNoAccess(Integer hid) throws Exception;

    /**
     * 将线路添加到响应类库
     * @param rock_wall_default
     * @throws Exception
     */
    Boolean addClass(Rock_wall_default rock_wall_default) throws Exception;

    /**
     * 查询指定类别下的线路
     * @param rock_wall_default
     * @return
     * @throws Exception
     */
    List<routerClass> listWallsInClass(Rock_wall_default rock_wall_default) throws Exception;

    /**
     * 移除响应类别下的线路
     * @param id
     */
    void removeFromClass(Integer id);

    /**
     * 审核带批线路
     * @param wid
     * @param access
     */
    void accessRockWall(Integer wid,Integer access);

    /**
     * 查询我的线路总库
     * @param hid
     * @param uid
     * @return
     */
    List<routerClass> selectMyRockWall(Integer hid,Integer uid);

    /**
     * 删除线路
     * @param id
     */
    void deleteRockWall(Integer id);

    /**
     * 通过岩馆和岩壁查询岩线
     * @param rsid
     * @param hid
     * @return
     */
    List<routerClass> selectMyRockWallByHidAndWallSys(Integer rsid,Integer hid);
}
