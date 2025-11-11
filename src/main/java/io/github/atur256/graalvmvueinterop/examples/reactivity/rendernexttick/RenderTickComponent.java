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

package io.github.atur256.graalvmvueinterop.examples.reactivity.rendernexttick;

import io.github.atur256.graalvmvueinterop.api.Component;
import io.github.atur256.graalvmvueinterop.api.Vue;
import io.github.atur256.graalvmwebimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;
import org.graalvm.webimage.api.JSValue;


/**
 * RenderTickComponent is the root Vue component for this GraalVM-based render + nextTick example.
 * <p>
 * Demonstrates:
 * <ul>
 *   <li>Reactive state via {@code Vue.reactive}</li>
 *   <li>Manual virtual DOM rendering via {@code Vue.h}</li>
 *   <li>Event-driven updates via {@code @click}</li>
 *   <li>Deferred DOM updates using {@code Vue.nextTick}</li>
 * </ul>
 */
public class RenderTickComponent extends Component {

    /**
     * Reactive state tracked by Vue.
     */
    public JSObject reactiveState;

    /**
     * Constructor initializes reactive state and render function.
     */
    public RenderTickComponent() {
        this.template = JSString.of("<div id='placeholder'></div>");

        // Initialize reactive state
        InitialState initialState = new InitialState();
        this.reactiveState = Vue.reactive(initialState);

        // Provide reactive state to Vue's data system
        this.data = JSFunction.fromSupp(() -> reactiveState);

        // Define render function using Vue.h
        this.render = JSFunction.fromSupp(() -> Vue.h(
                "div",
                new Props(initialState),
                Vue.h("span", new SpanProps(reactiveState),
                        JSValue.checkedCoerce(reactiveState.get("message"), JSString.class)
                )
        ));
    }

    /**
     * Initial reactive state model.
     */
    private static class InitialState extends JSObject {

        public JSString message = JSString.of("I am dynamic!");
        public JSString style = JSString.of("color: blue; font-weight: bold; font-size: 16px;");
    }

    /**
     * Props for root div element.
     */
    private static class Props extends JSObject {

        public JSString style;

        public Props(InitialState state) {
            this.style = state.style;
        }
    }

    /**
     * SpanProps defines event handlers for the span element.
     */
    private static class SpanProps extends JSObject {

        public JSFunction onClick;

        public SpanProps(JSObject state) {
            this.onClick = JSFunction.fromRun(() -> {
                // Immediate update
                state.set("message", JSString.of("Updated on click!"));
                state.set("style", JSString.of("color: green; font-weight: bold; font-size: 24px;"));

                // Deferred update after next tick
                Vue.nextTick(JSFunction.fromRun(() -> state.set("message", JSString.of("Final update after next tick!"))));
            });
        }
    }
}
