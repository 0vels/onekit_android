package cn.onekit;

import java.io.File;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

public class DB {

	public enum DBSelect {
		Add, Delete, Up, Query
	}

	/* 数据库对象 */
	private static SQLiteDatabase mSQLiteDatabase = null;
	private static JSONArray json;

	/**
	 * 打开数据库
	 * 
	 * @param name
	 *            数据库名称
	 * */
	public static void openDB(String name) {
		// mSQLiteDatabase = ACTIVITY.context.openOrCreateDatabase(DateBase,
		// ACTIVITY.MODE_PRIVATE, null);
		File file = new File(Environment.getExternalStorageDirectory(),
				name);
		if (file.exists()) {
			mSQLiteDatabase = SQLiteDatabase.openOrCreateDatabase(file, null);
		}
	}

	/**
	 * execSql 增删改查
	 * 
	 * @param select
	 *            :选择
	 * */
	public static Object execSql(DBSelect select, Object... obj) {
		if (select == DBSelect.Query) {
			return query(obj);
		}
		switch (select) {
		case Add: {
//			add(obj);
			break;
		}
		case Delete: {
			DeleteData(obj);
			break;
		}
		case Up: {
//			UpData(obj);
			break;
		}
		default:
			break;
		}
		return null;
	}

	/**
	 * 更新数据
	 * 
	 * @param id
	 *            ... obj[0]:表名； obj[1]：更改的列； obj[2]：表中的位置；（int） obj[3]：表中的字段；
	 *            obj[4]：位置上的参数（String）....
	 * */
	public static boolean updata(String table,String key,Object... id) {
		ContentValues cv = new ContentValues();
		int i = 0;
		while (i < id.length) {
			cv.put((String) id[i], (String) id[i + 1]);
			i = i + 2;
		}
		try {
			/* 更新数据 */
			mSQLiteDatabase.update(table, cv, id[1] + "=" + id[2],
					null);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();;
		}
		return true;
	}

	/**
	 * 添加数据
	 * 
	 * @param record
	 *            ... obj obj[0]:表名； obj[1]:表中的字段； obj[2]:参数
	 * 
	 * */
	private static void insert(String table,JSONObject record) {
		ContentValues cv = new ContentValues();

		/*int i = 1;
		while (i < record.length) {
			if (i == 1) {
				cv.put((String) obj[i], (Integer) obj[i + 1]);
			} else {
				cv.put((String) obj[i], (String) obj[i + 1]);
			}
			i = i + 2;
		}*/
		try {
			/* 插入数据 */
			mSQLiteDatabase.insert(table, null, cv);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除数据
	 * 
	 * @param obj
	 *            obj[0]:表名； obj[1]:要删除的行数
	 * */
	private static void DeleteData(Object... obj) {
		try {
			/* 删除数据 */
			mSQLiteDatabase.execSQL("DELETE FROM " + obj[0] + " WHERE "
					+ obj[1] + "=" + obj[2]);
		} catch (IllegalStateException e) {
//			DIALOG.alert("请先打开数据库");
			return;
		} catch (NullPointerException e) {
//			DIALOG.alert("请先打开数据库");
			return;
		}
	}

	/**
	 * 查找数据
	 * 
	 * @param obj
	 *            obj[0]:表名； obj[1]:列名；obj[2]:查找字段
	 * */
	private static JSONArray query(Object... obj) {
		json = new JSONArray();

		Cursor cursor = null;
		/*
		 * while (i < obj.length) { str[j] = (String) obj[i]; if (obj[i + 1]
		 * instanceof Integer) { inte[b] = (Integer) obj[i + 1]; str_[b] =
		 * str[j] + "=" + inte[b]; } else { str[b] = (String) obj[i + 1];
		 * str_[b] = str[j] + "=" + str[b]; } cursor =
		 * mSQLiteDatabase.query((String) obj[0], null, null, str_, null, null,
		 * null, null); i = i + 2; j = j + 2; b++; }
		 */
		try {
			if (obj.length < 2) {
				cursor = mSQLiteDatabase.query((String) obj[0], null, null,
						null, null, null, null, null);

			} else {
				String[] str = { (String) obj[2] };
				cursor = mSQLiteDatabase.query((String) obj[0], null,
						obj[1] + "=?", str, null, null, null, null);
			}

			JSONObject _object;

			int title = cursor.getColumnCount();
			for (int i = 0; i < cursor.getCount(); i++) {
				int b = 0;
				for (cursor.moveToFirst(); !cursor.isAfterLast(); cursor
						.moveToNext()) {
					_object = new JSONObject();
					for (int j = 0; j < title; j++) {
						_object.put(cursor.getColumnName(j),
								cursor.getString(j));
					}
					json.put(b, _object);
					b++;
				}
			}
			cursor.close();

		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IllegalStateException e) {
//			DIALOG.alert("请先打开数据库");
			return null;
		} catch (NullPointerException e) {
//			DIALOG.alert("请先打开数据库");
			return null;
		}
		return json;
	}

	/**
	 * fastExecSql 快速查找
	 * 
	 * @param DateBase
	 *            数据库
	 * 
	 * @param obj
	 *            表名，列名，参数...
	 * 
	 * */
	public static JSONArray fastExecSql(String DateBase, Object... obj) {
		openDB(DateBase);
		return query(obj);
	}

	/**
	 * 关闭数据库
	 * */
	public static void close() {
		try {
			mSQLiteDatabase.close();
		} catch (IllegalStateException e) {
//			DIALOG.alert("请先打开数据库");
			return;
		} catch (NullPointerException e) {
//			DIALOG.alert("请先打开数据库");
			return;
		}
	}
}
