package cn.newcode.climb.po;

public class Rock_record {
    private Integer id;

    private Integer wid;

    private Double max = 0.0;

    private Double today = 0.0;

    private Integer person;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWid() {
        return wid;
    }

    public void setWid(Integer wid) {
        this.wid = wid;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    public Double getToday() {
        return today;
    }

    public void setToday(Double today) {
        this.today = today;
    }

    public Integer getPerson() {
        return person;
    }

    public void setPerson(Integer person) {
        this.person = person;
    }
}