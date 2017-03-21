package cn.onekit;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.preference.PreferenceManager;
import android.util.Base64;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class CONFIG {

	/**
	 * set 用户配置
	 * 
	 * @param key
	 *            键值
	 * @param Value
	 *            数据
	 */

	private static void set(Context context, String key, Object Value) {
		SharedPreferences spf = PreferenceManager.getDefaultSharedPreferences(context);
		Editor edit = spf.edit();
		edit.putString(key, Value != null ? Value.toString() : null);
		edit.apply();
	}

	/**
	 * set 获取配置
	 * 
	 * @param key
	 *            键值
	 */
	private static Object get(Context context, String key) {
		SharedPreferences spf = PreferenceManager.getDefaultSharedPreferences(context);
		String string = spf.getString(key, null);
		return string;
	}

	public static void setString(Context context,String key,String string){
		SharedPreferences spf = PreferenceManager.getDefaultSharedPreferences(context);
		Editor edit = spf.edit();
		edit.putString(key,string);
		edit.apply();
	}

	public static String getString(Context context,String key){
		SharedPreferences spf = PreferenceManager.getDefaultSharedPreferences(context);
		return spf.getString(key, null);
	}

	/**
	 * 保存图片到SharedPreferences
	 * @param context		上下文
	 * @param key			键
	 * @param image		图片
     */
	public static void setImage(Context context,String key,Bitmap image){
		ByteArrayOutputStream bStream=new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.PNG,100,bStream);
		byte[] bytes = bStream.toByteArray();
		String string = Base64.encodeToString(bytes,Base64.DEFAULT);
		try {
			bStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		setString(context,key,string);
	}

	/**
	 * 获取图片
	 * @param context	上下文
	 * @param key		键
     * @return			返回图片
     */
	public static Bitmap getImage(Context context, String key){
		String string = getString(context, key);
		if (string == null){
			return null;
		}
		byte[] byteArray= Base64.decode(string, Base64.DEFAULT);
		ByteArrayInputStream byteArrayInputStream=new ByteArrayInputStream(byteArray);
		Bitmap bitmap= BitmapFactory.decodeStream(byteArrayInputStream);
		try {
			byteArrayInputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (bitmap != null){
			return bitmap;
		}else {
			return null;
		}
	}

	/**
	 * 存JSONObject到SharedPreferences
	 * @param context		上下文
	 * @param key			键
	 * @param jsonObject		json
     */
	public static void setJSON(Context context,String key,JSONObject jsonObject){
		String stringify = JSON.stringify(jsonObject);
		setString(context,key,stringify);
	}
	/**
	 * 存JSONArray到SharedPreferences
	 * @param context	上下文
	 * @param key		键
	 * @param jsonArray		json
	 */
	public static void setJSONs(Context context,String key,JSONArray jsonArray){
		String stringify = JSON.stringify(jsonArray);
		setString(context,key,stringify);
	}
	/**
	 * 获取JSON
	 * @param context  上下文
	 * @param key	键
     * @return		返回json
     */
	public static JSONObject getJSON(Context context, String key){
		String string = getString(context, key);
		JSONObject jsonObject = JSON.parse(string);
		return jsonObject;
	}
	/**
	 * 获取JSONArray
	 * @param context  上下文
	 * @param key		键
	 * @return			返回值
	 */
	public static JSONArray getJSONs(Context context, String key){
		String string = getString(context, key);
		JSONArray jsonArray = JSON.parses(string);
		if (jsonArray != null){
			return jsonArray;
		}else {
			return null;
		}
	}
	/**
	 * 存XML到SharedPreferences
	 * @param context		上下文
	 * @param key			键
	 * @param xml			xml参数
     */
	public static void setXML(Context context,String key,Document xml){
		String stringify = XML.stringify(xml);
		setString(context,key,stringify);
	}

	/**
	 * 获取xml
	 * @param context		上下文
	 * @param key			键
     * @return				返回xml
     */
	public static Document getXML(Context context,String key){
		String string = getString(context, key);
		Document document = XML.parse(string);
		return document;
	}

	/**
	 * 存二进制到SharedPreferences
	 * @param context		上下文
	 * @param key			键
	 * @param bytes		比特数组
     */
	public static void setBytes(Context context,String key,byte[] bytes){
		String string = Base64.encodeToString(bytes,Base64.DEFAULT);  //编码
		setString(context,key,string);
	}
	/**
	 * 获取二进制
	 * @param context		上下文
	 * @param key			键
     * @return				返回比特数组
     */
	public static byte[] getBytes(Context context,String key){
		String string = getString(context, key);
		if (string == null){
			return null;
		}
		byte[] bytes = Base64.decode(string, Base64.DEFAULT);     //解码
		return bytes;
	}

	/**
	 * 存DATA
	 * @param context		上下文
	 * @param key			键
	 * @param data			stream流
     */
	public static void setData(Context context, String key, InputStream data){
		String s = DATA.data2base64(data);
		setString(context,key,s);
	}

	/**
	 * 获取Data
	 * @param context		上下文
	 * @param key			键
     * @return				返回steam
     */
	public static InputStream getData(Context context,String key){
		String string = getString(context, key);
		if (string == null){
			return null;
		}
		InputStream inputStream = DATA.base642data(string);
		return inputStream;
	}
}
