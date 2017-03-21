package cn.onekit;

import java.util.ArrayList;

public class ARRAY {
	/**
	 * 				查找下标
	 *
	 * @param array
	 * @param value
	 * @return
	 */
	public static int indexOf(ArrayList array, Object value, String key) {
			for (int i = 0; i < array.size(); i++) {
				Object item  = array.get(i);
				if (key != null){
					if (item.equals(value)){
						return i;
					}
				}else {
					if (array.get(i).equals(item)) {
						return i;
					}
				}
			}
		return -1;
	}

	/**
	 * contains	包含项
	 *
	 * @param array 数组
	 * @param value
	 * @return
	 */
	public static boolean contains(ArrayList array, Object value,String key) {
		return indexOf(array, value,key) >= 0;
	}

	/**
	 * 查找项
	 * @param array
	 * @param value
	 * @param key
     * @return
     */
	public static Object find(ArrayList array,Object value,String key){
		int i = indexOf(array,value,key);
		return array.get(i);
	}
}
