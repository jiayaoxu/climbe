package cn.newcode.climb.controller;

import cn.newcode.climb.recordUtil.Record;
import cn.newcode.climb.recordUtil.calculationsRecord;
import cn.newcode.climb.vo.Status;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: shine
 * \* Date: 2017/12/15 0015
 * \* Time: 17:42
 * \* Description:对战模块controllrt
 * \
 */
@RequestMapping("/fight")
@Controller
public class FightController {

    /**
     * 对战结束后提交成绩
     * @param wid
     * @param record
     * @return
     */
    @RequestMapping("/submitGrade")
    public @ResponseBody Status submitGrade(Integer wid, Record record){
        try{
            calculationsRecord recordManager = calculationsRecord.getInstance();
            recordManager.compareMaxRecord(wid,record);
        } catch (Exception e){
            return new Status(null,"SystemError");
        }
        return new Status("sucess",null);
    }
}