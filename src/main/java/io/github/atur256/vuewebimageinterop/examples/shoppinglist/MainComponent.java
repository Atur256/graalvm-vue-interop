package io.github.atur256.vuewebimageinterop.examples.shoppinglist;

import io.github.atur256.vuewebimageinterop.api.Component;
import io.github.atur256.vuewebimageinterop.api.VueApp;
import io.github.atur256.webimageinterop.builtin.JSArray;
import io.github.atur256.webimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.*;


/**
 * MainComponent is the root Vue component for the Shopping List example.
 * <p>
 * It overrides key fields from the abstract Component class: template, data, methods, and components.
 * The template replicates a dynamic list with add/remove functionality, rendered via Java interop.
 */
public class MainComponent extends Component {

    public MainComponent() {


        // Vue template: input field, add button, and dynamic list of <list-item> components
        this.template = JSString.of("""
                <div class="app">
                    <h1>Grocery List</h1>
                        <input v-model="newItemText">
                        <button @click="addItem()">Add Item</button>
                        <list-item
                          v-for="(item, index) in shoppingList"
                          :item="item"
                          :index="index"
                          :key="item.id"
                          @remove="removeItem"
                        >
                        </list-item>
                
                        <hr />
                  </div>
                """);

        // Vue method bindings: add/remove item
        this.methods = new Methods();

        // Register child component <list-item>
        this.components = new Components();
    }

    /**
     * Overrides Component.data() to expose reactive state:
     * - shoppingList: array of item objects
     * - newItemText: bound to input field
     * - nextId: used to assign unique IDs to new items
     */
    public JSObject data() {
        return new Data();
    }

    /**
     * Data defines the reactive state model for this component.
     * It is returned by the overridden data() method.
     */
    private static class Data extends JSObject {

        public JSArray shoppingList = createShoppingList();
        public JSString newItemText = JSString.of("");
        public JSNumber nextId = JSNumber.of(3);
    }

    /**
     * Methods defines Vue event handlers.
     * These are bound to template actions via @click and @remove.
     */
    private static class Methods extends JSObject {

        // Adds a new item to the shopping list
        public JSFunction addItem = JSFunction.fromRunnable(() -> {
            String itemText = VueApp.getValue("newItemText", String.class);
            if(itemText == null || itemText.trim().isEmpty()) {
                return;
            }

            VueApp.setValue("newItemText", "");
            int nextId = VueApp.getValue("nextId", Integer.class) + 1;
            VueApp.setValue("nextId", nextId);

            JSObject newItem = createItem(nextId, itemText);
            JSArray shoppingList = VueApp.getValue("shoppingList", JSArray.class);
            shoppingList.push(newItem);
        });

        // Removes an item from the shopping list by ID
        public JSFunction removeItem = JSFunction.fromConsumer(idVal -> {
            int id = idVal.asInt();

            JSArray shoppingList = VueApp.getValue("shoppingList", JSArray.class);

            int index = -1;
            for(int i = 0; i < shoppingList.length; i++) {
                JSObject item = JSValue.checkedCoerce(shoppingList.get(i), JSObject.class);
                if(JSValue.checkedCoerce(item.get("id"), Integer.class) == id) {
                    index = i;
                    break;
                }
            }

            if(index < 0) {
                return;
            }

            shoppingList.splice(index, 1);
        });
    }

    /**
     * Components registers child components used in the template.
     * This includes <list-item>, which renders individual list entries.
     */
    private static class Components extends JSObject {

        public Component listItem = new ShoppingListComponent();
    }

    // Creates the initial array of shopping list items
    private static JSArray createShoppingList() {
        JSObject item0 = createItem(0, "Vegetables");
        JSObject item1 = createItem(1, "Cheese");
        JSObject item2 = createItem(2, "Whatever else humans are supposed to eat");

        return JSArray.of(item0, item1, item2);
    }

    // Creates a shopping list item with ID and text
    private static JSObject createItem(int id, String text) {
        JSObject item = JSObject.create();
        item.set("id", JSNumber.of(id));
        item.set("text", JSString.of(text));

        return item;
    }
}
