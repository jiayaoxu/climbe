package cn.newcode.climb.service;

import cn.newcode.climb.po.Rock_record;

import java.util.List;

/**
 * @Description:
 * @author: shine
 * @CreateDate: 12:16 2018/3/29 0029
 * @Version: 1.0
 */
public interface RecordService {

    /**
     * 查询线路的最高纪录
     * @param wid
     * @return
     */
    Rock_record selectMaxRecord(Integer wid);

    /**
     * 更新高纪录
     * @param rock_record
     */
    void updateMaxRecord(Rock_record rock_record);

    /**
     * 查询今天所有线路的最高成绩
     * @return
     */
    List<Rock_record> selectMaxRecords();

}
