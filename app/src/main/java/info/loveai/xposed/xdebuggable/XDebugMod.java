package info.loveai.xposed.xdebuggable;


import de.robv.android.xposed.*;
import de.robv.android.xposed.callbacks.XC_LoadPackage.LoadPackageParam;

public class XDebugMod implements IXposedHookLoadPackage, IXposedHookZygoteInit {
    // configuration XSharedPreferences
    // debuggable
    // misc
    // dumpdex

    @Override
    public void handleLoadPackage(final LoadPackageParam lpparam) throws Throwable {
        XLuckyPatcher.handle(lpparam);
        XDebugSwitch.handle(lpparam);
        XDumpDex.handle(lpparam);
    }

    @Override
    public void initZygote(IXposedHookZygoteInit.StartupParam param)
    {

    }

}