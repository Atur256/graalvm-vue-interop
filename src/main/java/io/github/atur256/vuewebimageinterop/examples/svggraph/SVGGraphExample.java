package io.github.atur256.vuewebimageinterop.examples.svggraph;

import io.github.atur256.vuewebimageinterop.api.Component;
import io.github.atur256.vuewebimageinterop.api.Vue;

/**
 * Entry point for the SVG Graph example using Vue + GraalVM interop.
 * <p>
 * This replicates the official Vue SVG example:
 * <a href="https://vuejs.org/examples/#svg">vuejs.org/examples/#svg</a>
 * <p>
 * The component hierarchy is implemented entirely in Java and rendered via GraalVM,
 * showcasing reactive data, computed geometry, and nested components in an SVG layout.
 */
public class SVGGraphExample {

    public static void main(String[] args) {

        // Instantiate the root Vue component (defined in Java).
        Component component = new MainComponent();

        // Create and mount the Vue application to the DOM.
        Vue.createApp(component).mount();
    }
}
