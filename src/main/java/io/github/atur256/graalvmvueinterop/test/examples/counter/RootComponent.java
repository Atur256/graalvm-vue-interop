package io.github.atur256.graalvmvueinterop.test.examples.counter;

import io.github.atur256.graalvmvueinterop.test.ComponentTest;
import io.github.atur256.graalvmvueinterop.test.Vue;
import org.graalvm.webimage.api.JSNumber;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;
import org.graalvm.webimage.api.JSValue;


public class RootComponent extends ComponentTest {

    public RootComponent() {

        this.template = JSString.of("""
                    <div class="app">
                        <h1>{{ title }}</h1>
                        <p>Count: {{ count }}</p>
                        <p>Status: {{ status }}</p>
                        <button @click="increment">Increment</button>
                        <button @click="decrement">Decrement</button>
                    </div>
                """);
    }

//    @Vue.Template
//    public String template = """
//                <div class="app">
//                    <h1>{{ title }}</h1>
//                    <p>Count: {{ count }}</p>
//                    <p>Status: {{ status }}</p>
//                    <button @click="increment">Increment</button>
//                    <button @click="decrement">Decrement</button>
//                </div>
//            """;

//    @Vue.Data
//    public String title = "Counter App";
//
//    @Vue.Data
//    public int count = 0;

    @Override
    public JSObject data() {
        return new Data();
    }

    private static class Data extends JSObject {

        public String title = "Counter App";

        public int count = 0;
    }

    @Vue.Method
    public void increment(JSObject data) {
        int current = JSValue.checkedCoerce(data.get("count"), Integer.class);
        data.set("count", current + 1);
    }

    @Vue.Method
    public void decrement(JSObject data) {
        int current = JSValue.checkedCoerce(data.get("count"), Integer.class);
        int decremented = current - 1;
        data.set("count", JSNumber.of(decremented));
    }

    @Vue.Computed
    public JSString status(JSObject data) {
        int value = JSValue.checkedCoerce(data.get("count"), Integer.class);
        return JSString.of(value > 4 ? "High" : (value < 0 ? "Minus" : "Low"));
    }
}
