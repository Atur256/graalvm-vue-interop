package io.github.atur256.vuewebimageinterop.oldClasses;

import io.github.atur256.vuewebimageinterop.api.Component;
import io.github.atur256.vuewebimageinterop.api.VueApp;
import io.github.atur256.webimageinterop.builtin.JSEval;
import org.graalvm.webimage.api.*;


@JS.Import("Vue")
public class Vue extends JSObject{

    private static VueApp mountedInstance;

    private Vue() {
    }

    @JS.Coerce
    @JS("return Vue.createApp(component);")
    public static native VueApp createApp(Component component);

    @JS.Coerce
    @JS("return Vue.createApp(component);")
    public static native VueApp createApp(JSObject component);

    // TODO: https://vuejs.org/api/application.html#createapp




    @JS.Coerce
    @JS("return app.mount('#app')")
    public static native VueApp mountApp(JSObject app);

    @JS.Coerce
    @JS(value = "return app.mount(selector)")
    public static native JSObject mountApp(JSObject app, String selector);

    public static void mountAndStore(JSObject app) {
        mountedInstance = mountApp(app);
    }

    public static JSObject getMountedInstance() {
        return mountedInstance;
    }

    public static Object getValue(String key) {
        return mountedInstance.get(key);
    }

    public static <R> R getValue(String key, Class<R> cls) {
        return JSValue.checkedCoerce(mountedInstance.get(key), cls);
    }

    public static void setValue(String key, JSObject value) {
        mountedInstance.set(key, value);
    }

    public static void setValue(String key, int value) {
        mountedInstance.set(key, JSNumber.of(value));
    }

    public static void setValue(String key, double value) {
        mountedInstance.set(key, JSNumber.of(value));
    }

    public static void setValue(String key, String value) {
        mountedInstance.set(key, JSString.of(value));
    }

    public static void setValue(String key, boolean value) {
        mountedInstance.set(key, JSBoolean.of(value));
    }

    public static JSValue getEventArg(String key) {
        JSObject vue = getMountedInstance();
        if(vue == null) return null;

        JSObject global = JSValue.checkedCoerce(JSEval.eval("window"), JSObject.class);
        JSObject lastArgs = JSValue.checkedCoerce(global.get("__lastVueArgs"), JSObject.class);
        if(lastArgs == null || lastArgs.get(key) == null) return null;

        return JSValue.checkedCoerce(lastArgs.get(key), JSValue.class);
    }

    @JS.Coerce
    @JS(value = "return this;")
    public static native JSObject getThis();
}
