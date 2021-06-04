package com.microprogramers.library;

public interface PermissionCallbacks
{
    /**
     * These are only called when you ask for storage permission
     */
    public void onStoragePermissionGranted();
    public void onStoragePermissionDenied();
}
