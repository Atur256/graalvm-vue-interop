package io.github.atur256.vuewebimageinterop.examples.composition.directivesandattrs;

import io.github.atur256.vuewebimageinterop.api.Component;
import io.github.atur256.webimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.*;


/**
 * DirectiveComponent demonstrates use of custom directives and manual attribute handling.
 * <p>
 * Demonstrates:
 * <ul>
 *   <li>Custom directive registration via {@code directives}</li>
 *   <li>Manual forwarding of non-prop attributes using {@code inheritAttrs = false}</li>
 *   <li>Accessing and printing $attrs from within the component</li>
 *   <li>Dynamic styling via directive binding value</li>
 * </ul>
 */
public class DirectiveComponent extends Component {

    public DirectiveComponent() {
        // Vue template: input uses v-focus, paragraph uses v-color
        this.template = JSString.of("""
                <div style="display: flex; flex-direction: column; gap: 0.75rem; max-width: 400px;">
                  <div>
                    <label for="input" style="white-space: nowrap;">Enter text:</label>
                  </div>
                  <div>
                    <input id="input" v-focus style="padding: 0.25rem; flex: 1;" />
                  </div>
                
                  <p v-color="'red'" style="margin: 0;">This text is red</p>
                </div>
                """);

        // Register custom directives
        this.directives = new Directives();

        // Enables automatic forwarding of non-prop attributes to the root <div> (e.g. class="highlighted")
        // Note: If true, the root <div> inherits attributes like class="highlighted", which may apply styles (e.g. red background)
        this.inheritAttrs = JSBoolean.of(true);
    }

    /**
     * Registers local custom directives available in this component's template.
     */
    public static class Directives extends JSObject {

        /**
         * Auto-focuses the bound element when the component is mounted.
         */
        public JSObject focus = new JSObject() {
            public JSFunction mounted = JSFunction.fromCons(DirectiveComponent::focus);
        };

        /**
         * Applies dynamic font color based on directive binding value.
         */
        public JSObject color = new JSObject() {
            public JSFunction mounted = JSFunction.fromBiCons(DirectiveComponent::bindColor);
            public JSFunction updated = JSFunction.fromBiCons(DirectiveComponent::bindColor);
        };
    }

    // JS-native method: focuses the input element when mounted (used by v-focus directive)
    @JS.Coerce
    @JS(value = "el.focus();")
    public static native void focus(JSObject el);

    // JS-native method: sets the element's text color based on directive binding value (used by v-color directive)
    @JS.Coerce
    @JS(value = "el.style.color = binding.value;")
    public static native void bindColor(JSObject el, JSObject binding);
}