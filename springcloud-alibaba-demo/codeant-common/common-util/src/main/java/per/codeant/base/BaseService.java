package per.codeant.base;

import per.codeant.jqgrid.JQGridPageParams;
import per.codeant.jqgrid.JQGridResultEntity;

import java.util.List;

/**
 * Service基类
 */
public class BaseService extends Base {


	/**
	 * 计算总页数
	 *
	 * @param totalRows 总行数
	 * @param pageRows  单页行数
	 * @return
	 */
	private long calcTotalPage(Long totalRows, Long pageRows) {
		return totalRows / pageRows + (totalRows % pageRows > 0 ? 1 : 0);
	}

	/**
	 * 填充数据
	 *
	 * @param list
	 * @param totalRecords
	 * @param pageParams
	 * @return
	 */
	protected <T> JQGridResultEntity<T> fillJQGrid(List<T> list, Long totalRecords, JQGridPageParams pageParams) {
		return fillJQGrid(null, list, totalRecords, pageParams);
	}

	/**
	 * 填充数据
	 *
	 * @param result
	 * @param list
	 * @param totalRecords
	 * @return
	 */
	protected <T> JQGridResultEntity<T> fillJQGrid(JQGridResultEntity<T> result, List<T> list, Long totalRecords, JQGridPageParams pageParams) {
		if (null == result) {
			result = new JQGridResultEntity<>();
		}

		if (pageParams != null) {
			result.setCurrentPage(pageParams.getPage());
			result.setTotalPages(calcTotalPage(totalRecords, pageParams.getRows()));
		} else {
			result.setCurrentPage(1);
			result.setTotalPages(0);
		}
		// 移除可能出现空指针异常的语句，修改人：宋进
		// result.setCurrentPage(pageParams.getPage());
		result.setRows(list);
		result.setTotalRecords(totalRecords);

		return result;
	}


//	左补位，右对齐
//	private String padLeft(String oriStr, int len, char alexin) {
//		int strlen = oriStr.length();
//		String str = "";
//		if (strlen < len) {
//			for (int i = 0; i < len - strlen; i++) {
//				str = str + alexin;
//			}
//		}
//		str += oriStr;
//		return str;
//	}

}
