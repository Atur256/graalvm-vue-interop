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

import io.github.atur256.graalvmwebimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;


/**
 * SetupComponent demonstrates use of Vue 3's Composition API via {@code setup()} in GraalVM interop.
 * <p>
 * Demonstrates:
 * <ul>
 *   <li>Reactive state using {@code Vue.ref()}</li>
 *   <li>Method binding and event handling inside {@code setup()}</li>
 *   <li>Reactive side effects using {@code watch()} and {@code watchEffect()}</li>
 * </ul>
 */
public class SetupComponent extends Component {

    public SetupComponent() {
        // Component name used in Vue DevTools
        this.name = JSString.of("SetupComponent");

        // Vue template: displays and updates reactive count
        this.template = JSString.of("""
                    <div>
                        <p>Count: {{ count }}</p>
                        <button @click="increment">Increment</button>
                    </div>
                """);
    }

    /**
     * Composition API setup function.
     * <p>
     * Returns a {@link JSObject} containing reactive state and methods
     * that are exposed to the template and component context.
     */

    public JSObject setup() {
        return new Setup();
    }

    /**
     * Reactive bindings exposed by the {@code setup()} function.
     * <p>
     * Includes:
     * <ul>
     *   <li>{@code count} — a reactive number initialized to 0</li>
     *   <li>{@code increment()} — a method that increases {@code count.value} by 1</li>
     *   <li>{@code watchEffect} — a side effect that runs whenever any reactive dependency changes</li>
     *   <li>{@code watch} — a watcher that tracks changes to {@code count} specifically</li>
     * </ul>
     */
    public static class Setup extends JSObject {

        // Reactive state: count initialized to 0
        public JSObject count = Vue.ref(0);

        // Method to increment count
        public JSFunction increment = JSFunction.of(() ->
                count.set("value", count.get("value", Integer.class) + 1));

        // Reactive effect: runs whenever any reactive dependency used inside changes
        public JSObject watchEffect = Vue.watchEffect(JSFunction.of(() ->
                System.out.println("[WatchEffect] count changed to: " + count.get("value", Integer.class))));

        // Watcher: runs only when 'count' changes
        public JSObject watch = Vue.watch(count, JSFunction.of(() ->
                System.out.println("[watch] count changed to: " + count.get("value", Integer.class))));
    }
}
