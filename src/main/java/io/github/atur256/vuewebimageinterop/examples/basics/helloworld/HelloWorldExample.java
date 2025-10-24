package io.github.atur256.vuewebimageinterop.examples.basics.helloworld;

import io.github.atur256.vuewebimageinterop.api.Component;
import io.github.atur256.vuewebimageinterop.api.Vue;



/**
 * Entry point for the Hello World example using Vue + GraalVM interop.
 * <p>
 * Demonstrates:
 * <ul>
 *   <li>Basic template binding via {@code {{ message }}}</li>
 *   <li>Reactive state via {@code data()}</li>
 *   <li>Minimal component definition in Java</li>
 * </ul>
 * Mirrors the official Vue Hello World example:
 * <a href="https://vuejs.org/examples/#hello-world">vuejs.org/examples/#hello-world</a>
 * <p>
 */
public class HelloWorldExample {

    public static void main(String[] args) {

        // Instantiate the root Vue component
        Component component = new RootComponent();

        // Create and mount the Vue application
        Vue.createApp(component).mount();
    }
}
