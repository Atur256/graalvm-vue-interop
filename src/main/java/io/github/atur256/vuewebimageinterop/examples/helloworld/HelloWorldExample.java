package io.github.atur256.vuewebimageinterop.examples.helloworld;

import io.github.atur256.vuewebimageinterop.api.Component;
import io.github.atur256.vuewebimageinterop.api.Vue;


/**
 * Entry point for the Hello World example using Vue + GraalVM interop.
 * <p>
 * This replicates the official Vue Hello World example:
 * <a href="https://vuejs.org/examples/#hello-world">vuejs.org/examples/#hello-world</a>
 * <p>
 * The root component is implemented in Java and rendered via GraalVM,
 * demonstrating basic template binding and reactivity.
 */
public class HelloWorldExample {

    public static void main(String[] args) {

        // Instantiate the root Vue component (defined in Java).
        Component component = new MainComponent();

        // Create and mount the Vue application.
        Vue.createApp(component).mount();
    }
}
