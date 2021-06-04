package com.microprogramers.permissionasker;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;
import android.view.View;

import com.microprogramers.library.PermissionAsker;
import com.microprogramers.library.PermissionCallbacks;

public class MainActivity extends AppCompatActivity implements PermissionCallbacks
{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void perm(View view)
    {
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_SMS};
        PermissionAsker permissionAsker = new PermissionAsker(this, this);
        permissionAsker.getUserPermissions(MainActivity.this, permissions);
    }

    @Override
    public void onStoragePermissionGranted()
    {

    }

    @Override
    public void onStoragePermissionDenied()
    {

    }
}