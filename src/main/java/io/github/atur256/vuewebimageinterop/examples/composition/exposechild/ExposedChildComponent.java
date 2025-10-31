package io.github.atur256.vuewebimageinterop.examples.composition.exposechild;

import io.github.atur256.vuewebimageinterop.api.Component;
import io.github.atur256.webimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;


/**
 * ExposedChildComponent is a child Vue component with an exposed method.
 * <p>
 * Demonstrates:
 * <ul>
 *   <li>Defining a method callable from the parent via ref</li>
 *   <li>Using the expose option to control what is accessible</li>
 * </ul>
 */
public class ExposedChildComponent extends Component {

    public ExposedChildComponent() {
        // Vue template: displays a button that logs a message
        this.template = JSString.of("""
                <div>
                    <p>Child Component</p>
                    <button @click="sayHello">Say Hello</button>
                </div>
                """);

        // Vue method bindings
        this.methods = new Methods();

        // Expose sayHello method to parent via template ref
        this.expose = new Expose();
    }

    /**
     * Vue method bindings for child actions.
     */
    public static class Methods extends JSObject {

        /**
         * Logs a message to the console.
         */
        public JSFunction sayHello = JSFunction.fromRun(() -> System.out.println("Hello from child!"));
    }

    /**
     * Declares which internal properties are exposed to parent via template ref.
     */
    public static class Expose extends JSObject {

        public boolean sayHello = true;
    }
}
