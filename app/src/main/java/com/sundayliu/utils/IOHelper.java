package com.sundayliu.utils;

import de.robv.android.xposed.XposedBridge;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class IOHelper {
    public  static void writeByte(byte[] data, String filename) {
        try {
            OutputStream outputStream = new FileOutputStream(filename);
            outputStream.write(data);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            XposedBridge.log("文件写出失败");
        }
    }
}
