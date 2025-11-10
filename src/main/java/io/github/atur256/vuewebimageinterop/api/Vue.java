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
public class Vue {

    private Vue() {
    }

    @JS.Coerce
    @JS("return Vue.createApp(component);")
    private static native JSObject createAppRaw(Component component);

    @JS.Coerce
    @JS("return Vue.createApp(component, config);")
    private static native JSObject createAppRaw(Component component, JSObject config);

    @JS.Coerce
    @JS("return Vue.createApp(component);")
    private static native JSObject createAppRaw(JSObject component);

    /**
     * Creates a Vue application from a Java-defined component.
     */
    public static VueApp createApp(Component component) {
        return new VueApp(createAppRaw(component));
    }

    /**
     * Creates a Vue application from a Java-defined component with optional configuration.
     */
    public static VueApp createApp(Component component, JSObject config) {
        return new VueApp(createAppRaw(component, config));
    }

    /**
     * Creates a Vue application from a raw JSObject component.
     */
    public static VueApp createApp(JSObject component) {
        return new VueApp(createAppRaw(component));
    }

    @JS.Coerce
    @JS("return Vue.ref(value);")
    public static native JSObject rawRef(JSValue value);

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

    @JS.Coerce
    @JS("return Vue.reactive(obj);")
    public static native JSObject reactive(JSObject obj);

    @JS.Coerce
    @JS("return Vue.computed(fn);")
    public static native JSObject computed(JSFunction fn);

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
