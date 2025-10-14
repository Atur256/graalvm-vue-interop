package io.github.atur256.vuewebimageinterop;

import io.github.atur256.webimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.*;


public class JSVueWatch {

    private JSVueWatch() {
    }

    @JS.Coerce
    @JS("return Vue.watch(source, callback);")
    public static native JSObject watch(JSValue source, JSFunction callback);

    public static JSObject watch(String keyPath, JSFunction callback) {
        return watch(JSString.of(keyPath), callback);
    }

    public static JSObject watch(JSObject reactiveSource, JSFunction callback) {
        return watch((JSValue) reactiveSource, callback);
    }

    public static <T> JSObject watch(JSVueRef<T> reactiveSource, JSFunction callback) {
        return watch(reactiveSource.raw(), callback);
    }

    @JS("Vue.watchEffect(effect);")
    public static native void watchEffect(JSObject effect);

    public static void watchEffect(JSFunction effectFn) {
        watchEffect((JSObject) effectFn);
    }
}
