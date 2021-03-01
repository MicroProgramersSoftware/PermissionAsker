![PermissionAsker](https://github.com/MicroProgramers/PermissionAsker/blob/master/placeholder.png)

# PermissionAsker

[![](https://www.jitpack.io/v/MicroProgramers/PermissionAsker.svg)](https://www.jitpack.io/#MicroProgramers/PermissionAsker)


Step 1. Set minSdkVersion to 21 before using the dependency:
------------------------------------------------------------
Go to **build.gradle(:app)** and set this
```
	minSdkVersion 21
```

Step 2. Add it in your root build.gradle at the end of repositories:
--------------------------------------------------------------------
```
allprojects {
		repositories {
			...
			maven { url 'https://www.jitpack.io' }
		}
	}
```
Step 3. Add the dependency:
---------------------------
```
dependencies {
    ...
    implementation 'com.github.MicroProgramers:PermissionAsker:1.0.2'
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
        PermissionAsker.getUserPermissions(getApplicationContext(), permissions);
```




![PermissionAsker](https://github.com/MicroProgramers/PermissionAsker/blob/master/permissions.jpg)

License
-------

    Copyright 2014 - 2021 MicroProgramers

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
