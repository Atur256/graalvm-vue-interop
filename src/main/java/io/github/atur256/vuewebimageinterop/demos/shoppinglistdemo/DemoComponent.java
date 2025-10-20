package io.github.atur256.vuewebimageinterop.demos.shoppinglistdemo;

import io.github.atur256.vuewebimageinterop.reworkedClasses.Component;
import io.github.atur256.vuewebimageinterop.reworkedClasses.VueApp;
import io.github.atur256.webimageinterop.builtin.JSArray;
import io.github.atur256.webimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.*;


public class DemoComponent extends Component {

    public DemoComponent() {
        this.template = JSString.of("""
                <div class="app">
                    <h1>Grocery List</h1>
                        <input v-model="newItemText">
                        <button @click="addItem()">Add Item</button>
                        <todo-item
                          v-for="(item, index) in shoppingList"
                          :todo="item"
                          :index="index"
                          :key="item.id"
                          @remove="removeItem"
                        >
                        </todo-item>
                
                        <hr />
                  </div>
                """);

        this.methods = new Methods();
        this.components = new Components();
    }

    public JSObject data() {
        return new Data();
    }

    private static class Data extends JSObject {

        public JSArray shoppingList = createShoppingList();
        public JSString newItemText = JSString.of("");
        public JSNumber nextId = JSNumber.of(3);

    }

    private static class Methods extends JSObject {

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

    private static class Components extends JSObject {

        public Component todoItem = new ShoppingListComponent();
    }

    private static JSArray createShoppingList() {
        JSObject item0 = createItem(0, "Vegetables");
        JSObject item1 = createItem(1, "Cheese");
        JSObject item2 = createItem(2, "Whatever else humans are supposed to eat");

        return JSArray.of(item0, item1, item2);
    }

    private static JSObject createItem(int id, String text) {
        JSObject item = JSObject.create();
        item.set("id", JSNumber.of(id));
        item.set("text", JSString.of(text));

        return item;
    }
}
