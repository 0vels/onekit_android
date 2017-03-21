package cn.onekit;

/**
 * Created by BryantCurry on 2016/12/28.
 */

public class PATH {
    /**
     * 获取文件名
     * @param path  路径
     * @return
     */
    public static String name(String path){
        int start = path.lastIndexOf("/");
        int end = path.lastIndexOf(".");
        if (start != -1 && end != -1){
            return path.substring(start + 1, end);
        }else {
            return null;
        }
    }

    /**
     * 获取文件扩展名
     * @param path
     * @return
     */
    public static String ext(String path){
        int end = path.lastIndexOf(".");
        if (end != -1){
            return path.substring(end+1);
        }else {
            return null;
        }
    }

    /**
     * 获取文件夹
     * @param path
     * @return
     */
    public static String folder(String path){
        int end = path.lastIndexOf("/");
        if (end != -1){
            return path.substring(0,end);
        }else {
            return null;
        }
    }
}
