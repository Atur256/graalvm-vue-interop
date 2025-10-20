package io.github.atur256.vuewebimageinterop.demos.watchdemo;

import io.github.atur256.vuewebimageinterop.reworkedClasses.*;
import io.github.atur256.webimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;


public class DemoComponent extends Component {

    public VueRef count = Vue.ref(0);
    public VueRef doubleCount = Vue.computedRef(JSFunction.fromSupplier(() -> {
        int c = count.get(Integer.class);
        return c * 2;
    }));

    public DemoComponent() {

        this.template = JSString.of("""
                    <div class="app">
                        <h2>Watch & WatchEffect Demo</h2>
                        <p>Count: {{ count }}</p>
                        <p>Double Count (computed): {{ doubleCount }}</p>
                        <button @click="increment">Increment</button>
                    </div>
                """);

        this.methods = new Methods(count);

        // Watch a specific reactive property (count)
        Vue.watch(count.unwrap(), JSFunction.fromRunnable(() -> {
            System.out.println("[watch] Count changed to: " + count.get(Integer.class));
        }));

        // WatchEffect reacts whenever any reactive used inside the function changes
        Vue.watchEffect(JSFunction.fromRunnable(() -> {
            int val = count.get(Integer.class);
            int doubled = doubleCount.get(Integer.class);
            System.out.println("[watchEffect] Count=" + val + ", DoubleCount=" + doubled);
        }));

    }

    public JSObject data() {
        return new Data(count, doubleCount);
    }

    private static class Data extends JSObject {

        public JSObject count;
        public JSObject doubleCount;

        public Data(VueRef count, VueRef doubleCount) {
            this.count = count.unwrap();
            this.doubleCount = doubleCount.unwrap();
        }
    }

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
