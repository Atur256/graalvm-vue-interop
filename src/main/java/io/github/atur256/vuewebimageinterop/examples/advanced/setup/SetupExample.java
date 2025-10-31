package io.github.atur256.vuewebimageinterop.examples.advanced.setup;

import io.github.atur256.vuewebimageinterop.api.Component;
import io.github.atur256.vuewebimageinterop.api.Vue;
import io.github.atur256.vuewebimageinterop.api.VueApp;

/**
 * Entry point for the SetupComponent demo using Vue 3 Composition API.
 * <p>
 * Demonstrates:
 * <ul>
 *   <li>Reactive state with {@code Vue.ref()}</li>
 *   <li>Event-driven updates via {@code setup()}</li>
 * </ul>
 */
public class SetupExample {

    public static void main(String[] args) {
        // Instantiate the component
        Component component = new SetupComponent();

        // Create the Vue application
        VueApp app = Vue.createApp(component);

        // Mount the Vue application
        app.mount();
    }
}
