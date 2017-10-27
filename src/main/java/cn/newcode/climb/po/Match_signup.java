package cn.newcode.climb.po;

import java.io.Serializable;

public class Match_signup implements Serializable {
    private Integer id;

    private Integer mid;

    private Integer uid;

    private Boolean ismatchd;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Boolean getIsmatchd() {
        return ismatchd;
    }

    public void setIsmatchd(Boolean ismatchd) {
        this.ismatchd = ismatchd;
    }
}