package io.github.atur256.vuewebimageinterop.oldClasses;

import io.github.atur256.webimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.*;


public final class JSVueRef<T> {

    private final JSObject ref;

    private JSVueRef(JSObject ref) {
        this.ref = ref;
    }

    @JS.Coerce
    @JS("return Vue.ref(value);")
    private static native JSObject createRef(JSValue value);

    public static <T> JSVueRef<T> of(JSValue value) {
        return new JSVueRef<>(createRef(value));
    }

    public static <T> JSVueRef<T> of(int value) {
        return new JSVueRef<>(createRef(JSNumber.of(value)));
    }

    public static <T> JSVueRef<T> of(double value) {
        return new JSVueRef<>(createRef(JSNumber.of(value)));
    }

    public static <T> JSVueRef<T> of(boolean value) {
        return new JSVueRef<>(createRef(JSBoolean.of(value)));
    }

    public static <T> JSVueRef<T> of(String value) {
        return new JSVueRef<>(createRef(JSString.of(value)));
    }

    public static <T> JSVueRef<T> of() {
        throw new IllegalArgumentException("Object data type is currently not supported!!");
    }

    @SuppressWarnings("unchecked")
    public T get() {
        Object value = ref.get("value");
        return switch(value) {
            case JSString jsString -> (T) JSValue.checkedCoerce(jsString, String.class);
            case JSNumber jsNumber -> {
                double num = jsNumber.asDouble();
                if(num == Math.floor(num) && !Double.isInfinite(num)) {
                    yield (T) Integer.valueOf((int) num);
                }
                else {
                    yield (T) Double.valueOf(num);
                }
            }
            case JSBoolean jsBoolean -> (T) JSValue.checkedCoerce(jsBoolean, Boolean.class);
            case null, default -> (T) value;
        };
    }

    public void set(JSValue value) {
        ref.set("value", value);
    }

    public void set(int value) {
        set(JSNumber.of(value));
    }

    public void set(double value) {
        set(JSNumber.of(value));
    }

    public void set(boolean value) {
        set(JSBoolean.of(value));
    }

    public void set(String value) {
        set(JSString.of(value));
    }

    public JSObject raw() {
        return ref;
    }

    @JS.Coerce
    @JS("return Vue.reactive(obj);")
    public static native JSObject reactive(JSObject obj);

    @JS.Coerce
    @JS("return Vue.isRef(obj);")
    public static native boolean isRef(JSObject obj);

    @JS.Coerce
    @JS("return Vue.unref(ref);")
    public static native JSValue unref(JSValue ref);

    @JS.Coerce
    @JS("return Vue.toRef(obj, key);")
    public static native JSObject toRef(JSObject obj, String key);

    @JS.Coerce
    @JS("return Vue.toRefs(obj);")
    public static native JSObject toRefs(JSObject obj);

    public void watch(JSFunction callback) {
        JSVueWatch.watch(this.ref, callback);
    }
}
