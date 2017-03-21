package cn.onekit;

import android.graphics.Color;

public class COLOR {

	/**
	 * 颜色转字符串
	 * 
	 * @param color 颜色
	 * */
	public static String toString(Integer color) {
		if (color == 0) {
			return "#00000000";
		}
		String red = Integer.toHexString((color & 0xff0000) >> 16);
		String green = Integer.toHexString((color & 0x00ff00) >> 8);
		String blue = Integer.toHexString((color & 0x0000ff));
		if (red.length() == 1) {
			red = String.format("%1s%2s", 0, red).replace(" ", "");
		}
		if (green.length() == 1) {
			green = String.format("%1s%2s", 0, green).replace(" ", "");
		}
		if (blue.length() == 1) {
			blue = String.format("%1s%2s", 0, blue).replace(" ", "");
		}
		return '#' + red + green + blue;
	}

	/**
	 * 字符串转颜色
	 * 
	 * @param str 字符串
	 * */
	@SuppressWarnings("static-access")
	public static Integer fromString(String str) {
		Color color = new Color();
		return color.parseColor(str);
	}

	/**
	 * 颜色转RGB
	 * @param color
	 * @return
     */
	public static String toRGB(Integer color){
		int red = (color & 0xff0000) >> 16;
		int green = (color & 0x00ff00) >> 8;
		int blue = (color & 0x0000ff);
		return "rgb(" + red + "," + green + "," + blue +")";
	}

	/**
	 * 颜色转RGBA
	 * @param color
	 * @return
     */
	public static String toRGBA(Integer color){
		int red = (color & 0xff000000) >> 32;
		int green = (color & 0x00ff0000) >> 16;
		int blue = (color & 0x0000ff00) >> 8;
		int alpha = (color & 0x000000ff);
		return "rgba(" + red + "," + green + "," + blue +","+alpha/255.0+")";
	}
}
