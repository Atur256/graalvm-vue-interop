package io.github.atur256.vuewebimageinterop.examples.reactivity.rendernexttick;

import io.github.atur256.vuewebimageinterop.api.Component;
import io.github.atur256.vuewebimageinterop.api.Vue;


/**
 * Entry point for the Render + nextTick example using Vue + GraalVM interop.
 * <p>
 * Demonstrates:
 * <ul>
 *   <li>Manual virtual DOM rendering via {@code Vue.h}</li>
 *   <li>Reactive state via {@code Vue.reactive}</li>
 *   <li>Deferred DOM updates via {@code Vue.nextTick}</li>
 * </ul>
 */
public class RenderNextTickExample {

    public static void main(String[] args) {

        // Instantiate the root Vue component
        Component dynamicComponent = new RenderTickComponent();

        // Create and mount the Vue application
        Vue.createApp(dynamicComponent).mount();
    }
}
