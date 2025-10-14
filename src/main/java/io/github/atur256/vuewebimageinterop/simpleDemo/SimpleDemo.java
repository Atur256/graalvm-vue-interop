package io.github.atur256.vuewebimageinterop.simpleDemo;

import io.github.atur256.vuewebimageinterop.Component;
import io.github.atur256.vuewebimageinterop.Vue;
import io.github.atur256.vuewebimageinterop.VueApp;


public class SimpleDemo {

    public static void main(String[] args) {

        Component component = new DemoComponent();
        VueApp app = Vue.createApp(component);
        Vue.mountApp(app);
    }
}
