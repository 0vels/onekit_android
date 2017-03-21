package cn.onekit;

import android.app.ActivityManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.os.StatFs;
import android.telephony.TelephonyManager;
import android.text.format.Formatter;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;

public class DEVICE {
	//没有网络连接
	public static final String NETWORN_NONE = "NETWORN_NONE";
	//wifi连接
	public static final String NETWORN_WIFI = "NETWORN_WIFI";
	//手机网络数据连接类型
	public static final String NETWORN_2G = "NETWORN_2G";
	public static final String NETWORN_3G = "NETWORN_3G";
	public static final String NETWORN_4G = "NETWORN_4G";
	public static final String NETWORN_MOBILE = "NETWORN_MOBILE";
	/**
	 * 获取当前应用CPU
	 * */
	public static String getCPU() {
		FileReader fr = null;
		BufferedReader br = null;
		String[] array = null;
		try {
			fr = new FileReader("/proc/cpuinfo");
			br = new BufferedReader(fr);
			String text = br.readLine();
			array = text.split(":\\s+",2);
			for(int i = 0; i < array.length; i++){
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				fr.close();
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			return array[1];
		}
	}

	/**
	 * 获取手机型号
	 * */
	private static String phoneMode() {
		return android.os.Build.MODEL;
	}

	/***
	 * 获取设备ID
	 * 需要权限：android.permission.READ_PHONE_STATE
	 * */
	public static String getID(Context context) {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		String DEVICE_ID = tm.getDeviceId();
		return DEVICE_ID;
	}

	/**
	 * 获取可使用内存情况
	 * */
	@SuppressWarnings("deprecation")
	public static String Memroy() {
		// /proc/meminfo读出的内核信息进行解释
		// String path = "/proc/meminfo";
		// String content = null;
		// BufferedReader br = null;
		// try {
		// br = new BufferedReader(new FileReader(path), 8);
		// String line;
		// if ((line = br.readLine()) != null) {
		// content = line;
		// }
		// } catch (FileNotFoundException e) {
		// e.printStackTrace();
		// } catch (IOException e) {
		// e.printStackTrace();
		// } finally {
		// if (br != null) {
		// try {
		// br.close();
		// } catch (IOException e) {
		// e.printStackTrace();
		// }
		// }
		// }
		// // beginIndex
		// int begin = content.indexOf(':');
		// // endIndex
		// int end = content.indexOf('k');
		// // 截取字符串信息
		// Float a = Float.parseFloat(content.substring(begin + 1, end).trim());
		// Double d1 = Double.parseDouble(new DecimalFormat("#.##")
		// .format(a / 1024 / 1024));

		File path_ = Environment.getDataDirectory();
		StatFs stat = new StatFs(path_.getPath());
		long blockSize = stat.getBlockSize();
		long availableBlocks = stat.getAvailableBlocks();

		Float b = (float) (blockSize * availableBlocks);
		Double d2 = Double.parseDouble(new DecimalFormat("#.##")
				.format(b / 1024 / 1024 / 10));

		return d2 + "MB";
	}

	/**
	 * 获取SD卡总大小
	 * */
	@SuppressWarnings("deprecation")
	public static String getDisk() {
		String sdCardInfo = new String();
		String state = Environment.getExternalStorageState();
		if (Environment.MEDIA_MOUNTED.equals(state)) {
			File sdcardDir = Environment.getExternalStorageDirectory();
			StatFs sf = new StatFs(sdcardDir.getPath());
			long bSize = sf.getBlockSize();
			long bCount = sf.getBlockCount();

			sdCardInfo = Double.parseDouble(new DecimalFormat("#.##")
					.format(((float) (bSize * bCount) / 1024 / 1024 / 1024)))
					+ "GB";// 总大小
		}
		return sdCardInfo;
	}

	/**
	 * 获取网络状态
	 * 需要权限：android.permission.ACCESS_NETWORK_STATE
	 * */
	public static String getNetwork(Context context) {
		//获取系统的网络服务
		ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

		//如果当前没有网络
		if (null == connManager)
			return NETWORN_NONE;

		//获取当前网络类型，如果为空，返回无网络
		NetworkInfo activeNetInfo = connManager.getActiveNetworkInfo();
		if (activeNetInfo == null || !activeNetInfo.isAvailable()) {
			return NETWORN_NONE;
		}

		// 判断是不是连接的是不是wifi
		NetworkInfo wifiInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
		if (null != wifiInfo) {
			NetworkInfo.State state = wifiInfo.getState();
			if (null != state)
				if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
					return NETWORN_WIFI;
				}
		}

		// 如果不是wifi，则判断当前连接的是运营商的哪种网络2g、3g、4g等
		NetworkInfo networkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

		if (null != networkInfo) {
			NetworkInfo.State state = networkInfo.getState();
			String strSubTypeName = networkInfo.getSubtypeName();
			if (null != state)
				if (state == NetworkInfo.State.CONNECTED || state == NetworkInfo.State.CONNECTING) {
					switch (activeNetInfo.getSubtype()) {
						//如果是2g类型
						case TelephonyManager.NETWORK_TYPE_GPRS: // 联通2g
						case TelephonyManager.NETWORK_TYPE_CDMA: // 电信2g
						case TelephonyManager.NETWORK_TYPE_EDGE: // 移动2g
						case TelephonyManager.NETWORK_TYPE_1xRTT:
						case TelephonyManager.NETWORK_TYPE_IDEN:
							return NETWORN_2G;
						//如果是3g类型
						case TelephonyManager.NETWORK_TYPE_EVDO_A: // 电信3g
						case TelephonyManager.NETWORK_TYPE_UMTS:
						case TelephonyManager.NETWORK_TYPE_EVDO_0:
						case TelephonyManager.NETWORK_TYPE_HSDPA:
						case TelephonyManager.NETWORK_TYPE_HSUPA:
						case TelephonyManager.NETWORK_TYPE_HSPA:
						case TelephonyManager.NETWORK_TYPE_EVDO_B:
						case TelephonyManager.NETWORK_TYPE_EHRPD:
						case TelephonyManager.NETWORK_TYPE_HSPAP:
							return NETWORN_3G;
						//如果是4g类型
						case TelephonyManager.NETWORK_TYPE_LTE:
							return NETWORN_4G;
						default:
							//中国移动 联通 电信 三种3G制式
							if (strSubTypeName.equalsIgnoreCase("TD-SCDMA") || strSubTypeName.equalsIgnoreCase("WCDMA") || strSubTypeName.equalsIgnoreCase("CDMA2000")) {
								return NETWORN_3G;
							} else {
								return NETWORN_MOBILE;
							}
					}
				}
		}
		return NETWORN_NONE;
	}

