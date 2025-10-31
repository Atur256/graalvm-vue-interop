package io.github.atur256.vuewebimageinterop.examples.composition.directivesandattrs;


import io.github.atur256.vuewebimageinterop.api.Component;
import io.github.atur256.vuewebimageinterop.api.Vue;


/**
 * Entry point for the Directives & Attributes example using Vue + GraalVM interop.
 * <p>
 * Demonstrates:
 * <ul>
 *   <li>Custom directive registration via {@code directives}</li>
 *   <li>Manual attribute forwarding using {@code inheritAttrs = false}</li>
 *   <li>Accessing and printing $attrs from within the component</li>
 * </ul>
 */
public class DirectivesAndAttrsExample {

    public static void main(String[] args) {
        // Instantiate the root Vue component
        Component component = new DirectiveParentComponent();

        // Create and mount the Vue application
        Vue.createApp(component).mount();
    }
}
