package io.github.atur256.vuewebimageinterop.examples.reactivity.lifecycle;

import io.github.atur256.vuewebimageinterop.api.Component;
import io.github.atur256.vuewebimageinterop.api.VueApp;
import io.github.atur256.webimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.*;


/**
 * LifecycleComponent is the root Vue component for this GraalVM-based lifecycle hooks example.
 * <p>
 * Demonstrates:
 * <ul>
 *   <li>All Vue lifecycle hooks via Options API fields</li>
 *   <li>Manual unmounting via button click</li>
 *   <li>Reactive update via button click</li>
 * </ul>
 */
public class LifecycleComponent extends Component {

    public VueApp app;

    public LifecycleComponent() {
        this.name = JSString.of("LifecycleComponent");

        this.template = JSString.of("""
                <div class="app">
                    <h2>Vue Lifecycle Hooks Example</h2>
                    <p>Open console to see lifecycle messages.</p>
                    <p>Message: {{ message }}</p>
                    <button @click="updateMessage">Update Message</button>
                    <button @click="unmountApp">Unmount App</button>
                </div>
                """);

        this.methods = new Methods(this);

        this.beforeCreate = JSFunction.fromRun(() ->
                System.out.println("[Lifecycle] beforeCreate → Component instance is being initialized")
        );

        this.created = JSFunction.fromRun(() ->
                System.out.println("[Lifecycle] created → Reactive data and events are now set up")
        );

        this.beforeMount = JSFunction.fromRun(() ->
                System.out.println("[Lifecycle] beforeMount → Component is about to be mounted to the DOM")
        );

        this.mounted = JSFunction.fromRun(() ->
                System.out.println("[Lifecycle] mounted → Component is now mounted and visible")
        );

        this.beforeUpdate = JSFunction.fromRun(() ->
                System.out.println("[Lifecycle] beforeUpdate → Reactive data changed, DOM update is imminent")
        );

        this.updated = JSFunction.fromRun(() ->
                System.out.println("[Lifecycle] updated → DOM has been updated with new reactive data")
        );

        this.beforeUnmount = JSFunction.fromRun(() ->
                System.out.println("[Lifecycle] beforeUnmount → Component is about to be removed from the DOM")
        );

        this.unmounted = JSFunction.fromRun(() ->
                System.out.println("[Lifecycle] unmounted → Component has been removed and cleaned up")
        );

    }

    public void setApp(VueApp app) {
        this.app = app;
    }

    @Override
    public JSObject data() {
        return new Data();
    }

    private static class Data extends JSObject {

        public JSString message = JSString.of("Initial message");
    }

    private static class Methods extends JSObject {

        public JSFunction unmountApp;
        public JSFunction updateMessage;

        public Methods(LifecycleComponent outer) {
            unmountApp = JSFunction.fromRun(() -> {
                if(outer.app != null) outer.app.unmount();
            });

            updateMessage = JSFunction.fromThisJSCons((JSObject data) -> {
                String current = JSValue.checkedCoerce(data.get("message"), String.class);
                String updated = current + " updated";
                data.set("message", JSString.of(updated));
            });
        }
    }
}
