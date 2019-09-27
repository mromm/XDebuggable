package org.conscrypt;

import android.app.AndroidAppHelper;
import android.app.Application;

import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.callbacks.XC_LoadPackage;

import static com.sundayliu.utils.DebugHelper.printStack;
import static de.robv.android.xposed.XposedHelpers.findAndHookMethod;

// external/conscrypt/platform/src/main/java/org/conscrypt/TrustManagerImpl.java
public class XTrustManagerImpl {
//    List<X509Certificate> checkTrustedRecursive(
//            X509Certificate[] certs,
//            byte[] ocspData,
//            byte[] tlsSctData,
//            String host,
//            boolean clientAuth,
//            ArrayList<X509Certificate> untrustedChain,
//            ArrayList<TrustAnchor> trustAnchorChain,
//            Set<X509Certificate> used)


    public static void handle(final XC_LoadPackage.LoadPackageParam lpparam) {

        findAndHookMethod(
                "com.android.org.conscrypt.TrustManagerImpl",
                lpparam.classLoader,
                "checkTrustedRecursive",
                X509Certificate[].class, // certs
                byte[].class, // ocspData
                byte[].class, // tlsSctData
                String.class, // host
                boolean.class, // clientAuth
                ArrayList.class, // untrustedChain
                ArrayList.class, // trustAnchorChain
                Set.class, // used
                new XC_MethodReplacement() {
                    private static final String mPrefix = "[TrustManagerImpl.checkTrustedRecursive]";
                    @Override
                    protected Object replaceHookedMethod(MethodHookParam param) throws Throwable {
                        // XposedBridge.log(mPrefix);
                        // printStack();
                        Application app = AndroidAppHelper.currentApplication();
                        if (app != null && app.getPackageName().startsWith("com.forkliu")){
                            printStack();
                        }
                        return new ArrayList<X509Certificate>();
                    }
                }
        );
    }
}
