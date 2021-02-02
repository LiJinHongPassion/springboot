package com.ruiyun.yuezhi.core.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
import java.util.Base64.Decoder;
import java.util.Base64.Encoder;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Random;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

/**
 * 字符串构件 有关字符串处理的构件
 * 
 * @author *
 */
public class StringUtil extends StringUtils {

	private static Log log = LogFactory.getLog(StringUtil.class);
	private static final String FOLDER_SEPARATOR = "/"; // folder separator

	private static final String WINDOWS_FOLDER_SEPARATOR = "\\"; // Windows folder separator

	private static final String TOP_PATH = ".."; // top folder

	private static final String CURRENT_PATH = "."; // current folder

	/**
	 * 获取字符串的字节长度
	 * 
	 * @param str
	 * @return
	 */
	public static int getLengthByByte(String str) {
		if (str == null || str.equals("")) {
			return 0;
		}
		return str.getBytes().length;
	}

	/**
	 * 按照二进制方式截取中文字符串
	 * 
	 * @param str        字符串
	 * @param startIndex 开始截取的字符串二进制位置
	 * @param length     截取字符串的二进制长度
	 * @return
	 */
	public static String substringOfChinese(String str, int startIndex, int length) {
		if (str == null || str.equals("")) {
			return "";
		}
		int iIndex = 0;
		int iLen = 0;
		int iInc = 0;
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			// 中文
			try {
				if (String.valueOf(str.charAt(i)).getBytes("GBK").length == 2) {
					iInc = 2;
				} else {
					iInc = 1;
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			iIndex += iInc;
			if (iIndex - iInc < startIndex) {
				continue;
			}
			iLen += iInc;
			if (iLen > length) {
				break;
			}
			sb.append(str.charAt(i));
		}
		return sb.toString();
	}

	/**
	 * @yhcip:title 判断字符串是否是以指定的字符开始
	 * @yhcip:desc 判断字符串是否是以指定的字符开始，不区分大小写
	 * @param str
	 * @param prefix
	 * @return
	 * @author Administrator
	 */
	public static boolean startsWithIgnoreCase(String str, String prefix) {
		if (str == null || prefix == null) {
			return false;
		}
		if (str.startsWith(prefix)) {
			return true;
		}
		if (str.length() < prefix.length()) {
			return false;
		}
		String lcStr = str.substring(0, prefix.length()).toLowerCase();
		String lcPrefix = prefix.toLowerCase();
		return lcStr.equals(lcPrefix);
	}

	/**
	 * @yhcip:title 计算字串在指定串中出现的次数
	 * @yhcip:desc 计算字串在指定串中出现的次数
	 * @param str 指定串
	 * @param sub 字串
	 * @return
	 * @author Administrator
	 */
	public static int countOccurrencesOf(String str, String sub) {
		if (str == null || sub == null || str.length() == 0 || sub.length() == 0) {
			return 0;
		}
		int count = 0, pos = 0, idx = 0;
		while ((idx = str.indexOf(sub, pos)) != -1) {
			++count;
			pos = idx + sub.length();
		}
		return count;
	}

	/**
	 * @yhcip:title 从字符串中删除指定的子串
	 * @yhcip:desc 从字符串中删除指定的子串
	 * @param inString 指定串
	 * @param pattern  要删除的子串
	 * @return
	 * @author Administrator
	 */
	public static String delete(String inString, String pattern) {
		return replace(inString, pattern, "");
	}

	/**
	 * 删除指定字符.
	 * 
	 * @yhcip:title 删除指定字符
	 * @yhcip:desc 删除指定字符
	 * @param charsToDelete a set of characters to delete. E.g. "az\n" will delete
	 *                      'a's, 'z's and new lines.
	 */
	public static String deleteAny(String inString, String charsToDelete) {
		if (inString == null || charsToDelete == null) {
			return inString;
		}
		StringBuffer out = new StringBuffer();
		for (int i = 0; i < inString.length(); i++) {
			char c = inString.charAt(i);
			if (charsToDelete.indexOf(c) == -1) {
				out.append(c);
			}
		}
		return out.toString();
	}

	/**
	 * 返回'.'后的所有字符串. 如"this.name.is.qualified", returns "qualified".
	 * 
	 * @yhcip:title 返回'.' 后的年有字符串
	 * @yhcip:desc 返回'.' 后的年有字符串
	 * @param qualifiedName the qualified name
	 * @return
	 * @author Administrator
	 */
	public static String unqualify(String qualifiedName) {
		return unqualify(qualifiedName, '.');
	}

	/**
	 * 返回指定字符后面的字符串，并是最后一个.
	 * 
	 * @yhcip:title 返回指定字符后面的字符串，并是最后一个
	 * @yhcip:desc 返回指定字符后面的字符串，并是最后一个
	 * @param qualifiedName the qualified name
	 * @param separator     the separator
	 * @return
	 * @author Administrator
	 */
	public static String unqualify(String qualifiedName, char separator) {
		return qualifiedName.substring(qualifiedName.lastIndexOf(separator) + 1);
	}

	/**
	 * 提取文件名,e.g. "mypath/myfile.txt" -> "myfile.txt".
	 * 
	 * @yhcip:title 提取文件名
	 * @yhcip:desc 提取文件名
	 * @param path the file path
	 * @return 文件名 "mypath/myfile.txt" -> "myfile.txt"
	 * @author Administrator
	 */
	public static String getFilename(String path) {
		int separatorIndex = path.lastIndexOf(FOLDER_SEPARATOR);
		return (separatorIndex != -1 ? path.substring(separatorIndex + 1) : path);
	}

	/**
	 * 给一个路径和相对路径，返回全路径 (i.e. "/" separators);
	 * 
	 * @yhcip:title 给一个路径和相对路径，返回全路径
	 * @yhcip:desc 给一个路径和相对路径，返回全路径
	 * @param path         the path to start from (usually a full file path)
	 * @param relativePath 相对路径
	 * @return
	 * @author Administrator
	 */
	public static String applyRelativePath(String path, String relativePath) {
		int separatorIndex = path.lastIndexOf(FOLDER_SEPARATOR);
		if (separatorIndex != -1) {
			String newPath = path.substring(0, separatorIndex);
			if (!relativePath.startsWith("/")) {
				newPath += "/";
			}
			return newPath + relativePath;
		} else {
			return relativePath;
		}
	}

	/**
	 * Normalize the path by suppressing sequences like "path/.." and inner simple
	 * dots folders.
	 * <p>
	 * The result is convenient for path comparison. For other uses, notice that
	 * Windows separators ("\") are replaced by simple dashes.
	 * 
	 * @yhcip:title Normalize the path by suppressing sequences like "path/.."
	 * @yhcip:desc Normalize the path by suppressing sequences like "path/.."
	 * @param path the original path
	 * @return the normalized path
	 * @author Administrator
	 */
	public static String cleanPath(String path) {
		String pathToUse = replace(path, WINDOWS_FOLDER_SEPARATOR, FOLDER_SEPARATOR);
		String[] pathArray = delimitedListToStringArray(pathToUse, FOLDER_SEPARATOR);
		List<String> pathElements = new LinkedList<String>();
		int tops = 0;
		for (int i = pathArray.length - 1; i >= 0; i--) {
			if (CURRENT_PATH.equals(pathArray[i])) {
				// do nothing
			} else if (TOP_PATH.equals(pathArray[i])) {
				tops++;
			} else {
				if (tops > 0) {
					tops--;
				} else {
					pathElements.add(0, pathArray[i]);
				}
			}
		}
		return collectionToDelimitedString(pathElements, FOLDER_SEPARATOR);
	}

	/**
	 * Compare two paths after normalization of them.
	 * 
	 * @yhcip:title Compare two paths after normalization of them.
	 * @yhcip:desc Compare two paths after normalization of them.
	 * @param path1 First path for comparizon
	 * @param path2 Second path for comparizon
	 * @return
	 * @author Administrator
	 */
	public static boolean pathEquals(String path1, String path2) {
		return cleanPath(path1).equals(cleanPath(path2));
	}

	/**
	 * Parse the given locale string into a java.util.Locale
	 * 
	 * @yhcip:title Parse the given locale string into a java.util.Locale
	 * @yhcip:desc Parse the given locale string into a java.util.Locale
	 * @param localeString
	 * @return
	 * @author Administrator
	 */
	public static Locale parseLocaleString(String localeString) {
		String[] parts = tokenizeToStringArray(localeString, "_ ", false, false);
		String language = parts.length > 0 ? parts[0] : "";
		String country = parts.length > 1 ? parts[1] : "";
		String variant = parts.length > 2 ? parts[2] : "";
		return (language.length() > 0 ? new Locale(language, country, variant) : null);
	}

	/**
	 * Append the given String to the given String array, returning a new array
	 * 
	 * @yhcip:title Append the given String to the given String array, returning a
	 *              new array
	 * @yhcip:desc Append the given String to the given String array, returning a
	 *             new array
	 * @param arr the array to append to
	 * @param str the String to append
	 * @return
	 * @author Administrator
	 */
	public static String[] addStringToArray(String[] arr, String str) {
		String[] newArr = new String[arr.length + 1];
		System.arraycopy(arr, 0, newArr, 0, arr.length);
		newArr[arr.length] = str;
		return newArr;
	}

	/**
	 * 对数组进行排序.
	 * 
	 * @yhcip:title 对数组进行排序.
	 * @yhcip:desc 对数组进行排序.
	 * @param source 原数组
	 * @return
	 * @author Administrator
	 */
	public static String[] sortStringArray(String[] source) {
		if (source == null) {
			return new String[0];
		}
		Arrays.sort(source);
		return source;
	}

	/**
	 * Take an array Strings and split each element based on the given delimiter. A
	 * <code>Properties</code> instance is then generated, with the left of the
	 * delimiter providing the key, and the right of the delimiter providing the
	 * value.
	 * <p>
	 * Will trim both the key and value before adding them to the
	 * <code>Properties</code> instance.
	 * 
	 * @yhcip:title
	 * @yhcip:desc
	 * @param array     the array to process
	 * @param delimiter to split each element using (typically the equals symbol)
	 * @return
	 * @author Administrator
	 */
	public static Properties splitArrayElementsIntoProperties(String[] array, String delimiter) {
		return splitArrayElementsIntoProperties(array, delimiter, null);
	}

	/**
	 * Take an array Strings and split each element based on the given delimiter. A
	 * <code>Properties</code> instance is then generated, with the left of the
	 * delimiter providing the key, and the right of the delimiter providing the
	 * value.
	 * <p>
	 * Will trim both the key and value before adding them to the
	 * <code>Properties</code> instance.
	 * 
	 * @yhcip:title
	 * @yhcip:desc
	 * @param array         the array to process
	 * @param delimiter     to split each element using (typically the equals
	 *                      symbol)
	 * @param charsToDelete
	 * @return
	 * @author Administrator
	 */
	public static Properties splitArrayElementsIntoProperties(String[] array, String delimiter, String charsToDelete) {

		if (array == null || array.length == 0) {
			return null;
		}

		Properties result = new Properties();
		for (int i = 0; i < array.length; i++) {
			String element = array[i];
			if (charsToDelete != null) {
				element = deleteAny(array[i], charsToDelete);
			}
			String[] splittedElement = split(element, delimiter);
			if (splittedElement == null) {
				continue;
			}
			result.setProperty(splittedElement[0].trim(), splittedElement[1].trim());
		}
		return result;
	}

	/**
	 * Tokenize the given String into a String array via a StringTokenizer. Trims
	 * tokens and omits empty tokens.
	 * <p>
	 * The given delimiters string is supposed to consist of any number of delimiter
	 * characters. Each of those characters can be used to separate tokens. A
	 * delimiter is always a single character; for multi-character delimiters,
	 * consider using <code>delimitedListToStringArray</code>
	 * 
	 * @yhcip:title
	 * @yhcip:desc
	 * @param str        the String to tokenize
	 * @param delimiters the delimiter characters, assembled as String
	 * @return
	 * @author Administrator
	 */
	public static String[] tokenizeToStringArray(String str, String delimiters) {
		return tokenizeToStringArray(str, delimiters, true, true);
	}

	/**
	 * Tokenize the given String into a String array via a StringTokenizer.
	 * <p>
	 * The given delimiters string is supposed to consist of any number of delimiter
	 * characters. Each of those characters can be used to separate tokens. A
	 * delimiter is always a single character; for multi-character delimiters,
	 * consider using <code>delimitedListToStringArray</code>
	 * 
	 * @yhcip:title
	 * @yhcip:desc
	 * @param str
	 * @param delimiters
	 * @param trimTokens
	 * @param ignoreEmptyTokens
	 * @return
	 * @author Administrator
	 */
	public static String[] tokenizeToStringArray(String str, String delimiters, boolean trimTokens, boolean ignoreEmptyTokens) {

		StringTokenizer st = new StringTokenizer(str, delimiters);
		List<String> tokens = new ArrayList<String>();
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			if (trimTokens) {
				token = token.trim();
			}
			if (!ignoreEmptyTokens || token.length() > 0) {
				tokens.add(token);
			}
		}
		return (String[]) tokens.toArray(new String[tokens.size()]);
	}

