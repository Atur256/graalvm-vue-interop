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
 *   <li>Manual virtual DOM rendering via {@code Vue.h}</li>
 *   <li>Event-driven updates via {@code @click}</li>
 *   <li>Deferred DOM updates using {@code Vue.nextTick}</li>
 * </ul>
 */
public class RenderTickComponent extends Component {

    /**
     * Public render function for manual virtual DOM generation.
     */
    public JSObject render;

    /**
     * Reactive state tracked by Vue.
     */
    public JSObject reactiveState;

    /**
     * Constructor initializes reactive state and render function.
     */
    public RenderTickComponent() {
        this.template = JSString.of("<div id='placeholder'></div>");

        // Initialize reactive state
        InitialState initialState = new InitialState();
        this.reactiveState = Vue.reactive(initialState);

        // Provide reactive state to Vue's data system
        this.data = JSFunction.fromSupplier(() -> reactiveState);

        // Define render function using Vue.h
        this.render = JSFunction.fromSupplier(() -> Vue.h(
                "div",
                new Props(initialState),
                Vue.h("span", new SpanProps(reactiveState),
                        JSValue.checkedCoerce(reactiveState.get("message"), JSString.class)
                )
        ));
    }

    /**
     * Initial reactive state model.
     */
    private static class InitialState extends JSObject {

        public JSString message = JSString.of("I am dynamic!");
        public JSString style = JSString.of("color: blue; font-weight: bold; font-size: 16px;");
    }

    /**
     * Props for root div element.
     */
    private static class Props extends JSObject {

        public JSString style;

        public Props(InitialState state) {
            this.style = state.style;
        }
    }

    /**
     * SpanProps defines event handlers for the span element.
     */
    private static class SpanProps extends JSObject {

        public JSFunction onClick;

        public SpanProps(JSObject state) {
            this.onClick = JSFunction.fromRunnable(() -> {
                // Immediate update
                state.set("message", JSString.of("Updated on click!"));
                state.set("style", JSString.of("color: green; font-weight: bold; font-size: 24px;"));

                // Deferred update after next tick
                Vue.nextTick(JSFunction.fromRunnable(() -> {
                    state.set("message", JSString.of("Final update after next tick!"));
                }));
            });
        }
    }
}
