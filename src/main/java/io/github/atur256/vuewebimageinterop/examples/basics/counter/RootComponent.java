package io.github.atur256.vuewebimageinterop.examples.basics.counter;

import io.github.atur256.vuewebimageinterop.api.Component;
import io.github.atur256.vuewebimageinterop.api.VueApp;
import io.github.atur256.webimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.JSNumber;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;


/**
 * RootComponent is the root Vue component for this GraalVM-based counter example.
 * <p>
 * It overrides key fields from the abstract {@code Component} class: {@code template}, {@code data}, and {@code methods}.
 */
public class RootComponent extends Component {

    public RootComponent() {

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

        // Vue method bindings: increment/decrement handlers
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
     * It is returned by the overridden {@code data()} method.
     */
    private static class Data extends JSObject {

        public JSNumber count = JSNumber.of(0);
        public JSString status = JSString.of("Low");
    }

    /**
     * Methods defines Vue event handlers.
     * These are bound to template actions via {@code @click}.
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

            VueApp.setValue("status", value > 4 ? "High" : (value < 0 ? "Minus" : "Low"));
        }
    }
}
