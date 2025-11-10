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

package io.github.atur256.vuewebimageinterop.examples.basics.helloworld;

import io.github.atur256.vuewebimageinterop.api.Component;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;


/**
 * RootComponent is the root Vue component for this GraalVM-based Hello World example.
 * <p>
 * This component overrides key fields from the abstract {@code Component} class:
 * {@code template} and {@code data()}.
 * <p>
 * The template renders a simple heading bound to a reactive message,
 * demonstrating minimal Vue interop in Java.
 */
public class RootComponent extends Component {

    public RootComponent() {
        // Define the Vue template: a heading bound to the reactive "message" property
        this.template = JSString.of("""
                <div id="app">
                  <h1>{{ message }}</h1>
                </div>
                """);
    }

    /**
     * Overrides {@code Component.data()} to provide reactive state.
     * <p>
     * Exposes the following reactive property:
     * <ul>
     *   <li>{@code message} – the text displayed inside the component's main heading (h1 element)</li>
     * </ul>
     */
    public JSObject data() {
        return new Data();
    }

    /**
     * Defines the reactive state for this component.
     * Returned by the {@code data()} method.
     */
    private static class Data extends JSObject {

        /**
         * The message displayed in the template.
         */
        public String message = "Hello World!";
    }
}
