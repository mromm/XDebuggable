package info.loveai.xposed.xdebuggable;

import de.robv.android.xposed.callbacks.XC_LoadPackage;

// https://codeshare.frida.re/@masbog/frida-android-unpinning-ssl/
// https://github.com/ac-pm/Inspeckage
public class XSSLPinning {
    private static final String PKG_NAME = "com.android.browser";
    public static void handle(final XC_LoadPackage.LoadPackageParam lpparam) {
        if (!lpparam.processName.equals(PKG_NAME)){
            return;
        }
        if (!lpparam.packageName.equals(PKG_NAME)){
            return;
        }

        org.conscrypt.XTrustManagerImpl.handle(lpparam);
    }
}
