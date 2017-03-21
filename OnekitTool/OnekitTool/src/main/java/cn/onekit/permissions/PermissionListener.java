package cn.onekit.permissions;

import java.util.List;

/**
 * Created by BryantCurry on 2017/1/14.
 */

public interface PermissionListener {
    void onGranted();

    void onDenied(List<String> deniedPermission);
}
