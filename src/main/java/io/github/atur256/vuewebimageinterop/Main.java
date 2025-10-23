package io.github.atur256.vuewebimageinterop;

import io.github.atur256.vuewebimageinterop.demos.fullcomponentdemo.ChildChildComponent;
import io.github.atur256.vuewebimageinterop.examples.helloworld.HelloWorldExample;
import io.github.atur256.vuewebimageinterop.examples.shoppinglist.ShoppingListExample;
import io.github.atur256.vuewebimageinterop.examples.simplecounter.MainComponent;
import io.github.atur256.vuewebimageinterop.api.VueApp;
import io.github.atur256.vuewebimageinterop.api.VueRef;
import io.github.atur256.vuewebimageinterop.examples.simplecounter.SimpleCounterExample;
import io.github.atur256.vuewebimageinterop.examples.svggraph.SVGGraphExample;


public class Main {

    public static void main(String[] args) throws InterruptedException {

        // TODO: add html and css file for the examples to this project (currently they are in the web-image dir)

        // Note: every Component that does not exist 1:1 in JS needs to be called at least once, otherwise it will be removed by the compiler and therefore fail
        new VueApp();
        new VueRef();
        new MainComponent();
        new io.github.atur256.vuewebimageinterop.demos.childcomponentdemo.DemoComponent();
        new io.github.atur256.vuewebimageinterop.demos.childcomponentdemo.ChildComponent();
        new io.github.atur256.vuewebimageinterop.demos.fullcomponentdemo.DemoComponent();
        new io.github.atur256.vuewebimageinterop.demos.fullcomponentdemo.ChildComponent();
        new ChildChildComponent();

        SVGGraphExample.main(null);
    }
}
