package io.github.atur256.vuewebimageinterop.examples.advanced.shoppinglist;

import io.github.atur256.vuewebimageinterop.api.Component;
import io.github.atur256.vuewebimageinterop.api.JSUtils;
import io.github.atur256.vuewebimageinterop.api.Vue;
import io.github.atur256.webimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.*;

import java.util.function.Function;
import java.util.function.Supplier;


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
     * Vue method binding for emitMessage
     */
    public static class Methods extends JSObject {

        // Note: must be written entirely in JS due to GraalVM limitations — `this` cannot be accessed from Java lambdas.
//        public JSFunction emitMessage = JSFunction.fromArgs("item", """
//                console.log(Object.keys(this));
//                this.$emit('remove', item.id);""");


//        public JSFunction emitMessage = JSFunction.fromThisJSCons((JSObject data) -> {
//            System.out.println("Data: " + data.keys());
//        });

//        public JSFunction emitMessage = JSFunction.fromJSConsWithThis((JSObject self, JSObject item) -> {
//            try {
//                // Extract id from item
//                Object id = item.get("id");
//
//
//                System.out.println("Id: " + id);
//
//
//
//                Vue.emit("remove", JSValue.checkedCoerce(id, Integer.class));
//
////                emit(self, "remove", id);
//
        /// /                // Call Vue's $emit('remove', id)
        /// /                JSFunction emit = (JSFunction) self.get("$emit");
        /// /                if (emit != null) {
        /// /                    emit.call(self, "remove", id);
        /// /                } else {
        /// /                    System.err.println("emitMessage: this.$emit is not defined");
        /// /                }
//            } catch (Exception e) {
//                System.err.println("emitMessage: Exception while emitting event");
//                e.printStackTrace();
//            }
//        });


//        public JSFunction emitMessage = JSFunction.fromJSConsWithThis((JSObject data, JSObject item) -> {
//           System.out.println("Data: " + data.keys());
//           System.out.println("Item: " + item.keys());
//
//            JSFunction.fromArgs("item", """
//                console.log(Object.keys(this));
//                this.$emit('remove', item.id);""").call(item);
//        });

//        public JSFunction emitMessage = JSFunction.fromThisJSCons((JSObject self) -> {
//            System.out.println("Keys: " + self.keys()); // now self is the Vue component
        /// /        / /            JSValue id = JSValue.checkedCoerce(item.get("id"), JSNumber.class);
        /// /        / /            // Call $emit
        /// /        / /            JSFunction emit = (JSFunction) self.get("$emit");
        /// /        / /            if (emit != null) {
        /// /        / /                emit.call(self, "remove", id);
        /// /        / /            }
//
//            Object maybeEmit = self.get("$emit");
//            if (maybeEmit != null) {
//                System.out.println("$emit exists!: " + maybeEmit);
//            } else {
//                System.out.println("$emit not found!");
//            }
//
//        });

        public JSFunction emitMessage = JSFunction.fromJSConsWithThis((JSObject self, JSObject item) -> {
            JSUtils.printKeys(self); // prints Object.keys(self) in JS, readable

            Object maybeEmit = self.get("$emit");
            if (maybeEmit instanceof JSFunction emitFn) {
                Object id = item.get("id");
                emitFn.call(self, "remove", id);
                System.out.println("[emitMessage] emitted remove for id=" + id);
            } else {
                System.err.println("$emit not available yet");
            }
        });


//        public JSFunction emitMessage = JSFunction.fromJSConsWithThis((JSObject self, JSObject item) -> {
//            try {
//                // self = Vue component
//                Object maybeEmit = self.get("$emit");
//                if(maybeEmit instanceof JSFunction emitFn) {
//                    Object id = item.get("id");
//                    emitFn.call(self, "remove", id); // emit event
//                    System.out.println("[emitMessage] emitted remove for id=" + id);
//                }
//                else {
//                    System.err.println("$emit not found on the component instance");
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });


//
//                JSFunction.fromArgs("item", """
//                console.log(Object.keys(this));
//                this.$emit('remove', item.id);""");

    }

    /**
     * Props defines the input data passed from the parent component.
     */
    public static class Props extends JSObject {

        public JSNumber index;
        public JSObject item;
    }

//    public static void emit(JSObject ctx, String event, Object... args) {
//        try {
//            // Try to get $emit from the context
//            Object maybeEmit = ctx.get("$emit");
//
//            System.out.println("Ctx: " + ctx.keys());
//
//            if(maybeEmit instanceof JSFunction emitFn) {
//                // Call the emit function with event name and args
//                emitFn.call(ctx, event, args);
//            }
//            else {
//                System.err.println("[Vue.emit] No $emit() function found on context");
//            }
//
//        } catch (Exception e) {
//            System.err.println("[Vue.emit] Exception while emitting event '" + event + "'");
//            e.printStackTrace();
//        }
//    }

}


