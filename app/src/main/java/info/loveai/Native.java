package info.loveai;

public class Native {

    static{
        System.loadLibrary("xdebuggable");
    }
    public native static void init();
    public native static void installHook();
    public native static void printVersion();
}
