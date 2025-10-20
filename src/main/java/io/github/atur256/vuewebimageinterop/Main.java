package io.github.atur256.vuewebimageinterop;

import io.github.atur256.vuewebimageinterop.demos.fullcomponentdemo.ChildChildComponent;
import io.github.atur256.vuewebimageinterop.demos.shoppinglistdemo.ShoppingListDemo;
import io.github.atur256.vuewebimageinterop.reworkedClasses.VueApp;
import io.github.atur256.vuewebimageinterop.reworkedClasses.VueRef;


public class Main {

    public static void main(String[] args) throws InterruptedException {

        // Note: every Component that does not exist 1:1 in JS needs to be called at least once, otherwise it will be removed by the compiler and therefore fail
        new VueApp();
        new VueRef();
        new io.github.atur256.vuewebimageinterop.demos.simpledemo.DemoComponent();
        new io.github.atur256.vuewebimageinterop.demos.childcomponentdemo.DemoComponent();
        new io.github.atur256.vuewebimageinterop.demos.childcomponentdemo.ChildComponent();
        new io.github.atur256.vuewebimageinterop.demos.fullcomponentdemo.DemoComponent();
        new io.github.atur256.vuewebimageinterop.demos.fullcomponentdemo.ChildComponent();
        new ChildChildComponent();

        ShoppingListDemo.main(null);
    }
}
