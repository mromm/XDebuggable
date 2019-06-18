package com.android.server.pm;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

public class XPackageManagerService {
    public static void handle(final XC_LoadPackage.LoadPackageParam lpparam) {
        findAndHookMethod(
                "com.android.server.pm.PackageManagerService",
                lpparam.classLoader,
                "getPackageInfo",
                String.class,
                int.class,
                int.class,
                new XC_MethodHook() {
                    private static final String mPrefix = "[PM.getPackageInfo]";
                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable{
                        super.afterHookedMethod(param);
                        PackageInfo pi = (PackageInfo)param.getResult();
                        if (pi != null){
                            ApplicationInfo ai = pi.applicationInfo;
                            int flags = ai.flags;
                            if ((flags & ApplicationInfo.FLAG_SYSTEM) == 0
                                    && (flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP)== 0){
                                if (pi.packageName.equals("com.tencent.mm")
                                    || pi.packageName.startsWith("com.tencent")
                                    || pi.packageName.equals("com.eg.android.AlipayGphone")){
                                    ai.flags = ai.flags | ApplicationInfo.FLAG_DEBUGGABLE;
                                    param.setResult(pi);
                                }
                            }
                        }
                    }
                }
        );
    }
}
