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
public class Vue {

    private Vue() {
    }

    /**
     * Creates a Vue application from a Java-defined component using raw JS interop.
     *
     * @param component The component to use for the application.
     * @return A JSObject representing the Vue application instance.
     */
    @JS.Coerce
    @JS("return Vue.createApp(component);")
    private static native JSObject createAppRaw(Component component);

    /**
     * Creates a Vue application from a Java-defined component with optional configuration using raw JS interop.
     *
     * @param component The component to use for the application.
     * @param config Optional configuration object for the app.
     * @return A JSObject representing the Vue application instance.
     */
    @JS.Coerce
    @JS("return Vue.createApp(component, config);")
    private static native JSObject createAppRaw(Component component, JSObject config);

    /**
     * Creates a Vue application from a raw JSObject component using raw JS interop.
     *
     * @param component The JSObject representing the Vue component.
     * @return A JSObject representing the Vue application instance.
     */
    @JS.Coerce
    @JS("return Vue.createApp(component);")
    private static native JSObject createAppRaw(JSObject component);

    /**
     * Creates a Vue application from a Java-defined component.
     *
     * @param component The component to use for the application.
     * @return A {@link VueApp} wrapping the created Vue application instance.
     */
    public static VueApp createApp(Component component) {
        return new VueApp(createAppRaw(component));
    }

    /**
     * Creates a Vue application from a Java-defined component with optional configuration.
     *
     * @param component The component to use for the application.
     * @param config Optional configuration object for the app.
     * @return A {@link VueApp} wrapping the created Vue application instance.
     */
    public static VueApp createApp(Component component, JSObject config) {
        return new VueApp(createAppRaw(component, config));
    }

    /**
     * Creates a Vue application from a raw JSObject component.
     *
     * @param component The JSObject representing the Vue component.
     * @return A {@link VueApp} wrapping the created Vue application instance.
     */
    public static VueApp createApp(JSObject component) {
        return new VueApp(createAppRaw(component));
    }

    /**
     * Creates a raw reactive reference from a {@link JSValue}.
     *
     * @param value The initial value for the reference.
     * @return A {@link JSObject} representing the reactive reference.
     */
    @JS.Coerce
    @JS("return Vue.ref(value);")
    public static native JSObject rawRef(JSValue value);

    /**
     * Creates a reactive reference from a Java object.
     * Supports primitive types, Strings, and JSValues.
     *
     * @param initialValue The initial value for the reference.
     * @return A {@link JSObject} representing the reactive reference.
     * @throws IllegalArgumentException If the type of initialValue is unsupported.
     */
    public static JSObject ref(Object initialValue) {
        return switch (initialValue) {
            case Integer i -> rawRef(JSNumber.of(i));
            case Double d -> rawRef(JSNumber.of(d));
            case Boolean b -> rawRef(JSBoolean.of(b));
            case String s -> rawRef(JSString.of(s));
            case JSValue j -> rawRef(j);
            default -> throw new IllegalArgumentException("Unsupported type: " + initialValue.getClass());
        };
    }

    /**
     * Makes a {@link JSObject} reactive.
     *
     * @param obj The JSObject to make reactive.
     * @return A reactive {@link JSObject}.
     */
    @JS.Coerce
    @JS("return Vue.reactive(obj);")
    public static native JSObject reactive(JSObject obj);

    /**
     * Creates a computed reactive value.
     *
     * @param fn The function that computes the value.
     * @return A {@link JSObject} representing the computed value.
     */
    @JS.Coerce
    @JS("return Vue.computed(fn);")
    public static native JSObject computed(JSFunction fn);

    /**
     * Watches a reactive source for changes and executes a callback when it changes.
     *
     * @param source The reactive source to watch.
     * @param callback The callback function invoked on changes.
     * @return A {@link JSFunction} that can be called to stop watching.
     */
    @JS.Coerce
    @JS("return Vue.watch(source, callback);")
    public static native JSFunction watch(JSObject source, JSFunction callback);

    /**
     * Creates a reactive effect that automatically tracks reactive dependencies.
     *
     * @param callback The function to execute reactively.
     * @return A {@link JSFunction} that can be called to stop the effect.
     */
    @JS.Coerce
    @JS("return Vue.watchEffect(callback);")
    public static native JSFunction watchEffect(JSFunction callback);

    /**
     * Registers a callback to run when the component is mounted.
     *
     * @param callback The function to execute on mounted.
     */
    @JS.Coerce
    @JS("Vue.onMounted(callback);")
    public static native void onMounted(JSFunction callback);

    /**
     * Registers a callback to run when the component is unmounted.
     *
     * @param callback The function to execute on unmounted.
     */
    @JS.Coerce
    @JS("Vue.onUnmounted(callback);")
    public static native void onUnmounted(JSFunction callback);

    /**
     * Defines a Vue component from a JSObject of options.
     *
     * @param options The options object defining the component.
     * @return A {@link JSObject} representing the Vue component.
     */
    @JS.Coerce
    @JS("return Vue.defineComponent(options);")
    public static native JSObject defineComponent(JSObject options);

    /**
     * Creates a virtual DOM node with a given tag.
     *
     * @param tag The HTML or component tag.
     * @return A {@link JSObject} representing the virtual DOM node.
     */
    @JS.Coerce
    @JS("return Vue.h(tag);")
    public static native JSObject h(String tag);

    /**
     * Creates a virtual DOM node with a given tag and properties.
     *
     * @param tag The HTML or component tag.
     * @param props The properties object.
     * @return A {@link JSObject} representing the virtual DOM node.
     */
    @JS.Coerce
    @JS("return Vue.h(tag, props);")
    public static native JSObject h(String tag, JSObject props);

    /**
     * Creates a virtual DOM node with a given tag, properties, and children.
     *
     * @param tag The HTML or component tag.
     * @param props The properties object.
     * @param children The children of the node (string, JSObject, or array).
     * @return A {@link JSObject} representing the virtual DOM node.
     */
    @JS.Coerce
    @JS("return Vue.h(tag, props, children);")
    public static native JSObject h(String tag, JSObject props, JSValue children);

    /**
     * Schedules a function to run on the next DOM update cycle.
     *
     * @param callback The function to execute after the next tick.
     */
    @JS.Coerce
    @JS("return Vue.nextTick(callback);")
    public static native void nextTick(JSFunction callback);
}
