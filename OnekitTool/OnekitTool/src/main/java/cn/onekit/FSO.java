package cn.onekit;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

/**
 * 需要写入文件的权限：android.permission.WRITE_EXTERNAL_STORAGE
 */
public class FSO {

	private static String getPath(Context context){
		String dir = context.getFilesDir().getAbsolutePath() ;
		return dir;
	}
	private static String createPath(Context context,String path){
		String dir ="";
		if (path.startsWith("/")){
			dir = getPath(context) + path;
		}else {
			dir = getPath(context) + File.separator + path;
		}
		Log.e("---",dir);
		return dir;
	}
	private static File createDir(Context context,String path){
		String path1 = createPath(context, path);
		File file = new File(path1);
			if (file.exists()) { // 判断文件是否存在
				if (file.isFile()) { // 判断是否是文件
					file.delete();
				} else if (file.isDirectory()) { // 否则如果它是一个目录
					File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
					for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
							files[i].delete();
					}
				}
			}

			//文件不存在则创建文件，先创建目录
			File dir2 = new File(file.getParent());
			Log.e("父目录",dir2.toString());
			dir2.mkdirs();
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
		}
		return file;
	}
	/**
	 * isExist() 是否为空
	 */
	public static Boolean isExist(Context context,String path) {
		String path1 = createPath(context, path);
		File file = new File(path1);
		return file.exists();
	}



	public static Boolean delete(Context context,String path) {
		String path1 = createPath(context, path);
		return delete(context ,new File(path1));
	}

	/**
	 * delete 删除文件
	 *
	 * @param file
	 *            :文件
	 */
	public static Boolean delete(Context context,File file) {
		if (file.exists()) { // 判断文件是否存在
			if (file.isFile()) { // 判断是否是文件
				file.delete();
			} else if (file.isDirectory()) { // 否则如果它是一个目录
				File files[] = file.listFiles(); // 声明目录下所有的文件 files[];
				for (int i = 0; i < files.length; i++) { // 遍历目录下所有的文件
					files[i].delete();
				}
			}
			 DIALOG.alert(context,"删除成功");
			return true;
		} else {
			DIALOG.alert(context,"文件不存在");
			return false;
		}
	}

	/**
	 * 存比特数组
	 * @param context
	 * @param path
	 * @param bytes
	 * @param callback
     */
	public static void saveBytes(Context context,String path,byte[] bytes,CALLBACK1<Boolean> callback){
		FileOutputStream fos = null;
		File dir = createDir(context, path);
		try {
			fos = new FileOutputStream(dir);
			fos.write(bytes);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				fos.close();
				if (fos != null){
					callback.run(true);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 加载byte数组
	 * @param context
	 * @param path
	 * @param callback
     */
	public static void loadBytes(Context context, String path, final CALLBACK1<byte[]> callback){
		FileInputStream fis = null;
		String path1 = createPath(context, path);
		File file = new File(path1);
		byte[] bytes = new byte[(int) file.length()];
		try {
			fis = new FileInputStream(file);
			fis.read(bytes);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				fis.close();
				if (fis != null){
					callback.run(bytes);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * loadData 加载数据
	 * 
	 * @param path
	 *            :文件路径
	 */

	public static void loadData(Context context, String path, final CALLBACK1<InputStream> callback){
		InputStream inputStream = null;
		String path1 = createPath(context, path);
		byte[] b = null;
		File file = new File(path1);
		try {
			inputStream = new FileInputStream(file);
			b=new byte[(int)file.length()];     //创建合适文件大小的数组
			inputStream.read(b);    //读取文件中的内容到b[]数组

		} catch (FileNotFoundException e) {
			DIALOG.alert(context,"当前路径不存在！");
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				inputStream.close();
				if (b != null){
					DATA.bytes2data(b, new CALLBACK1<InputStream>() {
						@Override
						public void run(InputStream arg1) {
							callback.run(arg1);
						}
					});
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 保存Data
	 * @param path
	 * @param data
	 * @param callback
     */
	public static void saveData(Context context,String path,InputStream data,CALLBACK1<Boolean> callback){
		File file = createDir(context, path);
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			byte[] b = new byte[1024];
			while((data.read(b)) != -1){
				fos.write(b);
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				fos.close();
				if (fos != null){
					callback.run(true);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	/**
	 * sava 保存任意类型数据
	 *
	 * @param path
	 *            :文件路径
	 * @param data
	 *            :任意类型
	 */
	private static void sava(Context context,String path,Object data,CALLBACK1<Boolean> callback){
		File file = createDir(context, path);
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		try {
			fos = new FileOutputStream(file);
			oos = new ObjectOutputStream(fos);
			oos.writeObject(data);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				oos.close();
				fos.close();
				if (oos != null){
					callback.run(true);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * loadString 加载字符串
	 *
	 * @param path
	 *            :文件路径
	 */
	public static void loadString(Context context,String path,CALLBACK1<String> callback) {
		String path1 = createPath(context, path);
		File file = new File(path1);
		StringBuffer RESULT = new StringBuffer();
		try {
			if (file.exists()) { // 判断文件是否存在
				InputStream stream = new FileInputStream(file);
				InputStreamReader streamReader = new InputStreamReader(stream);
				BufferedReader bufferedReader = new BufferedReader(streamReader);
				String temp;
				while ((temp = bufferedReader.readLine()) != null) {
					RESULT.append(temp);
				}
				if (RESULT != null){
					callback.run(RESULT.toString());
				}
				bufferedReader.close();
				streamReader.close();
				stream.close();
			} else {
				DIALOG.alert(context,"文件不存在");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * saveString 保存字符串
	 *
	 * @param path
	 *            :文件名
	 * @param string
	 *            :字段
	 */
	public static void saveString(Context context,String path, String string, CALLBACK1<Boolean> callback) {

		File file = createDir(context, path);
		try {
			FileOutputStream stream = new FileOutputStream(file);
			OutputStreamWriter writer = new OutputStreamWriter(stream, "utf-8");
			writer.write(string);
			writer.flush();
			writer.close();
			stream.close();
			if (writer != null){
				callback.run(true);
			}
		} catch (Exception e) {
			DIALOG.alert(context,"当前路径不存在！");
			e.printStackTrace();
		}
	}

	/**
	 * loadJSON 加载JSON
	 *
	 * @param path
	 *            :文件路径
	 */
	public static void loadJSON(Context context, String path, final CALLBACK1<JSONObject> callback) {

		loadString(context, path, new CALLBACK1<String>() {
			@Override
			public void run(String string) {
				if (STRING.isEmpty(string)) {
					return ;
				}
				callback.run(JSON.parse(string));
			}
		});

	}

	public static void loadJSONs(Context context, String path, final CALLBACK1<JSONArray> callback) {

		loadString(context, path, new CALLBACK1<String>() {
			@Override
			public void run(String string) {
				if (STRING.isEmpty(string)) {
					return ;
				}
				callback.run(JSON.parses(string));
			}
		});
	}

	/***
	 * saveJSON 保存JSON
	 *
	 * @param path
	 *            :保存的文件名
	 * @param jsonObject
	 *            :JSON数组（字典）
	 *
	 */
	public static void saveJSON(Context context,String path, JSONObject jsonObject, final CALLBACK1<Boolean> callback) {
		saveString(context, path, JSON.stringify(jsonObject), new CALLBACK1<Boolean>() {
			@Override
			public void run(Boolean arg1) {
				callback.run(arg1);
			}
		});

	}public static void saveJSONs(Context context,String path, JSONArray jsonArray, final CALLBACK1<Boolean> callback) {
		saveString(context, path, JSON.stringify(jsonArray), new CALLBACK1<Boolean>() {
			@Override
			public void run(Boolean arg1) {
				callback.run(true);
			}
		});
	}

	/**
	 * loadImage 加载图片
	 *  @param context
	 * @param path
	 */

	public static void loadImage(Context context, String path, CALLBACK1<Bitmap> callback) {
		String path1 = createPath(context, path);
		File file = new File(path1);
		try {
			Bitmap RESULT;
			FileInputStream stream = new FileInputStream(file);
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inPurgeable = true;
			options.inInputShareable = true;
			/*if (isMini) {
				options.inPreferredConfig = Bitmap.Config.RGB_565;
				options.inSampleSize = 2;
			}*/
			RESULT = BitmapFactory.decodeStream(stream, null, options);

			stream.close();
			if (RESULT != null){
				callback.run(RESULT);
			}
		} catch (IOException e) {
			DIALOG.alert(context,"当前路径不存在！");
			e.printStackTrace();
		}
	}

	/**
	 * saveImage 保存图片
	 * 
	 * @param bm
	 *            :图片
	 * @param path
	 *            :文件路径
	 * @param
	 *            :文件名
	 */
	public static void saveImage(Context context, String path,final Bitmap bm, CALLBACK1<Boolean> callback) {
		File imageFile = createDir(context, path);
		BufferedOutputStream bos;
		try {
			bos = new BufferedOutputStream(new FileOutputStream(imageFile));
			if (path.endsWith("jpg")) {
				bm.compress(Bitmap.CompressFormat.JPEG, 90, bos);
			} else if (path.endsWith("png")) {
				bm.compress(Bitmap.CompressFormat.PNG, 90, bos);
			}
			bos.flush();
			bos.close();
			if (bos != null){
				callback.run(true);

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 存XML
	 * @param path
	 * @param document
     */
	public static void saveXML(Context context,String path, Document document, final CALLBACK1<Boolean> callback){
		saveString(context, path, XML.stringify(document), new CALLBACK1<Boolean>() {
			@Override
			public void run(Boolean arg1) {
				callback.run(true);
			}
		});
	}
	/**
	 * 加载XML
	 * @param path	 文件路径
	 * @return		 Document
     */
	public static void loadXML(Context context,String path, CALLBACK1<Document> callback){
		String dirPath = createPath(context,path);
		DocumentBuilder documentBuilder = null;
		Document doc = null;
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		try {
			InputSource inputSource = new InputSource(new FileInputStream(new File(dirPath)));
			documentBuilder = factory.newDocumentBuilder();
			doc = documentBuilder.parse(inputSource);
			if (doc != null){
				callback.run(doc);
			}
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			DIALOG.alert(context,"当前路径不存在！");
			e.printStackTrace();
		}
	}

}