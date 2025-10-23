package io.github.atur256.vuewebimageinterop.demos.lifecycledemo;

import io.github.atur256.vuewebimageinterop.api.Vue;
import io.github.atur256.vuewebimageinterop.api.VueApp;


public class LifecycleDemo {

    public static void main(String[] args) {

        // Create the component (without app for now)
        DemoComponent component = new DemoComponent();

        // Create the Vue app
        VueApp app = Vue.createApp(component);

        // Pass the mounted app instance into the component so it can unmount
        component.setApp(app);

        // Mount it
        app.mount();
    }
}
