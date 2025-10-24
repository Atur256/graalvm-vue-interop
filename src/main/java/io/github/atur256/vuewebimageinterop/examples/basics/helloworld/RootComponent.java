package io.github.atur256.vuewebimageinterop.examples.basics.helloworld;

import io.github.atur256.vuewebimageinterop.api.Component;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;


/**
 * RootComponent is the root Vue component for this GraalVM-based Hello World example.
 * <p>
 * It mirrors the official Vue Hello World demo:
 * <a href="https://vuejs.org/examples/#hello-world">vuejs.org/examples/#hello-world</a>
 * <p>
 * This component overrides key fields from the abstract {@code Component} class: {@code template} and {@code data}.
 * The template renders a simple heading bound to a reactive message, showcasing minimal Vue interop in Java.
 */
public class RootComponent extends Component {

    public RootComponent() {

        // Vue template: renders a heading bound to the reactive "message" property
        this.template = JSString.of("""
                <div id="app">
                  <h1>{{ message }}</h1>
                </div>
                """);
    }

    /**
     * Overrides Component.data() to expose reactive state:
     * - message: bound to the <h1> element in the template
     */
    public JSObject data() {
        return new Data();
    }

    /**
     * Data defines the reactive state model for this component.
     * It is returned by the overridden data() method.
     */
    private static class Data extends JSObject {

        public String message = "Hello World!";
    }
}
