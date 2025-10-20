package io.github.atur256.vuewebimageinterop.demos.handnexttickdemo;

import io.github.atur256.vuewebimageinterop.reworkedClasses.Component;
import io.github.atur256.vuewebimageinterop.reworkedClasses.Vue;
import io.github.atur256.vuewebimageinterop.reworkedClasses.VueApp;


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
