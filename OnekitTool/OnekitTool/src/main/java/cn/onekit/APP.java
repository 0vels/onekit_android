package cn.onekit;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.SmsManager;
import java.util.List;
import cn.onekit.permissions.BaseActivity;
import cn.onekit.permissions.PermissionListener;

public class APP{
	private static final int CALL_PHONE_REQUEST_CODE = 0x0011;
	/**
	 * appID 查看appID
	 */
	public static String getID(Context context) {
		return context.getPackageName();
	}

	/**
	 * goUrl() 打开浏览器
	 *
	 * @param url
	 *            网页地址
	 */
	public static boolean goUrl(final Context context, final String url) {
		if (url != null) {
			DIALOG.confirm(context, "确认打开" + url + "？", new CALLBACK<Object>() {
				public void run(boolean isError, Object result) {
					if (isError) {
						return;
					}
					if (url.startsWith("http://")) {
						context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
					} else {
						context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://" + url)));
					}
				}
			}, new CALLBACK<Object>() {

				@Override
				public void run(boolean isError, Object obj) {
					if (isError) {
						return;
					}
				}
			});
			return true;
		} else {
			return false;
		}

	}

	/**
	 * goPhone 打电话 需要权限：android.permission.CALL_PHONE
	 *
	 * @param number
	 *            电话号码
	 */
	public static boolean goPhone(final Activity context, final String number) {
		if (STRING.isEmpty(number)) {
			return false;
		}
			BaseActivity.requestRuntimePermission(new String[]{Manifest.permission.CALL_PHONE}, new PermissionListener() {
				@Override
				public void onGranted() {
					Intent intent = new Intent(Intent.ACTION_CALL);
					Uri data = Uri.parse("tel:" + number);
					intent.setData(data);
					context.startActivity(intent);
				}

				@Override
				public void onDenied(List<String> deniedPermission) {
					for (String permission : deniedPermission) {
						DIALOG.toast(context,"被拒绝的权限是：" + permission);
					}
				}
			});
			return true;
	}

	/**
	 * 打开拨号界面
	 * @param context		上下文
	 * @param number		号码
	 * @return			返回值
	 */
	public static boolean openPhone(Activity context,String number){
		if (number != null){
			Intent intent = new Intent(Intent.ACTION_DIAL,Uri.parse("tel:" + number));
			intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			context.startActivity(intent);
			return true;
		}else {
			return false;
		}
	}
	/**
	 * goSMS 发短信
	 * 
	 * @param number
	 *            电话号码
	 */
	public static boolean goSMS(final Activity context, final String number, final String messge) {
		if (number != null){
			BaseActivity.requestRuntimePermission(new String[]{Manifest.permission.SEND_SMS}, new PermissionListener() {
				@Override
				public void onGranted() {
					SmsManager smsManager = SmsManager.getDefault();
					smsManager.sendTextMessage(number,null,messge,null,null);
				}

				@Override
				public void onDenied(List<String> deniedPermission) {
					for (String permission : deniedPermission) {
						DIALOG.toast(context,"被拒绝的权限是：" + permission);
					}
				}
			});
			return true;
		}else {
			return false;
		}
	}


	/**
	 * 打开短信界面
	 * @param context	上下文
	 * @param number	号码
	 * @param messge	内容
     */
	public static boolean openSMS(Activity context,String number,String messge){
		if (number != null || messge != null){
			Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
			sendIntent.setData(Uri.parse("smsto:" + number));
			sendIntent.putExtra("sms_body", messge);
			context.startActivity(sendIntent);
			return true;
		}else {
			return false;
		}
	}

	/**
	 * 打开邮件界面
	 * @param context 		上下文
	 * @param email		邮箱号
	 * @param subject		标题
     * @param body			正文
     */
	public static boolean openEmail(Activity context,String email,String subject,String body){
		if (email != null || subject != null || body != null){
			Intent it = new Intent(Intent.ACTION_SEND);
			it.putExtra(Intent.EXTRA_EMAIL, email);  				//接收人
			it.putExtra(Intent.EXTRA_SUBJECT, subject); // 主题
			it.putExtra(Intent.EXTRA_TEXT, body);		//正文
			it.setType("text/plain");					//邮件格式
			context.startActivity(Intent.createChooser(it, "Choose Email Client"));
			return true;
		}else {
			return false;
		}
	}

	/**
	 * goEmail 发送Email
	 *
	 * @param email
	 *            收件人
	 */
	public static boolean goEmail(final Activity context, final String email, final String subject, final String body) {
		if (email != null) {
			Intent it = new Intent(Intent.ACTION_SENDTO);
					it.setData(Uri.parse(email));
//			it.putExtra(Intent.EXTRA_EMAIL, email);                //接收人
			it.putExtra(Intent.EXTRA_SUBJECT, subject); // 主题
			it.putExtra(Intent.EXTRA_TEXT, body);        //正文
			it.setType("text/plain");					//邮件格式
			context.startActivity(Intent.createChooser(it, "Choose Email Client"));

			/*DIALOG.confirm(context, "确认向" + email + "发送邮件？", new CALLBACK<Object>() {
				public void run(boolean isError, Object result) {
					if (isError) {
						return;
					}
					Intent it = new Intent(Intent.ACTION_SEND);
//					it.setData(Uri.parse(email));
					it.putExtra(Intent.EXTRA_EMAIL, email);                //接收人
					it.putExtra(Intent.EXTRA_SUBJECT, subject); // 主题
					it.putExtra(Intent.EXTRA_TEXT, body);        //正文
					context.startActivity(Intent.createChooser(it, "Choose Email Client"));
//					context.startActivity(it);
				}
			}, new CALLBACK<Object>() {

				@Override
				public void run(boolean isError, Object obj) {
					if (isError) {
						return;
					}
				}
			});*/
			return true;
		} else {
			return false;
		}
	}
}
