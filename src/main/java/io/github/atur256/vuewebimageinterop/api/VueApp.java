package io.github.atur256.vuewebimageinterop.api;

import io.github.atur256.webimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.*;


/**
 * Represents a Vue application instance created via {@link Vue#createApp(Component)}.
 * <p>
 * Provides methods to mount and unmount the app, register global components,
 * install plugins, and access reactive state from Java using GraalVM WebImage interop.
 * <p>
 * Used with {@link Component}, {@link Vue}, {@link VueRef}, and {@link VueReactive}.
 */
public class VueApp extends JSObject {

    /**
     * Holds the mounted root component instance.
     */
    private static JSObject mountedInstance;

    @JS.Coerce
    @JS("return this.mount('#app')")
    private native JSObject mountJS();

    /**
     * Mounts the Vue application to the DOM element with id "#app" and stores the instance.
     *
     * @return the root component instance as {@link JSObject}
     */
    public JSObject mount() {
        mountedInstance = this.mountJS();
        return mountedInstance;
    }

    @JS.Coerce
    @JS("this.unmount('#app')")
    private native void unmountJS();

    /**
     * Unmounts the Vue application and clears the stored instance reference.
     */
    public void unmount() {
        mountedInstance = null;
        unmountJS();
    }

    /**
     * Registers a callback to run when the app is unmounted.
     *
     * @param callback a {@link JSFunction} to run on unmount
     */
    @JS.Coerce
    @JS("this.onUnmount(callback)")
    public native void onUnmountJS(JSFunction callback);

    /**
     * Provides values to descendant components via Vue's provide/inject mechanism.
     *
     * @param keys one or more keys to provide
     */
    @JS.Coerce
    @JS("this.provide(...keys)")
    public native void provide(String... keys);

    /**
     * Retrieves a reactive value from the mounted instance.
     *
     * @param key the reactive property name
     * @return the reactive value as {@link Object}
     */
    public static Object getValue(String key) {
        return mountedInstance.get(key);
    }

    /**
     * Retrieves and coerces a reactive value to a specific Java type.
     *
     * @param key the reactive property name
     * @param cls the target Java type
     * @param <R> the type parameter
     * @return the value coerced to {@code cls}
     */
    public static <R> R getValue(String key, Class<R> cls) {
        return JSValue.checkedCoerce(mountedInstance.get(key), cls);
    }

    /**
     * Sets a reactive value on the mounted instance.
     *
     * @param key   the reactive property name
     * @param value the new value (supports primitive wrappers, String, or JSObject)
     * @throws IllegalArgumentException if the value type is unsupported
     */
    public static void setValue(String key, Object value) {
        switch(value) {
            case Integer i -> mountedInstance.set(key, JSNumber.of(i));
            case Double d -> mountedInstance.set(key, JSNumber.of(d));
            case Boolean b -> mountedInstance.set(key, JSBoolean.of(b));
            case String s -> mountedInstance.set(key, JSString.of(s));
            case JSObject o -> mountedInstance.set(key, o);
            default -> throw new IllegalArgumentException("Unsupported type: " + value.getClass());
        }
    }

    @JS.Coerce
    @JS("this.component(name, definition)")
    private native void componentJS(String name, JSObject definition);

    /**
     * Registers a global component with the Vue application.
     *
     * @param name       the component name
     * @param definition a {@link JSObject} representing the component
     * @return this {@link VueApp} instance for chaining
     */
    public VueApp component(String name, JSObject definition) {
        this.componentJS(name, definition);
        return this;
    }

    @JS.Coerce
    @JS("this.use(plugin)")
    private native void useJS(JSObject plugin);

    /**
     * Installs a plugin into the Vue application.
     *
     * @param plugin a {@link JSObject} representing the plugin
     * @return this {@link VueApp} instance for chaining
     */
    public VueApp use(JSObject plugin) {
        this.useJS(plugin);
        return this;
    }
}
