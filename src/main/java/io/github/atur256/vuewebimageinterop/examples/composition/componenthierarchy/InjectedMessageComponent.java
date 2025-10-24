package io.github.atur256.vuewebimageinterop.examples.composition.componenthierarchy;

import io.github.atur256.vuewebimageinterop.api.Component;
import io.github.atur256.webimageinterop.builtin.JSArray;
import io.github.atur256.webimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;


/**
 * InjectedMessageComponent is the grandchild Vue component in the hierarchy example.
 * <p>
 * Demonstrates:
 * <ul>
 *   <li>Receiving values via {@code inject} from ancestor components</li>
 *   <li>Emitting events to communicate upward</li>
 *   <li>Rendering injected state in the template</li>
 * </ul>
 */
public class InjectedMessageComponent extends Component {

    public InjectedMessageComponent() {

        // Vue template: displays injected values and emits event on button click
        this.template = JSString.of("""
                <div class="child">
                    <h2>Grandchild Component: Injected Context</h2>
                    <p>Received Message via inject: {{ message }} ({{ count }}) </p>
                    <button @click="sendMessage">Send Message to Parent</button>
                </div>
                """);

        // Vue method bindings: send message logic
        this.methods = new Methods();

        // Declare injected keys expected from ancestor
        this.inject = JSArray.of("message", "count");
    }

    /**
     * Methods defines Vue event handlers.
     * Includes logic to emit a message to the parent component.
     */
    private static class Methods extends JSObject {

        public JSFunction sendMessage = JSFunction.fromBody("this.$emit('childEvent', 'Hello from Grandchild!');");
    }
}
