package info.loveai.xposed.xdebuggable;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import static de.robv.android.xposed.XposedHelpers.findClass;

public class XDebugMod implements IXposedHookLoadPackage {

    @Override
    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
//        XposedBridge.log("package: " + lpparam.packageName);
//        XposedBridge.log("process: " + lpparam.processName);
        if (!lpparam.processName.equals("android")){
            return;
        }
        if (!lpparam.packageName.equals("android")){
            return;
        }

        // XposedBridge.log(">>>package: " + lpparam.packageName);
        // XposedBridge.log(">>>process: " + lpparam.processName);


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
                                // XposedBridge.log(mPrefix + "pkg name:" + pi.packageName);
                                if (pi.packageName.equals("com.tencent.mm")){
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