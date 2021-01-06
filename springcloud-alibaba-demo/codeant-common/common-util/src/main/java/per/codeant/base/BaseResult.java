package per.codeant.base;

import java.io.Serializable;

/**
 * 结果集基类
 */
public class BaseResult implements Serializable {
	private static final long serialVersionUID = -6711131180313904332L;

	private String msg; // 返回消息
	private boolean isSuccess; // 是否成功
	private Object result; // 结果对象

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(boolean isSuccess) {
		this.isSuccess = isSuccess;
	}

	public Object getResult() {
		return result;
	}

	public void setResult(Object result) {
		this.result = result;
	}
}
