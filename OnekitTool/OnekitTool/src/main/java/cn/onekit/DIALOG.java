package cn.onekit;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;


public class DIALOG {
	private static String common_yes = "确定", common_no = "取消", common_no_input;

	private static ProgressDialog _loading;
	private static String str;

	private static Map<String, ProgressDialog> id = new HashMap<String, ProgressDialog>();

	/**
	 * toast 提示框
	 */
	public static void toast(Context context,String title) {
		Toast.makeText(context, title, Toast.LENGTH_SHORT).show();

	}

	/**
	 * 消息框
	 * 
	 * @param title
	 *            :标题
	 * @param callback
	 *            :需要实现的方法
	 */
	public static void alert(final Context context,final String title, final CALLBACK<Object> callback) {
		new Handler().post(new Runnable() {

			@Override
			public void run() {
				new AlertDialog.Builder(context).setTitle(null).setMessage(title)
						.setPositiveButton(common_yes, new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog, int which) {
								if (callback != null) {
									callback.run(true, null);
								}

							}
						}).show();
			}

		});

	}

	/**
	 * 消息框
	 * 
	 * @param message
	 *            :标题
	 */
	public static void alert(Context context,String message) {
		alert(context,message, null);
	}

	/**
	 * 确认框
	 * 
	 * @param title
	 *            :标题
	 * @param callback
	 *            :需要实现的方法
	 */
	public static void confirm(Context context,String title, final CALLBACK<Object> callback) {
		new AlertDialog.Builder(context).setTitle(null).setMessage(title)
				.setPositiveButton(common_yes, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						callback.run(false, null);
					}
				}).setNegativeButton(common_no, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						callback.run(true, null);
					}
				}).show().setCancelable(false);
	}

	/**
	 * 确认框
	 * 
	 * @param title
	 *            :标题
	 * @param callback
	 *            :需要实现的方法
	 * @param callback_no
	 *            :需要实现的方法
	 */
	public static void confirm(Context context, String title, final CALLBACK<Object> callback, final CALLBACK<Object> callback_no) {
		new AlertDialog.Builder(context).setTitle(null).setMessage(title)
				.setPositiveButton(common_yes, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						callback.run(false, null);
					}
				}).setNegativeButton(common_no, new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						callback_no.run(true, null);
					}
				}).show().setCancelable(false);
	}

	/**
	 * input 输入框
	 * 
	 * @param title
	 *            :标题
	 * @param callback
	 *            :需要实现的方法
	 */
	public static void input(final Context context,String title, final CALLBACK<String> callback) {
		final EditText inputServer = new EditText(context);
		new AlertDialog.Builder(context).setTitle(title).setView(inputServer)
				.setNegativeButton(common_no, null)
				.setPositiveButton(common_yes, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						String string = inputServer.getText().toString();
						if (string == "") {
							toast(context,common_no_input);
							return;
						}
						callback.run(false, string);
						dialog.dismiss();
					}
				}).show().setCancelable(false);
	}
}
