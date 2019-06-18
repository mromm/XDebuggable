package info.loveai.xposed.xdebuggable;

import com.android.server.pm.XPackageManagerService;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import org.conscrypt.XOpenSSLSignature;

public class XLuckyPatcher {
    public static void handle(final XC_LoadPackage.LoadPackageParam lpparam) {
        if (!lpparam.processName.equals("android")){
            return;
        }
        if (!lpparam.packageName.equals("android")){
            return;
        }

        XOpenSSLSignature.handle(lpparam);
    }

    // android.content.ContextWrapper.bindService
    // com.android.vending
    // inappbillingservice
    // com.android.vending.billing.marketbillingservice.bind
    // marketbillingservice
    // com.android.vending.licensing.ilicensingservice
    // licensingservice

    // android.app.ContextImpl.queryIntentServices

    // android android
    // com.android.server.pm.PackageManagerService
    // installPackageAsUser
    // installPackageWithVerification
    // installPackageWithVerificationAndEncryption
    // queryIntentServices
    // scanPackageLI
    // verifySignaturesLP
    // compareSignatures
    // getPackageInfo
    // getApplicationInfo
    // generatePackageInfo
    // getInstalledApplications
    // getInstalledPackages
}
