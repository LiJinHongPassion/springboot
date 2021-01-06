package per.codeant.jqgrid;

import com.baomidou.mybatisplus.plugins.Page;

public class JQGridPage extends Page {

    public JQGridPage(){}

    public JQGridPage(JQGridPageParams params){
        this.setOrderByField(params.getSort());
        this.setAsc("asc".equalsIgnoreCase(params.getOrder()));
        this.setCurrent(params.getPage().intValue());//设置当前页数
        this.setSize(params.getRows().intValue());//设置每页行数
    }


    public void setSidx(String sidx) {
        this.setOrderByField(sidx);
    }


    public void setRows(int rows) {
        this.setSize(rows);
    }


    public void setPage(int page) {
        this.setCurrent(page);
    }


    public void setSord(String sord) {
        this.setAsc("asc".equalsIgnoreCase(sord));
    }

}
