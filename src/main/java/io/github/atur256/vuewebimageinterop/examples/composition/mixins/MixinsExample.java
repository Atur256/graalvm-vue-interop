package io.github.atur256.vuewebimageinterop.examples.composition.mixins;

import io.github.atur256.vuewebimageinterop.api.Component;
import io.github.atur256.vuewebimageinterop.api.Vue;
import io.github.atur256.vuewebimageinterop.api.VueApp;


/**
 * Entry point for the mixins example using Vue + GraalVM interop.
 * <p>
 * Demonstrates:
 * <ul>
 *   <li>Combining multiple mixins into a single component</li>
 *   <li>Reactive state and methods injected via mixins</li>
 * </ul>
 */
public class MixinsExample {

    public static void main(String[] args) {

        // Instantiate the root component
        Component component = new MixinComponent();

        // Create the Vue application
        VueApp app = Vue.createApp(component);

        // Mount the Vue application
        app.mount();
    }
}
