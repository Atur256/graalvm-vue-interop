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
 * Demonstrates:
 * <ul>
 *   <li>Reactive state via {@code data()}</li>
 *   <li>Event handling via {@code methods}</li>
 * </ul>
 */
public class RootComponent extends Component {

    public RootComponent() {
        // Vue template: displays count, status, and increment/decrement buttons
        this.template = JSString.of("""
                    <div class="app">
                        <h1>Counter App</h1>
                        <p>Count: {{ count }}</p>
                        <p>Status: {{ status }}</p>
                        <button @click="increment">Increment</button>
                        <button @click="decrement">Decrement</button>
                    </div>
                """);

        // Bind Vue methods
        this.methods = new Methods();
    }

    /**
     * Provides reactive state for this component:
     * <ul>
     *   <li>{@code count} – numeric counter</li>
     *   <li>{@code status} – label based on current count</li>
     * </ul>
     */
    public JSObject data() {
        return new Data();
    }

    /**
     * Reactive state model for the counter component.
     */
    private static class Data extends JSObject {

        public JSNumber count = JSNumber.of(0);
        public JSString status = JSString.of("Low");
    }

    /**
     * Vue methods for incrementing/decrementing the counter.
     */
    private static class Methods extends JSObject {

        public JSFunction increment = JSFunction.fromRunnable(() -> {
            int current = VueApp.getValue("count", Integer.class);
            int incremented = current + 1;
            VueApp.setValue("count", incremented);
            updateStatus(incremented);
        });

        public JSFunction decrement = JSFunction.fromRunnable(() -> {
            int current = VueApp.getValue("count", Integer.class);
            int decremented = current - 1;
            VueApp.setValue("count", decremented);
            updateStatus(decremented);
        });

        /**
         * Updates the status label based on current count.
         */
        private static void updateStatus(int value) {
            VueApp.setValue("status", value > 4 ? "High" : (value < 0 ? "Minus" : "Low"));
        }
    }
}
