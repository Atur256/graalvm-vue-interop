package io.github.atur256.vuewebimageinterop.demos.vuecomponentusedemo;

import io.github.atur256.vuewebimageinterop.reworkedClasses.Component;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;


public class DemoComponent extends Component {

    public DemoComponent() {
        this.template = JSString.of("""
                <div>
                  <h2>VueApp with Component & Plugin</h2>
                  <hello-world></hello-world>
                </div>
                """);
    }

    public JSObject data() {
        return new DemoComponent.Data();
    }

    private static class Data extends JSObject {

    }
}
