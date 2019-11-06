package info.loveai.xposed.xdebuggable;

import java.util.ArrayList;
import java.util.List;

import de.robv.android.xposed.callbacks.XC_LoadPackage;

// https://codeshare.frida.re/@masbog/frida-android-unpinning-ssl/
// https://github.com/ac-pm/Inspeckage
public class XSSLPinning {
    private static final String PKG_NAME = "com.android.browser";
    private static List<String> PKG_NAME_LIST = new ArrayList<String>();
    static{
        PKG_NAME_LIST.add("com.android.browser");
        PKG_NAME_LIST.add("com.forkliu");
        PKG_NAME_LIST.add("org.chromium.chrome");
        PKG_NAME_LIST.add("com.sankuai.meituan");
        PKG_NAME_LIST.add("com.miui.packageinstaller");
        PKG_NAME_LIST.add("com.android.packageinstaller");
        PKG_NAME_LIST.add("com.huawei");
    }
    public static void handle(final XC_LoadPackage.LoadPackageParam lpparam) {
        boolean needHook = false;
        for(String name:PKG_NAME_LIST){
            if (lpparam.packageName.startsWith(name)){
                needHook = true;
                break;
            }
        }

        if (!needHook) return;
        org.conscrypt.XTrustManagerImpl.handle(lpparam);
    }
}
