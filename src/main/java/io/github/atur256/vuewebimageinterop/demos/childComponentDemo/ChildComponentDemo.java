package io.github.atur256.vuewebimageinterop.demos.childComponentDemo;

import io.github.atur256.vuewebimageinterop.Vue;
import io.github.atur256.vuewebimageinterop.reworkedCode.Component;
import io.github.atur256.vuewebimageinterop.reworkedCode.VueApp;


public class ChildComponentDemo {

    public static void main(String[] args) {

        Component component = new DemoComponent();
        VueApp app = Vue.createApp(component);
//        app.mount();
        Vue.mountAndStore(app); // TODO: Vue needs to store the app instance somewhere to access it later for value modification

    }
}
