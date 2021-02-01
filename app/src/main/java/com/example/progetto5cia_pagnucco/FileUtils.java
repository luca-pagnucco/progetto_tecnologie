package com.example.progetto5cia_pagnucco;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.IntentSender;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.UserHandle;
import android.util.Log;
import android.view.Display;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

public class FileUtils {
    public static final String directory = "/PermissionApp";
    public static final String FileUtilsTAG = "FILE_UTILS";

    public static boolean createDirectory(String dir) {

        boolean ret = false;
        String path = Environment.getExternalStorageDirectory().toString()+"/"+dir;
        Log.d(FileUtilsTAG, "Creating dir: " + path);
        File directory = new File(path);

        if(!directory.exists())  {
            directory.mkdirs();
            ret = true;
        }
        return ret;
    }

    public static String listDir() {

        String path = Environment.getExternalStorageDirectory().toString()+directory;
        Log.d(FileUtilsTAG, "Path: " + path);

        File directory = new File(path);
        if(!directory.exists())
            return "directory does not exists..";
        File[] files = directory.listFiles();
        Log.d(FileUtilsTAG, "Size: "+ files.length);
        String s ="";
        for (int i = 0; i < files.length; i++)
        {
            s+= files[i].getName();
            if(i<files.length-1)
                s+=", ";
            Log.d(FileUtilsTAG, "FileName:" + files[i].getName());
        }
        return s;
    }

    public static String readFile(String filename)
    {
        String path = Environment.getExternalStorageDirectory().toString()+directory;
        File file = new File(path, filename);
        try {
            //FileInputStream fis = context.openFileInput(filename);
            FileInputStream fis = new FileInputStream(file);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line).append("\n");
            }
            return sb.toString();
        } catch (FileNotFoundException e) {
            Log.d(FileUtilsTAG, "READ ERROR: File "+filename+" not found!");
            Log.e(FileUtilsTAG, "READ ERROR: File "+filename+" not found!");
            return null;
        } catch (UnsupportedEncodingException e) {
            Log.d(FileUtilsTAG, "READ ERROR: File "+filename+" encoding error!");
            Log.e(FileUtilsTAG, "READ ERROR: File "+filename+" encoding error!");
            return null;
        } catch (IOException e) {
            Log.d(FileUtilsTAG, "READ ERROR: generic I/O exception for "+filename+"..");
            Log.e(FileUtilsTAG, "READ ERROR: generic I/O exception for "+filename+"..");
            return null;
        }
    }

    public static void saveImage(String filename, String directoryName, Context context, Bitmap image)
    {
        createDirectory(directoryName);
        String path = Environment.getExternalStorageDirectory().toString()+"/"+directoryName;
        File file = new File(path, filename);

        try {
            FileOutputStream f = new FileOutputStream(file);
            image.compress(Bitmap.CompressFormat.PNG, 100, f);
            //f.flush();
            f.close();
            MediaScannerConnection.scanFile(context, new String[] {path}, null, null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Log.d(FileUtilsTAG, "File "+filename+" not found." +
                    " check WRITE_EXTERNAL_STORAGE permission..");
            Log.e(FileUtilsTAG, "File "+filename+" not found." +
                    " check WRITE_EXTERNAL_STORAGE permission..");
        } catch (IOException e) {
            e.printStackTrace();
            Log.d(FileUtilsTAG, "Generic I/O exception while trying to write file "+filename);
            Log.e(FileUtilsTAG, "Generic I/O exception while trying to write file "+filename);
        }
    }
}