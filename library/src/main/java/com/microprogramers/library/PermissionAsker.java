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

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import java.util.HashMap;
import java.util.Map;

public class PermissionAsker extends Activity {

    private static final int PERMISSION_REQUEST_CODE = 123;
    private static final int REQUEST_CODE_MANAGE_ALL_FILES_ACCESS_PERMISSION = 2296;

    public interface PermissionCallback {
        void onStoragePermissionGranted();
        void onStoragePermissionDenied();
    }

    public static void requestStoragePermission(Activity activity, PermissionCallback callback) {
        if (hasStoragePermission(activity)) {
            callback.onStoragePermissionGranted();
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try {
                Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                intent.addCategory("android.intent.category.DEFAULT");
                intent.setData(Uri.parse(String.format("package:%s", activity.getApplicationContext().getPackageName())));
                activity.startActivityForResult(intent, REQUEST_CODE_MANAGE_ALL_FILES_ACCESS_PERMISSION);
            } catch (Exception e) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                activity.startActivityForResult(intent, REQUEST_CODE_MANAGE_ALL_FILES_ACCESS_PERMISSION);
            }
        } else {
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    public static boolean hasStoragePermission(Context context) {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }

    public static void onActivityResult(Activity activity, int requestCode, int resultCode, Intent data, PermissionCallback callback) {
        if (requestCode == REQUEST_CODE_MANAGE_ALL_FILES_ACCESS_PERMISSION) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
                if (Environment.isExternalStorageManager()) {
                    callback.onStoragePermissionGranted();
                } else {
                    callback.onStoragePermissionDenied();
                }
            }
        }
    }

    public static void onRequestPermissionsResult(Activity activity, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults, PermissionCallback callback) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            Map<String, Integer> permissionResults = new HashMap<>();
            for (int i = 0; i < permissions.length; i++) {
                permissionResults.put(permissions[i], grantResults[i]);
            }

            if (permissionResults.containsKey(Manifest.permission.WRITE_EXTERNAL_STORAGE) &&
                permissionResults.get(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                callback.onStoragePermissionGranted();
            } else {
                callback.onStoragePermissionDenied();
            }
        }
    }
}