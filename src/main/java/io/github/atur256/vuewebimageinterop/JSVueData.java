package io.github.atur256.vuewebimageinterop;

import io.github.atur256.webimageinterop.builtin.JSArray;
import org.graalvm.webimage.api.*;

import java.util.function.Supplier;


public class JSVueData extends JSObject {

    private JSVueData() {
        super();
    }

    @JS.Coerce
    @JS("return function() { return javaFunc(); }")
    public static native JSObject wrapAsDataFunction(Supplier<JSVueData> javaFunc);

    public static Builder builder() {
        return new Builder();
    }

    public static final class Builder {

        private final JSVueData data = new JSVueData();

        public Builder set(String key, String value) {
            data.set(key, JSString.of(value));
            return this;
        }

        public Builder set(String key, int value) {
            data.set(key, JSNumber.of(value));
            return this;
        }

        public Builder set(String key, boolean value) {
            data.set(key, JSBoolean.of(value));
            return this;
        }

        public Builder set(String key, double value) {
            data.set(key, JSNumber.of(value));
            return this;
        }

        public Builder set(String key, JSObject value) {
            data.set(key, value);
            return this;
        }

        public Builder set(String key, JSArray value) {
            data.set(key, value);
            return this;
        }

        public Builder set(String key, Supplier<JSVueData> nestedBuilder) {
            data.set(key, nestedBuilder.get());
            return this;
        }

        public <T> Builder set(String key, JSVueRef<T> value) {
            data.set(key, value.raw());
            return this;
        }

        public JSVueData build() {
            return data;
        }
    }
}
