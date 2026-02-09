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
import io.github.atur256.graalvmvueinterop.api.Vue;
import io.github.atur256.graalvmwebimageinterop.builtin.JSArray;
import io.github.atur256.graalvmwebimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.JSBoolean;
import org.graalvm.webimage.api.JSNumber;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;


/**
 * RootComponent demonstrates use of Vue 3's Composition API via {@code setup()} in GraalVM interop.
 * <p>
 * Demonstrates:
 * <ul>
 *   <li>Reactive state using {@code Vue.ref()}</li>
 *   <li>Method binding and event handling inside {@code setup()}</li>
 *   <li>Dynamic rendering of {@code <list-item>} components via {@code v-for}</li>
 * </ul>
 */
public class RootComponent extends Component {

    public RootComponent() {
        // Component name used in Vue DevTools
        this.name = JSString.of("RootComponentCopy");

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
     *   <li>{@code shoppingList} – array of items</li>
     *   <li>{@code newItemText} – input field binding</li>
     *   <li>{@code nextId} – counter for unique item IDs</li>
     *   <li>{@code addItem()} – method to add a new item</li>
     *   <li>{@code removeItem()} – method to remove an item by ID</li>
     * </ul>
     */
    public static class Setup extends JSObject {

        // Reactive state
        public JSObject shoppingList = Vue.ref(createShoppingList());
        public JSObject newItemText = Vue.ref("");
        public JSObject nextId = Vue.ref(3);

        // Adds a new item to the shopping list
        public JSFunction addItem = JSFunction.of(() -> {
            String itemText = newItemText.get("value", String.class);
            if(itemText == null || itemText.trim().isEmpty()) return;

            newItemText.set("value", JSString.of(""));
            int nextIdValue = nextId.get("value", Integer.class) + 1;
            nextId.set("value", JSNumber.of(nextIdValue));

            ShoppingListItem newItem = new ShoppingListItem(nextIdValue, itemText);
            JSArray list = shoppingList.get("value", JSArray.class);
            list.push(newItem);

        });

        // Remove an item from the shopping list
        public JSFunction removeItem = JSFunction.of((JSNumber idVal) -> {
            int id = idVal.asInt();
            JSArray list = shoppingList.get("value", JSArray.class);

            int index = list.findIndex(
                    JSFunction.of((JSObject item) ->
                            JSBoolean.of(item.get("id", Integer.class) == id)
                    )
            );

            if(index != -1) list.splice(index, 1);
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
