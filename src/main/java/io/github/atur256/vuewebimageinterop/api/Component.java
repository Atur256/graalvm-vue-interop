package io.github.atur256.vuewebimageinterop.api;

import io.github.atur256.webimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;


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
     * Vue data function, returning the reactive state for this component.
     * Bound as a supplier to match Vue's {@code data: () => ({ ... })} pattern.
     */
    protected JSObject data = JSFunction.fromSupplier(this::data);

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
}
