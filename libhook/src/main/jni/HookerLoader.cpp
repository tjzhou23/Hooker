#include <jni.h>
#include <string>
#include <cassert>

#define CLASS_HOOKER "com/sysu/zhou/libhook/Hooker"

jstring init(JNIEnv *env, jclass type) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

static JNINativeMethod  jniHooker[] = {
        {
            "init",
            "()Ljava/lang/String;",
            (void *)init
        }
};

static bool registerNativeMethod(JNIEnv *env, const char *className, JNINativeMethod *jniMethods, int methods) {
    jclass clazz = env->FindClass(className);
    if (clazz == nullptr) {
        return false;
    }
    return env->RegisterNatives(clazz, jniMethods, methods) >= 0;
}

JNIEXPORT jint JNICALL JNI_OnLoad(JavaVM *vm, void* reserved) {
    JNIEnv *env = nullptr;
    if (vm->GetEnv((void **) &env, JNI_VERSION_1_6) != JNI_OK) {
        return -1;
    }
    assert(env != nullptr);
    if (!registerNativeMethod(env, CLASS_HOOKER, jniHooker, sizeof(jniHooker) / sizeof(JNINativeMethod))) {
        return -1;
    }
    return JNI_VERSION_1_6;
}