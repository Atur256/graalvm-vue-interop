package io.github.atur256.vuewebimageinterop.examples.reactivity.lifecycle;

import io.github.atur256.vuewebimageinterop.api.Component;
import io.github.atur256.vuewebimageinterop.api.Vue;
import io.github.atur256.vuewebimageinterop.api.VueApp;
import io.github.atur256.webimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;


/**
 * LifecycleComponent is the root Vue component for this GraalVM-based lifecycle hooks example.
 * <p>
 * Demonstrates:
 * <ul>
 *   <li>{@code onMounted} hook: logs when component is mounted</li>
 *   <li>{@code onUnmounted} hook: logs when component is unmounted</li>
 *   <li>Manual unmounting via button click</li>
 * </ul>
 */
public class LifecycleComponent extends Component {

    /**
     * Reference to VueApp to allow manual unmounting.
     */
    public VueApp app;

    public LifecycleComponent() {
        // Vue template: info and unmount button
        this.template = JSString.of("""
                <div class="app">
                    <h2>Vue Lifecycle Hooks Example</h2>
                    <p>Open console to see mount/unmount messages.</p>
                    <button @click="unmountApp">Unmount App</button>
                </div>
                """);

        // Bind methods
        this.methods = new Methods(this);
    }

    /**
     * Store VueApp instance for unmounting.
     */
    public void setApp(VueApp app) {
        this.app = app;
    }

    /**
     * Registers lifecycle hooks and returns empty reactive state.
     */
    public JSObject data() {
        Vue.onMounted(JSFunction.fromRunnable(() -> System.out.println("Component mounted!")));
        Vue.onUnmounted(JSFunction.fromRunnable(() -> System.out.println("Component unmounted!")));
        return JSObject.create();
    }

    /**
     * Vue methods for template actions.
     */
    private static class Methods extends JSObject {

        public JSFunction unmountApp;

        public Methods(LifecycleComponent outer) {
            unmountApp = JSFunction.fromRunnable(() -> {
                System.out.println("Unmount button clicked!");
                if(outer.app != null) outer.app.unmount();
            });
        }
    }
}
