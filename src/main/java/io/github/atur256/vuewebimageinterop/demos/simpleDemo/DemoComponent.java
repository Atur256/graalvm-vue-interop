package io.github.atur256.vuewebimageinterop.demos.simpleDemo;

import io.github.atur256.vuewebimageinterop.reworkedClasses.Component;
import io.github.atur256.vuewebimageinterop.reworkedClasses.VueApp;
import io.github.atur256.webimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.JSNumber;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;


public class DemoComponent extends Component {

    public DemoComponent() {
        this.template = JSString.of("""
                <div class="app">
                 <h1>Counter App</h1>
                 <p>Count: {{ count }}</p>
                 <p>Status: {{ status }}</p>
                 <button @click="increment">Increment</button>
                 <button @click="decrement">Decrement</button>
                </div>
                """);

        this.methods = new Methods();
    }

    public JSObject data() {
        return new Data();
    }

    private static class Data extends JSObject {

        public JSNumber count = JSNumber.of(0);
        public JSString status = JSString.of("Low");
    }

    private static class Methods extends JSObject {

        public JSFunction increment = JSFunction.fromRunnable(() -> {
            int current = VueApp.getValue("count", Integer.class);
            int incremented = current + 1;
            VueApp.setValue("count", incremented);
            System.out.println("Count incremented to: " + incremented);
            updateStatus(incremented);
        });

        public JSFunction decrement = JSFunction.fromRunnable(() -> {
            int current = VueApp.getValue("count", Integer.class);
            int decremented = current - 1;
            VueApp.setValue("count", decremented);
            System.out.println("Count incremented to: " + decremented);
            updateStatus(decremented);
        });

        private static void updateStatus(int value) {
            VueApp.setValue("status", value + 1 > 5 ? "High" : "Low");
        }
    }
}
