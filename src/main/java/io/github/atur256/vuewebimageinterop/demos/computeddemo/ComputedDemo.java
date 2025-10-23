package io.github.atur256.vuewebimageinterop.demos.computeddemo;

import io.github.atur256.vuewebimageinterop.api.Component;
import io.github.atur256.vuewebimageinterop.api.Vue;
import io.github.atur256.vuewebimageinterop.api.VueApp;


public class ComputedDemo {

    public static void main(String[] args) {

        // Create the component
        Component component = new DemoComponent();

        // Create the Vue app and mount
        VueApp app = Vue.createApp(component);
        app.mount();
    }
}
