package com.microprogramers.library;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;

import androidx.core.app.ActivityCompat;

public class PermissionAsker {



    public static void getUserPermissions(Context context, String[] PERMISSIONS)
    {
        if (!hasPermissions(context, PERMISSIONS))
        {
            int PERMISSION_ALL = 1;
            ActivityCompat.requestPermissions((Activity) context, PERMISSIONS, PERMISSION_ALL);
        }
    }


    private static boolean hasPermissions(Context context, String... permissions)
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
