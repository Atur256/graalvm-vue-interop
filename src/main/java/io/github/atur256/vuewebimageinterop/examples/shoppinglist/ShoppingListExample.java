package io.github.atur256.vuewebimageinterop.examples.shoppinglist;

import io.github.atur256.vuewebimageinterop.api.Component;
import io.github.atur256.vuewebimageinterop.api.Vue;


/**
 * Entry point for the Shopping List example using Vue + GraalVM interop.
 * <p>
 * Demonstrates dynamic list rendering, prop passing, and event handling
 * using Java-defined Vue components.
 */
public class ShoppingListExample {

    public static void main(String[] args) {

        // Instantiate the root Vue component (defined in Java).
        Component component = new MainComponent();

        // Create and mount the Vue application.
        Vue.createApp(component).mount();
    }
}
