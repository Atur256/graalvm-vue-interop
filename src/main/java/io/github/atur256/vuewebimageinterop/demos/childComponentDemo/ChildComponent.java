package io.github.atur256.vuewebimageinterop.demos.childComponentDemo;

import io.github.atur256.vuewebimageinterop.reworkedClasses.Component;
import io.github.atur256.webimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;


public class ChildComponent extends Component {

    public ChildComponent() {
        this.template = JSString.of("""
                <div class="child">
                    <h2>Child Component</h2>
                    <p>Received: {{ parentMessage }}</p>
                    <button @click="sendMessage">Send Message to Parent</button>
                  </div>
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

        // TODO: Replace this by a java function
        public JSFunction sendMessage = JSFunction.fromBody("this.$emit('childEvent', 'Hello Parent! (from Child)');");
    }

    public static class Props extends JSObject {

        public JSString parentMessage;
    }
}
