package io.github.atur256.vuewebimageinterop.demos.reactivedemo;

import io.github.atur256.vuewebimageinterop.reworkedClasses.Component;
import io.github.atur256.vuewebimageinterop.reworkedClasses.VueReactive;
import io.github.atur256.webimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;


public class DemoComponent extends Component {

    public VueReactive reactiveState;

    public DemoComponent(VueReactive reactiveState) {
        this.reactiveState = reactiveState;

        this.template = JSString.of("""
                    <div class="app">
                      <h2>Reactive Demo</h2>
                      <p>Counter: {{ state.counter }}</p>
                      <p>{{ state.message }}</p>
                      <button @click="increment">Increment</button>
                    </div>
                """);

        this.methods = new Methods(reactiveState);
    }

    public JSObject data() {
        return new Data(reactiveState);
    }

    private static class Data extends JSObject {

        public JSObject state;

        public Data(VueReactive reactiveState) {
            this.state = reactiveState.unwrap();
        }
    }

    private static class Methods extends JSObject {

        public JSFunction increment;

        public Methods(VueReactive reactiveState) {
            increment = JSFunction.fromRunnable(() -> {
                int current = reactiveState.get("counter", Integer.class);
                reactiveState.set("counter", current + 1);
                System.out.println("Counter incremented: " + (current + 1));
            });
        }
    }
}
