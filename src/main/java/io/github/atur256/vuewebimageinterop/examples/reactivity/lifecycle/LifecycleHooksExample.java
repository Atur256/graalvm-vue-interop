package io.github.atur256.vuewebimageinterop.examples.reactivity.lifecycle;

import io.github.atur256.vuewebimageinterop.api.Vue;
import io.github.atur256.vuewebimageinterop.api.VueApp;
import io.github.atur256.webimageinterop.builtin.JSFunction;


/**
 * Entry point for the Lifecycle Hooks example using Vue + GraalVM interop.
 * <p>
 * Demonstrates:
 * <ul>
 *   <li>Lifecycle hook registration via {@code onMounted} and {@code onUnmounted}</li>
 *   <li>Manual app unmounting triggered from within the component</li>
 *   <li>Interop between component and app instance</li>
 * </ul>
 */
public class LifecycleHooksExample {

    public static void main(String[] args) {

        // Instantiate the root Vue component
        LifecycleComponent component = new LifecycleComponent();

        // Create the Vue application
        VueApp app = Vue.createApp(component);

        // Pass the app instance into the component for self-unmounting
        component.setApp(app);

        // Mount the Vue application
        app.mount();

        // Register a JS-side unmount hook
        app.onUnmountJS(JSFunction.fromRun(() -> System.out.println("Vue Application unmounted!")));
    }
}
