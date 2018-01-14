package cn.newcode.climb.vo;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: shine
 * \* Date: 2018/1/14 0014
 * \* Time: 10:58
 * \* Description:用户权限vo
 * \
 */
public class UserLimitVo {
    private Integer id;
    private String headImg;
    private String nickName;
    private Boolean gender;
    private String clubName;
    private String limit;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

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

    public String getClubName() {
        return clubName;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }
}