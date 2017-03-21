package cn.onekit;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by BryantCurry on 2017/1/13.
 */

public class ACTIVITY extends FragmentActivity {
    private static CALLBACK1<Bitmap> imageCallback;
    private static CALLBACK1<Bitmap> uriCallback;
    private static final int IMAGE_CAPTURE = 100;
    private static final int VIDEO_CAPTURE = 200;
    private static final int GET_URI = 500;

    protected void onDestroy() {
        super.onDestroy();
        MESSAGE.clear(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null){
            switch (requestCode) {
                case IMAGE_CAPTURE: {
                    try {
//                        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                        Bitmap bitmap = data.getParcelableExtra("data");

                        imageCallback.run(bitmap);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                }
                case VIDEO_CAPTURE: {
                    if (resultCode == RESULT_OK) {
                        Uri uriVideo = data.getData();
                        Cursor cursor = this.getContentResolver().query(uriVideo, null, null, null, null);
                        if (cursor.moveToNext()) {
                            String strVideoPath = cursor.getString(cursor.getColumnIndex("_data"));
                            Toast.makeText(this, strVideoPath, Toast.LENGTH_SHORT).show();
                            String videoPath = "111111113.3gp";
                            Date now = new Date();
                            SimpleDateFormat f2 = new SimpleDateFormat("yyyyMMdd");
                            SimpleDateFormat f3 = new SimpleDateFormat("HHmmss");
                            videoPath = f2.format(now) + "_" + f3.format(now) + ".3gp";
                            File file = new File(strVideoPath);
                            String pFile = file.getParentFile().getPath() + "/";
                            String newPath = pFile + videoPath;
                            file.renameTo(new File(newPath));
                            DIALOG.toast(this, "录像文件存储在：" + newPath);
                        }
                    }
                    break;
                }
           /* case GET_URI: {
                if (data == null) {
                    return;
                }
                Uri originalUri = data.getData();
                uriCallback.run(false, originalUri);
                break;
                case IMAGE_CAPTURE_PATH: {
                    if (data == null) {
                        return;
                    }
                    Uri originalUri = data.getData();
                    uriCallback.run(false, originalUri);
                    break;
                }
            }*/
                default:
                    break;
            }
        }

    }

    protected static void openCamera(Activity activity ,CALLBACK1<Bitmap> callback) {
        imageCallback = callback;
        //启动拍照前判断sdcard是否可用
        String state = Environment.getExternalStorageState();
        try {
            if (state.equals(Environment.MEDIA_MOUNTED)) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                activity.startActivityForResult(intent, IMAGE_CAPTURE);
            } else {
                DIALOG.toast(activity,"sdcard不可用！");
            }
        } catch (ActivityNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected static void openUri(Activity activity,CALLBACK1<Bitmap> callback) {
        uriCallback = callback;
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        activity.startActivityForResult(intent, GET_URI);
    }
}
