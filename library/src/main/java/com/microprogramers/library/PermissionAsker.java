package com.microprogramers.library;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.Arrays;

import static android.os.Build.VERSION.SDK_INT;


public class PermissionAsker {

    private static final int PERMISSION_REQUEST_CODE = 123;

    public interface PermissionCallback {
        void onPermissionGranted(String[] permissions);
        void onPermissionDenied(String[] permissions);
    }

    public static boolean hasPermissions(Context context, String... permissions) {
        if (context == null || permissions == null) {
            return false;
        }
        for (String permission : permissions) {
            if (ContextCompat.checkSelfPermission(context.getApplicationContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
        }
        return true;
    }

    public static void getUserPermissions(Context context, String[] permissions, PermissionCallback callback) {
        if (!hasPermissions(context, permissions)) {
            ArrayList<String> permissionList = new ArrayList<>(Arrays.asList(permissions));
            if (permissionList.contains(Manifest.permission.READ_EXTERNAL_STORAGE) ||
                permissionList.contains(Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                    Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                    intent.setData(Uri.parse(String.format("package:%s", context.getPackageName())));
                    ((Activity) context).startActivityForResult(intent, PERMISSION_REQUEST_CODE);
                } else {
                    ActivityCompat.requestPermissions((Activity) context, permissions, PERMISSION_REQUEST_CODE);
                }
            } else {
                ActivityCompat.requestPermissions((Activity) context, permissions, PERMISSION_REQUEST_CODE);
            }
        } else if (callback != null) {
            callback.onPermissionGranted(permissions);
        }
    }

    public static void onRequestPermissionsResult(Activity activity, int requestCode, String[] permissions, int[] grantResults, PermissionCallback callback) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0) {
                ArrayList<String> grantedPermissions = new ArrayList<>();
                ArrayList<String> deniedPermissions = new ArrayList<>();
                for (int i = 0; i < permissions.length; i++) {
                    if (grantResults[i] == PackageManager.PERMISSION_GRANTED) {
                        grantedPermissions.add(permissions[i]);
                    } else {
                        deniedPermissions.add(permissions[i]);
                    }
                }

                if (callback != null) {
                    if (!deniedPermissions.isEmpty()) {
                        callback.onPermissionDenied(deniedPermissions.toArray(new String[0]));
                    } else {
                        callback.onPermissionGranted(grantedPermissions.toArray(new String[0]));
                    }
                }
            }
        }
    }
}
