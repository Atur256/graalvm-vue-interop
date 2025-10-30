package io.github.atur256.vuewebimageinterop.api;

import io.github.atur256.webimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.JS;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;
import org.graalvm.webimage.api.JSValue;

import java.util.Arrays;
import java.util.function.Function;
import java.util.function.Supplier;


/**
 * Abstract base class for defining Vue components in Java via GraalVM WebImage interop.
 * <p>
 * This class mirrors Vue's Options API, allowing developers to define:
 * <ul>
 *   <li>Templates</li>
 *   <li>Reactive data</li>
 *   <li>Methods</li>
 *   <li>Computed properties</li>
 *   <li>Child components</li>
 *   <li>Props and event emissions</li>
 *   <li>Dependency injection and provisioning</li>
 *   <li>Watchers and reactive effects</li>
 *   <li>Custom directives and attribute inheritance</li>
 *   <li>Lifecycle hooks</li>
 * </ul>
 * <p>
 * Subclasses should override specific fields (e.g., {@link #template}, {@link #methods}, {@link #components})
 * and implement the {@link #data()} method to expose reactive state.
 * <p>
 * Used with {@link Vue}, {@link VueApp}, {@link VueRef}, and {@link VueReactive}.
 *
 * @see Vue
 * @see VueApp
 * @see VueRef
 * @see VueReactive
 */
public abstract class Component extends JSObject {

    /**
     * Vue template as a {@link JSString}, typically HTML with Vue bindings.
     */
    public JSString template = null;

    /**
     * Vue data function, returning the reactive state for this component.
     * Bound as a supplier to match Vue's {@code data: () => ({ ... })} pattern.
     */
    protected JSObject data = JSFunction.fromSupp(this::data);

    /**
     * Returns the reactive state for this component.
     * <p>
     * Called internally by the WebImage runtime to initialize reactive data.
     * Subclasses should override this method to provide the component's reactive properties.
     *
     * @return a {@link JSObject} representing the component's reactive state
     */
    public JSObject data() {
        return JSObject.create();
    }

    /**
     * Object holding Vue methods (event handlers, template logic).
     */
    public JSObject methods = null;

    /**
     * Registry of child components used in this component's template.
     */
    public JSObject components = null;

    /**
     * Defines props accepted from parent components.
     */
    public JSObject props = null;

    /**
     * Allows dependency injection from ancestor components.
     */
    public JSObject inject = null;

    /**
     * Defines computed properties derived from reactive state.
     */
    public JSObject computed = null;

    /**
     * Exposes values to descendant components via dependency injection.
     */
    public JSObject provide = null;

    /**
     * Declares watchers for reactive properties.
     * Each entry maps a property name to a function receiving {@code (newVal, oldVal)}.
     */
    public JSObject watch = null;

    /**
     * Declares custom events this component may emit.
     * Used to validate emitted events and improve tooling support.
     */
    public JSObject emits = null;

    /**
     * Registers local custom directives available in this component's template.
     */
    public JSObject directives = null;

    /**
     * Controls which internal properties are exposed to parent components via template refs.
     * Vue 3 only.
     */
    public JSObject expose = null;

    /**
     * Controls whether non-prop attributes are automatically inherited by the root element.
     * Vue 3 only.
     */
    public JSValue inheritAttrs = null;

    /**
     * Optional name for the component.
     * Useful for debugging, recursive components, and devtools.
     */
    public JSString name = null;

    // Lifecycle hooks

    /**
     * Called synchronously after the instance is initialized, before data observation and event setup.
     */
    public JSFunction beforeCreate = null;

    /**
     * Called after the instance is created, with reactive data and events initialized.
     */
    public JSFunction created = null;

    /**
     * Called right before the component is mounted to the DOM.
     */
    public JSFunction beforeMount = null;

    /**
     * Called after the component has been mounted to the DOM.
     */
    public JSFunction mounted = null;

    /**
     * Called when reactive data changes but before the DOM is patched.
     */
    public JSFunction beforeUpdate = null;

    /**
     * Called after the DOM has been updated following reactive changes.
     */
    public JSFunction updated = null;

    /**
     * Called before the component is unmounted and its effects are torn down.
     * Vue 3 only.
     */
    public JSFunction beforeUnmount = null;

    /**
     * Called after the component has been unmounted.
     * Vue 3 only.
     */
    public JSFunction unmounted = null;
}

// TODO: untested:


//    public JSObject emits = null;

//    public JSObject directives = null;

//    public JSObject expose = null;

//    public JSValue inheritAttrs = null;



// TODO: tested:

//    public JSString template = null;

//    protected JSObject data = JSFunction.fromSupp(this::data);

//    public JSObject data() {
//        return JSObject.create();
//    }

//    public JSObject methods = null;

//    public JSObject components = null;

//    public JSFunction beforeCreate = null;

//    public JSFunction created = null;

//    public JSFunction beforeMount = null;

//    public JSFunction mounted = null;

//    public JSFunction beforeUpdate = null;

//    public JSFunction updated = null;

//    public JSFunction beforeUnmount = null;

//    public JSFunction unmounted = null;

//    public JSObject watch = null;

//    public JSObject computed = null;

//    public JSObject provide = null;

//    public JSObject props = null;

//    public JSString name = null;

//    public JSObject inject = null;