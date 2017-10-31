package cn.newcode.climb.po;

import java.io.Serializable;

/**
 * @Description: 向选手返回成绩po
 * @author: shine
 * @CreateDate: 2017/10/18 6:29
 * @Version: 1.0
 */
public class grade implements Serializable {
    private Integer uid;
    private Integer rank;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getRank() {
        return rank;
    }

    public void setRank(Integer rank) {
        this.rank = rank;
    }
}
