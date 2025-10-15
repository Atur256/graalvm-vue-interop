package io.github.atur256.vuewebimageinterop;

import io.github.atur256.vuewebimageinterop.demos.childComponentDemo.ChildComponent;
import io.github.atur256.vuewebimageinterop.demos.childComponentDemo.ChildComponentDemo;
import io.github.atur256.vuewebimageinterop.reworkedCode.VueApp;
import io.github.atur256.vuewebimageinterop.demos.simpleDemo.DemoComponent;


public class Main {

    public static void main(String[] args) {

        // Note: every Component that does not exist 1:1 in JS needs to be called at least once, otherwise it will be removed by the compiler and therefore fail
        new VueApp();
        new DemoComponent();
        new io.github.atur256.vuewebimageinterop.demos.childComponentDemo.DemoComponent();
        new ChildComponent();

        ChildComponentDemo.main(null);
    }
}
