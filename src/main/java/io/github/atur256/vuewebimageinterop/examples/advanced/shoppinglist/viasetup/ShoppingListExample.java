package io.github.atur256.vuewebimageinterop.examples.advanced.shoppinglist.viasetup;

import io.github.atur256.vuewebimageinterop.api.Component;
import io.github.atur256.vuewebimageinterop.api.Vue;

/**
 * Entry point for the Shopping List example using Vue + GraalVM interop.
 * <p>
 * Demonstrates:
 * <ul>
 *   <li>Reactive list management via {@code JSArray}</li>
 *   <li>Dynamic component rendering using {@code v-for}</li>
 *   <li>Event-driven item removal via {@code @remove}</li>
 * </ul>
 */
public class ShoppingListExample {

    public static void main(String[] args) {
        // Instantiate the root Vue component
        Component component = new RootComponent();

        // Create and mount the Vue application
        Vue.createApp(component).mount();
    }
}
