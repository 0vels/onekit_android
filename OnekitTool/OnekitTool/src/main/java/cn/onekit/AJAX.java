
package cn.onekit;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EncodingUtils;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Document;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Exchanger;


public class AJAX {

	/**
	 * 设置是否为demo方式
	 */

	public enum Mode {
		GET, POST, PUT, DELETE
	}

	private static String message;

	public static void get(String url, Map<String, Object> params,
										   final CALLBACK1<HttpResponse> successCallback,
										   final CALLBACK1<Exception> failureCallback, Mode mode) {
		try {
			List<NameValuePair> pairs;
			String query = "";
			JSONObject QUERY = new JSONObject();
			if (params != null) {
				pairs = new ArrayList<NameValuePair>();
				for (String key : params.keySet()) {
					Object value = params.get(key);
					if (value == null) {
						continue;
					}
					String json;
					if (value.getClass().isArray()) {
						json = new JSONArray((Collection) value).toString();
					} else if (value.getClass() == Map.class) {
						json = new JSONObject((Map) value).toString();
					} else {
						json = value.toString();
					}
					String string = json;
					if (mode == Mode.GET || mode == Mode.DELETE) {
						query += "&" + key + "=" + URLEncoder.encode(string);
					} else {
						pairs.add(new BasicNameValuePair(key, string));
					}
					QUERY.put(key, string);
				}
			} else {
				pairs = null;
			}
			final HttpRequestBase request;
			switch (mode) {
				case GET:
					if (!STRING.isEmpty(query)) {
						URL _url = new URL(url);
						if (STRING.isEmpty(_url.getQuery())) {
							url = url + "?" + query.substring(1);
						} else {
							url = url + "&" + query.substring(1);
						}
					}
					HttpGet HttpGet = new HttpGet(url);
					request = HttpGet;
					break;
				case POST:
					HttpPost HttpPost = new HttpPost(url);
					if (pairs != null) {
						HttpPost.setEntity(new StringEntity(JSON.stringify(QUERY), HTTP.UTF_8));
					}
					request = HttpPost;
					break;
				case PUT:
					HttpPut HttpPut = new HttpPut(url);
					if (pairs != null) {
						HttpPut.setEntity(new StringEntity(JSON.stringify(QUERY), HTTP.UTF_8));
					}
					request = HttpPut;
					break;
				case DELETE:
					if (!STRING.isEmpty(query)) {
						URL _url = new URL(url);
						if (STRING.isEmpty(_url.getQuery())) {
							url = url + "?" + query.substring(1);
						} else {
							url = url + "&" + query.substring(1);
						}
					}
					HttpDelete HttpDelete = new HttpDelete(url);
					request = HttpDelete;
					break;
				default:
					return;
			}
			request.setHeader("Content-Type", "text/json");
			if (getHeaders() != null) {
				setHeaders(request, getHeaders());
			} else {
				setHeaders(request, null);
			}


			new AsyncTask<HttpRequestBase, Integer, Object>() {

				@Override
				protected Object doInBackground(HttpRequestBase... params) {

					try {
						HttpResponse response = new DefaultHttpClient().execute(params[0]);
						HEADERS = response.getAllHeaders();
						return response;
					} catch (Exception e) {
						e.printStackTrace();
						return e;
					}
				}

				protected void onPreExecute() {
				}

				@Override
				protected void onPostExecute(Object response1) {
					if (response1 instanceof  Exception) {
						failureCallback.run((Exception)response1);
					} else {
						successCallback.run((HttpResponse)response1);
					}
				}
			}.execute(request);
		} catch (Exception e) {
			e.printStackTrace();
			failureCallback.run(e);
		}
	}

	private static void setHeaders(HttpRequestBase request, Map<String, Object> headers) {
		if (headers == null) {
			return;
		}
		for (String key : headers.keySet()) {
			if (headers.get(key) != null) {
				request.setHeader(key, headers.get(key).toString());
			}
		}
	}

	/**
	 * 返回的Header
	 */
	public static Header[] HEADERS;
	private static Map<String, Object> aHeaders;

	/**
	 * setHeaers 设置html头，如果要设置头请在网络请求之前设置。
	 *
	 * @param headers 头参数
	 */
	public static void setHeaders(Map<String, Object> headers) {
		aHeaders = headers;
		// String COOKIE = (String) CONFIG.get("COOKIE");
		// if (!STRING_.exists(COOKIE)) {
		// headers.put("Cookie", COOKIE);
		// }
	}

	public static Map<String, Object> getHeaders() {
		return aHeaders;
	}



