package io.github.atur256.vuewebimageinterop.reworkedClasses;

import org.graalvm.webimage.api.*;


public record VueReactive(JSObject jsReactive) {

    public static VueReactive of(JSObject obj) {
        JSObject reactiveObj = Vue.reactive(obj);
        return new VueReactive(reactiveObj);
    }

    public JSObject unwrap() {
        return jsReactive;
    }

    public <T> T get(String key, Class<T> cls) {
        return JSValue.checkedCoerce(jsReactive.get(key), cls);
    }

    public VueReactive set(String key, Object value) {
        switch(value) {
            case Integer i -> jsReactive.set(key, JSNumber.of(i));
            case Double d -> jsReactive.set(key, JSNumber.of(d));
            case Boolean b -> jsReactive.set(key, JSBoolean.of(b));
            case String s -> jsReactive.set(key, JSString.of(s));
            case JSObject o -> jsReactive.set(key, o);
            case JSValue j -> jsReactive.set(key, j);
            default -> throw new IllegalArgumentException("Unsupported type: " + value.getClass());
        }
        return this;
    }

    @Override
    public String toString() {
        return "VueReactive" + jsReactive.toString();
    }
}
