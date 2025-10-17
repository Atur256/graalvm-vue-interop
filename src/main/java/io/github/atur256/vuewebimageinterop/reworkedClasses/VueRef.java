package io.github.atur256.vuewebimageinterop.reworkedClasses;

import org.graalvm.webimage.api.*;

import javax.annotation.Nonnull;


public record VueRef(JSObject jsRef) {

    public VueRef() {
        this(JSObject.create());
    }

    public static VueRef of(JSObject jsRef) {
        return new VueRef(jsRef);
    }

    public JSObject unwrap() {
        return jsRef;
    }

    public <T> T get(Class<T> cls) {
        return JSValue.checkedCoerce(jsRef.get("value"), cls);
    }

    public VueRef set(Object value) {
        switch(value) {
            case Integer i -> jsRef.set("value", JSNumber.of(i));
            case Double d -> jsRef.set("value", JSNumber.of(d));
            case Boolean b -> jsRef.set("value", JSBoolean.of(b));
            case String s -> jsRef.set("value", JSString.of(s));
            case JSObject o -> jsRef.set("value", o);
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
