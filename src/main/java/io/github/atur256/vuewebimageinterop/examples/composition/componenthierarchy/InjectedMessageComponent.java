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
                    <p>Received Message via inject: {{ message }} ({{ count }})</p>
                    <button @click="sendMessage">Send Message to Parent</button>
                </div>
                """);

        // Declare injected keys expected from ancestor
        this.inject = JSArray.of("message", "count");

        // Vue method bindings
        this.methods = new Methods();
    }

    /**
     * Vue method bindings for event emission.
     */
    private static class Methods extends JSObject {

        // Note: must be written entirely in JS due to GraalVM limitations — `this` cannot be accessed from Java lambdas.
        public JSFunction sendMessage = JSFunction.fromBody("this.$emit('childEvent', 'Hello from Grandchild!');");
    }
}
