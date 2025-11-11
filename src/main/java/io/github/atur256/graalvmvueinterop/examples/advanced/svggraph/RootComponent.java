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

package io.github.atur256.graalvmvueinterop.examples.advanced.svggraph;

import io.github.atur256.graalvmvueinterop.api.Component;
import io.github.atur256.graalvmvueinterop.api.Vue;
import io.github.atur256.graalvmwebimageinterop.builtin.JSArray;
import io.github.atur256.graalvmwebimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.JSNumber;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;
import org.graalvm.webimage.api.JSValue;

/**
 * RootComponent is the root Vue component for this GraalVM-based SVG graph example.
 * <p>
 * Demonstrates:
 * <ul>
 *   <li>Reactive state via {@code setup()}</li>
 *   <li>Dynamic SVG rendering via {@code <poly-graph>} child component</li>
 *   <li>Interactive stat controls using {@code v-model} and {@code @click}</li>
 * </ul>
 */
public class RootComponent extends Component {

    public RootComponent() {
        // Vue template: SVG graph plus interactive controls
        this.template = JSString.of("""
            <div id="app">
                <svg width="200" height="200">
                    <poly-graph :stats="stats"></poly-graph>
                </svg>
                <div v-for="stat in stats">
                    <label>{{stat.label}}</label>
                    <input type="range" v-model="stat.value" min="0" max="100">
                    <span>{{stat.value}}</span>
                    <button @click="remove(stat)" class="remove">X</button>
                </div>
                <form id="add">
                    <input name="newlabel" v-model="newLabel">
                    <button @click="add">Add a Stat</button>
                </form>
                <pre id="raw">{{ stats }}</pre>
            </div>
        """);

        // Register child components
        this.components = new Components();
    }

    /**
     * Composition API setup function.
     * <p>
     * Returns a {@link JSObject} containing reactive state and methods
     * that are exposed to the template and component context.
     */
    @Override
    public JSObject setup() {
        return new Setup();
    }

    /**
     * Reactive bindings exposed by the {@code setup()} function.
     * <p>
     * Includes:
     * <ul>
     *   <li>{@code stats} – reactive array of stat objects</li>
     *   <li>{@code newLabel} – input field binding for new stat label</li>
     *   <li>{@code add()} – method to add a new stat</li>
     *   <li>{@code remove()} – method to remove a stat (minimum of 3 required)</li>
     * </ul>
     */
    public static class Setup extends JSObject {

        // Input field binding for new stat label
        public JSObject newLabel = Vue.ref("");

        // Reactive array of stat objects
        public JSObject stats = Vue.ref(initialiseStatArray());

        // Adds a new stat to the graph
        public JSFunction add = JSFunction.fromCons((JSObject e) -> {
            try {
                JSFunction.fromArgs("obj", "obj.preventDefault();").invoke(e);

                String newText = JSValue.checkedCoerce(newLabel.get("value"), String.class);
                if (newText.isEmpty()) return;

                JSArray arr = JSValue.checkedCoerce(stats.get("value"), JSArray.class);
                arr.push(createItem(newText));

                newLabel.set("value", JSString.of(""));
            } catch (Exception ex) {
                System.out.println("Exception caught!!!!");
                ex.printStackTrace();
            }
        });

        // Removes a stat from the graph (minimum of 3 stats required)
        public JSFunction remove = JSFunction.fromCons((JSObject stat) -> {
            JSArray arr = JSValue.checkedCoerce(stats.get("value"), JSArray.class);
            if (arr.length > 3) {
                arr.splice(arr.indexOf(stat), 1);
            } else {
                System.err.println("Can't delete more!");
            }
        });
    }

    /**
     * Registers child components used in the template, including {@code <poly-graph>}.
     */
    private static class Components extends JSObject {
        public Component polyGraph = new PolyGraph();
    }

    /**
     * Initializes the stat array with default labeled values.
     */
    private static JSArray initialiseStatArray() {
        return JSArray.of(
                createItem("A"),
                createItem("B"),
                createItem("C"),
                createItem("D"),
                createItem("E"),
                createItem("F")
        );
    }

    /**
     * Creates a single stat item with label and default value.
     *
     * @param label the label for the stat
     * @return a {@link JSObject} representing the stat
     */
    private static JSObject createItem(String label) {
        JSObject item = JSObject.create();
        item.set("label", JSString.of(label));
        item.set("value", JSNumber.of(100));
        return item;
    }
}
