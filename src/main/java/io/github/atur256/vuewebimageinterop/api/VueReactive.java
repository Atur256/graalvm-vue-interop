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
     * Wraps a plain JSObject into a reactive Vue object.
     *
     * @param obj the JSObject to make reactive
     * @return a new {@link VueReactive} wrapping the reactive object
     */
    public static VueReactive of(JSObject obj) {
        JSObject reactiveObj = Vue.reactive(obj);
        return new VueReactive(reactiveObj);
    }

    /**
     * Returns the underlying reactive JSObject.
     *
     * @return the wrapped {@link JSObject}
     */
    public JSObject getReactive() {
        return jsReactive;
    }

    /**
     * Retrieves a reactive field by key and coerces it to a Java type.
     *
     * @param key the field name
     * @param cls the target Java type
     * @param <T> the type parameter
     * @return the field value coerced to {@code cls}
     */
    public <T> T get(String key, Class<T> cls) {
        return JSValue.checkedCoerce(jsReactive.get(key), cls);
    }

    /**
     * Sets a reactive field to a new value.
     * <p>
     * Supported value types: {@link Integer}, {@link Double}, {@link Boolean}, {@link String},
     * {@link JSObject}, {@link JSValue}.
     *
     * @param key   the field name
     * @param value the new value
     * @return this {@link VueReactive} instance for chaining
     * @throws IllegalArgumentException if the value type is unsupported
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
