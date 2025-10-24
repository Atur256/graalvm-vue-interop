package io.github.atur256.vuewebimageinterop.examples.composition.globalcomponentplugin;

import io.github.atur256.vuewebimageinterop.api.Component;
import io.github.atur256.vuewebimageinterop.api.Vue;
import io.github.atur256.vuewebimageinterop.api.VueApp;
import io.github.atur256.webimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSValue;


/**
 * Entry point for the Global Component + Plugin example using Vue + GraalVM interop.
 * <p>
 * Demonstrates:
 * <ul>
 *   <li>Global component registration via {@code app.component}</li>
 *   <li>Plugin installation via {@code app.use}</li>
 *   <li>Global property injection via {@code app.config.globalProperties}</li>
 * </ul>
 */
public class GlobalComponentPluginExample {

    public static void main(String[] args) {

        // Instantiate the root Vue component
        Component component = new AppRootComponent();

        // Create the Vue application
        VueApp app = Vue.createApp(component);

        // Register a global child component <child-component>
        app.component("child-component", new ChildComponent());

        // Install a plugin that adds a global property
        app.use(new Plugin());

        // Mount the Vue application
        app.mount();
    }

    /**
     * Plugin that injects a global property into the Vue app.
     * <p>
     * Sets {@code app.config.globalProperties.globalMessage}, which can be accessed via {@code $root.globalMessage}.
     */
    private static class Plugin extends JSObject {

        public JSFunction install = JSFunction.fromConsumer((JSObject app) -> {
            JSObject config = JSValue.checkedCoerce(app.get("config"), JSObject.class);
            JSObject globalProperties = JSValue.checkedCoerce(config.get("globalProperties"), JSObject.class);
            globalProperties.set("globalMessage", "Hello from plugin!");
        });
    }
}
