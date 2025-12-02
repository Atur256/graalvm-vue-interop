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
import io.github.atur256.graalvmwebimageinterop.builtin.JSArray;
import org.graalvm.webimage.api.*;
import io.github.atur256.graalvmwebimageinterop.builtin.JSFunction;


/**
 * MixinComponent demonstrates use of multiple mixins in a Vue component using GraalVM interop.
 * <p>
 * Demonstrates:
 * <ul>
 *   <li>Reactive state and methods injected from multiple mixins</li>
 *   <li>Lifecycle hook merging from mixins and component</li>
 *   <li>Shared and local state management</li>
 * </ul>
 */
public class MixinComponent extends Component {

    public MixinComponent() {
        // Component name used in Vue DevTools
        this.name = JSString.of("MixinDemoComponent");

        // Vue template: displays and updates shared, extra, and local counts
        this.template = JSString.of("""
                    <div>
                        <p>Shared Count: {{ sharedCount }}</p>
                        <p>Extra Count: {{ extraCount }}</p>
                        <p>Local Count: {{ localCount }}</p>
                        <button @click="increment">Increment Shared</button>
                        <button @click="boost">Boost Extra</button>
                    </div>
                """);

        // Register two mixins that inject data, methods, and lifecycle hooks
        this.mixins = JSArray.of(new SharedCountMixin(), new ExtraCountMixin());

        // Component-level lifecycle hook
        this.created = JSFunction.of(() ->
                System.out.println("[Component] Component created!")
        );
    }

    /**
     * Reactive data model for the component.
     */
    @Override
    public JSObject data() {
        return new Data();
    }

    public static class Data extends JSObject {

        // Local state defined in the component
        public JSNumber localCount = JSNumber.of(100);
    }

    /**
     * First mixin: provides sharedCount and increment method.
     */
    public static class SharedCountMixin extends JSObject {

        // Reactive state injected by the mixin
        public JSFunction data = JSFunction.of(() -> new JSObject() {
            public JSNumber sharedCount = JSNumber.of(0);
        });

        // Method to increment sharedCount
        // Note: must be written entirely in JS due to a bug with GraalVM and Vue — `this` does not get passed correctly.
        public JSObject methods = new JSObject() {
            public JSFunction increment = JSFunction.fromBody("this.sharedCount++");
        };

        // Lifecycle hook triggered when component is created
        public JSFunction created = JSFunction.of(() ->
                System.out.println("[Mixin] SharedCountMixin created!")
        );
    }

    /**
     * Second mixin: provides extraCount and boost method.
     */
    public static class ExtraCountMixin extends JSObject {

        // Reactive state injected by the mixin
        public JSFunction data = JSFunction.of(() -> new JSObject() {
            public JSNumber extraCount = JSNumber.of(5);
        });

        // Method to boost extraCount by 10
        public JSObject methods = new JSObject() {
            public JSFunction boost = JSFunction.fromBody("this.extraCount += 10");
        };

        // Lifecycle hook triggered when component is created
        public JSFunction created = JSFunction.of(() ->
                System.out.println("[Mixin] ExtraCountMixin created!")
        );
    }
}
