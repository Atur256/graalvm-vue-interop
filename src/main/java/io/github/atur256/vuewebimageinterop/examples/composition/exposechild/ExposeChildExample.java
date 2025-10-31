package io.github.atur256.vuewebimageinterop.examples.composition.exposechild;

import io.github.atur256.vuewebimageinterop.api.Component;
import io.github.atur256.vuewebimageinterop.api.Vue;


/**
 * Entry point for the Expose Child example using Vue + GraalVM interop.
 * <p>
 * Demonstrates:
 * <ul>
 *   <li>Calling a child component method from the parent via template ref</li>
 *   <li>Using the expose option to control component API surface</li>
 * </ul>
 */
public class ExposeChildExample {

    public static void main(String[] args) {
        // Instantiate the root Vue component
        Component component = new ExposeParentComponent();

        // Create and mount the Vue application
        Vue.createApp(component).mount();
    }
}
