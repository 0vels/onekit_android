package cn.onekit;

import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class WEB {
	public static void load(String url,Map<String, Object> params,WebView webView) {
		String s = null;
		if (params != null) {
			StringBuffer stringBuffer = new StringBuffer();
			List<String> keys = new ArrayList<String>(params.keySet());
			for (int i = 0; i < keys.size(); i++) {
				String key = keys.get(i);
				String value = params.get(key).toString();
				stringBuffer.append(key + "=" + value);
				stringBuffer.append("&");
			}
			s = stringBuffer.toString();
			if (s.endsWith("&")) {
				s = s.substring(0, s.lastIndexOf("&"));
			}
		}
		//初始化webview
		//启用支持javascript
		WebSettings settings = webView.getSettings();
		settings.setJavaScriptEnabled(true);//支持javaScript
		settings.setDefaultTextEncodingName("utf-8");//设置网页默认编码
		settings.setJavaScriptCanOpenWindowsAutomatically(true);
		//设置可以访问文件
		settings.setAllowFileAccess(true);
		//设置支持缩放
		settings.setBuiltInZoomControls(true);
		//post请求(使用键值对形式，格式与get请求一样，key=value,多个用&连接)
//		s = "JSONpriKey=" +s;
		if (s != null){
		webView.postUrl(url, s.getBytes());
		}else {
			webView.loadUrl(url);//get
		}
		//覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
		webView.setWebViewClient(new WebViewClient(){

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				//返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
				view.loadUrl(url);
				return true;
			}
		});
	}
}