	public static String getAvailMemory(Context context) {// 获取android当前可用内存大小

		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
		am.getMemoryInfo(mi);
		//mi.availMem; 当前系统的可用内存

		return Formatter.formatFileSize(context, mi.availMem);// 将获取的内存大小规格化
	}

	public static String getMemory(Context context) {
		String str1 = "/proc/meminfo";// 系统内存信息文件
		String str2;
		String[] arrayOfString;
		long initial_memory = 0;

		try {
			FileReader localFileReader = new FileReader(str1);
			BufferedReader localBufferedReader = new BufferedReader(
					localFileReader, 8192);
			str2 = localBufferedReader.readLine();// 读取meminfo第一行，系统总内存大小

			arrayOfString = str2.split("\\s+");
			for (String num : arrayOfString) {
				Log.i(str2, num + "\t");
			}

			initial_memory = Integer.valueOf(arrayOfString[1]).intValue() * 1024;// 获得系统总内存，单位是KB，乘以1024转换为Byte
			localBufferedReader.close();

		} catch (IOException e) {
		}
		return Formatter.formatFileSize(context, initial_memory);// Byte转换为KB或者MB，内存大小规格化
	}

	private static long getTotalCpuTime() { // 获取系统总CPU使用时间
		String[] cpuInfos = null;
		try {
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream("/proc/stat")), 1000);
			String load = reader.readLine();
			reader.close();
			cpuInfos = load.split(" ");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		long totalCpu = Long.parseLong(cpuInfos[2])
				+ Long.parseLong(cpuInfos[3]) + Long.parseLong(cpuInfos[4])
				+ Long.parseLong(cpuInfos[6]) + Long.parseLong(cpuInfos[5])
				+ Long.parseLong(cpuInfos[7]) + Long.parseLong(cpuInfos[8]);
		return totalCpu;
	}

	private static long getAppCpuTime() { // 获取应用占用的CPU时间
		String[] cpuInfos = null;
		try {
			int pid = android.os.Process.myPid();
			BufferedReader reader = new BufferedReader(new InputStreamReader(
					new FileInputStream("/proc/" + pid + "/stat")), 1000);
			String load = reader.readLine();
			reader.close();
			cpuInfos = load.split(" ");
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		long appCpuTime = Long.parseLong(cpuInfos[13])
				+ Long.parseLong(cpuInfos[14]) + Long.parseLong(cpuInfos[15])
				+ Long.parseLong(cpuInfos[16]);
		return appCpuTime;
	}

}
