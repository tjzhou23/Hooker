package com.sysu.zhou.libhook.util;

import android.annotation.SuppressLint;
import android.os.Build;
import android.system.Os;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author zhousysu
 */
public class FileUtils {
    private static final String TAG = "FileUtils";

    public static final boolean IS_USING_PROTECTED_STORAGE = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;

    /**
     * Delete a file or a directory and its children.
     *
     * @param file The directory to delete.
     * @throws IOException Exception when problem occurs during deleting the directory.
     */
    public static void delete(File file) throws IOException {
        for (File childFile : file.listFiles()) {

            if (childFile.isDirectory()) {
                delete(childFile);
            } else {
                if (!childFile.delete()) {
                    throw new IOException();
                }
            }
        }

        if (!file.delete()) {
            throw new IOException();
        }
    }

    public static String readLine(File file) {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            return reader.readLine();
        } catch (Throwable throwable) {
            return "";
        }
    }

    public static void writeLine(File file, String line) {
        try {
            file.createNewFile();
        } catch (IOException ex) {
        }
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            writer.write(line);
            writer.flush();
        } catch (Throwable throwable) {
            Log.e(TAG, "error writing line to file " + file + ": " + throwable.getMessage());
        }
    }

    public static String getPackageName(String dataDir) {
        if (TextUtils.isEmpty(dataDir)) {
            Log.e(TAG, "getPackageName using empty dataDir");
            return "";
        }
        int lastIndex = dataDir.lastIndexOf("/");
        if (lastIndex < 0) {
            return dataDir;
        }
        return dataDir.substring(lastIndex + 1);
    }

    public static void chmod(String path, int mode) throws Exception {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            try {
                Os.chmod(path, mode);
                return;
            } catch (Exception e) {
                // ignore
            }
        }

        File file = new File(path);
        String cmd = "chmod ";
        if (file.isDirectory()) {
            cmd += " -R ";
        }
        String cmode = String.format("%o", mode);
        Runtime.getRuntime().exec(cmd + cmode + " " + path).waitFor();
    }

    public interface FileMode {
        int MODE_ISUID = 0x04000;
        int MODE_ISGID = 0x02000;
        int MODE_ISVTX = 0x01000;
        int MODE_IRUSR = 0x00400;
        int MODE_IWUSR = 0x00200;
        int MODE_IXUSR = 0x00100;
        int MODE_IRGRP = 0x00040;
        int MODE_IWGRP = 0x00020;
        int MODE_IXGRP = 0x00010;
        int MODE_IROTH = 0x00004;
        int MODE_IWOTH = 0x00002;
        int MODE_IXOTH = 0x00001;

        int MODE_755 = MODE_IRUSR | MODE_IWUSR | MODE_IXUSR
                | MODE_IRGRP | MODE_IXGRP
                | MODE_IROTH | MODE_IXOTH;
    }

    @SuppressLint("SdCardPath")
    public static String getDataPathPrefix() {
        return IS_USING_PROTECTED_STORAGE ? "/data/user_de/0/" : "/data/data/";
    }
}