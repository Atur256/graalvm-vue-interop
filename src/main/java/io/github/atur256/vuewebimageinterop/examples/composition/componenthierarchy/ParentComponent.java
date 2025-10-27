package io.github.atur256.vuewebimageinterop.examples.composition.componenthierarchy;

import io.github.atur256.vuewebimageinterop.api.Component;
import io.github.atur256.vuewebimageinterop.api.Vue;
import io.github.atur256.vuewebimageinterop.api.VueApp;
import io.github.atur256.vuewebimageinterop.api.VueRef;
import io.github.atur256.webimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.*;


/**
 * ParentComponent is the root Vue component for this GraalVM-based component hierarchy example.
 * <p>
 * Demonstrates:
 * <ul>
 *   <li>Reactive state via {@code Vue.ref}</li>
 *   <li>Computed property: doubled count</li>
 *   <li>Prop passing from parent → child → grandchild</li>
 *   <li>Provide/inject pattern for shared state</li>
 *   <li>Event emission from grandchild back to parent</li>
 * </ul>
 */
public class ParentComponent extends Component {

    private static final VueRef sharedCountRef = Vue.ref(0);

    public ParentComponent() {
        // Vue template: displays count, doubled value, child component
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

        // Vue methods
        this.methods = new Methods();

        // Register child components
        this.components = new Components();

        // Computed properties
        this.computed = new Computed();

        // Provide values for descendants
        this.provide = new Provide();
    }

    /**
     * Reactive state exposed to template.
     */
    public JSObject data() {
        return new Data();
    }

    private static class Data extends JSObject {

        public JSObject count = sharedCountRef.getRef();
        public JSString message = JSString.of("Hello from Parent!!!");
        public JSString grandMessage = JSString.of("-");
    }

    /**
     * Vue methods bound to template actions.
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
     * Child components used in template.
     */
    private static class Components extends JSObject {

        public Component messageReceiver = new MessageReceiverComponent();
    }

    /**
     * Computed properties.
     */
    private static class Computed extends JSObject {

        public JSFunction doubledCount = JSFunction.fromJavaFunction((JSObject thisObj) -> {
            int count = JSValue.checkedCoerce(thisObj.get("count"), Integer.class);
            return JSNumber.of(count * 2);
        });
    }

    /**
     * Values provided to descendant components via inject.
     */
    private static class Provide extends JSObject {

        public JSString message = JSString.of("Injected message!!!");
        public JSObject count = sharedCountRef.getRef();
    }
}
