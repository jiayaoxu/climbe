package cn.newcode.climb.Fight.vo;

import cn.newcode.climb.po.User;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: shine
 * \* Date: 2018/2/28 0028
 * \* Time: 19:43
 * \* Description:
 * \    继承user的po
 */
public class UserCustom extends User {

    /**
     * 岩壁id
     */

    private Integer bid;

    private Integer wid;

    public Integer getWid() {
        return wid;
    }

    public void setWid(Integer wid) {
        this.wid = wid;
    }

    public Integer getBid() {
        return bid;
    }

    public void setBid(Integer bid) {
        this.bid = bid;
    }


}