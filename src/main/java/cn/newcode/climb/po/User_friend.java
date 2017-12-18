package cn.newcode.climb.po;

import java.io.Serializable;

public class User_friend implements Serializable {
    private Integer id;

    private Integer uid;

    private Integer fuid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getFuid() {
        return fuid;
    }

    public void setFuid(Integer fuid) {
        this.fuid = fuid;
    }
}