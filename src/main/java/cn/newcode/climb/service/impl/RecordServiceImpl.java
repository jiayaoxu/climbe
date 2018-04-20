package cn.newcode.climb.service.impl;

import cn.newcode.climb.mapper.Rock_recordMapper;
import cn.newcode.climb.po.Rock_record;
import cn.newcode.climb.service.RecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: shine
 * \* Date: 2018/3/29 0029
 * \* Time: 12:21
 * \* Description:
 * \    纪录操作
 */
@Service("recordService")
public class RecordServiceImpl implements RecordService {

    @Autowired
    private Rock_recordMapper rock_recordMapper;

    @Override
    public Rock_record selectMaxRecord(Integer wid,String type) {
        return rock_recordMapper.selectByPrimaryKey(wid,type);
    }

    @Override
    public void updateMaxRecord(Rock_record rock_record) {
        rock_recordMapper.updateByPrimaryKeySelective(rock_record);
    }

    @Override
    public List<Rock_record> selectMaxRecords() {
        return rock_recordMapper.selectMaxRecords();
    }

    @Override
    public void addRecord(Rock_record rock_record) {
        rock_recordMapper.insertSelective(rock_record);
    }
}