package cn.newcode.climb.po;

public class User_limit {
    private Integer id;

    private Integer uid;

    private String limit;

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

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit == null ? null : limit.trim();
    }
}