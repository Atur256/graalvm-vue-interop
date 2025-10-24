package io.github.atur256.vuewebimageinterop.examples.reactivity.watcheffects;

import io.github.atur256.vuewebimageinterop.api.Component;
import io.github.atur256.vuewebimageinterop.api.Vue;
import io.github.atur256.vuewebimageinterop.api.VueRef;
import io.github.atur256.webimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;


/**
 * RootComponent is the root Vue component for this GraalVM-based reactivity example.
 * <p>
 * Demonstrates:
 * <ul>
 *   <li>Reactive state via {@code Vue.ref}</li>
 *   <li>Computed property via {@code Vue.computedRef}</li>
 *   <li>Reactive side effects via {@code Vue.watch} and {@code Vue.watchEffect}</li>
 * </ul>
 */
public class RootComponent extends Component {

    /**
     * Reactive counter value.
     */
    public VueRef count = Vue.ref(0);

    /**
     * Computed property that doubles the current count.
     */
    public VueRef doubleCount = Vue.computedRef(JSFunction.fromSupplier(() -> {
        int c = count.get(Integer.class);
        return c * 2;
    }));

    public RootComponent() {

        // Vue template: displays count and computed doubleCount, with increment button
        this.template = JSString.of("""
                    <div class="app">
                        <h2>Watch & WatchEffect Demo</h2>
                        <p>Count: {{ count }}</p>
                        <p>Double Count (computed): {{ doubleCount }}</p>
                        <button @click="increment">Increment</button>
                    </div>
                """);

        // Vue method bindings: increment logic
        this.methods = new Methods(count);

        // Watch a specific reactive property (count)
        Vue.watch(count.getRef(), JSFunction.fromRunnable(() -> {
            System.out.println("[watch] Count changed to: " + count.get(Integer.class));
        }));

        // WatchEffect reacts whenever any reactive used inside the function changes
        Vue.watchEffect(JSFunction.fromRunnable(() -> {
            int val = count.get(Integer.class);
            int doubled = doubleCount.get(Integer.class);
            System.out.println("[watchEffect] Count=" + val + ", DoubleCount=" + doubled);
        }));
    }

    /**
     * Overrides Component.data() to expose reactive state:
     * - count: ref value
     * - doubleCount: computed ref
     */
    public JSObject data() {
        return new Data(count, doubleCount);
    }

    /**
     * Data defines the reactive state model for this component.
     * It is returned by the overridden data() method.
     */
    private static class Data extends JSObject {

        public JSObject count;
        public JSObject doubleCount;

        public Data(VueRef count, VueRef doubleCount) {
            this.count = count.getRef();
            this.doubleCount = doubleCount.getRef();
        }
    }

    /**
     * Methods defines Vue event handlers.
     * These are bound to template actions via @click.
     */
    private static class Methods extends JSObject {

        public JSFunction increment;

        public Methods(VueRef count) {
            increment = JSFunction.fromRunnable(() -> {
                int current = count.get(Integer.class);
                count.set(current + 1);
            });
        }
    }
}
