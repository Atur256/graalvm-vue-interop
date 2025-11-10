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

package io.github.atur256.vuewebimageinterop.examples.reactivity.rendernexttick;

import io.github.atur256.vuewebimageinterop.api.Component;
import io.github.atur256.vuewebimageinterop.api.Vue;


/**
 * Entry point for the Render + nextTick example using Vue + GraalVM interop.
 * <p>
 * Demonstrates:
 * <ul>
 *   <li>Manual virtual DOM rendering via {@code Vue.h}</li>
 *   <li>Reactive state via {@code Vue.reactive}</li>
 *   <li>Deferred DOM updates via {@code Vue.nextTick}</li>
 * </ul>
 */
public class RenderNextTickExample {

    public static void main(String[] args) {

        // Instantiate the root Vue component
        Component dynamicComponent = new RenderTickComponent();

        // Create and mount the Vue application
        Vue.createApp(dynamicComponent).mount();
    }
}
