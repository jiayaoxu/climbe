package cn.newcode.climb.vo;

import cn.newcode.climb.po.Rank_teacher;
import cn.newcode.climb.po.User;

/**
 * 向前端返回教师排名信息
 */
public class Rank_teacherVo {

    private String username;

    private Integer point;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getPoint() {
        return point;
    }

    public void setPoint(Integer point) {
        this.point = point;
    }
}
