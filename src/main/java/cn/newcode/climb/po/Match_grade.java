package cn.newcode.climb.po;

import java.io.Serializable;

public class Match_grade implements Serializable {
    private Integer id;

    private Integer mid;

    private Integer uid;

    private Integer grade;

    private Integer sgrade;

    private Integer tgrade;

    private Integer fgrade;

    private Integer figrade;

    public Integer getFigrade() {
        return figrade;
    }

    public void setFigrade(Integer figrade) {
        this.figrade = figrade;
    }

    public Integer getSgrade() {
        return sgrade;
    }

    public void setSgrade(Integer sgrade) {
        this.sgrade = sgrade;
    }

    public Integer getTgrade() {
        return tgrade;
    }

    public void setTgrade(Integer tgrade) {
        this.tgrade = tgrade;
    }

    public Integer getFgrade() {
        return fgrade;
    }

    public void setFgrade(Integer fgrade) {
        this.fgrade = fgrade;
    }

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

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }
}