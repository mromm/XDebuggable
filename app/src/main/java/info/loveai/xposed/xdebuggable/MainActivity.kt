package info.loveai.xposed.xdebuggable

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdView
import info.loveai.Native

class MainActivity : AppCompatActivity() {

    lateinit var mAdView : AdView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mAdView = findViewById(R.id.adView)
        val adRequest = AdRequest.Builder()
            //.addTestDevice("0A4C32B28B972CA6FCD4BFC58ED367C3")
            .build()
        mAdView.loadAd(adRequest)

        Native.init();
        Native.installHook();
        Native.printVersion();
    }
}
