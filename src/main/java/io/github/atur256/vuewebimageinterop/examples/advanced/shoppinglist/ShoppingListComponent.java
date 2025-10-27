package io.github.atur256.vuewebimageinterop.examples.advanced.shoppinglist;

import io.github.atur256.vuewebimageinterop.api.Component;
import io.github.atur256.webimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.JSNumber;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;


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
        public JSFunction emitMessage = JSFunction.fromArgs("item","this.$emit('remove', item.id);");
    }

    /**
     * Props defines the input data passed from the parent component.
     */
    public static class Props extends JSObject {

        public JSNumber index;
        public JSObject item;
    }
}
