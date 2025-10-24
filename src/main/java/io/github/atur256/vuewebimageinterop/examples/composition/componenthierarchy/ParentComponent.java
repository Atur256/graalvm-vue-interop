package io.github.atur256.vuewebimageinterop.examples.composition.componenthierarchy;

import io.github.atur256.vuewebimageinterop.api.Component;
import io.github.atur256.vuewebimageinterop.api.Vue;
import io.github.atur256.vuewebimageinterop.api.VueApp;
import io.github.atur256.vuewebimageinterop.api.VueRef;
import io.github.atur256.webimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.*;


/**
 * ParentComponent is the root Vue component for this GraalVM-based hierarchy example.
 * <p>
 * Demonstrates:
 * <ul>
 *   <li>Reactive state via {@code Vue.ref}</li>
 *   <li>Computed property: doubled count</li>
 *   <li>Prop passing to child</li>
 *   <li>Provide/inject for shared state</li>
 * </ul>
 */
public class ParentComponent extends Component {

    // Shared reactive count used across component hierarchy
    private static final VueRef sharedCountRef = Vue.ref(0);

    public ParentComponent() {

        // Vue template: displays count, computed value, and renders child component with props
        this.template = JSString.of("""
                <div class="app">
                 <h1>Shared State & Hierarchy Example</h1>
                 <p>Message from Grandchild Component: {{ grandMessage }}</p>
                 <p>Count: {{ count }}</p>
                 <p>Doubled: {{ doubledCount }}</p>
                 <button @click="increment">Increment</button>
                
                 <messageReceiver
                    :parentMessage="message"
                    :parentCount="count"
                    />
                </div>
                """);

        // Vue method bindings: increment logic
        this.methods = new Methods();

        // Register child component <messageReceiver>
        this.components = new Components();

        // Define computed property: doubledCount
        this.computed = new Computed();

        // Provide values to descendants via inject
        this.provide = new Provide();
    }

    /**
     * Overrides Component.data() to expose reactive state:
     * - count: shared ref value
     * - message: static greeting
     * - grandMessage: updated by grandchild via event
     */
    public JSObject data() {
        return new Data();
    }

    /**
     * Data defines the reactive state model for this component.
     */
    private static class Data extends JSObject {

        public JSObject count = sharedCountRef.getRef();
        public JSString message = JSString.of("Hello from Parent!!!");
        public JSString grandMessage = JSString.of("-");
    }

    /**
     * Methods defines Vue event handlers.
     * These are bound to template actions via @click.
     */
    private static class Methods extends JSObject {

        public JSFunction increment = JSFunction.fromRunnable(() -> {
            int current = VueApp.getValue("count", Integer.class);
            int incremented = current + 1;
            VueApp.setValue("count", incremented);
            sharedCountRef.set(incremented);
        });
    }

    /**
     * Components registers child components used in the template.
     */
    private static class Components extends JSObject {

        public Component messageReceiver = new MessageReceiverComponent();
    }

    /**
     * Components registers child components used in the template.
     */
    private static class Computed extends JSObject {

        public JSFunction doubledCount = JSFunction.fromJavaFunction((JSObject thisObj) -> {
            int count = JSValue.checkedCoerce(thisObj.get("count"), Integer.class);
            return JSNumber.of(count * 2);
        });
    }

    /**
     * Provide defines values made available to descendant components via inject.
     */
    private static class Provide extends JSObject {

        public JSString message = JSString.of("Injected message!!!");
        public JSObject count = sharedCountRef.getRef();
    }
}
