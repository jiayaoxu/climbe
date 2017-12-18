package cn.newcode.climb.matchUtil;

import cn.newcode.climb.service.MatchService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: shine
 * \* Date: 2017/12/16 0016
 * \* Time: 16:00
 * \* Description:比赛结束后对奖牌进行计算
 * \
 */
public class medalCounter {

    /**
     * 存储奖牌用户的数组(9个   5 金 4 银 1铜)
     */
    private Integer [] medalUser = new Integer[9];

    @Autowired
    private MatchService matchService;


}