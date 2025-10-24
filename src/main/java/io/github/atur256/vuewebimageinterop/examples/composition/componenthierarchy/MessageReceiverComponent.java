package io.github.atur256.vuewebimageinterop.examples.composition.componenthierarchy;

import io.github.atur256.vuewebimageinterop.api.Component;
import io.github.atur256.vuewebimageinterop.api.VueApp;
import io.github.atur256.webimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.JSNumber;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;


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
                    <p>Received Message via props: {{ parentMessage }} ({{ parentCount }}) </p>
                    <button @click="decrement">Decrement</button>
                    <injectedMessageComponent @childEvent="updateParentMessage"/>
                  </div>
                """);

        // Vue method bindings: decrement and updateParentMessage logic
        this.methods = new Methods();

        // Register grandchild component
        this.components = new Components();

        // Declare props received from parent
        this.props = new Props();
    }

    /**
     * Methods defines Vue event handlers.
     * Includes logic to emit events and update shared state.
     */
    private static class Methods extends JSObject {

        public JSFunction updateParentMessage = JSFunction.fromConsumer((JSString messageVal) -> {
            String msg = messageVal.asString();
            VueApp.setValue("grandMessage", msg);
            System.out.println("Passed message: " + msg);
        });

        public JSFunction decrement = JSFunction.fromRunnable(() -> {
            int current = VueApp.getValue("count", Integer.class);
            int decremented = current - 1;
            VueApp.setValue("count", decremented);
        });
    }

    /**
     * Components registers child components used in the template.
     */
    private static class Components extends JSObject {

        public Component injectedMessageComponent = new InjectedMessageComponent();
    }

    /**
     * Props defines the input data passed from the parent component.
     */
    public static class Props extends JSObject {

        public JSString parentMessage;
        public JSNumber parentCount;
    }
}
