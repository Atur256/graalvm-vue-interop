package io.github.atur256.vuewebimageinterop.examples.composition.exposechild;

import io.github.atur256.vuewebimageinterop.api.Component;
import io.github.atur256.webimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;


/**
 * ExposeParentComponent is the root Vue component in this expose demo.
 * <p>
 * Demonstrates:
 * <ul>
 *   <li>Using a template ref to access a child component</li>
 *   <li>Calling an exposed method on the child component</li>
 * </ul>
 */
public class ExposeParentComponent extends Component {

    public ExposeParentComponent() {
        // Vue template: renders a button and the child component with a ref
        this.template = JSString.of("""
                    <div>
                        <h2>Parent Component</h2>
                        <button @click="callChild">Call Child Method</button>
                        <exposedChild ref="childRef" />
                    </div>
                """);

        // Register child component
        this.components = new Components();

        // Vue method bindings
        this.methods = new Methods();
    }

    /**
     * Vue method bindings for parent actions.
     */
    public static class Methods extends JSObject {

        /**
         * Calls the exposed sayHello() method on the child via template ref.
         * <p>
         * Note: must be written entirely in JS due to a bug with GraalVM and Vue — `this` does not get passed correctly.
         */
        public JSFunction callChild = JSFunction.fromBody("this.$refs.childRef.sayHello();");
    }

    /**
     * Registers child component.
     */
    public static class Components extends JSObject {

        public Component exposedChild = new ExposedChildComponent();
    }
}
