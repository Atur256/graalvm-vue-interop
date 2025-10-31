package io.github.atur256.vuewebimageinterop.api;

import io.github.atur256.webimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.*;


/**
 * Static interop access to the global Vue API via GraalVM WebImage.
 * <p>
 * Provides bindings for core Vue functions such as:
 * <ul>
 *   <li>{@code createApp}</li>
 *   <li>{@code ref} and {@code reactive}</li>
 *   <li>{@code computed}, {@code watch}, {@code watchEffect}</li>
 *   <li>Lifecycle hooks</li>
 *   <li>Virtual DOM rendering helpers</li>
 * </ul>
 * <p>
 * Used with {@link Component} and {@link VueApp}.
 *
 * @see Component
 * @see VueApp
 */
@JS.Import("Vue")
public class Vue extends JSObject {

    private Vue() {
    }

    /**
     * Creates a Vue application from a Java-defined component.
     *
     * @param component a {@link Component} instance
     * @return a {@link VueApp} representing the app
     */
    @JS.Coerce
    @JS("return Vue.createApp(component);")
    public static native VueApp createApp(Component component);

    /**
     * Creates a Vue application from a Java-defined component with optional configuration.
     *
     * @param component a {@link Component} instance
     * @param config    a {@link JSObject} containing app-level options (e.g., compilerOptions)
     * @return a {@link VueApp} representing the app
     */
    @JS.Coerce
    @JS("return Vue.createApp(component, config);")
    public static native VueApp createApp(Component component, JSObject config);

    /**
     * Creates a Vue application from a raw {@link JSObject}.
     *
     * @param component a {@link JSObject} defining a Vue component
     * @return a {@link VueApp} representing the app
     */
    @JS.Coerce
    @JS("return Vue.createApp(component);")
    public static native VueApp createApp(JSObject component);

    /**
     * Creates a raw Vue ref from a {@link JSValue}.
     *
     * @param value initial JS value
     * @return a reactive {@link JSObject} ref
     */
    @JS.Coerce
    @JS("return Vue.ref(value);")
    public static native JSObject rawRef(JSValue value);

    /**
     * Creates a typed {@link JSObject} from a Java value.
     *
     * @param initialValue a Java object or primitive
     * @return a {@link JSObject} wrapping the reactive value
     * @throws IllegalArgumentException if the type is unsupported
     */
    public static JSObject ref(Object initialValue) {
        return switch(initialValue) {
            case Integer i -> rawRef(JSNumber.of(i));
            case Double d -> rawRef(JSNumber.of(d));
            case Boolean b -> rawRef(JSBoolean.of(b));
            case String s -> rawRef(JSString.of(s));
            case JSValue j -> rawRef(j);
            default -> throw new IllegalArgumentException("Unsupported type: " + initialValue.getClass());
        };
    }

    /**
     * Wraps a {@link JSObject} in Vue's reactivity system.
     *
     * @param obj a JSObject
     * @return a reactive {@link JSObject}
     */
    @JS.Coerce
    @JS("return Vue.reactive(obj);")
    public static native JSObject reactive(JSObject obj);

    /**
     * Creates a computed property from a {@link JSFunction}.
     *
     * @param fn the function returning the computed value
     * @return a reactive {@link JSObject} representing the computed property
     */
    @JS.Coerce
    @JS("return Vue.computed(fn);")
    public static native JSObject computed(JSFunction fn);

    /**
     * Watches a reactive source and triggers a callback on change.
     *
     * @param source   reactive {@link JSObject} to watch
     * @param callback function to execute on change
     * @return a {@link JSFunction} representing the watcher
     */
    @JS.Coerce
    @JS("return Vue.watch(source, callback);")
    public static native JSFunction watch(JSObject source, JSFunction callback);

    /**
     * Runs a reactive effect that re-triggers on dependency change.
     *
     * @param callback function to execute on dependency change
     * @return a {@link JSFunction} representing the effect
     */
    @JS.Coerce
    @JS("return Vue.watchEffect(callback);")
    public static native JSFunction watchEffect(JSFunction callback);

    /**
     * Registers a callback to run when the component is mounted.
     *
     * @param callback a {@link JSFunction} to run after mounting
     */
    @JS.Coerce
    @JS("Vue.onMounted(callback);")
    public static native void onMounted(JSFunction callback);

    /**
     * Registers a callback to run when the component is unmounted.
     *
     * @param callback a {@link JSFunction} to run after unmounting
     */
    @JS.Coerce
    @JS("Vue.onUnmounted(callback);")
    public static native void onUnmounted(JSFunction callback);

    /**
     * Defines a component using Vue's defineComponent API.
     *
     * @param options a {@link JSObject} representing component options
     * @return a {@link JSObject} representing the defined component
     */
    @JS.Coerce
    @JS("return Vue.defineComponent(options);")
    public static native JSObject defineComponent(JSObject options);

    /**
     * Creates a virtual DOM node with tag only.
     *
     * @param tag the HTML tag name
     * @return a {@link JSObject} representing the VNode
     */
    @JS.Coerce
    @JS("return Vue.h(tag);")
    public static native JSObject h(String tag);

    /**
     * Creates a virtual DOM node with tag and props.
     *
     * @param tag   the HTML tag name
     * @param props a {@link JSObject} of attributes/props
     * @return a {@link JSObject} representing the VNode
     */
    @JS.Coerce
    @JS("return Vue.h(tag, props);")
    public static native JSObject h(String tag, JSObject props);

    /**
     * Creates a virtual DOM node with tag, props, and children.
     *
     * @param tag      the HTML tag name
     * @param props    a {@link JSObject} of attributes/props
     * @param children a {@link JSValue} or array of child nodes
     * @return a {@link JSObject} representing the VNode
     */
    @JS.Coerce
    @JS("return Vue.h(tag, props, children);")
    public static native JSObject h(String tag, JSObject props, JSValue children);

    /**
     * Defers execution until the next DOM update cycle.
     *
     * @param callback a {@link JSFunction} to run on the next tick
     */
    @JS.Coerce
    @JS("return Vue.nextTick(callback);")
    public static native void nextTick(JSFunction callback);
}
