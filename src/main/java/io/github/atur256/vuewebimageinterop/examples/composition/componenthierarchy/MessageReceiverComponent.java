package io.github.atur256.vuewebimageinterop.examples.composition.componenthierarchy;

import io.github.atur256.vuewebimageinterop.api.Component;
import io.github.atur256.vuewebimageinterop.api.Vue;
import io.github.atur256.vuewebimageinterop.api.VueApp;
import io.github.atur256.webimageinterop.builtin.JSArray;
import io.github.atur256.webimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.JSNumber;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;
import org.graalvm.webimage.api.JSValue;


/**
 * MessageReceiverComponent is a child Vue component in the hierarchy example.
 * <p>
 * Demonstrates:
 * <ul>
 *   <li>Receiving props from parent</li>
 *   <li>Emitting events to update parent state</li>
 *   <li>Rendering grandchild component</li>
 * </ul>
 */
public class MessageReceiverComponent extends Component {

    public MessageReceiverComponent() {
        // Vue template: displays props and renders grandchild component
        this.template = JSString.of("""
                <div class="child">
                    <h2>Child Component: Prop Receiver</h2>
                    <p>Received Message via props: {{ parentMessage }} ({{ count }})</p>
                    <button @click="decrement">Decrement</button>
                    <injectedMessageComponent @childEvent="updateParentMessage"/>
                </div>
                """);

        // Vue method bindings
        this.methods = new Methods();

        // Register grandchild component
        this.components = new Components();

        // Declare props received from parent
        this.props = new Props();

        this.inject = new JSObject() {{
            set("count", JSString.of("count"));
        }};


//        this.inject = JSArray.of("count");

//        this.inject = new JSObject() {{
//            set("count", JSString.of("count"));
//        }};


        // Declare injected keys expected from ancestor
//        this.inject = JSArray.of("parentCount");

//        this.inject = new JSObject() {{
//            set("count", JSString.of("count"));
//        }};

//        this.data = JSFunction.fromSupp(() -> {
//            JSObject data = JSObject.create();
//            data.set("count", this.get("count")); // ← pulls from injected context
//            return data;
//        });
//
//        this.data = JSFunction.fromSupp(() -> {
//            JSObject data = JSObject.create();
//            data.set("count", this.get("count")); // pulls injected value from component context
//            return data;
//        });

        this.mounted = JSFunction.fromThisJSCons((JSObject ctx) -> {
            Object count = ctx.get("count");
            System.out.println("Injected count type: " + count.getClass().getName());
        });


    }

    /**
     * Vue method bindings for decrement and parent message update.
     */
    private static class Methods extends JSObject {

        /**
         * Updates the grandMessage value in parent component
         */
        public JSFunction updateParentMessage = JSFunction.fromJSConsWithThis((JSObject data, JSString messageVal) -> {
            String msg = messageVal.asString();
            data.set("grandMessage", msg);
            System.out.println("Passed message: " + msg);
        });

        /**
         * Decrements the shared count value
         */
//        public JSFunction decrement = JSFunction.fromThisJSCons((JSObject data) -> {
//            int current = JSValue.checkedCoerce(data.get("parentCount"), Integer.class);
//            int decremented = current - 1;
//            data.set("parentCount", decremented);
//        });

//        public JSFunction decrement = JSFunction.fromThisJSCons((JSObject data) -> {
//            JSObject countRef = (JSObject) data.get("parentCount");
//            int current = JSValue.checkedCoerce(countRef.get("value"), Integer.class);
//            countRef.set("value", current - 1);
//        });

//        public JSFunction decrement = JSFunction.fromThisJSCons((JSObject data) -> {
//            JSObject countRef = (JSObject) data.get("count");
//            int current = JSValue.checkedCoerce(countRef.get("value"), Integer.class);
//            countRef.set("value", current - 1); // ✅ This works — no read-only proxy
//        });

//        public JSFunction decrement = JSFunction.fromThisJSCons((JSObject data) -> {
//            try {
//                System.out.println("Keys: " + data.keys());
////                int current = JSValue.checkedCoerce(data.get("parentCount"), Integer.class);
////                int decremented = current - 1;
////                data.set("parentCount", decremented);
//            } catch (Exception e) {
//                System.out.println("Exception caught!!!");
//                e.printStackTrace();
//            }
//        });
//        public JSFunction decrement = JSFunction.fromThisJSCons((JSObject ctx) -> {
//            try {
//                JSObject countRef = (JSObject) ctx.get("parentCount");
//                int current = JSValue.checkedCoerce(countRef.get("value"), Integer.class);
//                countRef.set("value", current - 1);
//            } catch (Exception e) {
//                System.out.println("Exception caught!");
//                e.printStackTrace();
//            }
//        });

        public JSFunction decrement = JSFunction.fromThisJSCons((JSObject ctx) -> {
            Object maybeCount = ctx.get("count");
            System.out.println("Data keys: " + ctx.keys());
            if (maybeCount instanceof JSObject countRef) {
                int current = JSValue.checkedCoerce(countRef.get("_rawValue"), Integer.class);
                countRef.set("_rawValue", current - 1);
            } else {
                System.out.println("Injected 'count' is missing or not a JSObject: " + maybeCount.getClass().getName());
            }
        });

    }
//    }

    /**
     * Registers grandchild component.
     */
    private static class Components extends JSObject {

        public Component injectedMessageComponent = new InjectedMessageComponent();
    }

    /**
     * Props received from parent component.
     */
    public static class Props extends JSObject {

        public JSString parentMessage;
//        public JSObject parentCount;
    }
}
