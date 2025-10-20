package io.github.atur256.vuewebimageinterop.reworkedClasses;

import io.github.atur256.webimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.*;


@JS.Import("Vue")
public class Vue extends JSObject {

    private Vue() {
    }

    @JS.Coerce
    @JS("return Vue.createApp(component);")
    public static native VueApp createApp(Component component);

    @JS.Coerce
    @JS("return Vue.createApp(component);")
    public static native VueApp createApp(JSObject component);

    @JS.Coerce
    @JS("return Vue.ref(value);")
    public static native JSObject rawRef(JSValue value);

    public static VueRef ref(Object initialValue) {
        return switch(initialValue) {
            case Integer i -> VueRef.of(rawRef(JSNumber.of(i)));
            case Double d -> VueRef.of(rawRef(JSNumber.of(d)));
            case Boolean b -> VueRef.of(rawRef(JSBoolean.of(b)));
            case String s -> VueRef.of(rawRef(JSString.of(s)));
            case JSValue j -> VueRef.of(rawRef(j));
            default -> throw new IllegalArgumentException("Unsupported type: " + initialValue.getClass());

        };
    }

    @JS.Coerce
    @JS("return Vue.reactive(obj);")
    public static native JSObject reactive(JSObject obj);

    @JS.Coerce
    @JS("return Vue.computed(fn);")
    public static native JSObject computed(JSFunction fn);

    public static VueRef computedRef(JSFunction fn) {
        return VueRef.of(computed(fn));
    }

    @JS.Coerce
    @JS("return Vue.watch(source, callback);")
    public static native JSFunction watch(JSObject source, JSFunction callback);

    @JS.Coerce
    @JS("return Vue.watchEffect(callback);")
    public static native JSFunction watchEffect(JSFunction callback);

    @JS.Coerce
    @JS("Vue.onMounted(callback);")
    public static native void onMounted(JSFunction callback);

    @JS.Coerce
    @JS("Vue.onUnmounted(callback);")
    public static native void onUnmounted(JSFunction callback);

    @JS.Coerce
    @JS("return Vue.defineComponent(options);")
    public static native JSObject defineComponent(JSObject options);

    @JS.Coerce
    @JS("return Vue.h(tag);")
    public static native JSObject h(String tag);

    @JS.Coerce
    @JS("return Vue.h(tag, props);")
    public static native JSObject h(String tag, JSObject props);

    @JS.Coerce
    @JS("return Vue.h(tag, props, children);")
    public static native JSObject h(String tag, JSObject props, JSValue children);

    @JS.Coerce
    @JS("return Vue.nextTick(callback);")
    public static native void nextTick(JSFunction callback);
}
