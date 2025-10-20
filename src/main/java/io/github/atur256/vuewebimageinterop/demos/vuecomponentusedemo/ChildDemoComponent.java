package io.github.atur256.vuewebimageinterop.demos.vuecomponentusedemo;

import io.github.atur256.vuewebimageinterop.reworkedClasses.Component;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;


public class ChildDemoComponent extends Component {

    public ChildDemoComponent() {
        this.template = JSString.of("<p>Hello from a registered component!</p>");
    }

    public JSObject data() {
        return new ChildDemoComponent.Data();
    }

    private static class Data extends JSObject {

    }
}
