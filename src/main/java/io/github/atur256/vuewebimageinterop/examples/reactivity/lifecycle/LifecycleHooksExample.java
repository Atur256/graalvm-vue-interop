package io.github.atur256.vuewebimageinterop.examples.reactivity.lifecycle;

import io.github.atur256.vuewebimageinterop.api.Vue;
import io.github.atur256.vuewebimageinterop.api.VueApp;
import io.github.atur256.webimageinterop.builtin.JSFunction;


/**
 * LifecycleHooksExample is the entry point for launching the Vue app.
 * <p>
 * Demonstrates:
 * <ul>
 *   <li>App creation via {@code Vue.createApp}</li>
 *   <li>Passing the {@code VueApp} instance to the root component</li>
 *   <li>Manual app unmounting triggered from within the component</li>
 *   <li>JS-side unmount hook registration via {@code onUnmountJS}</li>
 * </ul>
 */
public class LifecycleHooksExample {

    public static void main(String[] args) {

        // Instantiate the root Vue component
        LifecycleParentComponent component = new LifecycleParentComponent();

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
