package cn.newcode.climb.vo;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: shine
 * \* Date: 17-12-24
 * \* Time: 下午10:49
 * \* Description:
 * \
 */
public class IndexVo {

    private String imgPath;
    private String nickName;
    private String clubName;
    private String limit;

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPth) {
        this.imgPath = imgPth;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
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