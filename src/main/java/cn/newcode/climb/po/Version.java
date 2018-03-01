package cn.newcode.climb.po;

/**
 * \* Created with IntelliJ IDEA.
 * \* User: shine
 * \* Date: 2018/3/1 0001
 * \* Time: 19:44
 * \* Description:
 * \    版本po
 */
public class Version {

    private Integer id;
    private String version;
    private String link;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}