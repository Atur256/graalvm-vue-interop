package io.github.atur256.vuewebimageinterop.demos.fullComponentDemo;

import io.github.atur256.vuewebimageinterop.reworkedClasses.Component;
import io.github.atur256.vuewebimageinterop.reworkedClasses.Vue;
import io.github.atur256.vuewebimageinterop.reworkedClasses.VueApp;
import io.github.atur256.vuewebimageinterop.reworkedClasses.VueRef;
import io.github.atur256.webimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.*;


public class DemoComponent extends Component {

    private static final VueRef countRef = Vue.ref(JSNumber.of(0));

    public DemoComponent() {
        this.template = JSString.of("""
                <div class="app">
                 <h1>Test App</h1>
                 <p>Count: {{ count }}</p>
                 <p>Doubled: {{ doubledCount }}</p>
                 <button @click="increment">Increment</button>
                
                 <ChildComponent
                    :parentMessage="message"
                    :parentCount="count"
                    />
                </div>
                """);

        this.methods = new Methods();
        this.components = new Components();
        this.computed = new Computed();
        this.provide = new Provide();
    }

    public JSObject data() {
        return new Data();
    }

    private static class Data extends JSObject {

        public JSNumber count = JSNumber.of(countRef.get(Integer.class));
        public JSString message = JSString.of("Hello from Parent");
    }

    private static class Methods extends JSObject {

        public JSFunction increment = JSFunction.fromRunnable(() -> {
            int current = VueApp.getValue("count", Integer.class);
            int incremented = current + 1;
            VueApp.setValue("count", incremented);
            countRef.set(incremented);
        });
    }

    private static class Components extends JSObject {

        public Component ChildComponent = new ChildComponent();
    }

    private static class Computed extends JSObject {

        public JSFunction doubledCount = JSFunction.fromJavaFunction((JSObject thisObj) -> {
            int count = JSValue.checkedCoerce(thisObj.get("count"), Integer.class);
            return JSNumber.of(count * 2);
        });
    }

    private static class Provide extends JSObject {

        public JSString message = JSString.of("Test message!!!");
        public JSObject count = countRef.unwrap();
    }
}
