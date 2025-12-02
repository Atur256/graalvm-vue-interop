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

import io.github.atur256.graalvmvueinterop.api.Component;
import io.github.atur256.graalvmwebimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.*;


/**
 * LifecycleComponent demonstrates all Vue lifecycle hooks using the Options API.
 * <p>
 * Demonstrates:
 * <ul>
 *   <li>Lifecycle hook registration via {@code beforeCreate}, {@code mounted}, etc.</li>
 *   <li>Reactive state updates triggered by user interaction</li>
 *   <li>Error handling via {@code errorCaptured}</li>
 *   <li>Activation and deactivation via {@code <keep-alive>}</li>
 * </ul>
 */
public class LifecycleComponent extends Component {

    public LifecycleComponent() {
        // Component name used in Vue DevTools and debugging
        this.name = JSString.of("LifecycleComponent");

        // Vue template: displays reactive message and lifecycle buttons
        this.template = JSString.of("""
                <div class="app">
                    <h2>Vue Lifecycle Hooks Example</h2>
                    <p>Open console to see lifecycle messages.</p>
                    <p>Message: {{ message }}</p>
                    <button @click="updateMessage">Update Message</button>
                
                    <!-- Simulated child component that throws an error -->
                    <error-child />
                </div>
                """);

        // Vue method bindings for template actions
        this.methods = new Methods();

        // Register child components
        this.components = new JSObject() {{
            set("error-child", new ErrorChildComponent());
        }};

        // Lifecycle hook: called before component instance is initialized
        this.beforeCreate = JSFunction.of(() ->
                System.out.println("[Lifecycle] beforeCreate \u2192 Component instance is being initialized")
        );

        // Lifecycle hook: called after instance is created and reactive data is set up
        this.created = JSFunction.of(() ->
                System.out.println("[Lifecycle] created \u2192 Reactive data and events are now set up")
        );

        // Lifecycle hook: called before mounting to the DOM
        this.beforeMount = JSFunction.of(() ->
                System.out.println("[Lifecycle] beforeMount \u2192 Component is about to be mounted to the DOM")
        );

        // Lifecycle hook: called after component is mounted and visible
        this.mounted = JSFunction.of(() ->
                System.out.println("[Lifecycle] mounted \u2192 Component is now mounted and visible")
        );

        // Lifecycle hook: called before DOM updates due to reactive changes
        this.beforeUpdate = JSFunction.of(() ->
                System.out.println("[Lifecycle] beforeUpdate \u2192 Reactive data changed, DOM update is imminent")
        );

        // Lifecycle hook: called after DOM has been updated
        this.updated = JSFunction.of(() ->
                System.out.println("[Lifecycle] updated \u2192 DOM has been updated with new reactive data")
        );

        // Lifecycle hook: called before component is removed from the DOM
        this.beforeUnmount = JSFunction.of(() ->
                System.out.println("[Lifecycle] beforeUnmount \u2192 Component is about to be removed from the DOM")
        );

        // Lifecycle hook: called after component is removed and cleaned up
        this.unmounted = JSFunction.of(() ->
                System.out.println("[Lifecycle] unmounted \u2192 Component has been removed and cleaned up")
        );

        // Lifecycle hook: called when an error is captured from a child component
        this.errorCaptured = JSFunction.of((RuntimeException err) -> {
            System.out.println("[Lifecycle] errorCaptured \u2192 Error from child component: " + err);
            return JSBoolean.of(false);
        });

        // Lifecycle hook: called when a keep-alive component is activated
        this.activated = JSFunction.of(() ->
                System.out.println("[Lifecycle] activated \u2192 Component has been activated from cache")
        );

        // Lifecycle hook: called when a keep-alive component is deactivated
        this.deactivated = JSFunction.of(() ->
                System.out.println("[Lifecycle] deactivated \u2192 Component has been deactivated and cached")
        );
    }

    /**
     * Reactive state exposed to the template.
     */
    @Override
    public JSObject data() {
        return new Data();
    }

    /**
     * Reactive data model for the component.
     */
    private static class Data extends JSObject {

        // Initial message displayed in the template
        public JSString message = JSString.of("Initial message");
    }

    /**
     * Vue method bindings for template actions.
     */
    private static class Methods extends JSObject {

        // Method to update the reactive message
        public JSFunction updateMessage;

        public Methods() {
            updateMessage = JSFunction.withThis((JSObject data) -> {
                String current = JSValue.checkedCoerce(data.get("message"), String.class);
                String updated = current + " updated";
                data.set("message", JSString.of(updated));
            });
        }
    }

    /**
     * Simulated child component that throws an error on mount.
     */
    private static class ErrorChildComponent extends Component {

        public ErrorChildComponent() {
            // Vue template: triggers error during mount
            this.template = JSString.of("<div>Error child mounted</div>");
            this.mounted = JSFunction.of(() -> {
                throw new RuntimeException("Simulated error from child component");
            });
        }
    }
}
