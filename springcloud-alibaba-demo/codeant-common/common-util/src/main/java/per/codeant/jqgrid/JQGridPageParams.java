package per.codeant.jqgrid;

import java.io.Serializable;

public class JQGridPageParams implements Serializable {
	private static final long serialVersionUID = 928845822895702501L;

	public Long page;
	public Long rows;
	public String sort;
	public String order;
	public Long startRow = 0L;
	public Long endRow = 0L;

	public Long getPage() {
		return page;
	}

	public void setPage(Long page) {
		this.page = page;
		calc();
	}

	public Long getRows() {
		return rows;
	}

	public void setRows(Long rows) {
		this.rows = rows;
		calc();
	}

	public String getSort() {
		return sort;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public Long getStartRow() {
		return startRow;
	}

	public void setStartRow(Long startRow) {
		this.startRow = startRow;
	}

	public Long getEndRow() {
		return endRow;
	}

	public void setEndRow(Long endRow) {
		this.endRow = endRow;
	}
	
	private void calc() {
		if (null == page || null == rows) return;
		
		startRow = (page - 1) * rows;
		endRow = rows;
	}

	@Override
	public String toString() {
		return "JQGridPageParams [page=" + page + ", rows=" + rows + ", sort=" + sort + ", order=" + order
				+ ", startRow=" + startRow + ", endRow=" + endRow + "]";
	}
	
	
}
