package cn.onekit;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.provider.MediaStore;

/**
 * Created by BryantCurry on 2017/1/13.
 */

public class MEDIA extends ACTIVITY {
    private static final int VIDEO_CAPTURE = 200;

    /**
     * 打开相机
     *         需要权限     android.permission.CAMERA,
     *            android.permission.WRITE_EXTERNAL_STORAGE
     * */
    public static void openCamera(Activity activity, final CALLBACK1<Bitmap> okCallback, final CALLBACK0 cancelCallback) {
        ACTIVITY.openCamera(activity,new CALLBACK1<Bitmap>() {
            @Override
            public void run(Bitmap bitmap) {
                if (bitmap != null){
                    okCallback.run(bitmap);
                }else {
                    cancelCallback.run();
                }
            }
        });

    }

    /**
     * 打开录像机
     *            需要权限 android.permission.RECORD_AUDIO,
     *            android.permission.WRITE_EXTERNAL_STORAGE
     * */
    public static void openRecord(Activity activity) {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        activity.startActivityForResult(intent,
                VIDEO_CAPTURE);
    }

    /**
     * 打开相册
     * */
    public static void openLibrary(Activity activity,final CALLBACK1<Bitmap> okCallback,final CALLBACK0 cancelCallback) {
        ACTIVITY.openUri(activity,new CALLBACK1<Bitmap>() {
            @Override
            public void run(Bitmap bitmap) {
                if (bitmap != null){
                    okCallback.run(bitmap);
                }else {
                    cancelCallback.run();
                }
            }
        });
    }
}
