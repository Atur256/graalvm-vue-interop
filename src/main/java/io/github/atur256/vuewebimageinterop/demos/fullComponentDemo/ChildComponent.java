package io.github.atur256.vuewebimageinterop.demos.fullComponentDemo;

import io.github.atur256.vuewebimageinterop.reworkedClasses.Component;
import org.graalvm.webimage.api.JSNumber;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;


public class ChildComponent extends Component {

    public ChildComponent() {
        this.template = JSString.of("""
                <div class="child">
                    <h2>Child Component</h2>
                    <p>Received Message via props: {{ parentMessage }} ({{ parentCount }}) </p>
                
                    <ChildComponent/>
                  </div>
                """);

        this.components = new Components();
        this.props = new Props();
    }

    private static class Components extends JSObject {

        public Component ChildComponent = new ChildChildComponent();
    }

    public static class Props extends JSObject {

        public JSString parentMessage;
        public JSNumber parentCount;
    }
}
