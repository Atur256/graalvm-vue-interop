package io.github.atur256.vuewebimageinterop.demos.childcomponentdemo;

import io.github.atur256.vuewebimageinterop.reworkedClasses.Component;
import io.github.atur256.vuewebimageinterop.reworkedClasses.VueApp;
import io.github.atur256.webimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;
import org.graalvm.webimage.api.JSValue;


public class DemoComponent extends Component {

    public DemoComponent() {
        this.template = JSString.of("""
                <div class="app">
                    <h1>Parent Component</h1>
                    <p>Message from child: {{ messageFromChild }}</p>
                
                    <ChildComponent
                      :parentMessage="parentMessage"
                      @childEvent="handleChildEvent"
                    />
                  </div>
                """);

        this.methods = new Methods();
        this.components = new Components();
    }

    public JSObject data() {
        return new Data();
    }

    private static class Data extends JSObject {

        public JSString parentMessage = JSString.of("Hello from the Parent");
        public JSString messageFromChild = JSString.of("");
    }

    private static class Methods extends JSObject {

        public JSFunction handleChildEvent = JSFunction.fromConsumer(payload -> {
            VueApp.setValue("messageFromChild", JSValue.checkedCoerce(payload, String.class));
        });
    }

    private static class Components extends JSObject {

        public Component ChildComponent = new ChildComponent();
    }
}
