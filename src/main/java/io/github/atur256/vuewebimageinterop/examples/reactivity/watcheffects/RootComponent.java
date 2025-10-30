package io.github.atur256.vuewebimageinterop.examples.reactivity.watcheffects;

import io.github.atur256.vuewebimageinterop.api.Component;
import io.github.atur256.webimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.*;


/**
 * RootComponent — Vue component demonstrating reactivity, watch, watchEffect,
 * and the difference between reactive and non-reactive objects.
 */
public class RootComponent extends Component {

    public RootComponent() {
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

        this.methods = new Methods();

        this.watch = new Watchers();

        this.computed = new Computed();
    }

    @Override
    public JSObject data() {
        return new Data();
    }

    private static class Data extends JSObject {

        public int count = 0;
        public String status = "idle";
        public JSObject user = new JSObject() {
            public JSObject profile = new JSObject() {
                public String name = "Alice";
            };
        };
    }

    private static class Watchers extends JSObject {

        public JSFunction count = JSFunction.fromBiCons((Object newVal, Object oldVal) -> {
            int newCount = JSValue.checkedCoerce(newVal, Integer.class);
            int oldCount = JSValue.checkedCoerce(oldVal, Integer.class);
            System.out.println("[watch] Count changed from " + oldCount + " to " + newCount);
        });

        public JSFunction status = JSFunction.fromBiCons((Object newVal, Object oldVal) -> {
            String newStatus = JSValue.checkedCoerce(newVal, String.class);
            String oldStatus = JSValue.checkedCoerce(oldVal, String.class);
            System.out.println("[watch] Status changed from '" + oldStatus + "' to '" + newStatus + "'");
        });

        public JSObject user = new JSObject() {{
            // Important: This watcher is defined as an anonymous JSObject instead of a Java class.
            // Defining it as a named Java class would cause Vue's deep reactivity system to recursively traverse
            // the proxy structure, leading to a stack overflow or "too much recursion" error.
            // Using an inline JSObject with explicit fields avoids that issue and keeps the watcher safe.
            set("handler", JSFunction.fromJSCons((JSObject user) -> {
                JSObject profile = (JSObject) user.get("profile");
                String name = JSValue.checkedCoerce(profile.get("name"), String.class);
                System.out.println("[watch] user.profile.name changed to '" + name + "'");
            }));
            set("deep", JSBoolean.of(true));
        }};
    }

    private static class Methods extends JSObject {

        public JSFunction increment = JSFunction.fromThisJSCons((JSObject data) -> {
            int current = JSValue.checkedCoerce(data.get("count"), Integer.class);
            data.set("count", current + 1);
        });

        public JSFunction toggleStatus = JSFunction.fromThisJSCons((JSObject data) -> {
            String current = JSValue.checkedCoerce(data.get("status"), String.class);
            String next = current.equals("idle") ? "active" : "idle";
            data.set("status", next);
        });

        public JSFunction toggleProfileName = JSFunction.fromThisJSCons((JSObject data) -> {
            JSObject user = (JSObject) data.get("user");
            JSObject profile = (JSObject) user.get("profile");
            String current = JSValue.checkedCoerce(profile.get("name"), String.class);
            String next = current.equals("Alice") ? "Bob" : "Alice";
            profile.set("name", JSString.of(next));
        });
    }

    private static class Computed extends JSObject {

        public JSFunction greeting = JSFunction.fromThisJSFunc((JSObject data) -> {
            JSObject user = (JSObject) data.get("user");
            JSObject profile = (JSObject) user.get("profile");
            String name = JSValue.checkedCoerce(profile.get("name"), String.class);
            return JSString.of("Hello, " + name + "!");
        });

    }
}

