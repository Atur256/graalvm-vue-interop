package io.github.atur256.vuewebimageinterop;

import io.github.atur256.vuewebimageinterop.api.VueApp;
import io.github.atur256.vuewebimageinterop.examples.basics.helloworld.HelloWorldExample;


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

        // Run a specific example
        HelloWorldExample.main(args);
    }
}
