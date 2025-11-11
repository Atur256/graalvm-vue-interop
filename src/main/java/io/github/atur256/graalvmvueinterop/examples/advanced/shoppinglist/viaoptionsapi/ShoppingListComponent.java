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

package io.github.atur256.graalvmvueinterop.examples.advanced.shoppinglist.viaoptionsapi;

import io.github.atur256.graalvmvueinterop.api.Component;
import io.github.atur256.graalvmwebimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.JSNumber;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;


/**
 * ShoppingListComponent is a child Vue component used in the shopping list example.
 * <p>
 * Demonstrates:
 * <ul>
 *   <li>Receiving props from parent</li>
 *   <li>Rendering item content via {@code {{ item.text }}}</li>
 *   <li>Emitting events to trigger item removal</li>
 * </ul>
 */
public class ShoppingListComponent extends Component {

    public ShoppingListComponent() {
        // Vue template: renders a list item with delete icon
        this.template = JSString.of("""
                    <li>
                        {{ index + 1 }}. {{ item.text }}
                        <span style="cursor:pointer; margin-left:10px;" @click=emitMessage(item)>&#128465;&#65039;</span>
                    </li>
                """);

        // Bind Vue methods
        this.methods = new Methods();

        // Declare props received from parent
        this.props = new Props();
    }

    /**
     * Vue method binding for {@code emitMessage}.
     * <p>
     * Emits a {@code remove} event with the item's ID when the delete icon is clicked.
     */
    public static class Methods extends JSObject {

        // Note: must be written entirely in JS due to a bug with GraalVM and Vue — `this` does not get passed correctly.
        public JSFunction emitMessage = JSFunction.fromArgs("item", "this.$emit('remove', item.id);");
    }

    /**
     * Props defines the input data passed from the parent component.
     * <p>
     * Includes:
     * <ul>
     *   <li>{@code index} – item position in the list</li>
     *   <li>{@code item} – item object with {@code id} and {@code text}</li>
     * </ul>
     */
    public static class Props extends JSObject {

        public JSNumber index;
        public JSObject item;
    }
}
