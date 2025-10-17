package io.github.atur256.vuewebimageinterop.demos.fullComponentDemo;

import io.github.atur256.vuewebimageinterop.reworkedClasses.Component;
import io.github.atur256.webimageinterop.builtin.JSArray;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;


public class ChildChildComponent extends Component {

    public ChildChildComponent() {
        this.template = JSString.of("""
                <div class="child">
                    <h2>Child Child Component</h2>
                    <p>Received Message via inject: {{ message }} ({{ count }}) </p>
                  </div>
                """);

        this.inject = JSArray.of("message", "count");
    }
}