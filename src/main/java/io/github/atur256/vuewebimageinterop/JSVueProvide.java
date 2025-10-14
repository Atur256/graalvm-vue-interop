package io.github.atur256.vuewebimageinterop;

import org.graalvm.webimage.api.*;


public class JSVueProvide {

    private final JSObject provide = JSObject.create();

    public static JSVueProvide create() {
        return new JSVueProvide();
    }

    public JSVueProvide set(String key, JSValue value) {
        provide.set(key, value);
        return this;
    }

    public JSVueProvide set(String key, int value) {
        provide.set(key, JSNumber.of(value));
        return this;
    }

    public JSVueProvide set(String key, double value) {
        provide.set(key, JSNumber.of(value));
        return this;
    }

    public JSVueProvide set(String key, boolean value) {
        provide.set(key, JSBoolean.of(value));
        return this;
    }

    public JSVueProvide set(String key, String value) {
        provide.set(key, JSString.of(value));
        return this;
    }

    public JSObject getProvide() {
        return provide;
    }
}
