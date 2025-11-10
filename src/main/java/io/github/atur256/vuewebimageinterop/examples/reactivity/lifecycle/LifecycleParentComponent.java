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

package io.github.atur256.vuewebimageinterop.examples.reactivity.lifecycle;

import io.github.atur256.vuewebimageinterop.api.Component;
import io.github.atur256.vuewebimageinterop.api.VueApp;
import io.github.atur256.webimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.*;


/**
 * LifecycleParentComponent wraps LifecycleComponent in {@code <keep-alive>} and manages its visibility.
 * <p>
 * Demonstrates:
 * <ul>
 *   <li>Component toggling via {@code v-if}</li>
 *   <li>Preserving component state using {@code <keep-alive>}</li>
 *   <li>Manual app unmounting via parent-level button</li>
 * </ul>
 */
public class LifecycleParentComponent extends Component {

    // Reference to the VueApp instance, used for manual unmounting
    public VueApp app;

    public LifecycleParentComponent() {
        // Vue template: toggles child component and unmounts app
        this.template = JSString.of("""
                    <div>
                        <button @click="toggle">Toggle Lifecycle Component</button>
                        <keep-alive>
                            <lifecycle-component v-if="show" />
                        </keep-alive>
                        <button @click="unmountApp">Unmount App</button>
                    </div>
                """);

        // Vue method bindings for toggling and unmounting
        this.methods = new Methods(this);

        // Register child components
        this.components = new Components();
    }

    /**
     * Reactive state exposed to the template.
     */
    @Override
    public JSObject data() {
        return new Data();
    }

    /**
     * Reactive data model for the parent component.
     */
    public static class Data extends JSObject {

        // Controls visibility of the child component
        public boolean show = true;
    }

    /**
     * Vue method bindings for template actions.
     */
    public static class Methods extends JSObject {

        /**
         * Toggles visibility of the child component
         * Note: must be written entirely in JS due to a bug with GraalVM and Vue — `this` does not get passed correctly.
         */
        public JSFunction toggle = JSFunction.fromBody("this.show = !this.show");

        // Unmounts the Vue app manually
        public JSFunction unmountApp;

        public Methods(LifecycleParentComponent this_) {
            unmountApp = JSFunction.fromRun(() -> {
                if(this_.app != null) this_.app.unmount();
            });
        }
    }

    /**
     * Registers child components used in the parent template.
     */
    public static class Components extends JSObject {

        public Component lifecycleComponent = new LifecycleComponent();
    }

    /**
     * Sets the VueApp instance for this component.
     * Required for manual unmounting via button click.
     */
    public void setApp(VueApp app) {
        this.app = app;
    }
}
