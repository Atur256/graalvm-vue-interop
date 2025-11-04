package io.github.atur256.vuewebimageinterop.examples.composition.componenthierarchy;

import io.github.atur256.vuewebimageinterop.api.Component;
import io.github.atur256.vuewebimageinterop.api.Vue;
import io.github.atur256.webimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.*;


/**
 * ParentComponent is the root Vue component in this GraalVM-based hierarchy example.
 * <p>
 * Demonstrates:
 * <ul>
 *   <li>Reactive state management using {@code Vue.ref}</li>
 *   <li>Computed properties derived from reactive state</li>
 *   <li>Prop passing from parent -> child -> grandchild</li>
 *   <li>Provide/inject pattern for sharing reactive state across components</li>
 *   <li>Event emission from grandchild back to parent</li>
 * </ul>
 */
public class ParentComponent extends Component {

    public ParentComponent() {
        // Component name for Vue devtools and debugging
        this.name = JSString.of("ParentComponent");

        // Vue template: displays reactive state, computed value, and renders the child component
        this.template = JSString.of("""
                    <div class="app">
                        <h1>Shared State & Hierarchy Example</h1>
                        <p>Message from Grandchild Component: {{ grandMessage }}</p>
                        <p>Count: {{ count }}</p>
                        <p>Doubled: {{ doubledCount }}</p>
                        <button @click="increment">Increment</button>
                        <button @click="printComponentName">Print Component Name</button>
                
                        <messageReceiver
                            :parentMessage="message"
                            @childEvent="handleChildEvent"
                        />
                    </div>
                """);

        // Shared reactive count ref used across the component hierarchy
        // Passed to both data() and provide() for local use and injection into descendants
        JSObject countRef = Vue.ref(0);

        // data() is defined via constructor to allow passing the shared count ref
        this.data = JSFunction.fromSupp(() -> new Data(countRef));

        // Vue method bindings for template actions
        this.methods = new Methods();

        // Register child components
        this.components = new Components();

        // Computed properties
        this.computed = new Computed();

        // Provide values for descendants
        this.provide = new Provide(countRef);
    }

    private static class Data extends JSObject {

        public JSObject count;
        public JSString message = JSString.of("Hello from Parent!!!");
        public JSString grandMessage = JSString.of("-");

        public Data(JSObject countRef) {
            this.count = countRef;
        }
    }

    /**
     * Vue methods bound to template actions.
     */
    private static class Methods extends JSObject {

        /**
         * Increments the reactive count value.
         * Note: count is a Vue ref, so we access and mutate its .value field.
         */
        public JSFunction increment = JSFunction.fromThisCons((JSObject data) -> {
            int current = JSValue.checkedCoerce(data.get("count"), Integer.class);
            data.set("count", JSNumber.of(current + 1));
        });

        /**
         * Logs the component name to the browser console for debugging.
         * <p>
         * Note:
         * - Must be written in raw JS due to GraalVM limitations with `this` binding in Java lambdas.
         */
        public JSFunction printComponentName = JSFunction.fromBody("console.log(this.$options.name);");

        /**
         * Updates grandMessage with the value received from the child event.
         */
        public JSFunction handleChildEvent = JSFunction.fromConsWithThis((JSObject data, JSString messageVal) -> {
            String msg = messageVal.asString();
            data.set("grandMessage", msg);
            System.out.println("[Parent] Received message from child: " + msg);
        });
    }

    /**
     * Child components used in template.
     */
    private static class Components extends JSObject {

        public Component messageReceiver = new MessageReceiverComponent();
    }

    /**
     * Computed properties.
     */
    private static class Computed extends JSObject {

        public JSFunction doubledCount = JSFunction.fromFunc((JSObject thisObj) -> {
            int count = JSValue.checkedCoerce(thisObj.get("count"), Integer.class);
            return JSNumber.of(count * 2);
        });
    }

    /**
     * Values provided to descendant components via Vue's provide/inject API.
     * Enables shared reactive state and contextual messaging across the hierarchy.
     */
    private static class Provide extends JSObject {

        public JSString message = JSString.of("Injected message!!!");
        public JSObject count;

        // Assigns the shared count ref to be injected into child and grandchild components
        public Provide(JSObject countRef) {
            this.count = countRef;
        }
    }
}
