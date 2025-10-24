package io.github.atur256.vuewebimageinterop.examples.basics.counter;

import io.github.atur256.vuewebimageinterop.api.Component;
import io.github.atur256.vuewebimageinterop.api.Vue;


/**
 * Entry point for the Simple Counter example using Vue + GraalVM interop.
 * <p>
 * Demonstrates:
 * <ul>
 *   <li>Reactive state via {@code data()}</li>
 *   <li>Event handling via {@code methods}</li>
 *   <li>Manual component definition in Java</li>
 * </ul>
 */
public class CounterExample {

    public static void main(String[] args) {

        // Instantiate the root Vue component
        Component component = new RootComponent();

        // Create and mount the Vue application
        Vue.createApp(component).mount();
    }
}
