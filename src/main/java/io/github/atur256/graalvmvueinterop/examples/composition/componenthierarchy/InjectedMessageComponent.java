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
import org.graalvm.webimage.api.JS;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;


/**
 * InjectedMessageComponent is the grandchild Vue component in the hierarchy example.
 * <p>
 * Demonstrates:
 * <ul>
 *   <li>Receiving reactive values via {@code inject} from ancestor components</li>
 *   <li>Emitting events to communicate upward to parent components</li>
 *   <li>Rendering injected state directly in the template</li>
 * </ul>
 */
public class InjectedMessageComponent extends Component {

    public InjectedMessageComponent() {
        // Vue template: displays injected values and emits an event when the button is clicked
        this.template = JSString.of("""
                <div class="child">
                    <h2>Grandchild Component: Injected Context</h2>
                    <p>Received Message via inject: {{ message }} ({{ count }})</p>
                    <button @click="sendMessage">Send Message to Parent</button>
                </div>
                """);

        // Vue method bindings
        this.methods = new Methods();

        // Declares injected keys expected from ancestor components.
        // These values are provided via Vue's provide/inject mechanism and are accessible on the component instance.
        this.inject = JSArray.of("message", "count");

        // Declares custom events this component may emit.
        // Note: Intentionally mismatched to trigger Vue warning for undeclared event.
        this.emits = JSArray.of("notTheChildEvent");
    }

    /**
     * Vue method bindings for event emission.
     */
    private static class Methods extends JSObject {

        public JSFunction sendMessage = JSFunction.withThis((JSObject self) ->
                emit(self, "childEvent", "Hello from Grandchild!")
        );
    }

    /**
     * Emits a custom Vue event from a child component to its parent.
     *
     * @param self       The Vue component instance from which to emit the event.
     * @param methodName The name of the event to emit .
     * @param param      The payload of the event.
     */
    @JS.Coerce
    @JS(value = "self.$emit(methodName, param);")
    public static native void emit(JSObject self, String methodName, Object param);
}
