package io.github.atur256.vuewebimageinterop.demos.watchdemo;

import io.github.atur256.vuewebimageinterop.reworkedClasses.Component;
import io.github.atur256.vuewebimageinterop.reworkedClasses.Vue;
import io.github.atur256.vuewebimageinterop.reworkedClasses.VueApp;


public class WatchDemo {

    public static void main(String[] args) {

        Component component = new DemoComponent();

        VueApp app = Vue.createApp(component);
        app.mount();
    }
}
