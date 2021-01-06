package per.codeant.base;

import java.util.Random;

/**
 * 基类
 */
public class Base {

	protected BaseResult generateResult(boolean isSuccess, String msg) {
		return generateResult(isSuccess, msg, null);
	}

	protected BaseResult generateResult(boolean isSuccess, String msg, Object result) {
		BaseResult obj = new BaseResult();
		obj.setMsg(msg);
		obj.setIsSuccess(isSuccess);
		obj.setResult(result);
		return obj;
	}


	/**
	 * 获得4位数的验证码
	 *
	 * @return String
	 * @author chentianjin
	 * @date 2017年5月12日
	 */
	public String makeVcode() {
		Random random = new Random();
		String encode = "0123456789";
		String sRand = "";
		for (int i = 0; i < 4; i++) {
			String rand = encode.charAt(random.nextInt(10)) + "";
			sRand += rand;
		}
		return sRand;
	}

	/**
	 * 获得6位数的验证码
	 */
	public String makeVcode6() {
		Random random = new Random();
		String encode = "0123456789";
		String sRand = "";
		for (int i = 0; i < 6; i++) {
			String rand = encode.charAt(random.nextInt(10)) + "";
			sRand += rand;
		}
		return sRand;
	}

}
