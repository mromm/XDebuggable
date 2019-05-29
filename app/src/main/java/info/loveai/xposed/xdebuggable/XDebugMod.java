package info.loveai.xposed.xdebuggable;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import com.android.server.pm.XPackageManagerService;
import de.robv.android.xposed.*;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;
import org.conscrypt.XOpenSSLSignature;

import static com.sundayliu.utils.DebugHelper.printStack;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;
import static de.robv.android.xposed.XposedHelpers.findClass;

public class XDebugMod implements IXposedHookLoadPackage, IXposedHookZygoteInit {

    @Override
    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
        if (!lpparam.processName.equals("android")){
            return;
        }
        if (!lpparam.packageName.equals("android")){
            return;
        }

        XPackageManagerService.handle(lpparam);
        XOpenSSLSignature.handle(lpparam);
    }

    @Override
    public void initZygote(IXposedHookZygoteInit.StartupParam param)
    {

    }

}