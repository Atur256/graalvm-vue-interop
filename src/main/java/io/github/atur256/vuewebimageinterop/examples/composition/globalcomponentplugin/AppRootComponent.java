package io.github.atur256.vuewebimageinterop.examples.composition.globalcomponentplugin;

import io.github.atur256.vuewebimageinterop.api.Component;
import io.github.atur256.vuewebimageinterop.api.VueApp;
import io.github.atur256.webimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;
import org.graalvm.webimage.api.JSValue;


/**
 * AppRootComponent is the root Vue component for this GraalVM-based plugin example.
 * <p>
 * Demonstrates:
 * <ul>
 *   <li>Accessing global properties injected via a plugin</li>
 *   <li>Passing props to a child component</li>
 *   <li>Receiving events from child via {@code @childEvent}</li>
 * </ul>
 */
public class AppRootComponent extends Component {

    public AppRootComponent() {
        // Vue template: global message, parent message, child component
        this.template = JSString.of("""
                <div>
                    <h2>VueApp with Component & Plugin</h2>
                    <p>Global Message: {{ $root.globalMessage }}</p>
                    <p>Parent Message: {{ parentMessage }}</p>
                    <p>Message from Child: {{ childResponseMessage }}</p>
                    <child-component :messageFromParent="parentMessage" @childEvent="onChildMessage"/>
                </div>
                """);

        // Vue method bindings
        this.methods = new Methods();
    }

    /**
     * Reactive state for template bindings.
     */
    public JSObject data() {
        return new Data();
    }

    private static class Data extends JSObject {

        public JSString parentMessage = JSString.of("Hello from Parent!");
        public JSString childResponseMessage = JSString.of("-");
    }

    /**
     * Vue methods handling events from the child component.
     */
    private static class Methods extends JSObject {

        public JSFunction onChildMessage = JSFunction.fromConsumer((JSString msg) -> {
            System.out.println("Parent received event: " + msg.asString());
            VueApp.setValue("childResponseMessage", msg.asString());
        });
    }

    /**
     * Plugin to inject a global property into the Vue app.
     */
    public static class Plugin extends JSObject {

        public JSFunction install = JSFunction.fromConsumer((JSObject app) -> {
            JSObject config = JSValue.checkedCoerce(app.get("config"), JSObject.class);
            JSObject globalProperties = JSValue.checkedCoerce(config.get("globalProperties"), JSObject.class);
            globalProperties.set("globalMessage", "Hello from plugin!");
        });
    }
}
