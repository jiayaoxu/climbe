package cn.newcode.climb.vo;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: shine
 * \* Date: 17-12-24
 * \* Time: 下午11:55
 * \* Description:
 * \
 */
public class FriendsVo {
    private String nickName;
    private Boolean gender;
    private Integer id;
    private String clubName;
    private Integer fens;
    private Integer attention;
    private String limit;
    private String headImg;

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public Boolean getOnlion() {
        return onlion;
    }

    public void setOnlion(Boolean onlion) {
        this.onlion = onlion;
    }

    private Boolean onlion;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public Integer getFens() {
        return fens;
    }

    public void setFens(Integer fens) {
        this.fens = fens;
    }

    public Integer getAttention() {
        return attention;
    }

    public void setAttention(Integer attention) {
        this.attention = attention;
    }
}