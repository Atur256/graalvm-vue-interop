package io.github.atur256.vuewebimageinterop.examples.composition.globalcomponentplugin;

import io.github.atur256.vuewebimageinterop.api.Component;
import io.github.atur256.webimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;


/**
 * ChildComponent is a globally registered Vue component.
 * <p>
 * Demonstrates:
 * <ul>
 *   <li>Receiving props from parent</li>
 *   <li>Emitting events to communicate upward</li>
 *   <li>Rendering received state in the template</li>
 * </ul>
 */
public class ChildComponent extends Component {

    public ChildComponent() {

        // Vue template: displays received message and emits event on button click
        this.template = JSString.of("""
                <div>
                  <h2>Child Component:</h2>
                  <p>Child received: {{ messageFromParent }}</p>
                  <button @click="emitMessageToParent">Send Event to Parent</button>
                </div>
                """);

        // Declare props received from the parent component
        this.props = new Props();

        // Register Vue method bindings for event emission
        this.methods = new Methods();
    }

    /**
     * Props defines the input data passed from the parent component.
     */
    private static class Props extends JSObject {

        public JSString messageFromParent;
    }

    /**
     * Methods defines Vue event handlers.
     * Emits a custom event with a message payload.
     */
    private static class Methods extends JSObject {

        public JSFunction emitMessageToParent = JSFunction.fromBody("this.$emit('childEvent', 'Hello from Child!');");
    }
}
