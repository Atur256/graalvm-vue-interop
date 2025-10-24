package io.github.atur256.vuewebimageinterop.examples.reactivity.rendernexttick;

import io.github.atur256.vuewebimageinterop.api.Component;
import io.github.atur256.vuewebimageinterop.api.Vue;
import io.github.atur256.webimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;
import org.graalvm.webimage.api.JSValue;


/**
 * RenderTickComponent is the root Vue component for this GraalVM-based render + nextTick example.
 * <p>
 * Demonstrates:
 * <ul>
 *   <li>Reactive state via {@code Vue.reactive}</li>
 *   <li>Manual virtual DOM generation via {@code Vue.h}</li>
 *   <li>Event-driven updates via {@code @click}</li>
 *   <li>Deferred lifecycle timing via {@code Vue.nextTick}</li>
 * </ul>
 */
public class RenderTickComponent extends Component {

    /**
     * Public render function used by Vue to manually generate the virtual DOM.
     * Must be assigned to enable custom rendering via {@code Vue.h}.
     */
    public JSObject render;

    /**
     * Reactive state object tracked by Vue.
     */
    public JSObject reactiveState;

    /**
     * Render function that returns a virtual DOM tree.
     */
    public JSObject renderVNode;

    public RenderTickComponent() {

        // Initial placeholder template (not used once render function is active)
        this.template = JSString.of("<div id='placeholder'></div>");

        // Create initial state object with default message and style
        InitialState initialState = new InitialState();

        // Make the state reactive so Vue tracks changes
        this.reactiveState = Vue.reactive(initialState);

        // Provide reactive state to Vue's data system
        this.data = JSFunction.fromSupplier(() -> reactiveState);

        // Define a render function using Vue.h to manually construct the virtual DOM
        this.renderVNode = JSFunction.fromSupplier(() -> Vue.h(
                "div",                             // Root <div> element
                new Props(initialState),                // Style props for root
                Vue.h("span",                      // Child <span> element
                        new SpanProps(reactiveState),   // Click handler
                        JSValue.checkedCoerce(reactiveState.get("message"), JSString.class)
                )
        ));

        // Assign the render function to Vue's runtime renderer
        render = renderVNode;
    }

    /**
     * Initial reactive state model.
     * Contains message and style fields used in rendering.
     */
    private static class InitialState extends JSObject {

        public JSString message = JSString.of("I am dynamic!");
        public JSString style = JSString.of("color: blue; font-weight: bold; font-size: 16px;");
    }

    /**
     * Props defines style bindings for the root element.
     */
    private static class Props extends JSObject {

        public JSString style;

        public Props(InitialState initialState) {
            this.style = initialState.style;
        }
    }


    /**
     * SpanProps defines event handlers and bindings for the <span> element.
     * Includes logic to update state immediately and again after nextTick.
     */
    private static class SpanProps extends JSObject {

        public JSFunction onClick;

        public SpanProps(JSObject state) {
            this.onClick = JSFunction.fromRunnable(() -> {
                System.out.println("Span clicked: updating message and style");

                // First update: immediate
                state.set("message", JSString.of("Updated on click!"));
                state.set("style", JSString.of("color: green; font-weight: bold; font-size: 24px;"));

                // Second update: scheduled after DOM update
                Vue.nextTick(JSFunction.fromRunnable(() -> {
                    System.out.println("Next tick: updating message again");
                    state.set("message", JSString.of("Final update after next tick!"));
                }));
            });
        }
    }
}
