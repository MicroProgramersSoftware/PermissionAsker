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

import java.util.ArrayList;
import java.util.Arrays;

import static android.os.Build.VERSION.SDK_INT;

public class PermissionAsker extends Activity
{

    Context context;
    private static final int PERMISSION_REQUEST_CODE = 123;

    public PermissionAsker(Context context)
    {
        this.context = context;
    }


    public static void getUserPermissions(Context context, String[] PERMISSIONS)
    {
        if (!hasPermissions(context, PERMISSIONS))
        {
            int PERMISSION_ALL = 1;

            ArrayList<String> permissionsList = new ArrayList<>(Arrays.asList(PERMISSIONS));
            if (permissionsList.contains(Manifest.permission.READ_EXTERNAL_STORAGE) || permissionsList.contains(Manifest.permission.WRITE_EXTERNAL_STORAGE))
            {
                if (SDK_INT >= Build.VERSION_CODES.R)
                {
                    try
                    {
                        Intent intent = new Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
                        intent.addCategory("android.intent.category.DEFAULT");
                        intent.setData(Uri.parse(String.format("package:%s", context.getApplicationContext().getPackageName())));
                        ((Activity)context).startActivityForResult(intent, 2296);
                    } catch (Exception e)
                    {
                        Intent intent = new Intent();
                        intent.setAction(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                        ((Activity)context).startActivityForResult(intent, 2296);
                    }
                }
                else
                {
                    //below android 11
                    ActivityCompat.requestPermissions(((Activity) context), PERMISSIONS, PERMISSION_REQUEST_CODE);
                }
            }

            ActivityCompat.requestPermissions((Activity) context, PERMISSIONS, PERMISSION_ALL);
        }


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2296)
        {
            if (SDK_INT >= Build.VERSION_CODES.R)
            {
                if (Environment.isExternalStorageManager())
                {
//                    permissionCallbacks.onStoragePermissionGranted();
                }
                else
                {
//                    Toast.makeText(context, "Permission required", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        switch (requestCode)
        {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0)
                {
                    ArrayList<String> results = new ArrayList<>(Arrays.asList(permissions));
                    int index;
                    if (results.contains(Manifest.permission.WRITE_EXTERNAL_STORAGE))
                    {
                        index = results.indexOf(Manifest.permission.WRITE_EXTERNAL_STORAGE);
                        boolean WRITE_EXTERNAL_STORAGE = grantResults[index] == PackageManager.PERMISSION_GRANTED;
                        if (WRITE_EXTERNAL_STORAGE)
                        {
//                            permissionCallbacks.onStoragePermissionGranted();
                        }
                        else
                        {
//                            permissionCallbacks.onStoragePermissionDenied();
                        }
                    }


                }
                break;
        }
    }


    public static boolean hasPermissions(Context context, String... permissions)
    {
        if (context != null && permissions != null)
        {
            for (String permission : permissions)
            {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED)
                {
                    return false;
                }
            }
        }
        return true;
    }

}
