/*
 * Copyright (c) 2025 Arthur Schwaiger
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.atur256.graalvmvueinterop.api;

import io.github.atur256.graalvmwebimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.*;


/**
 * Represents a Vue application instance created via {@link Vue#createApp(Component)}.
 * <p>
 * Provides methods to mount and unmount the app, register global components,
 * install plugins, and access reactive state from Java using GraalVM WebImage interop.
 * <p>
 * Used with {@link Component} and {@link Vue}.
 */
public class VueApp {

    private final JSObject app;

    /**
     * Constructs a VueApp wrapper around a JSObject.
     *
     * @param app the underlying JSObject representing the Vue app
     */
    public VueApp(JSObject app) {
        this.app = app;
    }

    @JS.Coerce
    @JS("return self.mount('#app')")
    private native JSObject mountInternal(JSObject self);

    /**
     * Mounts the Vue application to the DOM element with id "#app".
     *
     * @return the root component instance as {@link JSObject}
     */
    public JSObject mount() {
        return mountInternal(app);
    }

    @JS.Coerce
    @JS("self.unmount('#app')")
    private native void unmountInternal(JSObject self);

    /**
     * Unmounts the Vue application.
     */
    public void unmount() {
        unmountInternal(app);
    }

    @JS.Coerce
    @JS("self.onUnmount(callback)")
    private native void onUnmountInternal(JSObject self, JSFunction callback);

    /**
     * Registers a callback to run when the app is unmounted.
     *
     * @param callback a {@link JSFunction} to run on unmount
     */
    public void onUnmount(JSFunction callback) {
        onUnmountInternal(app, callback);
    }

    @JS.Coerce
    @JS("self.component(name, definition)")
    private native void componentInternal(JSObject self, String name, JSObject definition);

    /**
     * Registers a global component with the Vue application.
     *
     * @param name       the component name
     * @param definition a {@link JSObject} representing the component
     * @return this {@link VueApp} instance for chaining
     */
    public VueApp component(String name, JSObject definition) {
        componentInternal(app, name, definition);
        return this;
    }

    @JS.Coerce
    @JS("self.use(plugin)")
    private native void useInternal(JSObject self, JSObject plugin);

    /**
     * Installs a plugin into the Vue application.
     *
     * @param plugin a {@link JSObject} representing the plugin
     * @return this {@link VueApp} instance for chaining
     */
    public VueApp use(JSObject plugin) {
        useInternal(app, plugin);
        return this;
    }

    /**
     * Returns the underlying JSObject representing the Vue app.
     *
     * @return the JSObject instance backing this Vue app
     */
    public JSObject getJSObject() {
        return app;
    }
}
