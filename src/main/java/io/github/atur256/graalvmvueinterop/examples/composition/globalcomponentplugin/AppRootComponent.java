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

package io.github.atur256.graalvmvueinterop.examples.composition.globalcomponentplugin;

import io.github.atur256.graalvmvueinterop.api.Component;
import io.github.atur256.graalvmwebimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;


/**
 * AppRootComponent is the root Vue component for this GraalVM-based plugin example.
 * <p>
 * Demonstrates:
 * <ul>
 *   <li>Accessing global properties injected via a plugin</li>
 *   <li>Passing props to a child component</li>
 *   <li>Receiving events from child via {@code @childEvent}</li>
 * </ul>
 */
public class AppRootComponent extends Component {

    public AppRootComponent() {
        // Vue template: global message, parent message, child component
        this.template = JSString.of("""
                <div>
                    <h2>VueApp with Component & Plugin</h2>
                    <p>Global Message: {{ $root.globalMessage }}</p>
                    <p>Parent Message: {{ parentMessage }}</p>
                    <p>Message from Child: {{ childResponseMessage }}</p>
                    <child-component :messageFromParent="parentMessage" @childEvent="onChildMessage"/>
                </div>
                """);

        // Vue method bindings
        this.methods = new Methods();
    }

    /**
     * Reactive state for template bindings.
     */
    public JSObject data() {
        return new Data();
    }

    private static class Data extends JSObject {

        public JSString parentMessage = JSString.of("Hello from Parent!");
        public JSString childResponseMessage = JSString.of("-");
    }

    /**
     * Vue methods handling events from the child component.
     */
    private static class Methods extends JSObject {

        public JSFunction onChildMessage = JSFunction.fromConsWithThis((JSObject data, JSString msg) -> {
            System.out.println("Parent received event: " + msg.asString());
            data.set("childResponseMessage", msg.asString());
        });
    }
}
