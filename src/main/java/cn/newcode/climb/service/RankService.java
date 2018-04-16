package cn.newcode.climb.service;

import cn.newcode.climb.po.Rank_medal;
import cn.newcode.climb.po.Rank_wall;
import cn.newcode.climb.po.User;
import cn.newcode.climb.vo.*;

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

    /**
     * 奖牌榜
     */

    /**
     * 分页查询奖牌榜
     * @param startPos
     * @param pageSize
     * @param rank_medal
     * @return
     */
    List<Rank_medalVo> rankMedalList(Integer startPos, Integer pageSize, Rank_medal rank_medal);

    /**
     * 查询某奖牌的总人数
     * @param medal
     * @return
     */
    Integer selectMedalCount(String medal);

    /**
     * 查询自己的排名
     */

    /**
     * 查询自己的奖牌排名
     * @param rank_medal
     * @return
     */
    Integer selectMyMedalRank(Rank_medal rank_medal);

    /**
     * 查询自己的攀龄榜
     * @param uid
     * @return
     */
    Integer selectMyAgeRank(Integer uid);

    /**
     * 查询自己的积分榜
     * @param uid
     * @return
     */
    Integer selectMyRecordRank(Integer uid);

    /**
     * 查询自己的名师榜
     * @param uid
     * @return
     */
    Integer selectMyTeacherRank(Integer uid);

    /**
     * 查询点赞过的线路
     * @param uid
     * @return
     */
    List<Rank_wall> selectPoints(Integer uid);

    /**
     * 添加奖牌
     * @param rank_medal
     */
    void addMedal(Rank_medal rank_medal);
}
