package io.github.atur256.vuewebimageinterop.demos.simpleDemo;

import io.github.atur256.vuewebimageinterop.reworkedClasses.Component;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;


public class DemoChildComponent extends Component {

    public DemoChildComponent() {
        this.template = JSString.of("""
                <div class="child">
                 <p>Child sees: {{ message }}</p>
                </div>
                """);
    }

    public JSObject data() {
        return new Data();
    }

    private static class Data extends JSObject {

        public JSString message;
    }
}
