package com.ruiyun.yuezhi.core.util;

/** 
 * 身份证前6位【ABCDEF】为行政区划数字代码（简称数字码）说明（参考《GB/T 2260-2007 中华人民共和国行政区划代码》）： 
 * 该数字码的编制原则和结构分析，它采用三层六位层次码结构，按层次分别表示我国各省（自治区，直辖市，特别行政区）、 
 * 市（地区，自治州，盟）、县（自治县、县级市、旗、自治旗、市辖区、林区、特区）。  
 数字码码位结构从左至右的含义是：  
 第一层为AB两位代码表示省、自治区、直辖市、特别行政区；  
 第二层为CD两位代码表示市、地区、自治州、盟、直辖市所辖市辖区、县汇总码、省（自治区）直辖县级行政区划汇总码，其中：  
 ——01~20、51~70表示市，01、02还用于表示直辖市所辖市辖区、县汇总码；  
 ——21~50表示地区、自治州、盟；  
 ——90表示省（自治区）直辖县级行政区划汇总码。  
 第三层为EF两位表示县、自治县、县级市、旗、自治旗、市辖区、林区、特区，其中：  
 ——01~20表示市辖区、地区（自治州、盟）辖县级市、市辖特区以及省（自治区）直辖县级行政区划中的县级市，01通常表示辖区汇总码； 
 ——21~80表示县、自治县、旗、自治旗、林区、地区辖特区；  
 ——81~99表示省（自治区）辖县级市。  
 */

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * <p>
 * 类说明:身份证合法性校验
 * </p>
 * <p>
 * --15位身份证号码：第7、8位为出生年份(两位数)，第9、10位为出生月份，第11、12位代表出生日期，第15位代表性别，奇数为男，偶数为女。 --18位身份证号码
 * ：第7、8、9、10位为出生年份(四位数)，第11、第12位为出生月份，第13、14位代表出生日期，第17位代表性别，奇数为男，偶数为女。
 * </p>
 */
@SuppressWarnings({ "unchecked", "unused", "all" })
public class IdcardValidator {

	protected String codeAndCity[][] = { { "11", "北京" }, { "12", "天津" }, { "13", "河北" }, { "14", "山西" }, { "15", "内蒙古" }, { "21", "辽宁" },
			{ "22", "吉林" }, { "23", "黑龙江" }, { "31", "上海" }, { "32", "江苏" }, { "33", "浙江" }, { "34", "安徽" }, { "35", "福建" }, { "36", "江西" },
			{ "37", "山东" }, { "41", "河南" }, { "42", "湖北" }, { "43", "湖南" }, { "44", "广东" }, { "45", "广西" }, { "46", "海南" }, { "50", "重庆" },
			{ "51", "四川" }, { "52", "贵州" }, { "53", "云南" }, { "54", "西藏" }, { "61", "陕西" }, { "62", "甘肃" }, { "63", "青海" }, { "64", "宁夏" },
			{ "65", "新疆" }, { "71", "台湾" }, { "81", "香港" }, { "82", "澳门" }, { "91", "国外" } };

	private String cityCode[] = { "11", "12", "13", "14", "15", "21", "22", "23", "31", "32", "33", "34", "35", "36", "37", "41", "42", "43", "44",
			"45", "46", "50", "51", "52", "53", "54", "61", "62", "63", "64", "65", "71", "81", "82", "91" };

