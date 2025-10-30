//package io.github.atur256.vuewebimageinterop.examples.basics.counter;
//
//import io.github.atur256.vuewebimageinterop.api.Component;
//import io.github.atur256.vuewebimageinterop.api.Vue;
//import io.github.atur256.vuewebimageinterop.api.VueApp;
//import io.github.atur256.webimageinterop.builtin.JSFunction;
//import org.graalvm.webimage.api.JSNumber;
//import org.graalvm.webimage.api.JSObject;
//import org.graalvm.webimage.api.JSString;
//import org.graalvm.webimage.api.JSValue;
//
//import java.util.HashMap;
//import java.util.Map;
//
//
/// **
// * RootComponent is the root Vue component for this GraalVM-based counter example.
// * <p>
// * Demonstrates:
// * <ul>
// *   <li>Reactive state via {@code data()}</li>
// *   <li>Event handling via {@code methods}</li>
// * </ul>
// */
//public class RootComponent extends Component {
//
//    public RootComponent() {
//        // Vue template: displays count, status, and increment/decrement buttons
//        this.template = JSString.of("""
//                    <div class="app">
//                        <h1>Counter App</h1>
//                        <p>Count: {{ count }}</p>
//                        <p>Status: {{ status }}</p>
//                        <p>Map: map.get("Test")</p>
//                        <button @click="increment">Increment</button>
//                        <button @click="decrement">Decrement</button>
//                    </div>
//                """);
//
//        // Bind Vue methods
//        this.methods = new Methods();
//    }
//
//    /**
//     * Provides reactive state for this component:
//     * <ul>
//     *   <li>{@code count} – numeric counter</li>
//     *   <li>{@code status} – label based on current count</li>
//     * </ul>
//     */
//    public JSObject data() {
//        return new Data();
//    }
//
//    /**
//     * Reactive state model for the counter component.
//     */
//    private static class Data extends JSObject {
//
//        public JSNumber count = JSNumber.of(0);
//        public JSString status = JSString.of("Low");
//    }
//
//    /**
//     * Vue methods for incrementing/decrementing the counter.
//     */
//    private static class Methods extends JSObject {
//
//        public JSFunction increment = JSFunction.fromThisJSCons((JSObject data) -> {
//            int current = JSValue.checkedCoerce(data.get("count"), Integer.class);
//            int incremented = current + 1;
//            data.set("count", incremented);
//            updateStatus(data, incremented);
//        });
//
//        public JSFunction decrement = JSFunction.fromThisJSCons((JSObject data) -> {
//            int current = JSValue.checkedCoerce(data.get("count"), Integer.class);
//            int decremented = current - 1;
//            data.set("count", decremented);
//            updateStatus(data, decremented);
//        });
//
//        /**
//         * Updates the status label based on current count.
//         */
//        private static void updateStatus(JSObject data, int value) {
//            data.set("status", value > 4 ? "High" : (value < 0 ? "Minus" : "Low"));
//        }
//    }
//}

package io.github.atur256.vuewebimageinterop.examples.basics.counter;

import io.github.atur256.vuewebimageinterop.api.Component;
import io.github.atur256.webimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.*;


public class RootComponent extends Component {

    public RootComponent() {
        this.name = JSString.of("RootComponent");

        this.template = JSString.of("""
                    <div class="app">
                        <h1>{{ title }}</h1>
                        <p>Count: {{ count }}</p>
                        <p>Status: {{ status }}</p>
                        <button @click="increment">Increment</button>
                        <button @click="decrement">Decrement</button>
                    </div>
                """);

        this.props = new Props();

        this.methods = new Methods();
        this.computed = new Computed();
        this.watch = new Watchers();

        this.provide = new Provide();
        this.inject = new Inject(); // expects theme from ancestor

        this.emits = new Emits();

        this.directives = new Directives();

        this.inheritAttrs = JSBoolean.of(false);

        this.expose = new Expose();

        this.beforeCreate = JSFunction.fromRun(() -> System.out.println("Called before RootComponent is created"));

        this.created = JSFunction.fromRun(() -> System.out.println("RootComponent created"));

        this.beforeMount = JSFunction.fromRun(() -> System.out.println("Called before RootComponent is mounted"));

        this.mounted = JSFunction.fromRun(() -> System.out.println("RootComponent mounted"));

        this.beforeUpdate = JSFunction.fromRun(() -> System.out.println("Called before RootComponent is updated"));

        this.updated = JSFunction.fromRun(() -> System.out.println("RootComponent updated"));

        this.beforeUnmount = JSFunction.fromRun(() -> System.out.println("Called before RootComponent is unmounted"));

        this.unmounted = JSFunction.fromRun(() -> System.out.println("RootComponent unmounted"));


    }

    public JSObject data() {
        return new Data();
    }

    private static class Data extends JSObject {

        public JSNumber count = JSNumber.of(0);
        public JSString status = JSString.of("Low");
    }

    private static class Props extends JSObject {

        public JSString title = JSString.of("Counter App");
    }

    private static class Methods extends JSObject {

        public JSFunction increment = JSFunction.fromThisJSCons((JSObject data) -> {
            int current = JSValue.checkedCoerce(data.get("count"), Integer.class);
            int incremented = current + 1;
            data.set("count", JSNumber.of(incremented));
            updateStatus(data, incremented);
//            JS.callVoid("console.log", JSString.of("Emitting countChanged"), JSNumber.of(incremented));
        });

        public JSFunction decrement = JSFunction.fromThisJSCons((JSObject data) -> {
            int current = JSValue.checkedCoerce(data.get("count"), Integer.class);
            int decremented = current - 1;
            data.set("count", JSNumber.of(decremented));
            updateStatus(data, decremented);
        });

        private static void updateStatus(JSObject data, int value) {
            data.set("status", value > 4 ? "High" : (value < 0 ? "Minus" : "Low"));
        }
    }

    private static class Computed extends JSObject {

        public JSFunction doubled = JSFunction.fromThisJSFunc((JSObject data) -> {
            int count = JSValue.checkedCoerce(data.get("count"), Integer.class);
            return JSNumber.of(count * 2);
        });
    }

    private static class Watchers extends JSObject {

        public JSFunction count = JSFunction.fromJSBiCons((JSNumber newVal, JSNumber oldVal) -> {
            int newCount = newVal.asInt();
            int oldCount = oldVal.asInt();
            System.out.println("[watch] Count changed from " + oldCount + " to " + newCount);
        });
    }

    private static class Provide extends JSObject {

        public JSString theme = JSString.of("dark");
    }

    private static class Inject extends JSObject {
//        public JSBoolean theme = JSBoolean.of(true);
    }

    private static class Emits extends JSObject {

        public JSBoolean countChanged = JSBoolean.of(true);
    }

    private static class Directives extends JSObject {

        public JSFunction focus = JSFunction.fromRun(() ->
                System.out.println("focus directive triggered")
        );
    }


    private static class Expose extends JSObject {

        public JSBoolean count = JSBoolean.of(true);
    }

}
