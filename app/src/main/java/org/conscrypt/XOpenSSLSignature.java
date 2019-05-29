package org.conscrypt;

import android.view.View;
import android.widget.ImageView;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static com.sundayliu.utils.DebugHelper.printStack;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

public final class XOpenSSLSignature {
    public static void handle(final XC_LoadPackage.LoadPackageParam lpparam) {
        findAndHookMethod(
                "com.android.org.conscrypt.OpenSSLSignature",
                lpparam.classLoader,
                "engineVerify",
                byte[].class,
                new XC_MethodHook() {
                    private static final String mPrefix = "[OpenSSLSignature.engineVerify]";

                    @Override
                    protected void beforeHookedMethod(MethodHookParam param) throws Throwable {
                        super.beforeHookedMethod(param);
                    }

                    @Override
                    protected void afterHookedMethod(MethodHookParam param) throws Throwable {
                        XposedBridge.log(mPrefix + "disabled verifysignature ...");
                        param.setResult(Boolean.TRUE);
                    }
                }
        );
    }
}
