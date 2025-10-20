package io.github.atur256.vuewebimageinterop.demos.handnexttickdemo;

import io.github.atur256.vuewebimageinterop.reworkedClasses.Component;
import io.github.atur256.vuewebimageinterop.reworkedClasses.Vue;
import io.github.atur256.webimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;
import org.graalvm.webimage.api.JSValue;


public class DemoComponent extends Component {

    // Reactive state object
    public JSObject state;

    // Render function (used to generate virtual DOM nodes)
    public JSObject render;

    public DemoComponent() {
        // Initial template placeholder
        this.template = JSString.of("<div id='placeholder'></div>");

        // Create a plain JSObject to hold initial reactive state
        PlainObject plainState = new PlainObject();

        // Make the plain object reactive so Vue will track its changes
        this.state = Vue.reactive(plainState);

        // Provide the reactive state to Vue's data system
        this.data = JSFunction.fromSupplier(() -> state);

        // Render function that generates virtual DOM nodes using Vue.h
        this.render = JSFunction.fromSupplier(() -> Vue.h(
                "div",                                                         // Root element
                new Props(plainState),                                              // Props (e.g., style) for root element
                Vue.h("span",                                                  // Child element
                        JSObject.create(),                                          // No special props for child
                        JSValue.checkedCoerce(state.get("message"), JSString.class) // Text content from state
                )
        ));

        // Use Vue.nextTick to update reactive state after the initial render
        Vue.nextTick(JSFunction.fromRunnable(() -> {
            System.out.println("Next tick: updating message and style dynamically");
            state.set("message", JSString.of("I am dynamic and updated!"));                     // Update message
            state.set("style", JSString.of("color: red; font-weight: bold; font-size: 20px;")); // Update style
        }));

        // Another nextTick to demonstrate chained updates
        Vue.nextTick(JSFunction.fromRunnable(() -> {
            System.out.println("Second tick: updating message again");
            state.set("message", JSString.of("Final dynamic text after second tick!")); // Update message again
        }));
    }

    // Plain JSObject class to hold the initial reactive state
    private static class PlainObject extends JSObject {

        public JSString message = JSString.of("I am dynamic!");
        public JSString style = JSString.of("color: blue; font-weight: bold; font-size: 16px;");
    }

    // Plain JSObject class to hold the initial reactive state
    private static class Props extends JSObject {

        public JSString style;

        public Props(PlainObject plainObject) {
            style = plainObject.style;
        }
    }
}