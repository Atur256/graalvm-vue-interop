package io.github.atur256.vuewebimageinterop.api;

import org.graalvm.webimage.api.*;

import javax.annotation.Nonnull;


/**
 * Represents a Vue ref object created via {@link Vue#ref(Object)}.
 * <p>
 * Encapsulates a reactive value accessible via {@code .value}, supporting primitives and objects.
 * Used for managing single reactive values in Composition API style.
 * <p>
 * Often used alongside {@link VueReactive}, {@link Vue}, and {@link VueApp}.
 */
public record VueRef(JSObject jsRef) {

    /**
     * Creates an empty ref with an uninitialized value.
     */
    public VueRef() {
        this(JSObject.create());
    }

    /**
     * Constructs a VueRef from an existing JSObject.
     */
    public static VueRef of(JSObject jsRef) {
        return new VueRef(jsRef);
    }

    /**
     * Returns the underlying JSObject representing the ref.
     */
    public JSObject getRef() {
        return jsRef;
    }

    /**
     * Retrieves the current value of the ref, coerced to the given Java type.
     */
    public <T> T get(Class<T> cls) {
        return JSValue.checkedCoerce(jsRef.get("value"), cls);
    }

    /**
     * Sets the value of the ref to a new object or primitive.
     */
    public VueRef set(Object value) {
        switch(value) {
            case Integer i -> jsRef.set("value", JSNumber.of(i));
            case Double d -> jsRef.set("value", JSNumber.of(d));
            case Boolean b -> jsRef.set("value", JSBoolean.of(b));
            case String s -> jsRef.set("value", JSString.of(s));
            case JSObject o -> jsRef.set("value", o);
            case JSValue j -> jsRef.set("value", j);
            default -> throw new IllegalArgumentException("Unsupported type: " + value.getClass());
        }
        return this;
    }

    @Nonnull
    @Override
    public String toString() {
        return "VueRef{value=" + get(Object.class) + "}";
    }
}
