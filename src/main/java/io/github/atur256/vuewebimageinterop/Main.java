package io.github.atur256.vuewebimageinterop;


import io.github.atur256.vuewebimageinterop.examples.advanced.shoppinglist.ShoppingListExample;
import io.github.atur256.vuewebimageinterop.examples.advanced.svggraph.SVGGraphExample;
import io.github.atur256.vuewebimageinterop.examples.basics.counter.CounterExample;
import io.github.atur256.vuewebimageinterop.examples.basics.helloworld.HelloWorldExample;
import io.github.atur256.vuewebimageinterop.examples.composition.componenthierarchy.ComponentHierarchyExample;
import io.github.atur256.vuewebimageinterop.examples.composition.componenthierarchy.InjectedMessageComponent;
import io.github.atur256.vuewebimageinterop.examples.composition.componenthierarchy.MessageReceiverComponent;
import io.github.atur256.vuewebimageinterop.examples.composition.componenthierarchy.ParentComponent;
import io.github.atur256.vuewebimageinterop.api.VueApp;
import io.github.atur256.vuewebimageinterop.api.VueRef;
import io.github.atur256.vuewebimageinterop.examples.basics.counter.RootComponent;
import io.github.atur256.vuewebimageinterop.examples.composition.globalcomponentplugin.GlobalComponentPluginExample;
import io.github.atur256.vuewebimageinterop.examples.reactivity.lifecycle.LifecycleHooksExample;
import io.github.atur256.vuewebimageinterop.examples.reactivity.rendernexttick.RenderNextTickExample;
import io.github.atur256.vuewebimageinterop.examples.reactivity.watcheffects.ReactiveWatchExample;


public class Main {

    public static void main(String[] args) throws InterruptedException {

        // TODO: add html and css file for the examples to this project (currently they are in the web-image dir)

        // Note: every Component that does not exist 1:1 in JS needs to be called at least once, otherwise it will be removed by the compiler and therefore fail
        new VueApp();
//        new VueRef();
//        new RootComponent();
//        new ParentComponent();
//        new MessageReceiverComponent();
//        new InjectedMessageComponent();


        // Examples

        // Basics

//        HelloWorldExample.main(null);

//        CounterExample.main(null);

        // Reactive

//        LifecycleHooksExample.main(null);

//        RenderNextTickExample.main(null);

//        ReactiveWatchExample.main(null);

        // Composition

//        ComponentHierarchyExample.main(null);

//        GlobalComponentPluginExample.main(null);

        // Advanced

//        ShoppingListExample.main(null);

        SVGGraphExample.main(null);
    }
}
