package cn.newcode.climb.po;

import java.io.Serializable;
import java.util.Date;

public class Match_inf implements Serializable {
    private Integer id;

    private Integer mid;

    private Integer route;

    private Integer venue;

    private Date starttime;

    private Date starttimeT;

    private Integer count;

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Date getStarttimeT() {
        return starttimeT;
    }

    public void setStarttimeT(Date starttimeT) {
        this.starttimeT = starttimeT;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public Integer getRoute() {
        return route;
    }

    public void setRoute(Integer route) {
        this.route = route;
    }

    public Integer getVenue() {
        return venue;
    }

    public void setVenue(Integer venue) {
        this.venue = venue;
    }

    public Date getStarttime() {
        return starttime;
    }

    public void setStarttime(Date starttime) {
        this.starttime = starttime;
    }
}