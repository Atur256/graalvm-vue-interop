package io.github.atur256.graalvmvueinterop.apiwithreflection.examples.counter;

import io.github.atur256.graalvmvueinterop.api.Vue;
import io.github.atur256.graalvmvueinterop.apiwithreflection.ComponentWithReflection;


public class CounterExample {

    public static void main(String[] args) {

        // Instantiate the root Vue component
        ComponentWithReflection component = new RootComponent();

        // Create and mount the Vue application
        Vue.createApp(component).mount();
    }
}