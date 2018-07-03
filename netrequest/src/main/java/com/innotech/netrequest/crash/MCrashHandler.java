package com.innotech.netrequest.crash;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;


import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by admin on 2018/4/23.
 * 1，线程安全
 * 2，信息收集
 * 3，信息保存
 * 4，信息上传
 * 5，友好界面退出
 */

public class MCrashHandler implements Thread.UncaughtExceptionHandler {

    private final static String TAG = "MCrashHandler";
    public static final String CRASH_PATH = Environment.getExternalStorageDirectory() + "/crash";
    public static final String CRASH_TYPE = ".log";

    private Thread.UncaughtExceptionHandler mDefaultHandler;

    //设置全局单例
    private static MCrashHandler mInstance;

    private Context context;

    //存储异常和参数信息
    private Map<String, String> paramsMap = new HashMap<String, String>();
    private String cause;

    //格式化时间
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    private Thread thread;
    private Handler handler = new Handler();

    private MCrashHandler() {
    }

    public static synchronized MCrashHandler getInstance() {
        if (mInstance == null) {
            mInstance = new MCrashHandler();
        }
        return mInstance;
    }

    /**
     * 初始化
     *
     * @param context
     */
    public void init(final Context context) {
        this.context = context.getApplicationContext();
        mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                final ArrayList<String> files = getReallyToUploadFiles(CRASH_PATH, CRASH_TYPE);
                Log.i("MCrashHandler", "crashinfos:" + (files == null ? "" : files.toString()));
                if (files != null || files.size() == 0) {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(context, CrashInfoUploadService.class);
                            intent.putStringArrayListExtra("files", files);
                            context.startService(intent);
                        }
                    });
                }
            }
        });
        thread.start();
    }

    /**
     * 检测需要上传的Log文件。
     *
     * @return
     */
    private ArrayList<String> getReallyToUploadFiles(String dir, String _type) {
        try {
            File directory = new File(dir);
            if (!directory.exists()) {
                return null;
            }
            File[] files = directory.listFiles();
            if (files == null) {
                return null;
            }
            ArrayList<String> listFiles = new ArrayList<>();
            for (int i = 0; i < files.length; i++) {
                if (files[i].isFile() && files[i].getAbsolutePath().endsWith(_type)
                        && getFileLastModify(files[i]) > 3000) {
                    listFiles.add(files[i].getAbsolutePath());
                }
            }
            return listFiles;
        } catch (Exception e) {
            Log.e(TAG, "an error occured when getReallyToUploadFiles", e);
        }
        return null;
    }

    /**
     * 返回文件最近更新为 N 毫秒前。
     *
     * @param file
     * @return
     */
    private long getFileLastModify(File file) {
        return System.currentTimeMillis() - file.lastModified();
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        long start = System.currentTimeMillis();
        Log.i(TAG, "" + Thread.currentThread());//主线程
        Log.i(TAG, "mDefaultHandler == null ?" + (mDefaultHandler == null));
        if (!handleException(e) && mDefaultHandler != null) {//如果自己没处理，交给系统处理
            mDefaultHandler.uncaughtException(t, e);
        } else {//如果自己处理了
            try {
                long current = System.currentTimeMillis();
                long delay = (current - start >= 3000 ? 0 : (3000 + start - current));
                Log.i(TAG, "delay:" + delay);
                Thread.sleep(delay);
                showCustomDialog(cause);
                System.exit(1);
            } catch (Exception ex) {

            }
        }
    }

    /**
     * 收集异常信息发送到服务器
     *
     * @param throwable
     */
    private boolean handleException(Throwable throwable) {
        if (throwable == null) {
            return false;
        }
        //手机设备信息
        collectDeviceInfo();
        //添加自定义信息
        addCustomInfo();
        //获取异常信息。
        cause = getCauseFromEx(throwable);
        //保存数据到本地
        saveCrashInfo2File(cause);
        return true;
    }

    private void collectDeviceInfo() {
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                paramsMap.put("versionName", versionName);
                paramsMap.put("versionCode", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "an error occured when collect package info", e);
        }

        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                paramsMap.put(field.getName(), field.get(null).toString());
            } catch (Exception e) {
                Log.e(TAG, "an error occured when collect crash info", e);
            }
        }
    }

    private void addCustomInfo() {

    }

    private String getCauseFromEx(Throwable ex) {
        try {
            Writer writer = new StringWriter();
            PrintWriter printWriter = new PrintWriter(writer);
            ex.printStackTrace(printWriter);
            Throwable cause = ex.getCause();
            while (cause != null) {
                cause.printStackTrace(printWriter);
                cause = cause.getCause();
            }
            printWriter.close();
            String result = writer.toString();
            return result;
        } catch (Exception e) {
            Log.e(TAG, "an error occured when getCauseFromEx", e);
        }
        return "";
    }

    private void saveCrashInfo2File(String cause) {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry entry : paramsMap.entrySet()) {
            sb.append(entry.getKey() + "=" + entry.getValue() + "\n");
        }
        sb.append(cause);
        Log.e(TAG, sb.toString());

        try {
            String fileName = "crash_" + format.format(new Date()) + ".log";
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/crash/";
                File dir = new File(path);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                Log.i(TAG, "dirs:" + dir.getAbsolutePath() + "" + fileName);
                File file = new File(dir, fileName);
                FileOutputStream fos = new FileOutputStream(file);
                fos.write(sb.toString().getBytes());
                fos.close();
                Log.i(TAG, "write successful!");
            }
        } catch (Exception e) {
            Log.e(TAG, "an error occured when saveCrashInfo2File", e);
        }
    }

    private void showCustomDialog(String cause) {
        try{
            Intent intent = new Intent(context, CrashActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("ex", cause);
            context.startActivity(intent);
        }catch (Exception e){
            Log.e(TAG, "an error occured when showCustomDialog", e);
        }
    }

}
