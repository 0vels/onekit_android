package cn.onekit;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;

import java.io.BufferedReader;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class ASSET {
	/**
	 * 加载二进制
	 * @param context		上下文
	 * @param path			路径
     * @return				二进制
     */
	public static byte[] loadData(Context context,String path){
		String s = ASSET.loadString(context, path);
		return s.getBytes();
	}
	/**
	 *加载字符串
	 * @param context		上下文
	 * @param path			路径
     * @return				字符串
     */
	public static String loadString(Context context,String path) {
		try {
			InputStreamReader inputReader = new InputStreamReader(
					context.getResources().getAssets().open(path));
			BufferedReader bufReader = new BufferedReader(inputReader);
			String line = "";
			String Result = "";
			while ((line = bufReader.readLine()) != null)
				Result += line;
			return Result;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 加载XML
	 * @param context  上下文
	 * @param path		 路径
     * @return			 XML
     */
	public static Document loadXML(Context context,String path){
		String s = ASSET.loadString(context, path);
		return XML.parse(s);
	}
	/**
	 * 加载JSON
	 * @param context		上下文
	 * @param path			路径
     * @return				JSON
     */
	public static JSONObject loadJSON(Context context,String path) {
		String string = null;

		if (path.startsWith("/")) {
			path = path.substring(1, path.length());
		}

		if (path.endsWith("/")) {
			path = path.substring(0, path.length() - 1);
		}

		string = loadString(context,path);
		if (STRING.isEmpty(string)) {
			return null;
		}
		return JSON.parse(string);
	}

	public static JSONArray loadJSONs(Context context,String path) {
		String string = null;

		if (path.startsWith("/")) {
			path = path.substring(1, path.length());
		}

		if (path.endsWith("/")) {
			path = path.substring(0, path.length() - 1);
		}

		string = loadString(context,path);
		if (STRING.isEmpty(string)) {
			return null;
		}
		return JSON.parses(string);
	}

	/**
	 * 加载图片
	 * @param context		上下文
	 * @param path			路径
     * @return				图片
     */
	@SuppressWarnings("deprecation")
	public static Bitmap loadImage(Context context,String path) {
		try {
			InputStream stream = context.getResources().getAssets().open(path);
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inPurgeable = true;
			options.inInputShareable = true;
			/*if (isMini) {
				options.inPreferredConfig = Bitmap.Config.ARGB_8888;
				options.inSampleSize = 2;
			}*/
			Bitmap RESULT = BitmapFactory.decodeStream(stream, null, options);
			// AJAX.DATA.put(name, RESULT);
			stream.close();
			return RESULT;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static void copy(Context context,String fromPath, String toPath) {
		InputStream myInput;
		OutputStream myOutput;
		try {
			myOutput = new FileOutputStream(toPath);
			myInput = context.getAssets().open(fromPath);
			byte[] buffer = new byte[1024];
			int length = myInput.read(buffer);
			while (length > 0) {
				myOutput.write(buffer, 0, length);
				length = myInput.read(buffer);
			}
			myOutput.flush();
			myInput.close();
			myOutput.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}