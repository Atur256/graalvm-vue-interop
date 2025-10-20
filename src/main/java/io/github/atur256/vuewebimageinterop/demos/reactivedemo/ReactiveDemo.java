package io.github.atur256.vuewebimageinterop.demos.reactivedemo;

import io.github.atur256.vuewebimageinterop.reworkedClasses.Component;
import io.github.atur256.vuewebimageinterop.reworkedClasses.Vue;
import io.github.atur256.vuewebimageinterop.reworkedClasses.VueApp;
import io.github.atur256.vuewebimageinterop.reworkedClasses.VueReactive;
import org.graalvm.webimage.api.JSNumber;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;


public class ReactiveDemo {

    public static void main(String[] args) throws InterruptedException {

        // Create a plain JS object to hold reactive data
        JSObject rawData = JSObject.create();
        rawData.set("counter", JSNumber.of(0));
        rawData.set("message", JSString.of("Hello from Java!"));

        // Wrap it in Vue’s reactivity system
        VueReactive reactiveState = VueReactive.of(Vue.reactive(rawData));

        // Create a component that binds to the reactive state
        Component reactiveComponent = new DemoComponent(reactiveState);

        // Create the Vue app and mount it
        VueApp app = Vue.createApp(reactiveComponent);
        app.mount();

        // Demonstrate reactivity from Java
        for(int i = 0; i < 5; i++) {
            Thread.sleep(1000); // TODO: apparently this does not work and gets skipped
            int newVal = reactiveState.get("counter", Integer.class) + 1;
            reactiveState.set("counter", newVal);
            System.out.println("Java updated counter to " + newVal);
        }
    }
}
