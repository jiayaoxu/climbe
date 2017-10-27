package cn.newcode.climb.po;

import java.io.Serializable;

/**
 * @Description: 向选手返回成绩po
 * @author: shine
 * @CreateDate: 2017/10/18 6:29
 * @Version: 1.0
 */
public class grade implements Serializable {
    private String matchName;
    private String nickname;
    private String username;
    private Integer grade;

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    private Integer uid;

    public String getMatchName() {
        return matchName;
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getGrade() {
         return  grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }
}
