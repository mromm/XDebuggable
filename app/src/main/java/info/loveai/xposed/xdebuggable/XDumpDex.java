package info.loveai.xposed.xdebuggable;


import com.android.dex.XDex;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

// https://github.com/WrBug/dumpDex
public class XDumpDex {
    // com.screeclibinvoke
    // com.jy.recorder
    private static final String PKG_NAME = "com.jy.recorder";
    public static String sCurrentPackageName = "";
    public static void handle(final XC_LoadPackage.LoadPackageParam lpparam) {
        if (!lpparam.packageName.equals(PKG_NAME)){
            return;
        }

        if (!lpparam.processName.equals(PKG_NAME)){
            return;
        }

        sCurrentPackageName = lpparam.packageName;

        XClassLoader.handle(lpparam);
        //XDex.handle(lpparam);
    }
}
