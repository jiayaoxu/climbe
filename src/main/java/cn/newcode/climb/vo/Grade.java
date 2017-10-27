package cn.newcode.climb.vo;

/**
 * @Description: 返回排名信息vo
 * @author: shine
 * @CreateDate: 2017/10/25 19:22
 * @Version: 1.0
 */
public class Grade {
    private Integer ranking;

    private Boolean hasNext;


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
