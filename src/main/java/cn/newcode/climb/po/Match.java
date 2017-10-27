package cn.newcode.climb.po;

import java.io.Serializable;

/**
 *@author:shine
 *@Description:成绩po
 *@Date:6:10 2017/10/18
 */
public class Match implements Serializable {
    private Integer id;

    private String name;

    private Boolean status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}