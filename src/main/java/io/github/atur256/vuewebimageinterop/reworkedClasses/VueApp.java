package io.github.atur256.vuewebimageinterop.reworkedClasses;

import org.graalvm.webimage.api.*;


public class VueApp extends JSObject {

    private static JSObject mountedInstance;

    @JS.Coerce
    @JS("return this.mount('#app')")
    private native JSObject mountJS();

    public JSObject mount() {
        mountedInstance = this.mountJS();
        return mountedInstance;
    }

    @JS.Coerce
    @JS("this.unmount('#app')")
    private native void unmountJS();

    public void unmount() {
        mountedInstance = null;
        unmountJS();
    }

    public static Object getValue(String key) {
        return mountedInstance.get(key);
    }

    public static <R> R getValue(String key, Class<R> cls) {
        return JSValue.checkedCoerce(mountedInstance.get(key), cls);
    }

    public static void setValue(String key, int value) {
        mountedInstance.set(key, JSNumber.of(value));
    }

    public static void setValue(String key, double value) {
        mountedInstance.set(key, JSNumber.of(value));
    }

    public static void setValue(String key, boolean value) {
        mountedInstance.set(key, JSBoolean.of(value));
    }

    public static void setValue(String key, String value) {
        mountedInstance.set(key, JSString.of(value));
    }

    public static void setValue(String key, JSObject value) {
        mountedInstance .set(key, value);
    }
}
