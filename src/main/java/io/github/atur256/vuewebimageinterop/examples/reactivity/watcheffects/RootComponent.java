package io.github.atur256.vuewebimageinterop.examples.reactivity.watcheffects;

import io.github.atur256.vuewebimageinterop.api.Component;
import io.github.atur256.vuewebimageinterop.api.Vue;
import io.github.atur256.vuewebimageinterop.api.VueRef;
import io.github.atur256.webimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;


/**
 * RootComponent is the root Vue component for this GraalVM-based Watch & WatchEffect example.
 * <p>
 * Demonstrates:
 * <ul>
 *   <li>Reactive state using {@code Vue.ref}</li>
 *   <li>Computed property via {@code Vue.computedRef}</li>
 *   <li>Side effects tracking using {@code Vue.watch} and {@code Vue.watchEffect}</li>
 * </ul>
 */
public class RootComponent extends Component {

    /**
     * Reactive counter value.
     */
    public VueRef count = Vue.ref(0);

    /**
     * Computed property that doubles the counter.
     */
    public VueRef doubleCount = Vue.computedRef(JSFunction.fromSupplier(() -> count.get(Integer.class) * 2));

    public RootComponent() {
        // Vue template: displays counter, doubleCount, and increment button
        this.template = JSString.of("""
                <div class="app">
                    <h2>Watch & WatchEffect Demo</h2>
                    <p>Count: {{ count }}</p>
                    <p>Double Count (computed): {{ doubleCount }}</p>
                    <button @click="increment">Increment</button>
                </div>
                """);

        // Bind increment method
        this.methods = new Methods(count);

        // Watch specific reactive property
        Vue.watch(count.getRef(), JSFunction.fromRunnable(() -> {
            System.out.println("[watch] Count changed to: " + count.get(Integer.class));
        }));

        // WatchEffect reacts to any reactive used inside
        Vue.watchEffect(JSFunction.fromRunnable(() -> {
            int val = count.get(Integer.class);
            int doubled = doubleCount.get(Integer.class);
            System.out.println("[watchEffect] Count=" + val + ", DoubleCount=" + doubled);
        }));
    }

    /**
     * Exposes reactive state for Vue template.
     */
    public JSObject data() {
        return new Data(count, doubleCount);
    }

    private static class Data extends JSObject {

        public JSObject count;
        public JSObject doubleCount;

        public Data(VueRef count, VueRef doubleCount) {
            this.count = count.getRef();
            this.doubleCount = doubleCount.getRef();
        }
    }

    /**
     * Vue methods for incrementing the counter.
     */
    private static class Methods extends JSObject {

        public JSFunction increment;

        public Methods(VueRef count) {
            this.increment = JSFunction.fromRunnable(() -> {
                int current = count.get(Integer.class);
                count.set(current + 1);
            });
        }
    }
}
