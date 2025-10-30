package io.github.atur256.vuewebimageinterop;

import io.github.atur256.vuewebimageinterop.api.VueRefTemp;
import io.github.atur256.vuewebimageinterop.examples.advanced.shoppinglist.ShoppingListExample;
import io.github.atur256.vuewebimageinterop.api.VueApp;
import io.github.atur256.vuewebimageinterop.examples.basics.counter.CounterExample;
import io.github.atur256.vuewebimageinterop.examples.composition.componenthierarchy.ComponentHierarchyExample;
import io.github.atur256.vuewebimageinterop.examples.reactivity.lifecycle.LifecycleHooksExample;
import io.github.atur256.vuewebimageinterop.examples.reactivity.watcheffects.ReactiveWatchExample;


/**
 * Entry point for generating browser-ready JavaScript via GraalVM WebImage.
 * <p>
 * This main class demonstrates how to call a Vue example from Java so it can be compiled
 * into JavaScript and executed in the browser.
 * <p>
 * Important: Vue components that do not have a direct 1:1 mapping in JavaScript
 * must be instantiated at least once in Java. Otherwise, they may be stripped out
 * during the compilation as unused code.
 */
public class Main {

    public static void main(String[] args) {

        // Instantiate VueApp to ensure core Vue APIs are retained
        new VueApp();
        new VueRefTemp();

        // Run a specific example
        ComponentHierarchyExample.main(args);
    }
}

// TODO:
// TODO: watchEffect
// TODO: emit
// TODO: this
// TODO: computed
// TODO: duplicate code and try with annotations
// TODO: test if java data strcutures can be used öike HashMap


// Limitations: "this" can not be accessed from the java code, so all functions relying on "this" need to be fully written in js (e.g. computed)