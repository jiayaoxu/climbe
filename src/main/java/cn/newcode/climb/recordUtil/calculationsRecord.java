package cn.newcode.climb.recordUtil;

import cn.newcode.climb.DataBaseUtil.DataBaseUtil;
import cn.newcode.climb.LogUtil.MLogger;
import cn.newcode.climb.po.Rock_record;
import cn.newcode.climb.service.RankService;
import cn.newcode.climb.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
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
        RecordService recordService = DataBaseUtil.dataBaseUtil.recordService;
        Rock_record rock_record = null;
        String type = record.getType();
        try{
            rock_record = recordService.selectMaxRecord(wid,record.getType());
            //对于速度,如果成绩越小越大
            if(type.equals("s")){
                //首先与数据库中本线路最高纪录比较,如果高于最该纪录就更新最高纪录
                if(record.getMaxRecord()<rock_record.getMax()){
                    rock_record.setMax((double) record.getMaxRecord());
                    //然后与今天的最高成绩进行比较,如果高于今天的最高成绩就更新今天的最高成绩
                    if(record.getMaxRecord()<rock_record.getToday()){
                        rock_record.setToday((double) record.getMaxRecord());
                        rock_record.setPerson(record.getUid());
                        rock_record.setType(record.getType());
                    }
                    recordService.updateMaxRecord(rock_record);
                }
                //对于难度,成绩越大越大
            }else if(type.equals("n")){
                //首先与数据库中本线路最高纪录比较,如果高于最该纪录就更新最高纪录
                if(record.getMaxRecord()>rock_record.getMax()){
                    rock_record.setMax((double) record.getMaxRecord());
                    //然后与今天的最高成绩进行比较,如果高于今天的最高成绩就更新今天的最高成绩
                    if(record.getMaxRecord()>rock_record.getToday()){
                        rock_record.setToday((double) record.getMaxRecord());
                        rock_record.setPerson(record.getUid());
                        rock_record.setType(record.getType());
                    }
                    recordService.updateMaxRecord(rock_record);
                }
            }
        } catch (Exception e){
            MLogger.info("没有本条线路的成绩信息");
            MLogger.error(e);
            rock_record = new Rock_record();
            rock_record.setWid(wid);
            rock_record.setType(type);
            rock_record.setPerson(record.getUid());
            rock_record.setToday((double)record.getMaxRecord());
            rock_record.setMax((double)record.getMaxRecord());
            recordService.addRecord(rock_record);
        }



//        Record r = recordMap.get(wid);
//        if(r==null){
//            recordMap.put(wid,record);
//        }else{
//            r.equals(record);
//        }

    }


    /**
     * 获取最高成绩的玩家uid,获取完后成绩归零
     * @return
     */
    public void getMaxRecordUser() {
        try{
            RecordService recordService = DataBaseUtil.dataBaseUtil.recordService;
            RankService rankService = DataBaseUtil.dataBaseUtil.rankService;
            List<Rock_record> records = recordService.selectMaxRecords();
            for(Rock_record r : records){
                rankService.addIntegral(r.getPerson());
            }
        } catch (Exception e){
            MLogger.warn("add integral error");
        }
        /*//遍历每条线路,给分数最高的玩家增加积分
        for(Map.Entry<Integer,Record> it : recordMap.entrySet()){
            Integer uid = it.getValue().getUid();
            //调用service,给相应玩家增加积分
            if(uid!=null)
                rankService.addIntegral(uid);
            else
                MLogger.warn("-----------uid is null when add integral-----------");
        }
        //遍历完之后清空今天的Map
        recordMap.clear();*/
    }
}