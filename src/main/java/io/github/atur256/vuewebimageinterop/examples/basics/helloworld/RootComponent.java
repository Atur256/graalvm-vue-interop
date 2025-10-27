package io.github.atur256.vuewebimageinterop.examples.basics.helloworld;

import io.github.atur256.vuewebimageinterop.api.Component;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;


/**
 * RootComponent is the root Vue component for this GraalVM-based Hello World example.
 * <p>
 * This component overrides key fields from the abstract {@code Component} class:
 * {@code template} and {@code data()}.
 * <p>
 * The template renders a simple heading bound to a reactive message,
 * demonstrating minimal Vue interop in Java.
 */
public class RootComponent extends Component {

    public RootComponent() {
        // Define the Vue template: a heading bound to the reactive "message" property
        this.template = JSString.of("""
                <div id="app">
                  <h1>{{ message }}</h1>
                </div>
                """);
    }

    /**
     * Overrides {@code Component.data()} to provide reactive state.
     * <p>
     * Exposes the following reactive property:
     * <ul>
     *   <li>{@code message} – the text displayed inside the component's main heading (h1 element)</li>
     * </ul>
     */
    public JSObject data() {
        return new Data();
    }

    /**
     * Defines the reactive state for this component.
     * Returned by the {@code data()} method.
     */
    private static class Data extends JSObject {

        /**
         * The message displayed in the template.
         */
        public String message = "Hello World!";
    }
}
