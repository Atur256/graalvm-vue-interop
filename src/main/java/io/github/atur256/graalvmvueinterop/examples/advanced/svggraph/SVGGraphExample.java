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

package io.github.atur256.graalvmvueinterop.examples.advanced.svggraph;

import io.github.atur256.graalvmvueinterop.api.Component;
import io.github.atur256.graalvmvueinterop.api.Vue;


/**
 * Entry point for the SVG Graph example using Vue + GraalVM interop.
 * <p>
 * Demonstrates:
 * <ul>
 *   <li>SVG rendering via custom components</li>
 *   <li>Computed geometry for dynamic layout</li>
 *   <li>Component hierarchy with prop passing</li>
 * </ul>
 *
 * Mirrors the official Vue SVG Graph example:
 * <a href="https://vuejs.org/examples/#svg">vuejs.org/examples/#svg</a>
 */
public class SVGGraphExample {

    public static void main(String[] args) {

        // Instantiate the root Vue component
        Component component = new RootComponent();

        // Create and mount the Vue application
        Vue.createApp(component).mount();
    }
}
