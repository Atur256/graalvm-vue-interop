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

        // Important: Vue components that do not have a direct 1:1 mapping in JavaScript
        // must be instantiated at least once in Java to ensure they are retained during compilation.
        // Otherwise, they may be stripped out by the compiler as unused code.
        new VueApp();


        // === Examples ===

        // === Basics ===
//        HelloWorldExample.main(null);         // Simple "Hello World" component

//        CounterExample.main(null);            // Reactive counter with VueRef

//        RootComponent.main(null);             // Root wrapper for counter example

        // === Reactivity ===
//        LifecycleHooksExample.main(null);     // Demonstrates Vue lifecycle hooks

//        RenderNextTickExample.main(null);     // Shows render timing with nextTick

//        ReactiveWatchExample.main(null);      // WatchEffect and reactive state

        // === Composition API ===
//        ComponentHierarchyExample.main(null); // Parent-child component structure

//        GlobalComponentPluginExample.main(null); // Global plugin registration

        // === Advanced Examples ===
//        ShoppingListExample.main(null);       // Interactive shopping list with state

        SVGGraphExample.main(null);        // A dynamic SVG graph rendered via Vue
    }
}
