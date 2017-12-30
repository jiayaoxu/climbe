package cn.newcode.climb.vo;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: shine
 * \* Date: 17-12-18
 * \* Time: 下午8:31
 * \* Description:向前台返回奖牌排行榜
 * \
 */
public class Rank_medalVo {
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private String username;
    private Integer medal;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getMedal() {
        return medal;
    }

    public void setMedal(Integer medal) {
        this.medal = medal;
    }
}