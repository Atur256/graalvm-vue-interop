package io.github.atur256.vuewebimageinterop.examples.composition.globalcomponentplugin;

import io.github.atur256.vuewebimageinterop.api.Component;
import io.github.atur256.vuewebimageinterop.api.VueApp;
import io.github.atur256.webimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;


/**
 * AppRootComponent is the root Vue component for this GraalVM-based plugin example.
 * <p>
 * Demonstrates:
 * <ul>
 *   <li>Accessing global properties via {@code $root.globalMessage}</li>
 *   <li>Passing props to a child component</li>
 *   <li>Receiving events from child via {@code @childEvent}</li>
 * </ul>
 */
public class AppRootComponent extends Component {

    public AppRootComponent() {

        // Vue template: displays global and local messages, renders child component
        this.template = JSString.of("""
                <div>
                  <h2>VueApp with Component & Plugin</h2>
                  <p>Global Message: {{ $root.globalMessage }}</p>
                  <p>Parent Message: {{ parentMessage }}</p>
                  <p>Message from Child: {{ childResponseMessage }}</p>
                  <child-component :messageFromParent="parentMessage" @childEvent="onChildMessage"/>
                </div>
                """);

        // Vue method bindings: onChildMessage logic
        this.methods = new Methods();
    }

    /**
     * Overrides Component.data() to expose reactive state:
     * - parentMessage: string passed to the child component
     * - childResponseMessage: string passed from the child component
     */
    public JSObject data() {
        return new Data();
    }

    /**
     * Data defines the reactive state model for this component.
     */
    private static class Data extends JSObject {

        public JSString parentMessage = JSString.of("Hello from Parent!");
        public JSString childResponseMessage = JSString.of("-");
    }

    /**
     * Methods defines Vue event handlers.
     * These are bound to template actions via @childEvent.
     */
    private static class Methods extends JSObject {

        public JSFunction onChildMessage = JSFunction.fromConsumer((JSString msg) -> {
            System.out.println("Parent received event: " + msg.asString());
            VueApp.setValue("childResponseMessage", msg.asString());
        });
    }
}
