package info.loveai.xposed.xdebuggable;


import com.android.dex.XDex;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

// https://github.com/WrBug/dumpDex
public class XDumpDex {
    public static void handle(final XC_LoadPackage.LoadPackageParam lpparam) {
        if (!lpparam.packageName.equals("com.cashtoutiao")){
            return;
        }

        if (!lpparam.processName.equals("com.cashtoutiao")){
            return;
        }

        XClassLoader.handle(lpparam);
        //XDex.handle(lpparam);
    }
}
