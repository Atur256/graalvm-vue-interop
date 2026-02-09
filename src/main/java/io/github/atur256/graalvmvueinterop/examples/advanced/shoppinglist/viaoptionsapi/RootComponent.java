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
import io.github.atur256.graalvmwebimageinterop.builtin.JSArray;
import io.github.atur256.graalvmwebimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.JSBoolean;
import org.graalvm.webimage.api.JSNumber;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;


/**
 * RootComponent is the root Vue component for this GraalVM-based shopping list example.
 * <p>
 * Demonstrates:
 * <ul>
 *   <li>Reactive state via {@code data()}</li>
 *   <li>Dynamic rendering of {@code <list-item>} components via {@code v-for}</li>
 *   <li>Event handling for adding and removing items</li>
 * </ul>
 */
public class RootComponent extends Component {

    public RootComponent() {
        // Vue template: input field, add button, and dynamic list of <list-item> components
        this.template = JSString.of("""
                    <div class="app">
                        <h1>Grocery List</h1>
                        <input v-model="newItemText">
                        <button @click="addItem">Add Item</button>
                        <list-item
                          v-for="(item, index) in shoppingList"
                          :item="item"
                          :index="index"
                          :key="item.id"
                          @remove="removeItem">
                        </list-item>
                    </div>
                """);

        // Bind Vue methods
        this.methods = new Methods();

        // Register child components
        this.components = new Components();
    }

    /**
     * Provides reactive state for this component:
     * <ul>
     *   <li>{@code shoppingList} – array of items</li>
     *   <li>{@code newItemText} – input field binding</li>
     *   <li>{@code nextId} – counter for unique item IDs</li>
     * </ul>
     */
    public JSObject data() {
        return new Data();
    }

    /**
     * Reactive state model for the shopping list component.
     */
    private static class Data extends JSObject {

        public JSArray shoppingList = createShoppingList();
        public JSString newItemText = JSString.of("");
        public JSNumber nextId = JSNumber.of(3);
    }

    /**
     * Vue method bindings for add/remove actions.
     */
    private static class Methods extends JSObject {

        // Adds a new item to the shopping list
        public JSFunction addItem = JSFunction.withThis((JSObject self) -> {
            String newItemText = self.get("newItemText", String.class).trim();

            if(newItemText.isEmpty()) return;

            int nextId = self.get("nextId", Integer.class);

            ShoppingListItem newItem = new ShoppingListItem(nextId, newItemText);

            JSArray shoppingList = self.get("shoppingList", JSArray.class);
            shoppingList.push(newItem);
            self.set("newItemText", "");
        });

        // Removes an item from the shopping list by ID
        public JSFunction removeItem = JSFunction.withThis((JSObject self, JSNumber id) -> {

            int convertedId = id.asInt();
            JSArray shoppingList = self.get("shoppingList", JSArray.class);

            int index = shoppingList.findIndex(
                    JSFunction.of((JSObject item) ->
                            JSBoolean.of(item.get("id", Integer.class) == convertedId)
                    )
            );

            if(index != -1) {
                shoppingList.splice(index, 1);
            }
        });
    }

    /**
     * Registers child components used in the template.
     */
    private static class Components extends JSObject {

        public Component listItem = new ShoppingListComponent();
    }

    /**
     * Represents a single item in the shopping list.
     * <ul>
     *   <li>{@code id} – a unique integer identifier for the item, used for tracking and removal.</li>
     *   <li>{@code text} – the display text of the shopping item (e.g., "Cheese").</li>
     * </ul>
     * <p>
     */
    public static class ShoppingListItem extends JSObject {

        public int id;
        public String text;

        public ShoppingListItem(int id, String text) {
            this.id = id;
            this.text = text;
        }
    }

    /**
     * Initializes the shopping list with default items.
     */
    private static JSArray createShoppingList() {
        return JSArray.of(
                new ShoppingListItem(0, "Vegetables"),
                new ShoppingListItem(1, "Cheese"),
                new ShoppingListItem(2, "Whatever else humans are supposed to eat")
        );
    }
}
