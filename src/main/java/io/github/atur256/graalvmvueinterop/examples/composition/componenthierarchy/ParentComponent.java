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

package io.github.atur256.graalvmvueinterop.examples.composition.componenthierarchy;

import io.github.atur256.graalvmvueinterop.api.Component;
import io.github.atur256.graalvmvueinterop.api.Vue;
import io.github.atur256.graalvmwebimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.JS;
import org.graalvm.webimage.api.JSNumber;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;


/**
 * ParentComponent is the root Vue component in this GraalVM-based hierarchy example.
 * <p>
 * Demonstrates:
 * <ul>
 *   <li>Reactive state management using {@code Vue.ref}</li>
 *   <li>Computed properties derived from reactive state</li>
 *   <li>Prop passing from parent -> child -> grandchild</li>
 *   <li>Provide/inject pattern for sharing reactive state across components</li>
 *   <li>Event emission from grandchild back to parent</li>
 * </ul>
 */
public class ParentComponent extends Component {

    public ParentComponent() {
        // Component name for Vue devtools and debugging
        this.name = JSString.of("ParentComponent");

        // Vue template: displays reactive state, computed value, and renders the child component
        this.template = JSString.of("""
                    <div class="app">
                        <h1>Shared State & Hierarchy Example</h1>
                        <p>Message from Grandchild Component: {{ grandMessage }}</p>
                        <p>Count: {{ count }}</p>
                        <p>Doubled: {{ doubledCount }}</p>
                        <button @click="increment">Increment</button>
                        <button @click="printComponentName">Print Component Name</button>
                
                        <messageReceiver
                            :parentMessage="message"
                            @childEvent="handleChildEvent"
                        />
                    </div>
                """);

        // Shared reactive count ref used across the component hierarchy
        // Passed to both data() and provide() for local use and injection into descendants
        JSObject countRef = Vue.ref(0);

        // data() is defined via constructor to allow passing the shared count ref
        this.data = JSFunction.of(() -> new Data(countRef));

        // Vue method bindings for template actions
        this.methods = new Methods();

        // Register child components
        this.components = new Components();

        // Computed properties
        this.computed = new Computed();

        // Provide values for descendants
        this.provide = new Provide(countRef);
    }

    private static class Data extends JSObject {

        public JSObject count;
        public JSString message = JSString.of("Hello from Parent!!!");
        public JSString grandMessage = JSString.of("-");

        public Data(JSObject countRef) {
            this.count = countRef;
        }
    }

    /**
     * Vue methods bound to template actions.
     */
    private static class Methods extends JSObject {

        /**
         * Increments the reactive count value.
         * Note: count is a Vue ref, so we access and mutate its .value field.
         */
        public JSFunction increment = JSFunction.withThis((JSObject self) -> {
            int current = self.get("count", Integer.class);
            self.set("count", JSNumber.of(current + 1));
        });

        /**
         * Logs the component name to the browser console for debugging.
         */
        public JSFunction printComponentName = JSFunction.withThis((JSObject self) ->
                System.out.println(getName(self))
        );

        /**
         * Updates grandMessage with the value received from the child event.
         */
        public JSFunction handleChildEvent = JSFunction.withThis((JSObject self, JSString messageVal) -> {
            String msg = messageVal.asString();
            self.set("grandMessage", msg);
            System.out.println("[Parent] Received message from child: " + msg);
        });
    }

    /**
     * Child components used in template.
     */
    private static class Components extends JSObject {

        public Component messageReceiver = new MessageReceiverComponent();
    }

    /**
     * Computed properties.
     */
    private static class Computed extends JSObject {

        public JSFunction doubledCount = JSFunction.withThis((JSObject self) -> {
            int count = self.get("count", Integer.class);
            return JSNumber.of(count * 2);
        });
    }

    /**
     * Values provided to descendant components via Vue's provide/inject API.
     * Enables shared reactive state and contextual messaging across the hierarchy.
     */
    private static class Provide extends JSObject {

        public JSString message = JSString.of("Injected message!!!");
        public JSObject count;

        // Assigns the shared count ref to be injected into child and grandchild components
        public Provide(JSObject countRef) {
            this.count = countRef;
        }
    }

    /**
     * Returns the Vue component name of the given component instance.
     *
     * @param object the Vue component instance whose name should be retrieved
     * @return the component’s declared name, or {@code null} if none is defined
     */
    @JS.Coerce
    @JS("return object.$options.name;")
    public static native String getName(JSObject object);
}
