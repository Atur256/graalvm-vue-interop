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
import io.github.atur256.graalvmwebimageinterop.builtin.JSArray;
import io.github.atur256.graalvmwebimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;


/**
 * MessageReceiverComponent is a child Vue component in the hierarchy example.
 * <p>
 * Demonstrates:
 * <ul>
 *   <li>Receiving props from the parent component</li>
 *   <li>Emitting events to update parent state</li>
 *   <li>Rendering a grandchild component</li>
 *   <li>Using Vue's provide/inject mechanism to access shared reactive state</li>
 * </ul>
 */
public class MessageReceiverComponent extends Component {

    public MessageReceiverComponent() {
        // Vue template: displays props, injected state, and renders the grandchild component
        this.template = JSString.of("""
                <div class="child">
                    <h2>Child Component: Prop Receiver</h2>
                    <p>Received Message via props: {{ parentMessage }} ({{ count }})</p>
                    <button @click="decrement">Decrement</button>
                    <injectedMessageComponent @childEvent="forwardMessageToParent"/>
                </div>
                """);

        // Vue method bindings for template actions
        this.methods = new Methods();

        // Registers grandchild component used in the template
        this.components = new Components();

        // Props received from the parent component
        this.props = new Props();

        // Inject reactive values from parent using Vue's provide/inject API
        this.inject = JSArray.of("count");

        // Declares custom events this component may emit
        // Enables validation and tooling support for upward communication
        this.emits = JSArray.of("childEvent");
    }

    /**
     * Vue method bindings for decrement and message forwarding.
     */
    private static class Methods extends JSObject {

        /**
         * Forwards the message received from the grandchild to the parent.
         * The original message is passed through unchanged.
         * <p>
         * Note:
         * - Must be written entirely in JS due to a bug with GraalVM and Vue — `this` does not get passed correctly.
         * - The `event` parameter is explicitly declared to avoid `$event` scoping issues.
         */
        public JSFunction forwardMessageToParent = JSFunction.fromArgs("event", "console.log(event); this.$emit('childEvent', event);");

        /**
         * Decrements the injected count value.
         * <p>
         * Note:
         * - Must be written entirely in JS due to a bug with GraalVM and Vue — `this` does not get passed correctly.
         * - Injected values are attached directly to the component instance, not to `data()`.
         */
        public JSFunction decrement = JSFunction.fromBody("this.count = this.count - 1;");
    }

    /**
     * Registers grandchild component used in the template.
     */
    private static class Components extends JSObject {

        public Component injectedMessageComponent = new InjectedMessageComponent();
    }

    /**
     * Props received from the parent component.
     * <p>
     * Note:
     * - Props are read-only and should not be mutated directly.
     * - Used to pass contextual data from parent to child.
     */
    public static class Props extends JSObject {

        public JSString parentMessage;
    }
}
