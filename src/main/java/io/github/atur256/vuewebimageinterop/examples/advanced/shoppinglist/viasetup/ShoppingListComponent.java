package io.github.atur256.vuewebimageinterop.examples.advanced.shoppinglist.viasetup;

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

        // Declare props received from parent
        this.props = new Props();
    }

    /**
     * Composition API setup function.
     * <p>
     * Returns a {@link JSObject} containing methods exposed to the template.
     * <p>
     * Includes:
     * <ul>
     *   <li>{@code emitMessage()} – emits a {@code remove} event with the item's ID</li>
     * </ul>
     */
    @Override
    public JSObject setup() {
        return new Setup();
    }

    /**
     * Reactive bindings exposed by the {@code setup()} function.
     * <p>
     * Includes:
     * <ul>
     *   <li>{@code emitMessage()} – emits a {@code remove} event with the item's ID</li>
     * </ul>
     */
    public static class Setup extends JSObject {

        // Emits a 'remove' event with the item's ID
        // Note: must be written entirely in JS due to a bug with GraalVM and Vue — `this` does not get passed correctly.
        public JSFunction emitMessage = JSFunction.fromArgs("item", "this.$emit('remove', item.id);");
    }

    /**
     * Props defines the input data passed from the parent component.
     * <p>
     * Includes:
     * <ul>
     *   <li>{@code index} – item position in the list</li>
     *   <li>{@code item} – item object with {@code id} and {@code text}</li>
     * </ul>
     */
    public static class Props extends JSObject {

        public JSNumber index;
        public JSObject item;
    }
}
