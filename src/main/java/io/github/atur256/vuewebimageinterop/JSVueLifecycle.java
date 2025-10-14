package io.github.atur256.vuewebimageinterop;

import io.github.atur256.webimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.*;

public class JSVueLifecycle {

    private final JSObject hooks = JSObject.create();

    public static JSVueLifecycle create() {
        return new JSVueLifecycle();
    }

    public JSVueLifecycle onMounted(JSFunction callback) {
        hooks.set("mounted", callback);
        return this;
    }

    public JSVueLifecycle onUpdated(JSFunction callback) {
        hooks.set("updated", callback);
        return this;
    }

    public JSVueLifecycle onUnmounted(JSFunction callback) {
        hooks.set("unmounted", callback);
        return this;
    }

    public JSObject getHooks() {
        return hooks;
    }
}
