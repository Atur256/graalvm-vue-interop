package io.github.atur256.vuewebimageinterop.reworkedClasses;

import io.github.atur256.webimageinterop.builtin.JSFunction;
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

    @JS.Coerce
    @JS("this.onUnmount(callback)")
    public native void onUnmountJS(JSFunction callback);

    @JS.Coerce
    @JS("this.provide(...keys)")
    public native void provide(String... keys);

    public static Object getValue(String key) {
        return mountedInstance.get(key);
    }

    public static <R> R getValue(String key, Class<R> cls) {
        return JSValue.checkedCoerce(mountedInstance.get(key), cls);
    }

    public static void setValue(String key, Object value) {
        switch(value) {
            case Integer i -> mountedInstance.set(key, JSNumber.of(i));
            case Double d -> mountedInstance.set(key, JSNumber.of(d));
            case Boolean b -> mountedInstance.set(key, JSBoolean.of(b));
            case String s -> mountedInstance.set(key, JSString.of(s));
            case JSObject o -> mountedInstance.set(key, o);
            default -> throw new IllegalArgumentException("Unsupported type: " + value.getClass());
        }
    }

    @JS.Coerce
    @JS("this.component(name, definition)")
    public native void component(String name, JSObject definition);

    @JS.Coerce
    @JS("this.use(plugin)")
    public native void use(JSObject plugin);
}
