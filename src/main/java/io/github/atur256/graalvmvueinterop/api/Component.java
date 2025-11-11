/*
 * Copyright (c) 2025 Arthur Schwaiger
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.atur256.graalvmvueinterop.api;

import io.github.atur256.graalvmwebimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;
import org.graalvm.webimage.api.JSValue;


/**
 * Abstract base class for defining Vue components in Java via GraalVM WebImage interop.
 * <p>
 * This class mirrors Vue's Options API, allowing developers to define:
 * <ul>
 *   <li>Templates and rendering logic</li>
 *   <li>Reactive state and computed properties</li>
 *   <li>Methods, watchers, and lifecycle hooks</li>
 *   <li>Props, events, and dependency injection</li>
 *   <li>Custom directives and attribute inheritance</li>
 * </ul>
 * <p>
 * Subclasses should override specific fields (e.g., {@link #template}, {@link #methods}, {@link #components})
 * and implement the {@link #data()} method to expose reactive state.
 * <p>
 * Used with {@link Vue} and {@link VueApp}.
 *
 * @see Vue
 * @see VueApp
 */
public abstract class Component extends JSObject {

    // Identification & composition

    /**
     * Optional name for the component.
     * Useful for debugging, devtools, and recursive components.
     */
    public JSString name = null;

    /**
     * Mixins to merge into this component.
     */
    public JSObject mixins = null;

    /**
     * Controls whether non-prop attributes are automatically inherited by the root element.
     */
    public JSValue inheritAttrs = null;

    /**
     * Controls which internal properties are exposed to parent components via template refs.
     */
    public JSObject expose = null;

    // Template & rendering

    /**
     * Vue template as a {@link JSString}, typically HTML with Vue bindings.
     */
    public JSString template = null;

    /**
     * Custom render function for advanced rendering logic.
     */
    public JSFunction render = null;

    // Reactivity & state

    /**
     * Defines props accepted from parent components.
     */
    public JSObject props = null;

    /**
     * Vue data function, returning the reactive state for this component.
     * Bound as a supplier to match Vue's {@code data: () => ({ ... })} pattern.
     */
    protected JSObject data = JSFunction.fromSupp(this::data);

    /**
     * Returns the reactive state for this component.
     * Subclasses should override this method to provide the component's reactive properties.
     */
    public JSObject data() {
        return JSObject.create();
    }

    /**
     * Defines computed properties derived from reactive state.
     */
    public JSObject computed = null;

    /**
     * Object holding Vue methods (event handlers, template logic).
     */
    public JSObject methods = null;

    /**
     * Declares watchers for reactive properties.
     * Each entry maps a property name to a function receiving {@code (newVal, oldVal)}.
     */
    public JSObject watch = null;

    // Composition API

    /**
     * Composition API setup function.
     * <p>
     * Allows defining reactive state, methods, and computed properties using {@code Vue.ref()}, {@code Vue.reactive()}, etc.
     * Returned bindings are exposed to the template and component context.
     */
    protected JSObject setup = JSFunction.fromSupp(this::setup);

    /**
     * Returns Composition API bindings for this component.
     * <p>
     * Subclasses can override to expose reactive state and logic.
     */
    public JSValue setup() {
        return JSValue.undefined();
    }

    // Dependency injection

    /**
     * Exposes values to descendant components via dependency injection.
     */
    public JSObject provide = null;

    /**
     * Allows dependency injection from ancestor components.
     */
    public JSObject inject = null;

    // Component hierarchy

    /**
     * Registry of child components used in this component's template.
     */
    public JSObject components = null;

    /**
     * Registers local custom directives available in this component's template.
     */
    public JSObject directives = null;

    /**
     * Declares custom events this component may emit.
     * Used to validate emitted events and improve tooling support.
     */
    public JSObject emits = null;

    // Lifecycle

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
     */
    public JSFunction beforeUnmount = null;

    /**
     * Called after the component has been unmounted.
     */
    public JSFunction unmounted = null;

    /**
     * Called when an error is captured from a child component.
     */
    public JSFunction errorCaptured = null;

    /**
     * Called when a keep-alive component is activated.
     */
    public JSFunction activated = null;

    /**
     * Called when a keep-alive component is deactivated.
     */
    public JSFunction deactivated = null;
}
