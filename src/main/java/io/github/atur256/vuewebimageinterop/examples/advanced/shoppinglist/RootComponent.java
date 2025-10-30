package io.github.atur256.vuewebimageinterop.examples.advanced.shoppinglist;

import io.github.atur256.vuewebimageinterop.api.Component;
import io.github.atur256.vuewebimageinterop.api.VueApp;
import io.github.atur256.webimageinterop.builtin.JSArray;
import io.github.atur256.webimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.JSNumber;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;
import org.graalvm.webimage.api.JSValue;


/**
 * RootComponent is the root Vue component for this GraalVM-based shopping list example.
 * <p>
 * Demonstrates:
 * <ul>
 *   <li>Reactive state via {@code data()}</li>
 *   <li>Dynamic rendering of <list-item> components via {@code v-for}</li>
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

        public JSFunction addItem = JSFunction.fromThisJSCons((JSObject data) -> {

            String itemText = JSValue.checkedCoerce(data.get("newItemText"), String.class);
            if(itemText == null || itemText.trim().isEmpty()) return;

            data.set("newItemText","");
            int nextId = JSValue.checkedCoerce(data.get("nextId"), Integer.class) + 1;
            data.set("nextId", nextId);


            JSObject newItem = createItem(nextId, itemText);
            JSArray shoppingList = JSValue.checkedCoerce(data.get("shoppingList"), JSArray.class);
            shoppingList.push(newItem);
        });

        public JSFunction removeItem = JSFunction.fromJSConsWithThis((JSObject data, JSNumber idVal) -> {
            int id = idVal.asInt();
            JSArray shoppingList = JSValue.checkedCoerce(data.get("shoppingList"), JSArray.class);

            int index = -1;
            for(int i = 0; i < shoppingList.length; i++) {
                JSObject item = JSValue.checkedCoerce(shoppingList.get(i), JSObject.class);
                if(JSValue.checkedCoerce(item.get("id"), Integer.class) == id) {
                    index = i;
                    break;
                }
            }

            if(index >= 0) shoppingList.splice(index, 1);
        });
    }

    /**
     * Registers child components used in the template.
     */
    private static class Components extends JSObject {

        public Component listItem = new ShoppingListComponent();
    }

    private static JSArray createShoppingList() {
        return JSArray.of(
                createItem(0, "Vegetables"),
                createItem(1, "Cheese"),
                createItem(2, "Whatever else humans are supposed to eat")
        );
    }

    private static JSObject createItem(int id, String text) {
        JSObject item = JSObject.create();
        item.set("id", JSNumber.of(id));
        item.set("text", JSString.of(text));
        return item;
    }
}
