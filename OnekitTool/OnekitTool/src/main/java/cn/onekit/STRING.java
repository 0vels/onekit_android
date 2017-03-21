package cn.onekit;

import android.graphics.Paint;
import android.graphics.Rect;

public class STRING {
	/**
	 * exists() 判断字符串是否为空
	 * 
	 * @param str
	 *            :字符串
	 * */
	public static boolean isEmpty(String str) {
		return (str == null || str.equals(""));
	}
	public static String fill(int value, int length) {
		String RESULT = value + "";
		while (RESULT.length() < length) {
			RESULT = "0" + RESULT;
		}
		return RESULT;
	}
	/**
	 * toString 数组转字符串
	 * 
	 * @param array
	 *            :数组
	 * @param seperator
	 *            :分离参数
	 * */
	public static String toString(Object[] array, String seperator) {
		if (array == null) {
			return null;
		}
		String RESULT = "";
		for (Object item : array) {
			if (!RESULT.equalsIgnoreCase("")) {
				RESULT += seperator;
			}
			RESULT += item.toString();
		}
		return RESULT;
	}

	/**
	 * parse 字符串转数组
	 * 
	 * @param str
	 *            :字符串
	 * @param seperator
	 *            :分离参数
	 * */
	public static Object[] parse(String str, String seperator) {
		if (str == null) {
			return null;
		}
		Object[] stringArr = str.split(seperator);
		return stringArr;
	}

	/**
	 * indexOf  子串首个位置
	 *
	 * @param str1   字符串
	 * @param str2   子串
     * @return  下标
     */
	public static int indexOf (String str1,String str2){
		int i = str1.indexOf(str2);
		return i;
	}

	/**
	 * lastIndexOf 子串尾个位置
	 *
	 * @param str1  字符串
	 * @param str2  子串
     * @return  下标
     */
	public static int lastIndexOf(String str1,String str2){
		int i = str1.lastIndexOf(str2);
		return i;
	}

	/**
	 * startWith	开始于子串
	 * @param str1    字符串
	 * @param str2    子串
     * @return
     */
	public static boolean startWith(String str1,String str2){
		boolean b = str1.startsWith(str2);
		return b;
	}

	/**
	 * endWith	结束于子串
	 * @param str1   字符串
	 * @param str2   子串
     * @return
     */
	public static boolean endWith(String str1,String str2){
		boolean b = str1.endsWith(str2);
		return b;
	}

	/**
	 * firstUpper	首字母大写
	 * @param str   字符串
	 * @return
     */
	public static String firstUpper(String str){
		String s = str.substring(0, 1).toUpperCase() + str.substring(1);
		return s;
	}

	/**
	 * size	占领尺寸
	 * @param str 	字符串
	 * @return
     */
	public static String size(String str,float fontsize){
		Paint paint= new Paint();
		Rect rect = new Rect();
		paint.setTextSize(fontsize);
		//返回包围整个字符串的最小的一个Rect区域
		paint.getTextBounds(str, 0, str.length(), rect);
		int strwidth = rect.width();
		int strheight = rect.height();
		return new SIZE(strwidth,strheight).result();
	}
}
