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

package io.github.atur256.graalvmvueinterop.examples.basics.helloworld;

import io.github.atur256.graalvmvueinterop.api.Component;
import io.github.atur256.graalvmvueinterop.api.Vue;



/**
 * Entry point for the Hello World example using Vue + GraalVM interop.
 * <p>
 * Demonstrates:
 * <ul>
 *   <li>Basic template binding with {@code {{ message }}}</li>
 *   <li>Reactive state via the {@code data()} method</li>
 *   <li>Minimal Vue component definition in Java</li>
 * </ul>
 *
 * Mirrors the official Vue Hello World example:
 * <a href="https://vuejs.org/examples/#hello-world">vuejs.org/examples/#hello-world</a>
 */
public class HelloWorldExample {

    public static void main(String[] args) {

        // Instantiate the root Vue component
        Component component = new RootComponent();

        // Create and mount the Vue application
        Vue.createApp(component).mount();
    }
}
