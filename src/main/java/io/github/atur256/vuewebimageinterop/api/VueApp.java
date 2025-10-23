package io.github.atur256.vuewebimageinterop.api;

import io.github.atur256.webimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.*;

/**
 * Represents a Vue application instance created via {@link Vue#createApp(Component)}.
 * <p>
 * Provides methods to mount and unmount the app, register components,
 * and access reactive state from Java using GraalVM interop.
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
     * Mounts the Vue app to #app and stores the instance.
     */
    public JSObject mount() {
        mountedInstance = this.mountJS();
        return mountedInstance;
    }

    /**
     * Unmounts the Vue app and clears the instance reference.
     */
    @JS.Coerce
    @JS("this.unmount('#app')")
    private native void unmountJS();

    public void unmount() {
        mountedInstance = null;
        unmountJS();
    }

    /**
     * Provides values to descendant components via injection.
     */
    @JS.Coerce
    @JS("this.onUnmount(callback)")
    public native void onUnmountJS(JSFunction callback);

    /**
     * Provides values to descendant components via injection.
     */
    @JS.Coerce
    @JS("this.provide(...keys)")
    public native void provide(String... keys);

    /**
     * Retrieves a reactive value from the mounted instance.
     */
    public static Object getValue(String key) {
        return mountedInstance.get(key);
    }

    /**
     * Retrieves and coerces a reactive value to a specific Java type.
     */
    public static <R> R getValue(String key, Class<R> cls) {
        return JSValue.checkedCoerce(mountedInstance.get(key), cls);
    }

    /**
     * Sets a reactive value on the mounted instance.
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

    /**
     * Registers a global component with the app.
     */
    @JS.Coerce
    @JS("this.component(name, definition)")
    public native void component(String name, JSObject definition);


    /**
     * Installs a plugin into the app.
     */
    @JS.Coerce
    @JS("this.use(plugin)")
    public native void use(JSObject plugin);
}