	/**
	 * Take a String which is a delimited list and convert it to a String array.
	 * <p>
	 * A single delimiter can consists of more than one character: It will still be
	 * considered as single delimiter string, rather than as bunch of potential
	 * delimiter characters - in contrast to <code>tokenizeToStringArray</code>.
	 * 
	 * @yhcip:title 把指定的字符串，通过指定的分隔符，转化为一个字符串数组
	 * @yhcip:desc 把指定的字符串，通过指定的分隔符，转化为一个字符串数组
	 * @param str
	 * @param delimiter
	 * @return
	 * @author Administrator
	 */
	public static String[] delimitedListToStringArray(String str, String delimiter) {
		if (str == null) {
			return new String[0];
		}
		if (delimiter == null) {
			return new String[] { str };
		}

		List<String> result = new ArrayList<String>();
		int pos = 0;
		int delPos = 0;
		while ((delPos = str.indexOf(delimiter, pos)) != -1) {
			result.add(str.substring(pos, delPos));
			pos = delPos + delimiter.length();
		}
		if (str.length() > 0 && pos <= str.length()) {
			// Add rest of String, but not in case of empty input.
			result.add(str.substring(pos));
		}

		return (String[]) result.toArray(new String[result.size()]);
	}

	/**
	 * Convert a CSV list into an array of Strings.
	 * 
	 * @yhcip:title 将一个以','分隔的字符串转化为字符数组.
	 * @yhcip:desc 将一个以','分隔的字符串转化为字符数组.
	 * @param str
	 * @return
	 * @author Administrator
	 */
	public static String[] commaDelimitedListToStringArray(String str) {
		return delimitedListToStringArray(str, ",");
	}

