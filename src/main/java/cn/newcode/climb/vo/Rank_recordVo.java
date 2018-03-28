package cn.newcode.climb.vo;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: shine
 * \* Date: 2017/12/15 0015
 * \* Time: 17:58
 * \* Description:向前端返回积分排行榜
 * \
 */
public class Rank_recordVo {
    private Integer id;

    private String imgPath;

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private String username;
    private Integer integral;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getIntegral() {
        return integral;
    }

    public void setIntegral(Integer integral) {
        this.integral = integral;
    }
}