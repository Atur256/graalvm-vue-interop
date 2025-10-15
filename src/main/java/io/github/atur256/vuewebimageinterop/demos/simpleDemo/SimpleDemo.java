package io.github.atur256.vuewebimageinterop.demos.simpleDemo;

import io.github.atur256.vuewebimageinterop.reworkedCode.Component;
import io.github.atur256.vuewebimageinterop.Vue;
import io.github.atur256.vuewebimageinterop.reworkedCode.VueApp;


public class SimpleDemo {

    public static void main(String[] args) {

        Component component = new DemoComponent();
        VueApp app = Vue.createApp(component);
        app.mount();
    }
}