	/**
	 * Convenience method to convert a CSV string list to a set.
	 * 
	 * @yhcip:title 将一个以','分隔的字符串转化为 set.
	 * @yhcip:desc 将一个以','分隔的字符串转化为 set.
	 * @param str
	 * @return
	 * @author Administrator
	 */
	public static Set<String> commaDelimitedListToSet(String str) {
		Set<String> set = new TreeSet<String>();
		String[] tokens = commaDelimitedListToStringArray(str);
		for (int i = 0; i < tokens.length; i++) {
			set.add(tokens[i]);
		}
		return set;
	}

	/**
	 * Convenience method to return a String array as a delimited (e.g. CSV)
	 * 
	 * @yhcip:title 将Object数组，转化为字符串
	 * @yhcip:desc 将Object数组，转化为字符串
	 * @param arr
	 * @param delim
	 * @return
	 * @author Administrator
	 */
	public static String arrayToDelimitedString(Object[] arr, String delim) {
		if (arr == null) {
			return "";
		}

		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < arr.length; i++) {
			if (i > 0) {
				sb.append(delim);
			}
			sb.append(arr[i]);
		}
		return sb.toString();
	}

	/**
	 * Convenience method to return a String array as a CSV String. E.g. useful for
	 * toString() implementations.
	 * 
	 * @yhcip:title 将对象数给转化为字符串，以','分隔
	 * @yhcip:desc 将对象数给转化为字符串，以','分隔
	 * @param arr
	 * @return
	 * @author Administrator
	 */
	public static String arrayToCommaDelimitedString(Object[] arr) {
		return arrayToDelimitedString(arr, ",");
	}

	/**
	 * 对字符串进行加密. If exception, the plain credentials
	 * 
	 * @yhcip:title 对字符串进行加密
	 * @yhcip:desc 对字符串进行加密
	 * @param password  Password or other credentials to use in authenticating
	 * @param algorithm Algorithm used to do the digest
	 * @return
	 * @author Administrator
	 */
	public static String encodePassword(String password, String algorithm) {
		byte[] unencodedPassword = password.getBytes();

		MessageDigest md = null;

		try {
			// first create an instance, given the provider
			md = MessageDigest.getInstance(algorithm);
		} catch (Exception e) {
			log.error("Exception: " + e);

			return password;
		}

		md.reset();

		// call the update method one or more times
		// (useful when you don't know the size of your data, eg. stream)
		md.update(unencodedPassword);

		// now calculate the hash
		byte[] encodedPassword = md.digest();

		StringBuffer buf = new StringBuffer();

		for (int i = 0; i < encodedPassword.length; i++) {
			if ((encodedPassword[i] & 0xff) < 0x10) {
				buf.append("0");
			}

			buf.append(Long.toString(encodedPassword[i] & 0xff, 16));
		}

		return buf.toString();
	}

	/**
	 * 对指定字符串进行Base64编码
	 * 
	 * @yhcip:title 对指定字符串进行Base64编码
	 * @yhcip:desc 对指定字符串进行Base64编码
	 * @param str
	 * @return
	 * @author Administrator
	 */
	public static String encodeString(String str) {
		Encoder encoder = Base64.getEncoder();
		return encoder.encodeToString(str.getBytes()).trim();
	}

	/**
	 * 对指定字符串进行Base64反编码
	 * 
	 * @yhcip:title 对指定字符串进行Base64反编码
	 * @yhcip:desc 对指定字符串进行Base64反编码
	 * @param str
	 * @return
	 * @author Administrator
	 */
	public static String decodeString(String str) {
		Decoder decoder = Base64.getDecoder();
		return new String(decoder.decode(str));
	}

	/**
	 * 中文处理
	 * 
	 * @yhcip:title 中文处理 以ISO8859_1转换为GBK
	 * @yhcip:desc 中文处理 以ISO8859_1转换为GBK
	 * @param strvalue
	 * @return
	 * @author Administrator
	 */
	public static String toChinese(String strvalue) {
		try {
			if (strvalue == null)
				return null;
			else {
				strvalue = new String(strvalue.getBytes("ISO8859_1"), "GBK");
				return strvalue;
			}
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * 判断 szStr1 > szStr2, 大于返回1,等于返回0, 小于返回-1
	 * 
	 * @param szStr1
	 * @param szStr2
	 * @return
	 */
	public static final int compareTo(String szStr1, String szStr2) {
		return szStr1.compareTo(szStr2);
	}


	/**
	 * 汉字串转首字母拼音缩写
	 * 
	 * @param str //要转换的汉字字符串
	 * @return String //拼音缩写
	 */
	public static String getPYString(String str) {
		String tempStr = "";
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c >= 33 && c <= 126) {// 字母和符号原样保留
				tempStr += String.valueOf(c);
			} else {// 累加拼音声母
				tempStr += getPYChar(String.valueOf(c));
			}
		}
		return tempStr;
	}

	/**
	 * 取单个字符的拼音声母
	 * 
	 * @param c //要转换的单个汉字
	 * @return String 拼音声母
	 */
	public static String getPYChar(String c) {
		if (null == c || 0 == c.trim().length()) {
			return c;
		}
		byte[] array = new byte[2];
		array = String.valueOf(c).getBytes();
		if (2 > array.length) {
			return c;
		}
		int i = (short) (array[0] - '\0' + 256) * 256 + ((short) (array[1] - '\0' + 256));
		if (i < 0xB0A1) {
			return "*";
		}
		if (i < 0xB0C5) {
			return "a";
		}
		if (i < 0xB2C1) {
			return "b";
		}
		if (i < 0xB4EE) {
			return "c";
		}
		if (i < 0xB6EA) {
			return "d";
		}
		if (i < 0xB7A2) {
			return "e";
		}
		if (i < 0xB8C1) {
			return "f";
		}
		if (i < 0xB9FE) {
			return "g";
		}
		if (i < 0xBBF7) {
			return "h";
		}
		if (i < 0xBFA6) {
			return "j";
		}
		if (i < 0xC0AC) {
			return "k";
		}
		if (i < 0xC2E8) {
			return "l";
		}
		if (i < 0xC4C3) {
			return "m";
		}
		if (i < 0xC5B6) {
			return "n";
		}
		if (i < 0xC5BE) {
			return "o";
		}
		if (i < 0xC6DA) {
			return "p";
		}
		if (i < 0xC8BB) {
			return "q";
		}
		if (i < 0xC8F6) {
			return "r";
		}
		if (i < 0xCBFA) {
			return "s";
		}
		if (i < 0xCDDA) {
			return "t";
		}
		if (i < 0xCEF4) {
			return "w";
		}
		if (i < 0xD1B9) {
			return "x";
		}
		if (i < 0xD4D1) {
			return "y";
		}
		if (i < 0xD7FA) {
			return "z";
		}
		return "*";
	}

	public static String getRandomString(int length) { // length表示生成字符串的长度
		String base = "abcdefghijklmnopqrstuvwxyz0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		return sb.toString();
	}

	/**
	 * 判断String 是否为Null或空字符串，如果str是Null或空字符串，返回true，否则返回false。
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isEmpty(String str) {
		if (str == null || "".equals(str.trim()) || "null".equals(str.trim()) || "undefined".equals(str)) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isNotEmpty(String str) {
		return !isEmpty(str);
	}

	/**
	 * 将null转换成""
	 */
	public static String null2String(String str) {
		return str == null || "null".equals(str.trim()) ? "" : str;
	}

	/**
	 * 将null转换成指定内容
	 */
	public static String null2String(String str, String toStr) {
		return str == null || "null".equals(str.trim()) ? toStr : str;
	}

	/**
	 * 将"未填写"转换成null
	 * 
	 * @param str
	 * @return
	 */
	public static String cleanStr(String str) {
		return str == null ? null : str.trim().equals("未填写") ? null : str.trim();
	}

	/**
	 * 将字符串的第一个字符转换成大写
	 * 
	 * @param str
	 * @return
	 */
	public static String firstLetterUpper(String str) {
		if (str == null || "".equals(str)) {
			throw new NullPointerException("str is null");
		}
		return (Character.valueOf(str.charAt(0)).toString().toUpperCase() + str.substring(1));
	}

	/**
	 * 将金额字符串转换格式。 如：input：20000000.00 === return 20,000,000.00 或者 input：20000000
	 * === return 20,000,000
	 * 
	 * @param money
	 * @return String add by chentianjin 2016-09-24
	 */
	public static String getMoneyStyle(String money) {
		if (StringUtil.isEmpty(money)) {
			return money;
		}
		StringBuffer result = new StringBuffer();
		String tempStr = "";
		int tempLength = 0, length = money.length();
		int pointIndex = money.indexOf(".") > 0 ? length - money.indexOf(".") : 0;
		for (int i = 0, j = (length - pointIndex) / 3; i < j; i++) {
			tempLength = length - ((j - i) * 3) - pointIndex;
			if (tempLength == 0) {
				continue;
			}
			tempStr = money.substring(0, tempLength);
			money = money.substring(tempLength);
			length = money.length();
			result.append(tempStr).append(",");
		}
		result.append(money);
		return result.toString();
	}

	/**
	 * 去除字符串中的空格、回车、换行符、制表符及特殊字符
	 * 
	 * @param str
	 * @return
	 */
	public static String replaceBlank(String str) {
		if (StringUtil.isNotEmpty(str)) {
			String regEx = "[／`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？|\\s*|\t|\r|\n]";
			Pattern p = Pattern.compile(regEx);
			Matcher m = p.matcher(str);
			str = m.replaceAll("");
		}
		return str;
	}

	/**
	 * 规范分隔符:将字符串中不规范的分隔符，转化证想要的分隔符
	 * 
	 * @author: chentianjin
	 * @date: 2020年9月1日 上午10:33:57
	 * @param str
	 * @param separator
	 * @return
	 */
	public static String replaceBlank(String str, String separator) {
		if (StringUtil.isNotEmpty(str)) {
			String regEx = "[／`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？|\\s*|\t|\r|\n]";
			Pattern p = Pattern.compile(regEx);
			Matcher m = p.matcher(str);
			str = m.replaceAll(separator);
		}
		return str;
	}

	/**
	 * 正则表达式验证是否手机号
	 * 
	 * @param mobiles
	 * @return
	 * @return boolean
	 * @author chentianjin
	 * @date 2017年8月8日
	 */
	public static boolean isMobileNO(String mobiles) {
		// String telRegex =
		// "^((17[0-9])|(14[0-9])|(13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
		String telRegex = "[1][3456789]\\d{9}";// "[1]"代表第1位为数字1，"[358]"代表第二位可以为3、5、8中的一个，"\\d{9}"代表后面是可以是0～9的数字，有9位。
		if (isEmpty(mobiles)) {
			return false;
		} else {
			return mobiles.matches(telRegex);
		}
	}

	/**
	 * 验证是否为数字
	 * 
	 * @param str
	 * @return
	 */
	public static boolean isNum(String str) {
		// 带小数的
		Pattern pattern = Pattern.compile("^[-+]?[0-9]+(\\.[0-9]+)?$");

		if (pattern.matcher(str).matches()) {
			// 数字
			return true;
		} else {
			// 非数字
			return false;
		}
	}

	/**
	 * 整型字符串,分成数组
	 * 
	 * @param strs
	 * @return
	 */
	public static List<Integer> getIntList(String strs) {
		List<Integer> results = new ArrayList<Integer>();
		if (strs != null && !"".equals(strs)) {
			String ss[] = strs.split(",");
			for (String s : ss) {
				results.add(Integer.parseInt(s));
			}
		}
		return results;
	}

	/*
	 * 将字符串编码成16进制数字,适用于所有字符（包括中文）
	 */
	public static String str2Hex(String str) {
		if (StringUtil.isEmpty(str)) {
			return "";
		}
		String hexString = "0123456789ABCDEF";
		// 根据默认编码获取字节数组
		byte[] bytes = str.getBytes();
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		// 将字节数组中每个字节拆解成2位16进制整数
		for (int i = 0; i < bytes.length; i++) {
			sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
			sb.append(hexString.charAt((bytes[i] & 0x0f) >> 0));
		}
		return sb.toString();
	}

}