package io.github.atur256.vuewebimageinterop;

import io.github.atur256.webimageinterop.builtin.JSArray;
import io.github.atur256.webimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;
import org.graalvm.webimage.api.JSValue;


public class JSVueOptions extends JSObject {

    private JSVueOptions() {
        super();
    }

    public static JSVueOptions create() {
        return new JSVueOptions();
    }

    public JSVueOptions setData(JSObject dataFn) {
        this.set("data", dataFn);
        return this;
    }

    public JSVueOptions setTemplate(String html) {
        this.set("template", JSString.of(html));
        return this;
    }

    public JSVueOptions setTemplate(JSVueTemplate template) {
        this.set("template", template.getJS());
        return this;
    }

    public JSVueOptions setMethods(JSObject methods) {
        this.set("methods", methods);
        return this;
    }

    public JSVueOptions setComputed(JSObject computed) {
        this.set("computed", computed);
        return this;
    }

    public <T> JSVueOptions setComputed(JSVueComputed<T> computed) {
        this.set("computed", computed.toMap());
        return this;
    }

    public JSVueOptions setHooks(JSObject hooks) {
        JSArray keys = JSValue.checkedCoerce(hooks.keys(), JSArray.class);
        keys.forEach(JSFunction.fromJavaConsumer((JSString key) -> this.set(key, hooks.get(key.asString()))));
        return this;
    }

    public JSVueOptions setComponents(JSObject components) {
        this.set("components", components);
        return this;
    }

    public JSVueOptions set(String key, Object value) {
        super.set(key, value);
        return this;
    }

    public JSVueOptions setProvide(JSVueProvide provide) {
        super.set("provide", provide.getProvide());
        return this;
    }

    public JSObject getRaw() {
        return this;
    }
}
