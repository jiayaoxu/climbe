package cn.newcode.climb.page;

/**
 * 实现分页功能的bean
 * @author shine
 */
public class pageBean {
    private int pageNow = 1; // 当前页
    private int pageSize = 5; // 每页记录显示的数量
    private int totalCount; // 总的记录条数
    private int totalPageCount; // 总的页数

    private int startPos; // 开始位置
    private boolean hasFirst; // 是否有首页
    private boolean hasPre; // 是否有前一页
    private boolean hasNext; // 是否有下一页
    private boolean hasLast; // 是否有最后一页

    public pageBean(int pageNow, int totalCount) {
        super();
        this.pageNow = pageNow;
        this.totalCount = totalCount;
    }

    public int getPageNow() {
        return pageNow;
    }

    public void setPageNow(int pageNow) {
        this.pageNow = pageNow;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    // 取得总页数 总记录数/一页的记录数
    public int getTotalPageCount() {
        return (totalCount % pageSize == 0) ? totalCount / pageSize
                : totalCount / pageSize + 1;
    }

    public void setTotalPageCount(int totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    // 开始位置计算
    public int getStartPos() {
        return (pageNow - 1) * pageSize;
    }

    public void setStartPos(int startPos) {
        this.startPos = startPos;
    }

    // 判断是否为第一页
    public boolean isHasFirst() {
        return pageNow == 1;
    }

    // 判断是否有首页
    public boolean isHasPre() {
        // 判断是否为第一页，如果是，则没有首页，如果否，则有首页
        return !isHasFirst();
    }

    // 判断是否有下一页
    public boolean isHasNext() {
        // 如果有尾页，则有下一页
        return isHasLast();
    }

    // 判断是否有尾页
    public boolean isHasLast() {
        // 如果当前页等于总页数，则为最后一页，没有尾页
        return pageNow != getTotalPageCount();
    }
}
