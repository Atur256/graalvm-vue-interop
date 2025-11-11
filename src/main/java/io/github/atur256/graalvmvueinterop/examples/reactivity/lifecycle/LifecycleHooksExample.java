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

package io.github.atur256.graalvmvueinterop.examples.reactivity.lifecycle;

import io.github.atur256.graalvmvueinterop.api.Vue;
import io.github.atur256.graalvmvueinterop.api.VueApp;
import io.github.atur256.graalvmwebimageinterop.builtin.JSFunction;


/**
 * LifecycleHooksExample is the entry point for launching the Vue app.
 * <p>
 * Demonstrates:
 * <ul>
 *   <li>App creation via {@code Vue.createApp}</li>
 *   <li>Passing the {@code VueApp} instance to the root component</li>
 *   <li>Manual app unmounting triggered from within the component</li>
 *   <li>JS-side unmount hook registration via {@code onUnmountJS}</li>
 * </ul>
 */
public class LifecycleHooksExample {

    public static void main(String[] args) {

        // Instantiate the root Vue component
        LifecycleParentComponent component = new LifecycleParentComponent();

        // Create the Vue application
        VueApp app = Vue.createApp(component);

        // Pass the app instance into the component for self-unmounting
        component.setApp(app);

        // Mount the Vue application
        app.mount();

        // Register a JS-side unmount hook
        app.onUnmount(JSFunction.fromRun(() -> System.out.println("Vue Application unmounted!")));
    }
}
