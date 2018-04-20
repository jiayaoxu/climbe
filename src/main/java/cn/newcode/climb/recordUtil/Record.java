package cn.newcode.climb.recordUtil;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: shine
 * \* Date: 2017/12/15 0015
 * \* Time: 17:28
 * \* Description:存储对战成绩的实体
 */
public class Record {
    private float MaxRecord = 0;
    private Integer uid;
    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public float getMaxRecord() {
        return MaxRecord;
    }

    public void setMaxRecord(float maxRecord) {
        MaxRecord = maxRecord;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    /**
     * 将record里的记录与自己的记录进行对比，如果大于，就更新记录和uid
     * @param record
     */
    public void equals(Record record){
        float f = record.getMaxRecord();
        if(f<this.MaxRecord){
            this.MaxRecord = f;
            this.uid = record.getUid();
        }
    }
}