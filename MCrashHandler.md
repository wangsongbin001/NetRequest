#MCrashHandler(全局的异常处理)

-------------------

**简介**：
使用MCrashHandler,对全局未捕获的异常，进行统一处理。并提供友好的退出界面。

**封装成果**，封装后具有如下功能：

1. 异常信息的收集。
2. 异常信息本地存储。
3. 异常信息策略上传（未完）
4. 提供统一界面（自定义）

**具体配置**：

- 需要添加依赖Lib Module：NetQuest
- 添加权限：(必要时需要动态授权)

```

       <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
       <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
       <uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS"/>
```

- 再Application中onCreate中初始化：MCrashHandler.getInstance().init(this);
- 兼容Android 7.0的FileProvider：
       
        <provider
            android:name="android.support.v4.content.FileProvider"
            android:authorities="com.innotech.mydemo.fileprovider"
            android:exported="false"
            android:grantUriPermissions="true">
            <meta-data
                android:name="android.support.FILE_PROVIDER_PATHS"
                android:resource="@xml/filepaths"/>

        </provider>
     
