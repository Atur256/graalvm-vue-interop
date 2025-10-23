package io.github.atur256.vuewebimageinterop.api;

import org.graalvm.webimage.api.*;

import javax.annotation.Nonnull;


/**
 * Represents a reactive object created via {@link Vue#reactive(JSObject)}.
 * <p>
 * Enables access and mutation of reactive fields using typed Java interop.
 * Used for managing structured reactive state in Composition API style.
 * <p>
 * Often used alongside {@link VueRef}, {@link Vue}, and {@link VueApp}.
 */
public record VueReactive(JSObject jsReactive) {

    /**
     * Creates a reactive object from a plain JSObject.
     */
    public static VueReactive of(JSObject obj) {
        JSObject reactiveObj = Vue.reactive(obj);
        return new VueReactive(reactiveObj);
    }

    /**
     * Returns the underlying reactive JSObject.
     */
    public JSObject unwrap() {
        return jsReactive;
    }

    /**
     * Retrieves a reactive field and coerces it to a Java type.
     */
    public <T> T get(String key, Class<T> cls) {
        return JSValue.checkedCoerce(jsReactive.get(key), cls);
    }


    /**
     * Sets a reactive field to a new value.
     */
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

    @Nonnull
    @Override
    public String toString() {
        return "VueReactive{value=" + jsReactive.toString() + "}";
    }
}
