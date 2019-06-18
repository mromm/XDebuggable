package info.loveai.xposed.xdebuggable

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import info.loveai.Native

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Native.init();
        Native.installHook();
        Native.printVersion();
    }
}
