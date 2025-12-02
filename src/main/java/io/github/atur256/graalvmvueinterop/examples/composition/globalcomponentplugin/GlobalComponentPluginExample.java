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

package io.github.atur256.graalvmvueinterop.examples.composition.globalcomponentplugin;

import io.github.atur256.graalvmvueinterop.api.Component;
import io.github.atur256.graalvmvueinterop.api.Vue;
import io.github.atur256.graalvmvueinterop.api.VueApp;
import io.github.atur256.graalvmwebimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSValue;


/**
 * Entry point for the Global Component + Plugin example using Vue + GraalVM interop.
 * <p>
 * Demonstrates:
 * <ul>
 *   <li>Global component registration via {@code app.component}</li>
 *   <li>Plugin installation via {@code app.use}</li>
 *   <li>Global property injection via {@code app.config.globalProperties}</li>
 * </ul>
 */
public class GlobalComponentPluginExample {

    public static void main(String[] args) {

        // Instantiate the root Vue component
        Component component = new AppRootComponent();

        // Create the Vue application
        VueApp app = Vue.createApp(component);

        // Register a global child component <child-component>
        app.component("child-component", new ChildComponent());

        // Install a plugin that adds a global property
        app.use(new Plugin());

        // Mount the Vue application
        app.mount();
    }

    /**
     * Plugin that injects a global property into the Vue app.
     * <p>
     * Sets {@code app.config.globalProperties.globalMessage}, accessible via {@code $root.globalMessage}.
     */
    private static class Plugin extends JSObject {

        public JSFunction install = JSFunction.of((JSObject app) -> {
            JSObject config = JSValue.checkedCoerce(app.get("config"), JSObject.class);
            JSObject globalProperties = JSValue.checkedCoerce(config.get("globalProperties"), JSObject.class);
            globalProperties.set("globalMessage", "Hello from plugin!");
        });
    }
}
