package cn.newcode.climb.service;

import cn.newcode.climb.po.Rank_wall;
import cn.newcode.climb.po.User;
import cn.newcode.climb.vo.Rank_ageVo;
import cn.newcode.climb.vo.Rank_recordVo;
import cn.newcode.climb.vo.Rank_teacherVo;
import cn.newcode.climb.vo.Rank_wallVo;

import java.util.List;

public interface RankService {
    /**
     * 名师榜
     */

    /**
     * 点赞
     * @param teacher
     */
    public void addPoint(Integer teacher);

    /**
     * 分页查询排名
     * @param startPos
     * @param pageSize
     * @return
     */
    public List<Rank_teacherVo> rankTeacherList(Integer startPos,Integer pageSize);

    /**
     * 查询名师榜总数
     * @return
     */
    public Integer selectCount();

    /**
     * 名定榜
     */

    /**
     * 用户给线路送鲜花/点赞
     * @param rank_wall
     */
    public Boolean addFlover(Rank_wall rank_wall);

    /**
     * 查询名定榜总数
     * @return
     */
    List<Integer> selectCountWall();

    /**
     * 分页查询名定榜
     * @param startPos
     * @param pageSize
     * @return
     */
    List<Rank_wallVo> rankWallList(Integer startPos,Integer pageSize);

    /**
     * 攀龄榜
     */

    /**
     * 查询攀龄榜
     * @param startPos
     * @param pageSize
     * @return
     */
    List<Rank_ageVo> rankAgeList(Integer startPos,Integer pageSize);

    /**
     * 查询人数
     * @return
     */
    Integer selectAgeCount();

    /**
     * 积分榜
     */

    /**
     * 给玩家增加积分
     * @param uid
     */
    void addIntegral(Integer uid);

    /**
     * 查询积分排行榜
     * @param startPos
     * @param pageSize
     * @return
     */
    List<Rank_recordVo> rankRecordList(Integer startPos,Integer pageSize);

    /**
     * 查询积分表总数
     * @return
     */
    Integer selectRecordCount();
}
