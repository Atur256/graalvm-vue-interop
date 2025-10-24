package io.github.atur256.vuewebimageinterop.examples.reactivity.lifecycle;

import io.github.atur256.vuewebimageinterop.api.Component;
import io.github.atur256.vuewebimageinterop.api.Vue;
import io.github.atur256.vuewebimageinterop.api.VueApp;
import io.github.atur256.webimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;


/**
 * LifecycleComponent is the root Vue component for this GraalVM-based lifecycle example.
 * <p>
 * Demonstrates:
 * <ul>
 *   <li>{@code onMounted} hook: logs when the component is mounted</li>
 *   <li>{@code onUnmounted} hook: logs when the component is unmounted</li>
 *   <li>Manual unmounting via button click</li>
 * </ul>
 */
public class LifecycleComponent extends Component {

    /**
     * Reference to the VueApp instance, used to trigger unmounting.
     */
    public VueApp app;

    public LifecycleComponent() {

        // Vue template: displays lifecycle info and unmount button
        this.template = JSString.of("""
                    <div class="app">
                        <h2>Vue Lifecycle Hooks Example</h2>
                        <p>Open console to see mount/unmount messages.</p>
                        <button @click="unmountApp">Unmount App</button>
                    </div>
                """);

        // Vue method bindings: unmount logic
        this.methods = new Methods(this);
    }

    /**
     * Stores the VueApp instance so it can be unmounted from within the component.
     */
    public void setApp(VueApp app) {
        this.app = app;
    }

    /**
     * Overrides Component.data() to register lifecycle hooks:
     * - Logs to console when mounted
     * - Logs to console when unmounted
     */
    public JSObject data() {

        // Register onMounted hook
        Vue.onMounted(JSFunction.fromRunnable(() -> {
            System.out.println("Component mounted!");
        }));

        // Register onUnmounted hook
        Vue.onUnmounted(JSFunction.fromRunnable(() -> {
            System.out.println("Component unmounted!");
        }));

        return JSObject.create();
    }

    /**
     * Methods defines Vue event handlers.
     * Includes logic to unmount the app when the button is clicked.
     */
    private static class Methods extends JSObject {

        public JSFunction unmountApp;

        public Methods(LifecycleComponent outer) {

            unmountApp = JSFunction.fromRunnable(() -> {
                System.out.println("Unmount button clicked!");
                if(outer.app != null) {
                    outer.app.unmount();
                }
            });
        }
    }
}
