package cn.newcode.climb.po;

import java.io.Serializable;

/**
 *@author:shine
 *@Description:用户等级po类
 *@Date:15:51 2017/10/19
 */
public class User_class implements Serializable {
    private Integer id;

    private Integer uid;

    private Integer order;

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

    public Integer getOrder() {
        return order;
    }

    public void setOrder(Integer order) {
        this.order = order;
    }
}