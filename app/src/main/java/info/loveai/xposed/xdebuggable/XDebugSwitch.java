package info.loveai.xposed.xdebuggable;

import com.android.server.pm.XPackageManagerService;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

public class XDebugSwitch {
    public static void handle(final XC_LoadPackage.LoadPackageParam lpparam) {
        if (!lpparam.processName.equals("android")){
            return;
        }
        if (!lpparam.packageName.equals("android")){
            return;
        }

        XPackageManagerService.handle(lpparam);
    }
}
