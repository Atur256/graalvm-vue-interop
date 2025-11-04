package io.github.atur256.vuewebimageinterop.examples.basics.counter;

import io.github.atur256.vuewebimageinterop.api.Component;
import io.github.atur256.webimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.*;


/**
 * RootComponent is the root Vue component for this GraalVM-based counter example.
 * <p>
 * Demonstrates:
 * <ul>
 *   <li>Reactive state via {@code data()}</li>
 *   <li>Computed property for dynamic status display</li>
 *   <li>Event handling for incrementing and decrementing a counter</li>
 * </ul>
 */
public class RootComponent extends Component {

    public RootComponent() {
        // Component name used in Vue DevTools
        this.name = JSString.of("RootComponent");

        // Vue template: displays title, count, status, and buttons to modify count
        this.template = JSString.of("""
                    <div class="app">
                        <h1>{{ title }}</h1>
                        <p>Count: {{ count }}</p>
                        <p>Status: {{ status }}</p>
                        <button @click="increment">Increment</button>
                        <button @click="decrement">Decrement</button>
                    </div>
                """);

        // Bind Vue methods
        this.methods = new Methods();

        // Register computed properties
        this.computed = new Computed();
    }

    /**
     * Provides reactive state for this component:
     * <ul>
     *   <li>{@code title} – static heading text</li>
     *   <li>{@code count} – numeric counter value</li>
     * </ul>
     */
    public JSObject data() {
        return new Data();
    }

    /**
     * Reactive state model for the counter component.
     */
    private static class Data extends JSObject {

        // Static title displayed at the top
        public String title = "Counter App";

        // Reactive counter value
        public JSNumber count = JSNumber.of(0);
    }

    /**
     * Vue method bindings for increment/decrement actions.
     * <p>
     * Methods receive the reactive {@code data} object as input.
     */
    private static class Methods extends JSObject {

        // Increments the counter by 1
        public JSFunction increment = JSFunction.fromThisCons((JSObject data) -> {
            int current = JSValue.checkedCoerce(data.get("count"), Integer.class);
            data.set("count", current + 1);
        });

        // Decrements the counter by 1
        public JSFunction decrement = JSFunction.fromThisCons((JSObject data) -> {
            int current = JSValue.checkedCoerce(data.get("count"), Integer.class);
            int decremented = current - 1;
            data.set("count", JSNumber.of(decremented));
        });
    }

    /**
     * Computed properties derived from reactive state.
     * <p>
     * Includes:
     * <ul>
     *   <li>{@code status} – derived label based on {@code count} value</li>
     * </ul>
     */
    private static class Computed extends JSObject {

        // Computes status label based on count value
        public JSFunction status = JSFunction.fromThisFunc((JSObject data) -> {
            int value = JSValue.checkedCoerce(data.get("count"), Integer.class);
            return JSString.of(value > 4 ? "High" : (value < 0 ? "Minus" : "Low"));
        });
    }
}
