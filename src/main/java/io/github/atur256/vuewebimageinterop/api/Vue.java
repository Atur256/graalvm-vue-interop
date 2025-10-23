package io.github.atur256.vuewebimageinterop.api;

import io.github.atur256.webimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.*;

/**
 * Static interop access to the global Vue API.
 * <p>
 * Provides bindings for core Vue functions such as {@code createApp}, {@code ref}, {@code reactive},
 * {@code computed}, lifecycle hooks, and virtual DOM rendering.
 * <p>
 * Used with {@link Component}, {@link VueApp}, {@link VueRef}, and {@link VueReactive}.
 */
@JS.Import("Vue")
public class Vue extends JSObject {

    private Vue() {
    }

    /**
     * Creates a Vue application from a Java-defined component.
     */
    @JS.Coerce
    @JS("return Vue.createApp(component);")
    public static native VueApp createApp(Component component);

    /**
     * Creates a Vue application from a raw JSObject.
     */
    @JS.Coerce
    @JS("return Vue.createApp(component);")
    public static native VueApp createApp(JSObject component);


    /**
     * Creates a raw Vue ref from a JSValue.
     */
    @JS.Coerce
    @JS("return Vue.ref(value);")
    public static native JSObject rawRef(JSValue value);

    /**
     * Creates a typed VueRef from a Java value.
     */
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

    /**
     * Wraps a JSObject in Vue's reactivity system.
     */
    @JS.Coerce
    @JS("return Vue.reactive(obj);")
    public static native JSObject reactive(JSObject obj);

    /**
     * Creates a computed property from a JSFunction.
     */
    @JS.Coerce
    @JS("return Vue.computed(fn);")
    public static native JSObject computed(JSFunction fn);

    /**
     * Creates a computed ref from a JSFunction.
     */
    public static VueRef computedRef(JSFunction fn) {
        return VueRef.of(computed(fn));
    }

    /**
     * Watches a reactive source and triggers a callback.
     */
    @JS.Coerce
    @JS("return Vue.watch(source, callback);")
    public static native JSFunction watch(JSObject source, JSFunction callback);

    /**
     * Runs a reactive effect that re-triggers on dependency change.
     */
    @JS.Coerce
    @JS("return Vue.watchEffect(callback);")
    public static native JSFunction watchEffect(JSFunction callback);

    /**
     * Registers a callback to run when the component is mounted.
     */
    @JS.Coerce
    @JS("Vue.onMounted(callback);")
    public static native void onMounted(JSFunction callback);

    /**
     * Registers a callback to run when the component is unmounted.
     */
    @JS.Coerce
    @JS("Vue.onUnmounted(callback);")
    public static native void onUnmounted(JSFunction callback);

    /**
     * Defines a component using Vue's defineComponent API.
     */
    @JS.Coerce
    @JS("return Vue.defineComponent(options);")
    public static native JSObject defineComponent(JSObject options);

    /**
     * Creates a virtual DOM node with tag only.
     */
    @JS.Coerce
    @JS("return Vue.h(tag);")
    public static native JSObject h(String tag);

    /**
     * Creates a virtual DOM node with tag and props.
     */
    @JS.Coerce
    @JS("return Vue.h(tag, props);")
    public static native JSObject h(String tag, JSObject props);

    /**
     * Creates a virtual DOM node with tag, props, and children.
     */
    @JS.Coerce
    @JS("return Vue.h(tag, props, children);")
    public static native JSObject h(String tag, JSObject props, JSValue children);

    /**
     * Defers execution until the next DOM update cycle.
     */
    @JS.Coerce
    @JS("return Vue.nextTick(callback);")
    public static native void nextTick(JSFunction callback);
}
