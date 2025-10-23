package io.github.atur256.vuewebimageinterop.examples.simplecounter;

import io.github.atur256.vuewebimageinterop.api.Component;
import io.github.atur256.vuewebimageinterop.api.Vue;


/**
 * Entry point for the Simple Counter demo using Vue + GraalVM interop.
 * <p>
 * Demonstrates reactive state and event handling in a Java-defined Vue component.
 * Mirrors the structure of a basic Vue counter app, implemented entirely in Java.
 */
public class SimpleCounterExample {

    public static void main(String[] args) {

        // Instantiate the root Vue component (defined in Java).
        Component component = new MainComponent();

        // Create and mount the Vue application.
        Vue.createApp(component).mount();
    }
}
