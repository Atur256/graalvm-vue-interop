package io.github.atur256.vuewebimageinterop.demos.vuecomponentusedemo;

import io.github.atur256.vuewebimageinterop.reworkedClasses.Component;
import io.github.atur256.vuewebimageinterop.reworkedClasses.Vue;
import io.github.atur256.vuewebimageinterop.reworkedClasses.VueApp;
import io.github.atur256.webimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.JSObject;


public class VueComponentUseDemo {

    public static void main(String[] args) {

        // Create the root component for the Vue app
        Component component = new DemoComponent();

        // Create a Vue application instance from the root component
        VueApp app = Vue.createApp(component);

        // --- Define a simple child component ---
        Component childComponent = new ChildDemoComponent();

        // Register the child component globally in the app with the tag <hello-world>
        app.component("hello-world", childComponent);

        // Register a plugin with the app
        app.use(new Plugin());

        // --- Mount the Vue app ---
        app.mount();

        // Log to console when the app is successfully mounted
        System.out.println("Vue app with custom component and plugin mounted!");
    }

    private static class Plugin extends JSObject {

        public JSFunction install = JSFunction.fromConsumer((JSObject obj) -> System.out.println("Plugin installed on app with keys: " + obj.keys()));
    }
}
