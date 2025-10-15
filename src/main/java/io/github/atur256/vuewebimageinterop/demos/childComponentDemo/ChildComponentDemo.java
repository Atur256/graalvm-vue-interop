package io.github.atur256.vuewebimageinterop.demos.childComponentDemo;

import io.github.atur256.vuewebimageinterop.oldClasses.Vue;
import io.github.atur256.vuewebimageinterop.reworkedClasses.Component;
import io.github.atur256.vuewebimageinterop.reworkedClasses.VueApp;


public class ChildComponentDemo {

    public static void main(String[] args) {

        Component component = new DemoComponent();
        VueApp app = Vue.createApp(component);
        app.mount();
//        Vue.mountApp(app); // TODO: Vue needs to store the app instance somewhere to access it later for value modification

    }
}
