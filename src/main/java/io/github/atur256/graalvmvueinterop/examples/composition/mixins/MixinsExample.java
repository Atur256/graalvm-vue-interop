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

package io.github.atur256.graalvmvueinterop.examples.composition.mixins;

import io.github.atur256.graalvmvueinterop.api.Component;
import io.github.atur256.graalvmvueinterop.api.Vue;
import io.github.atur256.graalvmvueinterop.api.VueApp;


/**
 * Entry point for the mixins example using Vue + GraalVM interop.
 * <p>
 * Demonstrates:
 * <ul>
 *   <li>Combining multiple mixins into a single component</li>
 *   <li>Reactive state and methods injected via mixins</li>
 * </ul>
 */
public class MixinsExample {

    public static void main(String[] args) {

        // Instantiate the root component
        Component component = new MixinComponent();

        // Create the Vue application
        VueApp app = Vue.createApp(component);

        // Mount the Vue application
        app.mount();
    }
}
