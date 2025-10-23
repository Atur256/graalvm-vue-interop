package io.github.atur256.vuewebimageinterop.demos.fullcomponentdemo;

import io.github.atur256.vuewebimageinterop.api.Component;
import io.github.atur256.vuewebimageinterop.api.Vue;
import io.github.atur256.vuewebimageinterop.api.VueApp;


public class FullComponentDemo {

    public static void main(String[] args) {

        Component component = new DemoComponent();

        VueApp app = Vue.createApp(component);

        app.mount();
    }
}
