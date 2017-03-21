package cn.onekit;

import java.io.IOException;

/**
 * Created by BryantCurry on 2017/1/3.
 */

public class OS {
    /**
     * 获取平台名
     * @return
     */
    public static String platform(){
//        return android.os.Build.ID;
        return android.os.Build.MODEL;
    }

    /**
     * 获取版本号
     * @return
     */
    public static String version(){
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取内核版本号
     * @return
     */
    public static String Kernel(){
        Process process = null;
        String kernelVersion = "";
        try {
            process = Runtime.getRuntime().exec("cat /proc/version");
        } catch (IOException e) {
// TODO Auto-generated catch block
            e.printStackTrace();
        }
        return process.toString();
    }
}
