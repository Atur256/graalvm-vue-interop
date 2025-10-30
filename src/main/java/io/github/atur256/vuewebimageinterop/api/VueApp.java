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
     * Mounts the Vue application to the DOM element with id "#app".
     *
     * @return the root component instance as {@link JSObject}
     */
    @JS.Coerce
    @JS("return this.mount('#app')")
    public native JSObject mount();

    /**
     * Unmounts the Vue application.
     */
    @JS.Coerce
    @JS("this.unmount('#app')")
    public native void unmount();

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
