package com.android.dex;

import android.os.Build;
import com.sundayliu.utils.IOHelper;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;

import static com.sundayliu.utils.DebugHelper.printStack;
import static de.robv.android.xposed.XposedHelpers.*;

public class XDex {
    public static void handle(final XC_LoadPackage.LoadPackageParam lpparam) {
        findAndHookMethod(
                "com.android.dex.Dex",
                lpparam.classLoader,
                "loadFrom",
                InputStream.class,
                new XC_MethodHook() {
                    private static final String mPrefix = "[Dex.loadFrom]";

                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        Object dex = param.thisObject;
                        Method mGetBytes = findMethodBestMatch(Class.forName("com.android.dex.Dex"),
                                "getBytes");
                        if (mGetBytes != null && dex != null){
                            byte[] data = (byte[])mGetBytes.invoke(dex);
                            if (data != null){
                                XposedBridge.log(mPrefix + "dex size:" + data.length);
                                String filename = String.format("/sdcard/sdk/dex1/dex_%d.dex",data.length);
                                IOHelper.writeByte(data,filename);
                            }

                        }
                    }
                }
        );

        findAndHookConstructor("com.android.dex.Dex",
                lpparam.classLoader,
                ByteBuffer.class,new XC_MethodHook() {
                    private static final String mPrefix = "[Dex.Dex]";

                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        Object dex = param.thisObject;
                        Method mGetBytes = findMethodBestMatch(Class.forName("com.android.dex.Dex"),
                                "getBytes");
                        if (mGetBytes != null && dex != null){
                            byte[] data = (byte[])mGetBytes.invoke(dex);
                            if (data != null){
                                XposedBridge.log(mPrefix + "dex size:" + data.length);
                                String filename = String.format("/sdcard/sdk/dex1/dex_%d.dex",data.length);
                                IOHelper.writeByte(data,filename);
                            }

                        }
                    }
                });
    }
}
