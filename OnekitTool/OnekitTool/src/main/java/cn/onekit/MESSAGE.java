package cn.onekit;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class MESSAGE {
	public static Map<Context, List<BroadcastReceiver>> allUnregisters = new HashMap<Context, List<BroadcastReceiver>>();
	public static Map<Context, List<IntentFilter>> allFilters = new HashMap<Context, List<IntentFilter>>();
	public static Map<Context, Map<String, List<CALLBACK<Map>>>> _globalCallbacks = new HashMap<Context, Map<String, List<CALLBACK<Map>>>>();
	public static Map<Context, Map<String, Map<String, List<CALLBACK<Map>>>>> _receiverCallbacks = new HashMap<Context, Map<String, Map<String, List<CALLBACK<Map>>>>>();

	/**
	 * sendMessage 发送消息
	 * 
	 * @param message
	 *            :发送字段
	 */
	public static void send(Context context,String message, Map params) {
		send(context,message,params,null);
	}
	static class SerializableMap implements Serializable {
		private Map<String,Object> map;

		public Map<String, Object> getMap() {
			return map;
		}

		public void setMap(Map<String, Object> map) {
			this.map = map;
		}
	}
	public static void send(Context context,String message, Map params,Object target) {
		Intent intent = new Intent();
		Bundle bundle = new Bundle();

		SerializableMap serializableMap = new SerializableMap();
		serializableMap.setMap(params);
		bundle.putSerializable("params",serializableMap);

		if (target != null) {
			intent.addCategory(target.toString());
		}
		intent.setAction(message);
		if (params != null) {
			intent.putExtras(bundle);
		}
		context.sendBroadcast(intent);
	}

	/**
	 * sendMessage 接收消息
	 * 
	 * @param message
	 *            :接收字段
	 * @param callback
	 *            :回调方法
	 */
	public static void receive(Context context,String message, CALLBACK<Map> callback) {
		receive(context,message,callback,null );
	}

	public static void receive(Context context, String message, CALLBACK<Map> callback, Object target) {
		boolean isExist = false;
		if (allFilters.containsKey(context)) {
			List<IntentFilter> filters = allFilters.get(context);
			for (IntentFilter filter : filters) {
				if (filter.countActions() == 1 && filter.getAction(0).equalsIgnoreCase(message)) {
					if (target == null) {
						if (filter.countCategories() == 0) {
							isExist = true;
						}
					} else {
						if (filter.getCategory(0).equalsIgnoreCase(target.toString())) {
							isExist = true;
						}
					}
				}
			}
		}
		if (!isExist) {
			IntentFilter filter = new IntentFilter();
			if (target != null) {
				filter.addCategory(target.toString());
			}
			filter.addAction(message);
			List<IntentFilter> filters;
			if (allFilters.containsKey(context)) {
				filters = allFilters.get(context);
			} else {
				filters = new ArrayList<IntentFilter>();
			}
			filters.add(filter);
			allFilters.put(context, filters);
			BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
				@Override
				public void onReceive(Context context, Intent intent) {
					String message = intent.getAction();
					Set<String> categories = intent.getCategories();
					List<CALLBACK<Map>> callbacks;
					if (categories != null && categories.size() == 1) {
						String category = categories.toArray(new String[categories.size()])[0];
						callbacks = _receiverCallbacks.get(context).get(message).get(category);
					} else {
						callbacks = _globalCallbacks.get(context).get(message);
					}
					for (CALLBACK<Map> callback : callbacks) {
						SerializableMap map = (SerializableMap) intent.getSerializableExtra("params");
						if (map != null){
							callback.run(false,map.getMap());
						}

					}
				}

			};
			context.registerReceiver(broadcastReceiver, filter);
			List<BroadcastReceiver> unregisters;
			if (allUnregisters.containsKey(context)) {
				unregisters = allUnregisters.get(context);
			} else {
				unregisters = new ArrayList<BroadcastReceiver>();
			}
			unregisters.add(broadcastReceiver);
			allUnregisters.put(context, unregisters);
		}
		List<CALLBACK<Map>> callbacks;
		if (target != null) {
			String category = target.toString();
			Map<String, Map<String, List<CALLBACK<Map>>>> receiverCallbacks;
			if (_receiverCallbacks.containsKey(message)) {
				receiverCallbacks = _receiverCallbacks.get(context);
			} else {
				receiverCallbacks = new HashMap<String, Map<String, List<CALLBACK<Map>>>>();
			}
			Map<String, List<CALLBACK<Map>>> allCallbacks;
			if (receiverCallbacks.containsKey(message)) {
				allCallbacks = receiverCallbacks.get(message);
			} else {
				allCallbacks = new HashMap<String, List<CALLBACK<Map>>>();
			}
			if (allCallbacks.containsKey(category)) {
				callbacks = allCallbacks.get(category);
			} else {
				callbacks = new ArrayList<CALLBACK<Map>>();
			}
			callbacks.add(callback);
			allCallbacks.put(category, callbacks);
			receiverCallbacks.put(message, allCallbacks);
			_receiverCallbacks.put(context, receiverCallbacks);
		} else {
			Map<String, List<CALLBACK<Map>>> globalCallbacks = new HashMap<String, List<CALLBACK<Map>>>();
			if (_globalCallbacks.containsKey(context)) {
				globalCallbacks = _globalCallbacks.get(context);
			} else {
				globalCallbacks = new HashMap<String, List<CALLBACK<Map>>>();
			}
			if (globalCallbacks.containsKey(message)) {
				callbacks = globalCallbacks.get(message);
			} else {
				callbacks = new ArrayList<CALLBACK<Map>>();
			}
			callbacks.add(callback);
			globalCallbacks.put(message, callbacks);
			_globalCallbacks.put(context, globalCallbacks);
		}
	}

	public static void clear(Context context) {
		if (allUnregisters.containsKey(context)) {
			List<BroadcastReceiver> unregisters = allUnregisters.get(context);
			for (BroadcastReceiver unregister : unregisters) {
				context.unregisterReceiver(unregister);
			}
			allUnregisters.remove(context);
		}
		if (allFilters.containsKey(context)) {
			allFilters.remove(context);
		}
	}
}
