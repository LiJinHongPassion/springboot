package per.codeant.jqgrid;

import java.util.List;

/**
 * JQGrid实体
 */
public class JQGridResultEntity<T> {
	private List<T> rows;
	private long currentPage;
	private long totalPages;
	private long totalRecords;

	private Object responseParam;

	public Object getResponseParam() {
		return responseParam;
	}

	public void setResponseParam(Object responseParam) {
		this.responseParam = responseParam;
	}

	public List<T> getRows() {
		return rows;
	}

	public void setRows(List<T> rows) {
		this.rows = rows;
	}

	public long getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(long currentPage) {
		this.currentPage = currentPage;
	}

	public long getTotalPages() {
		return totalPages;
	}

	public void setTotalPages(long totalPages) {
		this.totalPages = totalPages;
	}

	public long getTotalRecords() {
		return totalRecords;
	}

	public void setTotalRecords(long totalRecords) {
		this.totalRecords = totalRecords;
	}

}
