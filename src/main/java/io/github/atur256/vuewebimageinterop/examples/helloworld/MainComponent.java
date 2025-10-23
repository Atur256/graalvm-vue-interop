package io.github.atur256.vuewebimageinterop.examples.helloworld;

import io.github.atur256.vuewebimageinterop.api.Component;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;


/**
 * MainComponent is the root Vue component for the Hello World example.
 * <p>
 * It mirrors the official Vue Hello World example:
 * <a href="https://vuejs.org/examples/#hello-world">vuejs.org/examples/#hello-world</a>
 * <p>
 * This component defines a simple template and reactive data using GraalVM interop.
 */
public class MainComponent extends Component {

    public MainComponent() {

        // Vue template: renders a heading bound to the reactive "message" property
        this.template = JSString.of("""
                <div id="app">
                  <h1>{{ message }}</h1>
                </div>
                """);
    }

    /**
     * Overrides Component.data() to expose reactive state.
     * Defines a single property: "message", bound to the template.
     */
    public JSObject data() {
        return new Data();
    }

    /**
     * Data model for the component.
     * Contains one reactive field: "message".
     */
    private static class Data extends JSObject {

        public String message = "Hello World!";
    }
}
