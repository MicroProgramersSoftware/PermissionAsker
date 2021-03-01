# PermissionAsker

[![](https://www.jitpack.io/v/MicroProgramers/PermissionAsker.svg)](https://www.jitpack.io/#MicroProgramers/PermissionAsker)

Step 1. Add it in your root build.gradle at the end of repositories:
--------------------------------------------------------------------
```
allprojects {
		repositories {
			...
			maven { url 'https://www.jitpack.io' }
		}
	}
```
Step 2. Add the dependency:
---------------------------
```
dependencies {
    ...
    implementation 'com.github.MicroProgramers:PermissionAsker:1.0.1'
}
```

Usage
-----
Get Permission Dialog in just 2 lines. 
* Make a String array of your required permissions. 
* And pass the **context** and **permissions** array in to PermissionAsker.getUserPermissions(). 
And that's it.


```
        String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_SMS};
        PermissionAsker.getUserPermissions(MainActivity.this, permissions);
```
