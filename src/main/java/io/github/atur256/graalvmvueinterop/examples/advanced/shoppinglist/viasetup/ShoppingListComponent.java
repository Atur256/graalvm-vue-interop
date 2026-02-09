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

package io.github.atur256.graalvmvueinterop.examples.advanced.shoppinglist.viasetup;

import io.github.atur256.graalvmvueinterop.api.Component;
import io.github.atur256.graalvmwebimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.JS;
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

        // Declare props received from parent
        this.props = new Props();
    }

    /**
     * Composition API setup function.
     * <p>
     * Returns a {@link JSObject} containing methods exposed to the template.
     * <p>
     * Includes:
     * <ul>
     *   <li>{@code emitMessage()} – emits a {@code remove} event with the item's ID</li>
     * </ul>
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
     *   <li>{@code emitMessage()} – emits a {@code remove} event with the item's ID</li>
     * </ul>
     */
    public static class Setup extends JSObject {

        // Emits a 'remove' event with the item's ID
        public JSFunction emitMessage = JSFunction.withThis((JSObject self, JSObject item) -> {
            emit(self, "remove", item.get("id", Integer.class));
        });
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
