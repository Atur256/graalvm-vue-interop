package io.github.atur256.vuewebimageinterop.examples.reactivity.watcheffects;

import io.github.atur256.vuewebimageinterop.api.Component;
import io.github.atur256.webimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.JSBoolean;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;
import org.graalvm.webimage.api.JSValue;


/**
 * RootComponent — Vue component demonstrating reactivity, watch, and computed properties
 * using GraalVM Java interop.
 * <p>
 * Demonstrates:
 * <ul>
 *   <li>Reactive state via {@code data()}</li>
 *   <li>Watchers for tracking changes to reactive fields</li>
 *   <li>Deep watching of nested objects</li>
 *   <li>Computed properties derived from reactive state</li>
 *   <li>Methods that mutate reactive state via user interaction</li>
 * </ul>
 */
public class RootComponent extends Component {

    public RootComponent() {
        // Vue template: displays reactive fields, computed greeting, and buttons to trigger mutations
        this.template = JSString.of("""
                    <div class="app">
                        <h2>Watch & WatchEffect Demo</h2>
                        <p>Count: {{ count }}</p>
                        <p>Status: {{ status }}</p>
                        <p>Profile Name: {{ user.profile.name }}</p>
                        <p>{{ greeting }}</p>
                        <button @click="increment">Increment</button>
                        <button @click="toggleStatus">Toggle Status</button>
                        <button @click="toggleProfileName">Rename Profile</button>
                    </div>
                """);

        // Vue method bindings for button actions
        this.methods = new Methods();

        // Vue watchers for reactive state changes
        this.watch = new Watchers();

        // Vue computed properties
        this.computed = new Computed();
    }

    /**
     * Reactive state exposed to the template.
     */
    @Override
    public JSObject data() {
        return new Data();
    }

    /**
     * Reactive data model for the component.
     */
    private static class Data extends JSObject {

        // Primitive reactive fields
        public int count = 0;
        public String status = "idle";

        // Nested reactive object
        public JSObject user = new JSObject() {
            public JSObject profile = new JSObject() {
                public String name = "Alice";
            };
        };
    }

    /**
     * Vue watchers for reactive state changes.
     */
    private static class Watchers extends JSObject {

        // Watcher for primitive field: count
        public JSFunction count = JSFunction.fromBiCons((Object newVal, Object oldVal) -> {
            int newCount = JSValue.checkedCoerce(newVal, Integer.class);
            int oldCount = JSValue.checkedCoerce(oldVal, Integer.class);
            System.out.println("[watch] Count changed from " + oldCount + " to " + newCount);
        });

        // Watcher for primitive field: status
        public JSFunction status = JSFunction.fromBiCons((Object newVal, Object oldVal) -> {
            String newStatus = JSValue.checkedCoerce(newVal, String.class);
            String oldStatus = JSValue.checkedCoerce(oldVal, String.class);
            System.out.println("[watch] Status changed from '" + oldStatus + "' to '" + newStatus + "'");
        });

        // Deep watcher for nested object: user.profile.name
        public JSObject user = new JSObject() {{
            /*
             * Important:
             * - This watcher is defined as an anonymous JSObject instead of a named Java class.
             * - Defining it as a named class causes Vue's deep reactivity system to recursively traverse
             *   the proxy structure, which can lead to stack overflow or "too much recursion" errors.
             * - Using an inline JSObject avoids that issue and keeps the watcher safe.
             */
            set("handler", JSFunction.fromCons((JSObject user) -> {
                JSObject profile = (JSObject) user.get("profile");
                String name = JSValue.checkedCoerce(profile.get("name"), String.class);
                System.out.println("[watch] user.profile.name changed to '" + name + "'");
            }));
            set("deep", JSBoolean.of(true));
        }};
    }

    /**
     * Vue method bindings for template actions.
     */
    private static class Methods extends JSObject {

        // Increments the count value
        public JSFunction increment = JSFunction.fromThisCons((JSObject data) -> {
            int current = JSValue.checkedCoerce(data.get("count"), Integer.class);
            data.set("count", current + 1);
        });

        // Toggles the status between "idle" and "active"
        public JSFunction toggleStatus = JSFunction.fromThisCons((JSObject data) -> {
            String current = JSValue.checkedCoerce(data.get("status"), String.class);
            String next = current.equals("idle") ? "active" : "idle";
            data.set("status", next);
        });

        // Toggles the profile name between "Alice" and "Bob"
        public JSFunction toggleProfileName = JSFunction.fromThisCons((JSObject data) -> {
            JSObject user = (JSObject) data.get("user");
            JSObject profile = (JSObject) user.get("profile");
            String current = JSValue.checkedCoerce(profile.get("name"), String.class);
            String next = current.equals("Alice") ? "Bob" : "Alice";
            profile.set("name", JSString.of(next));
        });
    }

    /**
     * Vue computed properties.
     */
    private static class Computed extends JSObject {

        // Returns a greeting message based on the current profile name
        public JSFunction greeting = JSFunction.fromThisFunc((JSObject data) -> {
            JSObject user = (JSObject) data.get("user");
            JSObject profile = (JSObject) user.get("profile");
            String name = JSValue.checkedCoerce(profile.get("name"), String.class);
            return JSString.of("Hello, " + name + "!");
        });
    }
}
