package io.github.atur256.vuewebimageinterop.demos.mountunmountdemo;

import io.github.atur256.vuewebimageinterop.api.Component;
import io.github.atur256.vuewebimageinterop.examples.simplecounter.MainComponent;
import io.github.atur256.vuewebimageinterop.api.Vue;
import io.github.atur256.vuewebimageinterop.api.VueApp;
import io.github.atur256.webimageinterop.builtin.JSFunction;


public class MountUnmountDemo {


    public static void main(String[] args) {

        Component component = new MainComponent();

        VueApp app = Vue.createApp(component);

        app.onUnmountJS(JSFunction.fromRunnable(() -> System.out.println("Unmount callback triggered!")));

        app.mount();

        System.out.println("App mounted");

        app.unmount(); // triggers the callback
    }
}
