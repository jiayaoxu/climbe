package cn.newcode.climb.timerUtil;

import cn.newcode.climb.LogUtil.MLogger;
import cn.newcode.climb.recordUtil.calculationsRecord;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: shine
 * \* Date: 2017/12/15 0015
 * \* Time: 16:51
 * \* Description:积分定时器
 * \
 */
public class IntegradTimer {

    public void executed(){
        //每天24时执行,给最牛逼的玩家加积分
        calculationsRecord.getInstance().getMaxRecordUser();
        MLogger.info("-----------add Integraling------------");
    }
}