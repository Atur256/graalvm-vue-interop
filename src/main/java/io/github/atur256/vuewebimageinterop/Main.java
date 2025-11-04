package io.github.atur256.vuewebimageinterop;

import io.github.atur256.vuewebimageinterop.examples.advanced.setup.SetupExample;
import io.github.atur256.vuewebimageinterop.examples.advanced.shoppinglist.viaoptionsapi.ShoppingListExample;
import io.github.atur256.vuewebimageinterop.examples.advanced.svggraph.SVGGraphExample;
import io.github.atur256.vuewebimageinterop.examples.basics.counter.CounterExample;
import io.github.atur256.vuewebimageinterop.examples.basics.helloworld.HelloWorldExample;
import io.github.atur256.vuewebimageinterop.examples.composition.componenthierarchy.ComponentHierarchyExample;
import io.github.atur256.vuewebimageinterop.examples.composition.directivesandattrs.DirectivesAndAttrsExample;
import io.github.atur256.vuewebimageinterop.examples.composition.exposechild.ExposeChildExample;
import io.github.atur256.vuewebimageinterop.examples.composition.globalcomponentplugin.GlobalComponentPluginExample;
import io.github.atur256.vuewebimageinterop.examples.composition.mixins.MixinComponent;
import io.github.atur256.vuewebimageinterop.examples.composition.mixins.MixinsExample;
import io.github.atur256.vuewebimageinterop.examples.reactivity.lifecycle.LifecycleHooksExample;
import io.github.atur256.vuewebimageinterop.examples.reactivity.rendernexttick.RenderNextTickExample;
import io.github.atur256.vuewebimageinterop.examples.reactivity.watcheffects.ReactiveWatchExample;


/**
 * Entry point for generating browser-ready JavaScript via GraalVM WebImage.
 * <p>
 * This main class demonstrates how to call a Vue example from Java so it can be compiled
 * into JavaScript and executed in the browser.
 */
public class Main {

    public static void main(String[] args) {

        // Run a specific example
//        HelloWorldExample.main(args);

        SVGGraphExample.main(args); // TODO:
    }
}
