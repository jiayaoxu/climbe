package cn.newcode.climb.Fight.vo;

/**
 * @Description: java类作用描述
 * @author: 作者姓名
 * @CreateDate: 2017/10/29 16:52
 * @Version: 1.0
 */
public class Room {
    /**
     * 房间id
     */
    private Integer rid;

    /**
     * 房间密码
     */
    private String password;

    /**
     * 房间名称
     */
    private String roomName;

    /**
     * 岩线id
     */
    private String wid;

    /**
     *
     */
    private String roid;

    public String getWid() {
        return wid;
    }

    public void setWid(String wid) {
        this.wid = wid;
    }

    public String getRoid() {
        return roid;
    }

    public void setRoid(String roid) {
        this.roid = roid;
    }

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
