package com.sundayliu.utils;

import android.util.Base64;

import de.robv.android.xposed.XposedBridge;

public final class DebugHelper {
    public static void printStack() {
        XposedBridge.log("Printing stack trace{");
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
        for (int i = 1; i < elements.length; i++) {
            StackTraceElement s = elements[i];
            XposedBridge.log("\tat " + s.getClassName() + "." + s.getMethodName()
                    + "(" + s.getFileName() + ":" + s.getLineNumber() + ")");
        }
        XposedBridge.log("}Printing stack trace");
    }

    public static String byteToString(byte[] input){
        StringBuffer sb = new StringBuffer();
        for(byte x : input){
            sb.append(String.format("%02X", x));
        }
        return sb.toString();
    }

    public static String base64Encode(byte[] input)
    {
        //Base64.getEncoder().encode(input);
        byte[] encode = Base64.encode(input, Base64.DEFAULT);
        return new String(encode);
    }

//    public static void printStack() {
//        XposedBridge.log("Printing stack trace{");
//        StackTraceElement[] elements = Thread.currentThread().getStackTrace();
//        for (int i = 1; i < elements.length; i++) {
//            StackTraceElement s = elements[i];
//            XposedBridge.log("\tat " + s.getClassName() + "." + s.getMethodName()
//                    + "(" + s.getFileName() + ":" + s.getLineNumber() + ")");
//        }
//        XposedBridge.log("}Printing stack trace");
//    }
}
