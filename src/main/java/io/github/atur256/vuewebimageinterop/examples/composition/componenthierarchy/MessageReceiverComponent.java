package io.github.atur256.vuewebimageinterop.examples.composition.componenthierarchy;

import io.github.atur256.vuewebimageinterop.api.Component;
import io.github.atur256.webimageinterop.builtin.JSArray;
import io.github.atur256.webimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;


/**
 * MessageReceiverComponent is a child Vue component in the hierarchy example.
 * <p>
 * Demonstrates:
 * <ul>
 *   <li>Receiving props from the parent component</li>
 *   <li>Emitting events to update parent state</li>
 *   <li>Rendering a grandchild component</li>
 *   <li>Using Vue's provide/inject mechanism to access shared reactive state</li>
 * </ul>
 */
public class MessageReceiverComponent extends Component {

    public MessageReceiverComponent() {
        // Vue template: displays props, injected state, and renders the grandchild component
        this.template = JSString.of("""
                <div class="child">
                    <h2>Child Component: Prop Receiver</h2>
                    <p>Received Message via props: {{ parentMessage }} ({{ count }})</p>
                    <button @click="decrement">Decrement</button>
                    <injectedMessageComponent @childEvent="forwardMessageToParent"/>
                </div>
                """);

        // Vue method bindings for template actions
        this.methods = new Methods();

        // Registers grandchild component used in the template
        this.components = new Components();

        // Props received from the parent component
        this.props = new Props();

        // Inject reactive values from parent using Vue's provide/inject API
        this.inject = JSArray.of("count");

        // Declares custom events this component may emit
        // Enables validation and tooling support for upward communication
        this.emits = JSArray.of("childEvent");
    }

    /**
     * Vue method bindings for decrement and message forwarding.
     */
    private static class Methods extends JSObject {

        /**
         * Forwards the message received from the grandchild to the parent.
         * The original message is passed through unchanged.
         * <p>
         * Note:
         * - Must be written entirely in JS due to a bug with GraalVM and Vue — `this` does not get passed correctly.
         * - The `event` parameter is explicitly declared to avoid `$event` scoping issues.
         */
        public JSFunction forwardMessageToParent = JSFunction.fromArgs("event", "console.log(event); this.$emit('childEvent', event);");

        /**
         * Decrements the injected count value.
         * <p>
         * Note:
         * - Must be written entirely in JS due to a bug with GraalVM and Vue — `this` does not get passed correctly.
         * - Injected values are attached directly to the component instance, not to `data()`.
         */
        public JSFunction decrement = JSFunction.fromBody("this.count = this.count - 1;");
    }

    /**
     * Registers grandchild component used in the template.
     */
    private static class Components extends JSObject {

        public Component injectedMessageComponent = new InjectedMessageComponent();
    }

    /**
     * Props received from the parent component.
     * <p>
     * Note:
     * - Props are read-only and should not be mutated directly.
     * - Used to pass contextual data from parent to child.
     */
    public static class Props extends JSObject {

        public JSString parentMessage;
    }
}
