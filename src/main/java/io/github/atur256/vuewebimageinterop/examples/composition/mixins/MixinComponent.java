package io.github.atur256.vuewebimageinterop.examples.composition.mixins;

import io.github.atur256.vuewebimageinterop.api.Component;
import io.github.atur256.webimageinterop.builtin.JSArray;
import org.graalvm.webimage.api.*;
import io.github.atur256.webimageinterop.builtin.JSFunction;


/**
 * MixinComponent demonstrates use of multiple mixins in a Vue component using GraalVM interop.
 * <p>
 * Demonstrates:
 * <ul>
 *   <li>Reactive state and methods injected from multiple mixins</li>
 *   <li>Lifecycle hook merging from mixins and component</li>
 *   <li>Shared and local state management</li>
 * </ul>
 */
public class MixinComponent extends Component {

    public MixinComponent() {
        // Component name used in Vue DevTools
        this.name = JSString.of("MixinDemoComponent");

        // Vue template: displays and updates shared, extra, and local counts
        this.template = JSString.of("""
                    <div>
                        <p>Shared Count: {{ sharedCount }}</p>
                        <p>Extra Count: {{ extraCount }}</p>
                        <p>Local Count: {{ localCount }}</p>
                        <button @click="increment">Increment Shared</button>
                        <button @click="boost">Boost Extra</button>
                    </div>
                """);

        // Register two mixins that inject data, methods, and lifecycle hooks
        this.mixins = JSArray.of(new SharedCountMixin(), new ExtraCountMixin());

        // Component-level lifecycle hook
        this.created = JSFunction.fromRun(() ->
                System.out.println("[Component] Component created!")
        );
    }

    /**
     * Reactive data model for the component.
     */
    @Override
    public JSObject data() {
        return new Data();
    }

    public static class Data extends JSObject {

        // Local state defined in the component
        public JSNumber localCount = JSNumber.of(100);
    }

    /**
     * First mixin: provides sharedCount and increment method.
     */
    public static class SharedCountMixin extends JSObject {

        // Reactive state injected by the mixin
        public JSFunction data = JSFunction.fromSupp(() -> new JSObject() {
            public JSNumber sharedCount = JSNumber.of(0);
        });

        // Method to increment sharedCount
        // Note: Must use raw JS string due to `this` binding limitations in Java lambdas
        public JSObject methods = new JSObject() {
            public JSFunction increment = JSFunction.fromBody("this.sharedCount++");
        };

        // Lifecycle hook triggered when component is created
        public JSFunction created = JSFunction.fromRun(() ->
                System.out.println("[Mixin] SharedCountMixin created!")
        );
    }

    /**
     * Second mixin: provides extraCount and boost method.
     */
    public static class ExtraCountMixin extends JSObject {

        // Reactive state injected by the mixin
        public JSFunction data = JSFunction.fromSupp(() -> new JSObject() {
            public JSNumber extraCount = JSNumber.of(5);
        });

        // Method to boost extraCount by 10
        public JSObject methods = new JSObject() {
            public JSFunction boost = JSFunction.fromBody("this.extraCount += 10");
        };

        // Lifecycle hook triggered when component is created
        public JSFunction created = JSFunction.fromRun(() ->
                System.out.println("[Mixin] ExtraCountMixin created!")
        );
    }
}
