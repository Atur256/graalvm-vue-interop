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

package io.github.atur256.vuewebimageinterop.examples.composition.globalcomponentplugin;

import io.github.atur256.vuewebimageinterop.api.Component;
import io.github.atur256.webimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;


/**
 * ChildComponent is a globally registered Vue component.
 * <p>
 * Demonstrates:
 * <ul>
 *   <li>Receiving props from parent</li>
 *   <li>Emitting events to communicate upward</li>
 *   <li>Rendering received state in the template</li>
 * </ul>
 */
public class ChildComponent extends Component {

    public ChildComponent() {
        // Vue template: displays received message and emits event on button click
        this.template = JSString.of("""
                <div>
                    <h2>Child Component:</h2>
                    <p>Child received: {{ messageFromParent }}</p>
                    <button @click="emitMessageToParent">Send Event to Parent</button>
                </div>
                """);

        // Declare props received from parent component
        this.props = new Props();

        // Vue method bindings for event emission
        this.methods = new Methods();
    }

    /**
     * Props received from parent component.
     */
    private static class Props extends JSObject {

        public JSString messageFromParent;
    }

    /**
     * Vue method bindings for event emission.
     */
    private static class Methods extends JSObject {

        // Note: must be written entirely in JS due to a bug with GraalVM and Vue — `this` does not get passed correctly.
        public JSFunction emitMessageToParent = JSFunction.fromBody("this.$emit('childEvent', 'Hello from Child!');");
    }
}
