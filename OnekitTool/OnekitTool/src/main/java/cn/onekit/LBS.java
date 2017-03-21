package cn.onekit;

import android.Manifest;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import java.util.List;
import cn.onekit.permissions.BaseActivity;
import cn.onekit.permissions.PermissionListener;

/**
 * Created by BryantCurry on 2017/1/9.
 */

public class LBS {
    private static  LocationManager locationManager = null;
    private static final int ACCESS_COARSE_LOCATION_CODE = 0x0022;
    private static final int GPS_PROVIDER = 0x0023;
    private static final int NETWORK_PROVIDER = 0x0024;
    public static void getLocation(final Context context, final CALLBACK1<String> successCallback, final CALLBACK0 failureCallback) {
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        final LocationListener locationListener = new LocationListener() {

            // Provider的状态在可用、暂时不可用和无服务三个状态直接切换时触发此函数
            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            // Provider被enable时触发此函数，比如GPS被打开
            @Override
            public void onProviderEnabled(String provider) {
                Log.e("Map", "onProviderEnabled ");
            }

            // Provider被disable时触发此函数，比如GPS被关闭
            @Override
            public void onProviderDisabled(String provider) {

            }

            //当坐标改变时触发此函数，如果Provider传进相同的坐标，它就不会被触发
            @Override
            public void onLocationChanged(Location location) {
                if (location != null) {
                    Log.e("Map", "Location changed : Lat: "
                            + location.getLatitude() + " Lng: "
                            + location.getLongitude());
                    successCallback.run("{经度是："+ location.getLongitude() + "，" + "纬度是：" + location.getLatitude() + "}");
                    stopLister(this);
                }else {
                    failureCallback.run();
                }
            }
        };
        if(locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER))
        {
            BaseActivity.requestRuntimePermission(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, new PermissionListener() {
                @Override
                public void onGranted() {
                    try{
                        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000, 0,locationListener);
                    }catch (SecurityException e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onDenied(List<String> deniedPermission) {

                }
            });
        }
        else
        {
            BaseActivity.requestRuntimePermission(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, new PermissionListener() {
                @Override
                public void onGranted() {
                    try{
                        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,1000, 0,locationListener);
                    }catch (SecurityException e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onDenied(List<String> deniedPermission) {

                }
            });

        }
    }
    /**
     * 销毁定位
     */
    private static void stopLister(final LocationListener listener) {
        if (locationManager != null) {
            BaseActivity.requestRuntimePermission(new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, new PermissionListener() {
                @Override
                public void onGranted() {
                    try{
                        locationManager.removeUpdates(listener);
                    }catch (SecurityException e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onDenied(List<String> deniedPermission) {

                }
            });
        }
        locationManager = null;
    }

}
