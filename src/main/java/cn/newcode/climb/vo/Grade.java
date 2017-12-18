package cn.newcode.climb.vo;

/**
 * @Description: 返回排名信息vo
 * @author: shine
 * @CreateDate: 2017/10/25 19:22
 * @Version: 1.0
 */
public class Grade {
    //排名
    private Integer ranking;
    //是否晋级
    private Boolean hasNext;
    //总晋级人数
    private Integer totalIn;
    //对手id
    private Integer equal;

    public Integer getEqual() {
        return equal;
    }

    public void setEqual(Integer equal) {
        this.equal = equal;
    }

    public Integer getTotalIn() {
        return totalIn;
    }

    public void setTotalIn(Integer totalIn) {
        this.totalIn = totalIn;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public Boolean getHasNext() {
        return hasNext;
    }

    public void setHasNext(Boolean hasNext) {
        this.hasNext = hasNext;
    }


}
