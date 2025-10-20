package io.github.atur256.vuewebimageinterop.demos.mountunmountdemo;

import io.github.atur256.vuewebimageinterop.demos.simpledemo.DemoComponent;
import io.github.atur256.vuewebimageinterop.reworkedClasses.Component;
import io.github.atur256.vuewebimageinterop.reworkedClasses.Vue;
import io.github.atur256.vuewebimageinterop.reworkedClasses.VueApp;
import io.github.atur256.webimageinterop.builtin.JSFunction;


public class MountUnmountDemo {


    public static void main(String[] args) {

        Component component = new DemoComponent();

        VueApp app = Vue.createApp(component);

        app.onUnmountJS(JSFunction.fromRunnable(() -> System.out.println("Unmount callback triggered!")));

        app.mount();

        System.out.println("App mounted");

        app.unmount(); // triggers the callback
    }
}
