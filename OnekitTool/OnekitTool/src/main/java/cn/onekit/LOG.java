package cn.onekit;

import android.content.Context;
import android.util.Log;

public class LOG {
	public static void v(Context context,String msg) {
			Log.v(context.toString(), msg);
	}

	public static void d(Context context,String msg) {
			Log.d(context.toString(), msg);
	}

	public static void i(Context context,String msg) {
			Log.i(context.toString(), msg);
	}

	public static void w(Context context,String msg) {
			Log.w(context.toString(), msg);
	}

	public static void e(Context context,String msg) {
			Log.e(context.toString(), msg);
	}
}
