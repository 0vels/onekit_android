package cn.onekit;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Handler;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by BryantCurry on 2017/1/4.
 */

public class LOADING {
    private static ProgressDialog dialog;
    private static Map<String, ProgressDialog> id = new HashMap<String, ProgressDialog>();
    public static void show(final Context context){
        try {
            if (id.get(context) == null) {
                dialog = new ProgressDialog(context);
                // _loading.setMessage("正在处理……");
                id.put(context.toString(), dialog);
            }
            new Handler().post(new Runnable() {

                @Override
                public void run() {
                    id.get(context.toString()).show();
                }

            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void hide(Context context){
        dialog = new ProgressDialog(context);
        if (id.get(context.toString()) == null) {
            return;
        }
        id.get(context.toString()).dismiss();
    }
}
