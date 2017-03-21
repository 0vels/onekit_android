package cn.onekit;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;

/**
 * Created by BryantCurry on 2017/1/3.
 */

public class RES {
    /**
     * 加载图像
     * @param context
     * @param key
     * @return
     */
    public static BitmapDrawable loadImage(Context context,String key){
        int resID = context.getResources().getIdentifier(key, "drawable", context.getPackageName());
        BitmapDrawable drawable = (BitmapDrawable) context.getResources().getDrawable(resID);
        return drawable;
    }

    /**
     * 本地化
     * @param context
     * @param key
     * @return
     */
    public static String loadLocalize(Context context,String key){
        int resId = context.getResources().getIdentifier(key, "string", context.getPackageName());
        String todayStateStr =  context.getResources( ).getString(resId);
        return todayStateStr;
    }
}
