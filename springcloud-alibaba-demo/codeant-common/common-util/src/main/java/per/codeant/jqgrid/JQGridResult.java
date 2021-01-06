package per.codeant.jqgrid;

import java.util.List;

public class JQGridResult<T> {

    /**
     * 页数
     */
    private long page;
    /**
     * 总页数
     */
    private long total;
    /**
     * 记录数量
     */
    private long records;
    /**
     * 行数据
     */
    private List<T> rows;



    public JQGridResult(JQGridPage page) {
        this.page = page.getCurrent();
        this.records = page.getTotal();
        this.rows = page.getRecords();
        this.total = records / page.getSize();
        if (records % page.getSize() != 0) {
            this.total++;
        }
    }


    public long getPage() {
        return page;
    }

    public void setPage(long page) {
        this.page = page;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public long getRecords() {
        return records;
    }

    public void setRecords(long records) {
        this.records = records;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
