package java.lang;

import android.os.Build;
import com.sundayliu.utils.IOHelper;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import java.lang.reflect.Method;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import static de.robv.android.xposed.XposedHelpers.findMethodBestMatch;
import static java.lang.Class.forName;

public class XClassLoader {
    public static void handle(final XC_LoadPackage.LoadPackageParam lpparam) {
        findAndHookMethod(
                "java.lang.ClassLoader",
                lpparam.classLoader,
                "loadClass",
                String.class,
                boolean.class,
                new XC_MethodHook() {
                    private static final String mPrefix = "[ClassLoader.loadClass]";

                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        String name = (String)param.args[0];
                        // XposedBridge.log(mPrefix + "class name:" + name);
                        Class<?> cls = (Class<?>)param.getResult();
                        if (cls != null){
                            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
                                Method mGetDex = findMethodBestMatch(Class.forName("java.lang.Class"),
                                        "getDex");
                                if (mGetDex != null) {
                                    //XposedBridge.log("support getDex");

                                    Object dex = mGetDex.invoke(cls);
                                    Method mGetBytes = findMethodBestMatch(Class.forName("com.android.dex.Dex"),
                                            "getBytes");
                                    if (mGetBytes != null){
                                        //XposedBridge.log("support Dex.getBytes");
                                        byte[] data = (byte[])mGetBytes.invoke(dex);
                                        if (data != null){
                                            if (name.startsWith("de.robv.android.xposed")) return;
                                            
                                            XposedBridge.log(mPrefix + "dex size:" + data.length);
                                            XposedBridge.log(mPrefix + "class name:" + name);
                                            ClassLoader classLoader = (ClassLoader)param.thisObject;
                                            if (classLoader != null){
                                                XposedBridge.log(mPrefix + "classloader:" + classLoader.toString());
                                            }

                                            String filename = String.format("/sdcard/sdk/dex2/dex_%d.dex",data.length);
                                            IOHelper.writeByte(data,filename);
                                        }
                                    }

                                }
                            }
                            // dumpdex
//                            ClassLoader classloader = cls.getClassLoader();
//                            XposedBridge.log(mPrefix + "classloader:" + classloader.toString());
                        }


                    }
                }
        );
    }
}
