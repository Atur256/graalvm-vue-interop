package io.github.atur256.vuewebimageinterop.demos.shoppinglistdemo;

import io.github.atur256.vuewebimageinterop.reworkedClasses.Component;
import org.graalvm.webimage.api.JSNumber;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;


public class ShoppingListComponent extends Component {

    public ShoppingListComponent() {
        this.template = JSString.of("""
                <li>
                    {{ index + 1 }}. {{ todo.text }}
                    <span style="cursor:pointer; margin-left:10px;" @click="$emit('remove', todo.id)">&#128465;&#65039;</span>
                </li>
                """);

        this.methods = new Methods();
        this.props = new Props();
    }

    public JSObject data() {
        return new Data();
    }

    private static class Data extends JSObject {

    }

    private static class Methods extends JSObject {

    }

    public static class Props extends JSObject {

        public JSNumber index;
        public JSObject todo;
    }
}
