package io.github.atur256.vuewebimageinterop.examples.reactivity.watcheffects;

import io.github.atur256.vuewebimageinterop.api.Component;
import io.github.atur256.vuewebimageinterop.api.Vue;
import org.graalvm.webimage.api.JSNumber;
import org.graalvm.webimage.api.JSObject;


/**
 * Entry point for the Watch & WatchEffect example using Vue + GraalVM interop.
 * <p>
 * Demonstrates:
 * <ul>
 *   <li>Reactive state via {@code Vue.ref}</li>
 *   <li>Computed properties via {@code Vue.computedRef}</li>
 *   <li>Side effect tracking via {@code Vue.watch} and {@code Vue.watchEffect}</li>
 * </ul>
 */
public class ReactiveWatchExample {

    public static void main(String[] args) {

        // Instantiate the root Vue component
        Component component = new RootComponent();

        // Create and mount the Vue application
        Vue.createApp(component).mount();
    }
}
