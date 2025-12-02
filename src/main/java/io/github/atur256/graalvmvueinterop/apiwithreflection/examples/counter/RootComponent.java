package io.github.atur256.graalvmvueinterop.apiwithreflection.examples.counter;

import io.github.atur256.graalvmvueinterop.apiwithreflection.ComponentWithReflection;
import io.github.atur256.graalvmvueinterop.apiwithreflection.VueWithReflection;
import org.graalvm.webimage.api.JSNumber;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;
import org.graalvm.webimage.api.JSValue;


public class RootComponent extends ComponentWithReflection {

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

    @VueWithReflection.Data
    public String title() {
        return "Counter App";
    }

    @VueWithReflection.Data
    public int count() {
        return 0;
    }

//    @Override
//    public JSObject data() {
//        return new Data();
//    }
//
//    private static class Data extends JSObject {
//
//        public String title = "Counter App";
//
//        public int count = 0;
//    }

    @VueWithReflection.Method
    public void increment(JSObject data) {
        int current = JSValue.checkedCoerce(data.get("count"), Integer.class);
        data.set("count", JSNumber.of(current + 1));
    }

    @VueWithReflection.Method
    public void decrement(JSObject data) {
        int current = JSValue.checkedCoerce(data.get("count"), Integer.class);
        data.set("count", JSNumber.of(current - 1));
    }

    @VueWithReflection.Computed
    public JSString status(JSObject data) {
        int value = JSValue.checkedCoerce(data.get("count"), Integer.class);
        return JSString.of(value > 4 ? "High" : (value < 0 ? "Minus" : "Low"));
    }
}
