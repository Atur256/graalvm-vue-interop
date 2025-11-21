package io.github.atur256.graalvmvueinterop.test.examples.counter;

import io.github.atur256.graalvmvueinterop.api.Component;
import io.github.atur256.graalvmvueinterop.api.Vue;
import io.github.atur256.graalvmvueinterop.test.ComponentTest;


public class CounterExample {

    public static void main(String[] args) {

        // Instantiate the root Vue component
        ComponentTest component = new RootComponent();

        // Create and mount the Vue application
        Vue.createApp(component).mount();
    }
}