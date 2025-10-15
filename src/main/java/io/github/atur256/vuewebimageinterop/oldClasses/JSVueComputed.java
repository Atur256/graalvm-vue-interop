package io.github.atur256.vuewebimageinterop.oldClasses;

import io.github.atur256.webimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.*;

public final class JSVueComputed<T> {

    private final String name;
    private final JSObject computed;
    private final JSFunction getterFn;

    private JSVueComputed(String name, JSObject computed, JSFunction getterFn) {
        this.name = name;
        this.computed = computed;
        this.getterFn = getterFn;
    }

    @JS.Coerce
    @JS("return Vue.computed(fn);")
    private static native JSObject create(JSObject fn);

    public static <T> JSVueComputed<T> of(String name, JSFunction getterFn) {
        return new JSVueComputed<>(name, create(getterFn), getterFn);
    }

    public T get(Class<T> type) {
        return JSValue.checkedCoerce(computed.get("value"), type);
    }

    public JSObject raw() {
        return computed;
    }

    public JSFunction getter() {
        return getterFn;
    }

    public String name() {
        return name;
    }

    public JSObject toMap() {
        JSObject map = JSObject.create();
        map.set(name, getterFn);
        return map;
    }
}
