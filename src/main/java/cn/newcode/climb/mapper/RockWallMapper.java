package cn.newcode.climb.mapper;

import cn.newcode.climb.po.RockWall;
import cn.newcode.climb.vo.routerClass;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RockWallMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RockWall record);

    /**
     * 添加岩壁
     * @param record
     * @return
     */
    int insertSelective(RockWall record);

    RockWall selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RockWall record);

    int updateByPrimaryKey(RockWall record);

    /**
     * 通过岩馆id查询所有岩线
     * @param hid
     * @return
     */
    List<RockWall> selectWalls(Integer hid);

    /**
     * 通过岩线名字查询id
     * @param name
     * @return
     */
    Integer selectByName(String name);

    /**
     * 通过岩馆查询馆下所有岩线(已经通过审核)
     * @param hid
     * @return
     */
    List<routerClass> selectByHid(Integer hid);

    /**
     * 通过岩馆查询馆下所有岩线(待审核)
     * @param hid
     * @return
     */
    List<routerClass> selectByHidNoAccess(Integer hid);

    /**
     * 查询自己的线路
     * @param hid
     * @param uid
     * @return
     */
    List<routerClass> selectMyRockWall(@Param("hid")Integer hid,@Param("uid")Integer uid);

    /**
     * 通过岩壁和岩馆查询岩线
     * @param hid
     * @param rsid
     * @return
     */
    List<routerClass> selectMyRockWallByHidAndWallSys(@Param("hid")Integer hid,@Param("rsid") Integer rsid);
}