	// 每位加权因子
	private int power[] = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };

	// 第18位校检码
	private String verifyCode[] = { "1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2" };
	
	/**
	 * 台湾身份首字母对应数字 。A 台北市 10,,B 台中市 11,C 基隆市 12,D 台南市 13,E 高雄市 14,F 台北县 15,G 宜兰县 16,H 桃园县 17,I 嘉义市 34,J 新竹县 18,K 苗栗县 19,L 台中县 20,M 南投县 21,N 彰化县 22,O 新竹市
	 * 35,P 云林县 23,Q 嘉义县 24,R 台南县 25,S 高雄县 26,T 屏东县 27,U 花莲县 28,V 台东县 29,W 金门县 32,X 澎湖县 30,Y 阳明山管理局 31,Z 连江县 33
	 */
	private Map<String, Integer> twCityCode = new HashMap<String, Integer>() {
		{
			put("A", 10);
			put("B", 11);
			put("C", 12);
			put("D", 13);
			put("E", 14);
			put("F", 15);
			put("G", 16);
			put("H", 17);
			put("J", 18);
			put("K", 19);
			put("L", 20);
			put("M", 21);
			put("N", 22);
			put("P", 23);
			put("Q", 24);
			put("R", 25);
			put("S", 26);
			put("T", 27);
			put("U", 28);
			put("V", 29);
			put("X", 30);
			put("Y", 31);
			put("W", 32);
			put("Z", 33);
			put("I", 34);
			put("O", 35);
		}
	};

	/**
	 * 验证所有的身份证的合法性
	 * 
	 * @param idcard
	 * @return
	 */
	public boolean isValidatedAllIdcard(String idcard) {
		if (idcard.length() == 15) {
			idcard = this.convertIdcarBy15bit(idcard);
		}
		return this.isValidate18Idcard(idcard);
	}

	/**
	 * <p>
	 * 判断18位身份证的合法性
	 * </p>
	 * 根据〖中华人民共和国国家标准GB11643-1999〗中有关公民身份号码的规定，公民身份号码是特征组合码，由十七位数字本体码和一位数字校验码组成。 排列顺序从左至右依次为：六位数字地址码，八位数字出生日期码，三位数字顺序码和一位数字校验码。
	 * <p>
	 * 顺序码: 表示在同一地址码所标识的区域范围内，对同年、同月、同 日出生的人编定的顺序号，顺序码的奇数分配给男性，偶数分配 给女性。
	 * </p>
	 * <p>
	 * 1.前1、2位数字表示：所在省份的代码； 2.第3、4位数字表示：所在城市的代码； 3.第5、6位数字表示：所在区县的代码； 4.第7~14位数字表示：出生年、月、日； 5.第15、16位数字表示：所在地的派出所的代码； 6.第17位数字表示性别：奇数表示男性，偶数表示女性；
	 * 7.第18位数字是校检码：也有的说是个人信息码，一般是随计算机的随机产生，用来检验身份证的正确性。校检码可以是0~9的数字，有时也用x表示。
	 * </p>
	 * <p>
	 * 第十八位数字(校验码)的计算方法为： 1.将前面的身份证号码17位数分别乘以不同的系数。从第一位到第十七位的系数分别为：7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2
	 * </p>
	 * <p>
	 * 2.将这17位数字和系数相乘的结果相加。
	 * </p>
	 * <p>
	 * 3.用加出来和除以11，看余数是多少？
	 * </p>
	 * 4.余数只可能有0 1 2 3 4 5 6 7 8 9 10这11个数字。其分别对应的最后一位身份证的号码为1 0 X 9 8 7 6 5 4 3 2。
	 * <p>
	 * 5.通过上面得知如果余数是2，就会在身份证的第18位数字上出现罗马数字的Ⅹ。如果余数是10，身份证的最后一位号码就是2。
	 * </p>
	 * 
	 * @param idcard
	 * @return
	 */
	public boolean isValidate18Idcard(String idcard) {
		// 非18位为假
		if (idcard.length() != 18) {
			return false;
		}
		// 获取前17位
		String idcard17 = idcard.substring(0, 17);
		// 获取第18位
		String idcard18Code = idcard.substring(17, 18);
		char c[] = null;
		String checkCode = "";
		// 是否都为数字
		if (isDigital(idcard17)) {
			c = idcard17.toCharArray();
		} else {
			return false;
		}

		if (null != c) {
			int bit[] = new int[idcard17.length()];

			bit = converCharToInt(c);

			int sum17 = 0;

			sum17 = getPowerSum(bit);

			// 将和值与11取模得到余数进行校验码判断
			checkCode = getCheckCodeBySum(sum17);
			if (null == checkCode) {
				return false;
			}
			// 将身份证的第18位与算出来的校码进行匹配，不相等就为假
			if (!idcard18Code.equalsIgnoreCase(checkCode)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 验证15位身份证的合法性,该方法验证不准确，最好是将15转为18位后再判断，该类中已提供。
	 * 
	 * @param idcard
	 * @return
	 */
	public boolean isValidate15Idcard(String idcard) {
		// 非15位为假
		if (idcard.length() != 15) {
			return false;
		}

		// 是否全都为数字
		if (isDigital(idcard)) {
			String provinceid = idcard.substring(0, 2);
			String birthday = idcard.substring(6, 12);
			int year = Integer.parseInt(idcard.substring(6, 8));
			int month = Integer.parseInt(idcard.substring(8, 10));
			int day = Integer.parseInt(idcard.substring(10, 12));

			// 判断是否为合法的省份
			boolean flag = false;
			for (String id : cityCode) {
				if (id.equals(provinceid)) {
					flag = true;
					break;
				}
			}
			if (!flag) {
				return false;
			}
			// 该身份证生出日期在当前日期之后时为假
			Date birthdate = null;
			try {
				birthdate = new SimpleDateFormat("yyMMdd").parse(birthday);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			if (birthdate == null || new Date().before(birthdate)) {
				return false;
			}

			// 判断是否为合法的年份
			GregorianCalendar curDay = new GregorianCalendar();
			int curYear = curDay.get(Calendar.YEAR);
			int year2bit = Integer.parseInt(String.valueOf(curYear).substring(2));

			// 判断该年份的两位表示法，小于50的和大于当前年份的，为假
			if ((year < 50 && year > year2bit)) {
				return false;
			}

			// 判断是否为合法的月份
			if (month < 1 || month > 12) {
				return false;
			}

			// 判断是否为合法的日期
			boolean mflag = false;
			curDay.setTime(birthdate); // 将该身份证的出生日期赋于对象curDay
			switch (month) {
			case 1:
			case 3:
			case 5:
			case 7:
			case 8:
			case 10:
			case 12:
				mflag = (day >= 1 && day <= 31);
				break;
			case 2: // 公历的2月非闰年有28天,闰年的2月是29天。
				if (curDay.isLeapYear(curDay.get(Calendar.YEAR))) {
					mflag = (day >= 1 && day <= 29);
				} else {
					mflag = (day >= 1 && day <= 28);
				}
				break;
			case 4:
			case 6:
			case 9:
			case 11:
				mflag = (day >= 1 && day <= 30);
				break;
			}
			if (!mflag) {
				return false;
			}
		} else {
			return false;
		}
		return true;
	}

	/**
	 * 将15位的身份证转成18位身份证
	 * 
	 * @param idcard
	 * @return
	 */
	public String convertIdcarBy15bit(String idcard) {
		String idcard17 = null;
		// 非15位身份证
		if (idcard.length() != 15) {
			return null;
		}

		if (isDigital(idcard)) {
			// 获取出生年月日
			String birthday = idcard.substring(6, 12);
			Date birthdate = null;
			try {
				birthdate = new SimpleDateFormat("yyMMdd").parse(birthday);
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Calendar cday = Calendar.getInstance();
			cday.setTime(birthdate);
			String year = String.valueOf(cday.get(Calendar.YEAR));

			idcard17 = idcard.substring(0, 6) + year + idcard.substring(8);

			char c[] = idcard17.toCharArray();
			String checkCode = "";

			if (null != c) {
				int bit[] = new int[idcard17.length()];

				// 将字符数组转为整型数组
				bit = converCharToInt(c);
				int sum17 = 0;
				sum17 = getPowerSum(bit);

				// 获取和值与11取模得到余数进行校验码
				checkCode = getCheckCodeBySum(sum17);
				// 获取不到校验位
				if (null == checkCode) {
					return null;
				}

				// 将前17位与第18位校验码拼接
				idcard17 += checkCode;
			}
		} else { // 身份证包含数字
			return null;
		}
		return idcard17;
	}

	/**
	 * 15位和18位身份证号码的基本数字和位数验校
	 * 
	 * @param idcard
	 * @return
	 */
	public boolean isIdcard(String idcard) {
		return idcard == null || "".equals(idcard) ? false : Pattern.matches("(^\\d{15}$)|(\\d{17}(?:\\d|x|X)$)", idcard);
	}

	/**
	 * 15位身份证号码的基本数字和位数验校
	 * 
	 * @param idcard
	 * @return
	 */
	public boolean is15Idcard(String idcard) {
		return idcard == null || "".equals(idcard) ? false : Pattern.matches("^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$", idcard);
	}

	/**
	 * 18位身份证号码的基本数字和位数验校
	 * 
	 * @param idcard
	 * @return
	 */
	public boolean is18Idcard(String idcard) {
		return Pattern.matches("^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([\\d|x|X]{1})$", idcard);
	}

	/**
	 * 数字验证
	 * 
	 * @param str
	 * @return
	 */
	public boolean isDigital(String str) {
		return str == null || "".equals(str) ? false : str.matches("^[0-9]*$");
	}

	/**
	 * 将身份证的每位和对应位的加权因子相乘之后，再得到和值
	 * 
	 * @param bit
	 * @return
	 */
	public int getPowerSum(int[] bit) {

		int sum = 0;

		if (power.length != bit.length) {
			return sum;
		}

		for (int i = 0; i < bit.length; i++) {
			for (int j = 0; j < power.length; j++) {
				if (i == j) {
					sum = sum + bit[i] * power[j];
				}
			}
		}
		return sum;
	}

	/**
	 * 将和值与11取模得到余数进行校验码判断
	 * 
	 * @param checkCode
	 * @param sum17
	 * @return 校验位
	 */
	public String getCheckCodeBySum(int sum17) {
		String checkCode = null;
		switch (sum17 % 11) {
		case 10:
			checkCode = "2";
			break;
		case 9:
			checkCode = "3";
			break;
		case 8:
			checkCode = "4";
			break;
		case 7:
			checkCode = "5";
			break;
		case 6:
			checkCode = "6";
			break;
		case 5:
			checkCode = "7";
			break;
		case 4:
			checkCode = "8";
			break;
		case 3:
			checkCode = "9";
			break;
		case 2:
			checkCode = "x";
			break;
		case 1:
			checkCode = "0";
			break;
		case 0:
			checkCode = "1";
			break;
		}
		return checkCode;
	}

	/**
	 * 将字符数组转为整型数组
	 * 
	 * @param c
	 * @return
	 * @throws NumberFormatException
	 */
	public int[] converCharToInt(char[] c) throws NumberFormatException {
		int[] a = new int[c.length];
		int k = 0;
		for (char temp : c) {
			a[k++] = Integer.parseInt(String.valueOf(temp));
		}
		return a;
	}
	
	/**
     * 验证台湾身份证号码
     * 
     * @param idCard
     *            身份证号码
     * @return 验证码是否符合
     */
	public boolean validateTWCard(String idCard) {
		String start = idCard.substring(0, 1);
		String mid = idCard.substring(1, 9);
		String end = idCard.substring(9, 10);
		Integer iStart = twCityCode.get(start);
		if (null == iStart) {
			return false;
		}
		Integer sum = iStart / 10 + (iStart % 10) * 9;
		char[] chars = mid.toCharArray();
		Integer iflag = 8;
		for (char c : chars) {
			sum = sum + Integer.valueOf(c + "") * iflag;
			iflag--;
		}
		return (sum % 10 == 0 ? 0 : (10 - sum % 10)) == Integer.valueOf(end) ? true : false;
	}
    
    /**
     * 验证香港身份证号码
     * 
     * 香港身份证号码由三部分组成：一个英文字母；6个数字；括号及0-9中的任一个数字，或者字母A。
     * 括号中的数字或字母A，是校验码，用于检验括号前面的号码的逻辑正确性。
	 * 逻辑关系：
  	 * 先把首位字母改为数字，即A为1，B为2，C为3...Z为26，再乘以8；然后把字母后面的6个数字依次乘以7、6、5、4、3、2；
  	 * 再将以上所有乘积相加的和，除以11，得到余数；如果整除，则括号中的校验码为0，如果余数为1，则校验码为A，如果余数为2～10，则用11减去这个余数的差作校验码。
	 * 例如：P103265（1），P，在字母表中排行16，则以16代表，则计算校验码：
	 * 16×8＋1×7＋0×6＋3×5＋2×4＋6×3＋5×2＝186
	 * 186÷11＝16......余10
	 * 11－10＝1，即校验码为1。
	 * 
     * @param idCard 身份证号码
     * @return 验证码是否符合
     */
	public boolean validateHKCard(String idCard) {
		// 此处只处理英文括号
		String card = idCard.replaceAll("[\\(|\\)]", "");
		Integer sum = 0;
		if (card.length() != 8) {
			return false;
		}
		// 转换第一个字母并乘以加权因子
		sum = (Integer.valueOf(card.substring(0, 1).toUpperCase().toCharArray()[0]) - 64) * 8;

		String mid = card.substring(1, 7);
		String end = card.substring(7, 8);
		char[] chars = mid.toCharArray();
		Integer iflag = 7;
		for (char c : chars) {
			sum = sum + Integer.valueOf(c + "") * iflag;
			iflag--;
		}

		if (end.toUpperCase().equals("A")) {
			sum = sum + 10;
		} else {
			sum = sum + Integer.valueOf(end);
		}
		return (sum % 11 == 0) ? true : false;
	}
    
	/**
	 * 验证10位身份编码是否合法
	 * 
	 * @param idCard
	 * @return
	 */
	public Boolean is10Idcard(String idCard) {
		boolean validateRs = false;
		String card = idCard.replaceAll("[\\(|\\)]", "");
		if (card.length() != 8 && card.length() != 9 && idCard.length() != 10) {
			return false;
		}
		if (idCard.matches("^[a-zA-Z][0-9]{9}$")) {
			// 台湾
			validateRs = validateTWCard(idCard);
		} else if (idCard.matches("^[1|5|7][0-9]{6}\\(?[0-9A-Z]\\)?$")) {
			// 澳门身份证暂无规则
			validateRs = true;
		} else if (idCard.matches("^[A-Z]{1,2}[0-9]{6}\\(?[0-9A]\\)?$")) { // 香港
			validateRs = validateHKCard(idCard);
		}
		return validateRs;
	}
    
	public static void main(String[] args) throws Exception {
		String idcardHK = "C668668(9)";// 10位
		String idcardTW = "D257856145";// 10位
		String idcardAM = "1225180(7)";// 10位
		String idcard15 = "142431199001145";// 15位
		String idcard18 = "140223198904283836";// 18位
		IdcardValidator iv = new IdcardValidator();
		System.out.println(iv.is10Idcard(idcardHK));
		System.out.println(iv.is10Idcard(idcardTW));
		System.out.println(iv.is10Idcard(idcardAM));
		System.out.println(iv.isValidatedAllIdcard(idcard15));
		System.out.println(iv.isValidatedAllIdcard(idcard18));
	}
}