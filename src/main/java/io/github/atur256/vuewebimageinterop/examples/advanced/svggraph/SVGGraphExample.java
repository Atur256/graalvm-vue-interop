package io.github.atur256.vuewebimageinterop.examples.advanced.svggraph;

import io.github.atur256.vuewebimageinterop.api.Component;
import io.github.atur256.vuewebimageinterop.api.Vue;


/**
 * Entry point for the SVG Graph example using Vue + GraalVM interop.
 * <p>
 * Demonstrates:
 * <ul>
 *   <li>SVG rendering via custom components</li>
 *   <li>Computed geometry for dynamic layout</li>
 *   <li>Component hierarchy with prop passing</li>
 * </ul>
 *
 * Mirrors the official Vue SVG Graph example:
 * <a href="https://vuejs.org/examples/#svg">vuejs.org/examples/#svg</a>
 */
public class SVGGraphExample {

    public static void main(String[] args) {

        // Instantiate the root Vue component
        Component component = new RootComponent();

        // Create and mount the Vue application
        Vue.createApp(component).mount();
    }
}
