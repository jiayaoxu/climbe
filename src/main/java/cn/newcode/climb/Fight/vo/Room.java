package cn.newcode.climb.Fight.vo;

/**
 * @Description: java类作用描述
 * @author: 作者姓名
 * @CreateDate: 2017/10/29 16:52
 * @Version: 1.0
 */
public class Room {
    private Integer rid;

    private String password;

    private String roomName;

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getRid() {
        return rid;
    }

    public void setRid(Integer rid) {
        this.rid = rid;
    }
}
