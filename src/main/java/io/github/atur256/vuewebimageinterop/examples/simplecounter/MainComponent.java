package io.github.atur256.vuewebimageinterop.examples.simplecounter;

import io.github.atur256.vuewebimageinterop.api.Component;
import io.github.atur256.vuewebimageinterop.api.VueApp;
import io.github.atur256.webimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.JSNumber;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;


/**
 * MainComponent is the root Vue component for the Simple Counter demo.
 * <p>
 * It overrides key fields from the abstract Component class: template, data, and methods.
 * The template replicates a basic Vue counter app, rendered via Java interop.
 */
public class MainComponent extends Component {

    public MainComponent() {

        // Vue template: displays count and status, with increment/decrement buttons
        this.template = JSString.of("""
                <div class="app">
                 <h1>Counter App</h1>
                 <p>Count: {{ count }}</p>
                 <p>Status: {{ status }}</p>
                 <button @click="increment">Increment</button>
                 <button @click="decrement">Decrement</button>
                </div>
                """);

        // Vue method bindings: increment/decrement
        this.methods = new Methods();
    }

    /**
     * Overrides Component.data() to expose reactive state:
     * - count: numeric counter
     * - status: label based on count threshold
     */
    public JSObject data() {
        return new Data();
    }

    /**
     * Data defines the reactive state model for this component.
     * It is returned by the overridden data() method.
     */
    private static class Data extends JSObject {

        public JSNumber count = JSNumber.of(0);
        public JSString status = JSString.of("Low");
    }

    /**
     * Methods defines Vue event handlers.
     * These are bound to template actions via @click.
     */
    private static class Methods extends JSObject {

        // Increments the counter and updates status
        public JSFunction increment = JSFunction.fromRunnable(() -> {
            int current = VueApp.getValue("count", Integer.class);
            int incremented = current + 1;
            VueApp.setValue("count", incremented);
            updateStatus(incremented);
        });

        // Decrements the counter and updates status
        public JSFunction decrement = JSFunction.fromRunnable(() -> {
            int current = VueApp.getValue("count", Integer.class);
            int decremented = current - 1;
            VueApp.setValue("count", decremented);
            updateStatus(decremented);
        });

        // Updates the status label based on the current count
        private static void updateStatus(int value) {
            VueApp.setValue("status", value + 1 > 5 ? "High" : "Low");
        }
    }
}
