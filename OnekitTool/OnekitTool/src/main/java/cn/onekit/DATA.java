package cn.onekit;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by BryantCurry on 2016/12/28.
 */

public class DATA {
    /**
     * BASE64转byte数组
     * @param base64
     * @return
     */
    public static void base642bytes(String base64,CALLBACK1<byte[]> callback){
        byte[] bytes = Base64.decode(base64, Base64.DEFAULT);
        callback.run(bytes);
    }
    /**
     * BASE64转图片
     * @param base64 base64
     */
    public static void base642Image(String base64,final CALLBACK1<Bitmap> callback){
        DATA.base642bytes(base64, new CALLBACK1<byte[]>() {
            @Override
            public void run(byte[] bytes) {
                Bitmap iamge = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                callback.run(iamge);
            }
        });
    }

    /**
     * base64转data数据
     * @param base64  base64
     */
    public static void base642data(String base64, final CALLBACK1<InputStream> callback){
        DATA.base642bytes(base64, new CALLBACK1<byte[]>() {
            @Override
            public void run(byte[] bytes) {
                DATA.bytes2data(bytes, new CALLBACK1<InputStream>() {
                    @Override
                    public void run(InputStream inputStream) {
                        callback.run(inputStream);
                    }
                });
            }
        });
    }
    /**
     * 图片转BASE64
     * @param image 图片
     *
     */
    public static void image2base64(Bitmap image,final CALLBACK1<String> callback){
       DATA.image2bytes(image, new CALLBACK1<byte[]>() {
            @Override
            public void run(byte[] bytes) {
                DATA.bytes2base64(bytes, new CALLBACK1<String>() {
                    @Override
                    public void run(String arg1) {
                        callback.run(arg1);
                    }
                });
            }
        });
    }
    /**
     * 图片转byte数组
     * @param image
     * @return
     */
    public static void image2bytes(Bitmap image,final CALLBACK1<byte[]> callback){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 80, baos);
        byte[] bytes = baos.toByteArray();
        callback.run(bytes);
    }
    /**
     * Bitmap图片转data数据（InpuSteam）
     * @param image
     * @return
     */
    public static void image2data(Bitmap image,final CALLBACK1<InputStream> callback){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG,80,baos);
        ByteArrayInputStream is = new ByteArrayInputStream(baos.toByteArray());
        callback.run(is);
    }
    /**
     * data数据转Bitmap
     * @param data
     * @return
     */
    public static void data2Image(InputStream data,final CALLBACK1<Bitmap> callback){
        Bitmap bitmap = BitmapFactory.decodeStream(data);
        if (bitmap != null){
            callback.run(bitmap);
        }else {
            return;
        }
    }
    /**
     * data数据转Base64
     * @param data
     * @return
     */
    public static void data2base64(InputStream data,final CALLBACK1<String> callback){
        DATA.data2bytes(data, new CALLBACK1<byte[]>() {
            @Override
            public void run(byte[] bytes) {
                DATA.bytes2base64(bytes, new CALLBACK1<String>() {
                    @Override
                    public void run(String arg1) {
                        callback.run(arg1);
                    }
                });
            }
        });
    }

    /**
     * data转byte数组
     * @param data
     * @return
     */
    public static void data2bytes(InputStream data,final CALLBACK1<byte[]> callback){
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        try {
            while ((rc = data.read(buff, 0, 100)) > 0) {
                swapStream.write(buff, 0, rc);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] in2b = swapStream.toByteArray();
        callback.run(in2b);
    }

    /**
     * byte数组转data数据
     * @param bytes
     * @return
     */
    public static void bytes2data(byte[] bytes,final CALLBACK1<InputStream> callback){
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        callback.run(bais);
    }
    /**
     * byte数组转BASE64
     * @param bytes
     * @return
     */
    public static void bytes2base64(byte[] bytes,final CALLBACK1<String> callback){
        byte[] encode = Base64.encode(bytes, Base64.DEFAULT);
        String s = new String(encode);
        callback.run(s);
    }

    /**
     * byte数组转图片
     * @param bytes
     * @return
     */
    public static void bytes2image(byte[] bytes,final CALLBACK1<Bitmap> callback){
        if (bytes.length != 0){
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            callback.run(bitmap);
        }
    }


    //同步的方法
    /**
     * BASE64转byte数组
     * @param base64
     * @return
     */
    static byte[] base642bytes(String base64){
        byte[] bytes = Base64.decode(base64, Base64.DEFAULT);
        return bytes;
    }
    /**
     * BASE64转图片
     * @param base64 base64
     */
    static Bitmap base642Image(String base64){
        byte[] bytes = DATA.base642bytes(base64);
        Bitmap iamge = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return iamge;
    }

    /**
     * base64转data数据
     * @param base64  base64
     */
    static InputStream base642data(String base64){
        byte[] bytes = DATA.base642bytes(base64);
        return DATA.bytes2data(bytes);
    }
    /**
     * 图片转BASE64
     * @param image 图片
     *
     */
    static String image2base64(Bitmap image){
        byte[] bytes = DATA.image2bytes(image);
        return DATA.bytes2base64(bytes);
    }
    /**
     * 图片转byte数组
     * @param image
     * @return
     */
    static byte[] image2bytes(Bitmap image){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.PNG, 80, baos);
        byte[] bytes = baos.toByteArray();
        return bytes;
    }
    /**
     * Bitmap图片转data数据（InpuSteam）
     * @param image
     * @return
     */
    static InputStream image2data(Bitmap image){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG,80,baos);
        ByteArrayInputStream is = new ByteArrayInputStream(baos.toByteArray());
        return is;
    }
    /**
     * data数据转Bitmap
     * @param data
     * @return
     */
    static Bitmap data2Image(InputStream data){
        Bitmap bitmap = BitmapFactory.decodeStream(data);
        return bitmap;
    }
    /**
     * data数据转Base64
     * @param data
     * @return
     */
    static String data2base64(InputStream data){
        byte[] bytes = DATA.data2bytes(data);
        return DATA.bytes2base64(bytes);
    }

    /**
     * data转byte数组
     * @param data
     * @return
     */
    static byte[] data2bytes(InputStream data){
        ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
        byte[] buff = new byte[100];
        int rc = 0;
        try {
            while ((rc = data.read(buff, 0, 100)) > 0) {
                swapStream.write(buff, 0, rc);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        byte[] in2b = swapStream.toByteArray();
        return in2b;
    }

    /**
     * byte数组转data数据
     * @param bytes
     * @return
     */
    static InputStream bytes2data(byte[] bytes){
        ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
        return bais;
    }
    /**
     * byte数组转BASE64
     * @param bytes
     * @return
     */
    static String bytes2base64(byte[] bytes){
        byte[] encode = Base64.encode(bytes, Base64.DEFAULT);
        String s = new String(encode);
        return s;
    }

    /**
     * byte数组转图片
     * @param bytes
     * @return
     */
    static Bitmap bytes2image(byte[] bytes){
        Bitmap bitmap = null;
        if (bytes.length != 0){
            bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        }
        return bitmap;
    }
}
