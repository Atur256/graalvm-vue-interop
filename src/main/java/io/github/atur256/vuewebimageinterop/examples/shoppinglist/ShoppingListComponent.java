package io.github.atur256.vuewebimageinterop.examples.shoppinglist;

import io.github.atur256.vuewebimageinterop.api.Component;
import org.graalvm.webimage.api.JSNumber;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;


/**
 * ShoppingListComponent is a child Vue component used in the Shopping List demo.
 * <p>
 * It renders a single list item and emits a 'remove' event when the delete icon is clicked.
 * Props are passed from the parent component to display item text and index.
 */
public class ShoppingListComponent extends Component {

    public ShoppingListComponent() {

        // Vue template: renders a list item with delete icon
        this.template = JSString.of("""
                <li>
                    {{ index + 1 }}. {{ item.text }}
                    <span style="cursor:pointer; margin-left:10px;" @click="$emit('remove', item.id)">&#128465;&#65039;</span>
                </li>
                """);

        // Declare props received from parent
        this.props = new Props();
    }

    /**
     * Overrides Component.data().
     * This component does not define local reactive state.
     */
    public JSObject data() {
        return new Data();
    }

    /**
     * Empty data model — required by Component base class.
     */
    private static class Data extends JSObject {

    }

    /**
     * Props defines input data passed from the parent component.
     * - index: position in the list
     * - item: item object with id and text
     */
    public static class Props extends JSObject {

        public JSNumber index;
        public JSObject item;
    }
}
