package io.github.atur256.vuewebimageinterop.api;

import io.github.atur256.webimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;


/**
 * Abstract base class for defining Vue components in Java via GraalVM WebImage interop.
 * <p>
 * This class mirrors Vue's Options API structure, allowing to define
 * templates, reactive data, methods, computed properties, and child components.
 * <p>
 * Subclasses override specific fields (e.g., {@link #template}, {@link #methods}, {@link #components})
 * and implement the {@link #data()} method to expose reactive state.
 * <p>
 * Used with {@link Vue}, {@link VueApp}, {@link VueRef}, and {@link VueReactive}.
 */

public abstract class Component extends JSObject {

    /**
     * Vue template as a JSString, typically HTML with Vue bindings.
     */
    public JSString template = null;

    /**
     * Vue methods object: event handlers and logic bound to template actions.
     */
    public JSObject methods = null;

    /**
     * Vue components object: registry of child components used in the template.
     */
    public JSObject components = null;

    /**
     * Vue props object: defines props accepted from parent components.
     */
    public JSObject props = null;

    /**
     * Vue inject object: allows dependency injection from ancestor components.
     */
    public JSObject inject = null;

    /**
     * Vue computed object: defines computed properties derived from reactive state.
     */
    public JSObject computed = null;

    /**
     * Vue provide object: exposes values to descendant components via injection.
     */
    public JSObject provide = null;

    /**
     * Vue data function: returns the reactive state for the component.
     * Bound as a supplier to match Vue's `data: () => ({ ... })` pattern.
     */
    protected JSObject data = JSFunction.fromSupplier(this::data);

    /**
     * Default implementation of the data function.
     * Subclasses override this to return their reactive state.
     */
    public JSObject data() {
        return JSObject.create();
    }
}
