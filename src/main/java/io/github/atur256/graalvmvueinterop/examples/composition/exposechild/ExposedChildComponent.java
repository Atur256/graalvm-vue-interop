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

package io.github.atur256.graalvmvueinterop.examples.composition.exposechild;

import io.github.atur256.graalvmvueinterop.api.Component;
import io.github.atur256.graalvmwebimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;


/**
 * ExposedChildComponent is a child Vue component with an exposed method.
 * <p>
 * Demonstrates:
 * <ul>
 *   <li>Defining a method callable from the parent via ref</li>
 *   <li>Using the expose option to control what is accessible</li>
 * </ul>
 */
public class ExposedChildComponent extends Component {

    public ExposedChildComponent() {
        // Vue template: displays a button that logs a message
        this.template = JSString.of("""
                <div>
                    <p>Child Component</p>
                    <button @click="sayHello">Say Hello</button>
                </div>
                """);

        // Vue method bindings
        this.methods = new Methods();

        // Expose sayHello method to parent via template ref
        this.expose = new Expose();
    }

    /**
     * Vue method bindings for child actions.
     */
    public static class Methods extends JSObject {

        /**
         * Logs a message to the console.
         */
        public JSFunction sayHello = JSFunction.of(() -> System.out.println("Hello from child!"));
    }

    /**
     * Declares which internal properties are exposed to parent via template ref.
     */
    public static class Expose extends JSObject {

        public boolean sayHello = true;
    }
}
