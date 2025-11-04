package io.github.atur256.vuewebimageinterop;

import io.github.atur256.vuewebimageinterop.examples.basics.helloworld.HelloWorldExample;


/**
 * Entry point for generating browser-ready JavaScript via GraalVM WebImage.
 * <p>
 * This main class demonstrates how to call a Vue example from Java so it can be compiled
 * into JavaScript and executed in the browser.
 */
public class Main {

    public static void main(String[] args) {

        // Run a specific example
        HelloWorldExample.main(args);
    }
}
