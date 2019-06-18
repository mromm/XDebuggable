#include "hookzz.h"

#include <android/log.h>
#include <jni.h>
#include <stdio.h>


const char* xdebug_version = "2019.6.18";
const char* TAG = "XDebuggable";
#define JNIREG_CLASS "info/loveai/Native"

#define __ASSERT(a)
#define DEBUG_LOG(...) __android_log_print(ANDROID_LOG_DEBUG,TAG,__VA_ARGS__)

/*
 * boolean  Z   jboolean
 * byte     B   jbyte
 * char     C   jchar
 * short    S   jshort
 * int      I   jint
 * long     L   jlong
 * float    F   jfloat
 * double   D   jdouble
 * void     V
 * objects  Lfully-qualified-class-name;
 * arrays   [array-type
 * method   (argument-types)return-type
 * Object   jobject
 * Class    jclass
 * String   jstring
 * Object[] jobjectArray
 * boolean[]jbooleanArray
 * byte[]   jbyteArray
 * char[]   jcharArray
 * short[]  jshortArray
 * int[]    jintArray
 * long[]   jlongArray
 * float[]  jfloatArray
 * double[] jdoubleArray
 */

void print_version(){
    // print xdebug version
    __android_log_print(ANDROID_LOG_INFO,TAG,"xdebuggable version:%s",xdebug_version);
    __android_log_print(ANDROID_LOG_INFO,TAG,"xdebuggable version:%s",xdebug_version);
    __android_log_print(ANDROID_LOG_INFO,TAG,"xdebuggable version:%s",xdebug_version);
    __android_log_print(ANDROID_LOG_INFO,TAG,"xdebuggable version:%s",xdebug_version);
    __android_log_print(ANDROID_LOG_INFO,TAG,"xdebuggable version:%s",xdebug_version);
    __android_log_print(ANDROID_LOG_INFO,TAG,"xdebuggable version:%s",xdebug_version);
}

void* pfn_print_version = nullptr;
void stub_print_version(){
    __android_log_print(ANDROID_LOG_DEBUG,TAG,"stub print version");
    if (pfn_print_version != nullptr){
        typedef void(*pfn)();
        pfn x = (pfn)pfn_print_version;
        x();
    }
}

FILE* (*pfn_origin_fopen)(const char*,const char*) = nullptr;
FILE* stub_fopen(const char *filename, const char*  mode)
{
    DEBUG_LOG("fopen %s|%s", filename,mode);
    if (pfn_origin_fopen != nullptr){
        return pfn_origin_fopen(filename,mode);
    }
    return nullptr;
}

void install_inline_hook(JNIEnv* env,jobject clazz){
    // ZzReplace((void*)fopen,(void*)stub_fopen,(void**)&pfn_origin_fopen);
}

void native_init(JNIEnv* env,jobject clazz){

}

void java_print_version(JNIEnv* env,jobject clazz){
    print_version();
}

static JNINativeMethod gNativeMethods[] = {
        {"init","()V",(void*)native_init},
        {"installHook","()V",(void*)install_inline_hook},
        {"printVersion","()V",(void*)java_print_version},
};

JNIEXPORT jint JNI_OnLoad(JavaVM* jvm, void* reserved)
{
    DEBUG_LOG("version:%s", xdebug_version);
#if defined(__arm__)
    DEBUG_LOG("so arch:armeabi");
#elif  defined(__i386__)
    DEBUG_LOG("so arch:x86");
#elif defined(__aarch64__)
    DEBUG_LOG("so arch:arm64");
#elif defined(__x86_64__)
    DEBUG_LOG("so arch:x64");
#endif

    JNIEnv* env = NULL;
    if (jvm->GetEnv((void**)&env,JNI_VERSION_1_4) == JNI_OK)
    {
        jclass clazz = env->FindClass(JNIREG_CLASS);
        if (clazz != NULL)
        {
            if (env->RegisterNatives(clazz,gNativeMethods, sizeof(gNativeMethods)/sizeof(gNativeMethods[0])) >= 0)
            {
                return JNI_VERSION_1_4;
            }
            else
            {
                __ASSERT(0);
            }
        }
        else
        {
            __ASSERT(0);
        }
    }
    else
    {
        __ASSERT(0);
    }
    return -1;
}

void __attribute__ ((constructor)) Constructor1(void)
{
    // INIT_ARRAY
    DEBUG_LOG("constructor1 function");
}
