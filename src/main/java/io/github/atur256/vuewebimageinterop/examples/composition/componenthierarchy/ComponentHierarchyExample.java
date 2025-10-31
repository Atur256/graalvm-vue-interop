package io.github.atur256.vuewebimageinterop.examples.composition.componenthierarchy;

import io.github.atur256.vuewebimageinterop.api.Component;
import io.github.atur256.vuewebimageinterop.api.Vue;


/**
 * Entry point for the Component Hierarchy example using Vue + GraalVM interop.
 * <p>
 * Demonstrates:
 * <ul>
 *   <li>Prop passing across parent -> child -> grandchild</li>
 *   <li>Reactive state sharing via {@code Vue.ref}</li>
 *   <li>Event emission from grandchild to parent</li>
 *   <li>Computed properties and provide/inject usage</li>
 * </ul>
 */
public class ComponentHierarchyExample {

    public static void main(String[] args) {

        // Instantiate the root Vue component
        Component component = new ParentComponent();

        // Create and mount the Vue application
        Vue.createApp(component).mount();
    }
}
