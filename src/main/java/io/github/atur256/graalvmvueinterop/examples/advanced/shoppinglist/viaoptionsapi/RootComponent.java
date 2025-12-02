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
        // Note: must be written entirely in JS due to a bug with GraalVM and Vue — `this` does not get passed correctly.
        public JSFunction addItem = JSFunction.fromBody("""
                    const itemText = this.newItemText.trim();
                    if (!itemText) return;
                
                    const newItem = {
                      id: this.nextId,
                      text: itemText
                    };
                
                    this.shoppingList.push(newItem);
                    this.newItemText = '';
                    this.nextId++;
                """);

        // Removes an item from the shopping list by ID
        // Note: must be written entirely in JS due to a bug with GraalVM and Vue — `this` does not get passed correctly.
        public JSFunction removeItem = JSFunction.fromArgs(new String[]{"id"}, """
                    const index = this.shoppingList.findIndex(item => item.id === id);
                    if (index !== -1) {
                      this.shoppingList.splice(index, 1);
                    }
                """);
    }

    /**
     * Registers child components used in the template.
     */
    private static class Components extends JSObject {

        public Component listItem = new ShoppingListComponent();
    }

    /**
     * Initializes the shopping list with default items.
     */
    private static JSArray createShoppingList() {
        return JSArray.of(
                createItem(0, "Vegetables"),
                createItem(1, "Cheese"),
                createItem(2, "Whatever else humans are supposed to eat")
        );
    }

    /**
     * Creates a single shopping list item.
     *
     * @param id   unique identifier
     * @param text item description
     * @return a {@link JSObject} representing the item
     */
    private static JSObject createItem(int id, String text) {
        JSObject item = JSObject.create();
        item.set("id", JSNumber.of(id));
        item.set("text", JSString.of(text));
        return item;
    }
}
