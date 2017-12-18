package cn.newcode.climb.recordUtil;

import cn.newcode.climb.LogUtil.MLogger;
import cn.newcode.climb.service.RankService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: shine
 * \* Date: 2017/12/15 0015
 * \* Time: 14:16
 * \* Description:用来计算积分的类,单例
 * \
 */
public class calculationsRecord {

    @Autowired
    private RankService rankService;

    Map<Integer,Record> recordMap = new HashMap<Integer, Record>(16);

    private static class Instance{
        private static final calculationsRecord recordManage = new calculationsRecord();
    }

    public static calculationsRecord getInstance(){
        return Instance.recordManage;
    }

    /**
     * 与最高成绩对比，如果高于最高成绩，就记录为最高成绩
     * @param record
     */
    public void compareMaxRecord(Integer wid,Record record){
        Record r = recordMap.get(wid);
        if(r==null){
            recordMap.put(wid,record);
        }else{
            r.equals(record);
        }
    }


    /**
     * 获取最高成绩的玩家uid,获取完后成绩归零
     * @return
     */
    public void getMaxRecordUser() {
        //遍历每条线路,给分数最高的玩家增加积分
        for(Map.Entry<Integer,Record> it : recordMap.entrySet()){
            Integer uid = it.getValue().getUid();
            //调用service,给相应玩家增加积分
            if(uid!=null)
                rankService.addIntegral(uid);
            else
                MLogger.warn("-----------uid is null when add integral-----------");
        }
        //遍历完之后清空今天的Map
        recordMap.clear();
    }
}