	/**
	 * getBytes 返回二进制
	 *
	 * @param url        服务器地址
	 * @param params 传输数据
	 * @param mode       获取的类型
	 * @param successCallback   回调函数
	 */
	public static void getBytes(String url, Map<String, Object> params,
							   final CALLBACK1<byte[]> successCallback,
							   final CALLBACK1<Exception> failureCallback, Mode mode) {
		getData(url, params, new CALLBACK1<ByteArrayOutputStream >() {
			@Override
			public void run(ByteArrayOutputStream arg1) {
				successCallback.run(arg1.toByteArray());
			}
		}, failureCallback, mode);
	}

	/**
	 * 获取Data数据（InputStream）
	 * @param url
	 * @param params
	 * @param successCallback
	 * @param failureCallback
     * @param mode
     */
	public static void getData(String url, Map<String, Object> params, final CALLBACK1<ByteArrayOutputStream> successCallback,
							   final CALLBACK1<Exception> failureCallback, Mode mode){
			get(url, params, new CALLBACK1<HttpResponse>() {
				@Override
				public void run(HttpResponse response) {
					try {
						if(response.getStatusLine().getStatusCode()!=200) {
							Exception exception = new Exception(response.getStatusLine().getReasonPhrase());
							failureCallback.run(exception);
							return;
						}
						new AsyncTask<HttpResponse,Integer,Object>(){

							@Override
							protected Object doInBackground(HttpResponse... params) {
								try {
									InputStream inputStream = params[0].getEntity().getContent();
									byte[] buffer = new byte[8 * 1024];
									ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
									int read;
									while ((read = inputStream.read(buffer, 0, buffer.length)) > 0) {
										outputStream.write(buffer, 0, read);
									}
									inputStream.close();
									return outputStream;
								} catch (Exception e) {
									return e;
								}
							}

							@Override
							protected void onPostExecute(Object result) {
								if(result instanceof Exception ){
									failureCallback.run((Exception)result);
								}else {
									successCallback.run((ByteArrayOutputStream)result);
								}
							}
						}.execute(response);
					} catch (Exception e) {
						e.printStackTrace();
						failureCallback.run(e);
					}
				}
			}, failureCallback,mode);
	}
	/**
	 * getString 返回字符串 需要权限：android.permission.INTERNET
	 *
	 * @param url        服务器地址
	 * @param params 传输数据
	 * @param mode       获取的类型
	 * @param successCallback   成功回调函数
	 */
	public static void getString(String url, Map<String, Object> params, final CALLBACK1<String> successCallback,
								 final CALLBACK1<Exception> failureCallback, Mode mode) {

		try {
			get(url, params, new CALLBACK1<HttpResponse>() {
				@Override
				public void run(HttpResponse response) {
					HttpEntity entity = response.getEntity();
					try {
						String str = EntityUtils.toString(entity, "utf-8");
						successCallback.run(str);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}, new CALLBACK1<Exception>() {
				@Override
				public void run(Exception e) {
					failureCallback.run(e);
				}
			},mode);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * getVoid 返回null
	 *
	 * @param url      服务器地址
	 * @param params   传输数据
	 * @param mode     获取的类型
	 * @param successCallback 成功回调函数
	 */
	public static void getVoid( String url, Map<String, Object> params,
							   final CALLBACK0 successCallback, final CALLBACK1<Exception> failureCallback, Mode mode) {
		try {
			get(url, params, new CALLBACK1<HttpResponse>() {
				@Override
				public void run(HttpResponse response) {
					successCallback.run();
				}
			}, new CALLBACK1<Exception>() {
				@Override
				public void run(Exception e) {
					failureCallback.run(e);
				}
			}, mode);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * getJSON 返回JSON
	 *
	 * @param url      服务器地址
	 * @param params   传输数据
	 * @param mode     获取的类型
	 * @param successCallback 成功回调函数
	 */
	public static void getJSON(String url, Map<String, Object> params,
							   final CALLBACK1<JSONObject> successCallback, final CALLBACK1<Exception> failureCallback, Mode mode) {

		getString(url, params, new CALLBACK1<String>() {
			@Override
			public void run(String arg1) {
				successCallback.run(JSON.parse(arg1));
			}
		}, new CALLBACK1<Exception>() {
			@Override
			public void run(Exception e) {
				failureCallback.run(e);
			}
		},mode);
	}

	public static void getJSONs(String url, Map<String, Object> params,
								final CALLBACK1<JSONArray> successCallback, final CALLBACK1<Exception> failureCallback, Mode mode) {

		getString(url, params, new CALLBACK1<String>() {
			@Override
			public void run(String arg1) {
				successCallback.run(JSON.parses(arg1));
			}
		}, new CALLBACK1<Exception>() {
			@Override
			public void run(Exception e) {
				failureCallback.run(e);
			}
		},mode);
	}

	static Map<String, Bitmap> BUFFER = new HashMap<String, Bitmap>();

	/**
	 * getImage 返回图片
	 *
	 * @param url      服务器地址
	 * @param successCallback 回调函数
	 */
	public static void getImage(String url, Map<String, Object> params,
								final CALLBACK1<Bitmap> successCallback, final CALLBACK1<Exception> failureCallback,Mode mode) {

		getBytes(url, params, new CALLBACK1<byte[]>() {
			@Override
			public void run(byte[] arg1) {
				successCallback.run(DATA.bytes2image(arg1));
			}
		},failureCallback,mode);

	}

	/**
	 * getXML	获取XML
	 * @param url		路径
	 * @param params	字典
	 * @param successCallback		成功回调方法
	 * @param failureCallback		失败回调方法
     * @param mode					模式
     */
	public static void getXML(String url, Map<String, Object> params,
							  final CALLBACK1<Document> successCallback, final CALLBACK1<Exception> failureCallback, Mode mode){
		getString(url, params, new CALLBACK1<String>() {
			@Override
			public void run(String arg1) {
				successCallback.run(XML.parse(arg1));
			}
		}, new CALLBACK1<Exception>() {
			@Override
			public void run(Exception e) {
				failureCallback.run(e);
			}
		},mode);
	}

	/**
	 * 返回Base64
	 * @param url		路径
	 * @param params	字典
	 * @param successCallback		成功返回
	 * @param failureCallback		失败返回
     * @param mode		模式
     */
	public static void getBase64(String url, Map<String, Object> params,
								 final CALLBACK1<String> successCallback, final CALLBACK1<Exception> failureCallback, Mode mode){
		getBytes(url, params, new CALLBACK1<byte[]>() {
			@Override
			public void run(byte[] bytes) {
				successCallback.run(DATA.bytes2base64(bytes));
			}
		}, new CALLBACK1<Exception>() {
			@Override
			public void run(Exception e) {
				failureCallback.run(e);
			}
		},mode);
	}
	/**
	 * upload 上传
	 *
	 * @param url      服务器地址
	 * @param params   传输数据
	 * @param files    图片地址
	 * @param successCallback 成功回调函数
	 */
	public static void upload(final Context context, final String url, Map<String, Object> params, Map<String, InputStream> files,
							  final CALLBACK1<String> successCallback, final CALLBACK1<Exception> failureCallback, Mode mode) {
		try {
			final HttpRequestBase request = RequestFiles(url, params, files, mode);
			if (request == null) {
				return;
			}
			message = null;
			new AsyncTask<HttpRequestBase, Integer, Object>() {
				Integer statusCode = null;
				@SuppressLint("DefaultLocale")
				@Override
				protected Object doInBackground(HttpRequestBase... params) {
					try {
						HttpResponse response = new DefaultHttpClient().execute(request);
						HEADERS = response.getAllHeaders();
						statusCode = response.getStatusLine().getStatusCode();

						if (statusCode != 200) {
							if (statusCode == 401) {
								MESSAGE.send(context, "LOGIN", null);
								return null;
							}
							switch (statusCode) {
								case -1:
								case 0:
									message = "网络无法连接，请检查网络设置。";
								default:
									message = String.format("网络错误:%d,请稍后重试。", statusCode);
									break;
							}
							HttpEntity entity = response.getEntity();
							String error = EntityUtils.toString(entity, "utf-8");
							if (!STRING.isEmpty(error)) {
								JSONObject json = JSON.parse(error);
								if (json != null) {
									message = json.optString("error_msg");
									JSONObject error_detail = json.optJSONObject("error_detail");
									if (error_detail != null) {
										Iterator<String> iterator = error_detail.keys();
										while (iterator.hasNext()) {
											String key = iterator.next();
											message += "\n[" + key + "]" + error_detail.optString(key);

										}
									}
								}
							}
							return null;
						}
						HttpEntity entity = response.getEntity();
						String str = EntityUtils.toString(entity, "utf-8");
						return str;
					} catch (Exception e) {
						e.printStackTrace();
						DIALOG.alert(context, e.getMessage());
						return e;
					}
				}

				protected void onPreExecute() {
				}

				@SuppressWarnings({"unchecked", "rawtypes"})
				@Override
				protected void onPostExecute(Object object) {
					if (object instanceof Exception){
						failureCallback.run((Exception)object);
					}else {
						successCallback.run((String)object);
					}
				}

			}.execute(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}/**
	 * uploads 上传
	 *
	 * @param url      服务器地址
	 * @param params   传输数据
	 * @param files    图片地址
	 * @param successCallback 成功回调函数
	 */
	public static void uploads(final Context context, final String url, Map<String, Object> params, Map<String, InputStream> files,
							  final CALLBACK1<String> successCallback, final CALLBACK1<Exception> failureCallback, Mode mode) {

		try {
			final HttpRequestBase request = RequestFiles(url, params, files, mode);
			if (request == null) {
				return;
			}
			message = null;
			new AsyncTask<HttpRequestBase, Integer, Object>() {
				Integer statusCode = null;
				@SuppressLint("DefaultLocale")
				@Override
				protected Object doInBackground(HttpRequestBase... params) {
					try {
						HttpResponse response = new DefaultHttpClient().execute(request);
						HEADERS = response.getAllHeaders();
						statusCode = response.getStatusLine().getStatusCode();

						if (statusCode != 200) {
							if (statusCode == 401) {
								MESSAGE.send(context, "LOGIN", null);
								return null;
							}
							switch (statusCode) {
								case -1:
								case 0:
									message = "网络无法连接，请检查网络设置。";
								default:
									message = String.format("网络错误:%d,请稍后重试。", statusCode);
									break;
							}
							HttpEntity entity = response.getEntity();
							String error = EntityUtils.toString(entity, "utf-8");
							if (!STRING.isEmpty(error)) {
								JSONObject json = JSON.parse(error);
								if (json != null) {
									message = json.optString("error_msg");
									JSONObject error_detail = json.optJSONObject("error_detail");
									if (error_detail != null) {
										Iterator<String> iterator = error_detail.keys();
										while (iterator.hasNext()) {
											String key = iterator.next();
											message += "\n[" + key + "]" + error_detail.optString(key);

										}
									}
								}
							}
							return null;
						}else {

						}
						HttpEntity entity = response.getEntity();
						String str = EntityUtils.toString(entity, "utf-8");
						return str;
					} catch (Exception e) {
						e.printStackTrace();
						return e;
					}
				}

				protected void onPreExecute() {
				}

				@SuppressWarnings({"unchecked", "rawtypes"})
				@Override
				protected void onPostExecute(Object object) {
					if (object instanceof Exception){
						failureCallback.run((Exception) object);
					}else {
						successCallback.run((String) object);
					}
				}

			}.execute(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	private static HttpRequestBase RequestFiles(String url, Map<String, Object> params,
												Map<String, InputStream> files, Mode mode) throws UnsupportedEncodingException {
		try {
			// OneKit.init();
			HttpRequestBase request;
			List<NameValuePair> pairs;
			String query = "";
			JSONObject QUERY = new JSONObject();
			MultipartEntity multipartEntity = new MultipartEntity();
			if (files != null) {
				if (files.keySet() != null) {
					for (String key : files.keySet()) {
						InputStream file = files.get(key);
						multipartEntity.addPart(key, key + ".png", file, false);
					}
				}
			}

			if (params != null) {

				int i = 0;
				for (String key : params.keySet()) {
					Object value = params.get(key);
					if (value == null) {
						continue;
					}
					String json;
					if (value.getClass().isArray()) {
						json = new JSONArray((Collection) value).toString();
					} else if (value.getClass() == Map.class) {
						json = new JSONObject((Map) value).toString();
					} else {
						json = value.toString();
					}
					String string = json;
					multipartEntity.addPart(key, string, i == (params.size() - 1));
					i++;

				}

			}

			switch (mode) {
				case GET:
					if (!STRING.isEmpty(query)) {
						URL _url = new URL(url);
						if (STRING.isEmpty(_url.getQuery())) {
							url = url + "?" + query.substring(1);
						} else {
							url = url + "&" + query.substring(1);
						}
					}
					HttpGet HttpGet = new HttpGet(url);
					request = HttpGet;
					break;
				case POST:
					HttpPost HttpPost = new HttpPost(url);
					HttpPost.setEntity(multipartEntity);

					request = HttpPost;
					break;
				case PUT:
					HttpPut HttpPut = new HttpPut(url);
					HttpPut.setEntity(multipartEntity);

					request = HttpPut;
					break;
				case DELETE:
					if (!STRING.isEmpty(query)) {
						URL _url = new URL(url);
						if (STRING.isEmpty(_url.getQuery())) {
							url = url + "?" + query.substring(1);
						} else {
							url = url + "&" + query.substring(1);
						}
					}
					HttpDelete HttpDelete = new HttpDelete(url);
					request = HttpDelete;
					break;
				default:
					request = null;
					break;
			}
			if (request == null) {
				return null;
			}
			if (getHeaders() != null) {
				setHeaders(request, getHeaders());
			} else {
				setHeaders(request, null);
			}
			String contentType = multipartEntity.getContentType().getValue();
			request.setHeader("Content-Type", contentType);
			// request.getParams().setParameter(CoreProtocolPNames.HTTP_CONTENT_CHARSET,
			// Charset.forName("UTF-8"));
			request.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 20000);
			request.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 20000);

			return request;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
