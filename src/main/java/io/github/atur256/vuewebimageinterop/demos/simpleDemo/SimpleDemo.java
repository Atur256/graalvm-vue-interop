package io.github.atur256.vuewebimageinterop.demos.simpleDemo;

import io.github.atur256.vuewebimageinterop.reworkedClasses.Component;
import io.github.atur256.vuewebimageinterop.oldClasses.Vue;
import io.github.atur256.vuewebimageinterop.reworkedClasses.VueApp;


public class SimpleDemo {

    public static void main(String[] args) {

        Component component = new DemoComponent();
        VueApp app = Vue.createApp(component);
//        Vue.mountAndStore(app);
        app.mount();
    }
}
