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

package io.github.atur256.graalvmvueinterop.examples.advanced.setup;

import io.github.atur256.graalvmvueinterop.api.Component;
import io.github.atur256.graalvmvueinterop.api.Vue;
import io.github.atur256.graalvmvueinterop.api.VueApp;

/**
 * Entry point for the SetupComponent demo using Vue 3 Composition API.
 * <p>
 * Demonstrates:
 * <ul>
 *   <li>Reactive state with {@code Vue.ref()}</li>
 *   <li>Event-driven updates via {@code setup()}</li>
 * </ul>
 */
public class SetupExample {

    public static void main(String[] args) {
        // Instantiate the component
        Component component = new SetupComponent();

        // Create the Vue application
        VueApp app = Vue.createApp(component);

        // Mount the Vue application
        app.mount();
    }
}
