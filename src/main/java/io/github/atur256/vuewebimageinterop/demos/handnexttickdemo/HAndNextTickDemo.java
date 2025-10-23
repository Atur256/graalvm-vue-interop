package io.github.atur256.vuewebimageinterop.demos.handnexttickdemo;

import io.github.atur256.vuewebimageinterop.api.Component;
import io.github.atur256.vuewebimageinterop.api.Vue;
import io.github.atur256.vuewebimageinterop.api.VueApp;


public class HAndNextTickDemo {

    public static void main(String[] args) {

        // Create the root component for the Vue app
        Component dynamicComponent = new DemoComponent();

        // Create a Vue application instance from the root component
        VueApp app = Vue.createApp(dynamicComponent);

        // Mount the Vue app
        app.mount();
    }
}
