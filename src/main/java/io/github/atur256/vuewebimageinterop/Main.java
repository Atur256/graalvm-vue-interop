package io.github.atur256.vuewebimageinterop;

import io.github.atur256.vuewebimageinterop.demos.fullComponentDemo.ChildChildComponent;
import io.github.atur256.vuewebimageinterop.demos.fullComponentDemo.FullComponentDemo;
import io.github.atur256.vuewebimageinterop.reworkedClasses.VueApp;
import io.github.atur256.vuewebimageinterop.reworkedClasses.VueRef;


public class Main {

    public static void main(String[] args) {

        // Note: every Component that does not exist 1:1 in JS needs to be called at least once, otherwise it will be removed by the compiler and therefore fail
        new VueApp();
        new VueRef();
        new io.github.atur256.vuewebimageinterop.demos.simpleDemo.DemoComponent();
        new io.github.atur256.vuewebimageinterop.demos.childComponentDemo.DemoComponent();
        new io.github.atur256.vuewebimageinterop.demos.childComponentDemo.ChildComponent();
        new io.github.atur256.vuewebimageinterop.demos.fullComponentDemo.DemoComponent();
        new io.github.atur256.vuewebimageinterop.demos.fullComponentDemo.ChildComponent();
        new ChildChildComponent();

        FullComponentDemo.main(null);
    }
}
