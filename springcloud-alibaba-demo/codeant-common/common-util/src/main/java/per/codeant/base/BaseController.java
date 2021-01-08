package per.codeant.base;

//import org.apache.commons.lang.StringUtils;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.util.HashMap;
//import java.util.Map;

/**
 * Controller父类 Copyright © 2016 重庆锐云科技有限公司
 * 
 * @author chentianjin
 */
public abstract class BaseController extends Base {
//
//	/**
//	 * 把request值封装成Map
//	 *
//	 * @param request
//	 * @return
//	 */
//	protected Map<String, Object> requestParamsToMap(HttpServletRequest request) {
//		Map<String, Object> paraMap = new HashMap<String, Object>();
//		Map<String, String[]> tmp = request.getParameterMap();
//		if (tmp != null) {
//			for (String key : tmp.keySet()) {
//				String[] values = tmp.get(key);
//				String value = StringUtils.isEmpty(values[0].trim()) ? "" : values[0].trim();
//				paraMap.put(key, setRequestParameter(key, value));
//			}
//		}
//		return paraMap;
//	}
//
//
//	/**
//	 * 根据key重置request参数值，默认不做逻辑处理，返回原值<br>
//	 * 如遇特殊情况需对request请求参数特殊处理，可在子类中重写些方法
//	 *
//	 * @param key
//	 *            request参数key
//	 * @param value
//	 *            request参数value
//	 * @return String
//	 */
//	protected String setRequestParameter(String key, String value) {
//		return value;
//	}
//
//	/**
//	 * 得到request对象
//	 */
//	protected HttpServletRequest getRequest() {
//		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//	}
//
//	/**
//	 * 得到response对象
//	 */
//	protected HttpServletResponse getResponse() {
//		return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
//	}

}
