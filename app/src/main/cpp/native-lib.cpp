#include <jni.h>
#include <string>
#include <fstream>
#include <sstream>

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_upworktask_MainActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "i am reading this from cpp";
    return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_upworktask_MainActivity_readFileFromCpp(
        JNIEnv* env,
        jobject /* this */,
        jstring filePath) {
    const char *path = env->GetStringUTFChars(filePath, nullptr);

    std::ifstream file(path);
    if (!file.is_open()) {
        env->ReleaseStringUTFChars(filePath, path);
        return nullptr;
    }

    std::stringstream buffer;
    buffer << file.rdbuf();
    file.close();

    env->ReleaseStringUTFChars(filePath, path);
    return env->NewStringUTF(buffer.str().c_str());
}
