package io.github.atur256.vuewebimageinterop.oldClasses;

import io.github.atur256.webimageinterop.builtin.JSArray;
import io.github.atur256.webimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;
import org.graalvm.webimage.api.JSValue;


public class JSVueComponent extends JSObject {

    private JSVueComponent() {
        super();
    }

    public static JSVueComponent create() {
        return new JSVueComponent();
    }

    public JSVueComponent setProps(JSArray props) {
        this.set("props", props);
        return this;
    }

    public JSVueComponent setTemplate(JSVueTemplate template) {
        this.set("template", template.getJS());
        return this;
    }

    public JSVueComponent setComputed(JSObject computed) {
        this.set("computed", computed);
        return this;
    }

    public JSVueComponent setTemplate(String template) {
        this.set("template", JSString.of(template));
        return this;
    }

    public JSVueComponent setSlots(JSObject slots) {
        this.set("slots", slots);
        return this;
    }

    public JSVueComponent set(String key, JSValue value) {
        super.set(key, value);
        return this;
    }

    public JSVueComponent setData(JSObject dataFn) {
        this.set("data", dataFn);
        return this;
    }

    public JSVueComponent setComponents(JSObject components) {
        this.set("components", components);
        return this;
    }

    public JSVueComponent setHooks(JSObject hooks) {
        JSArray keys = JSValue.checkedCoerce(hooks.keys(), JSArray.class);
        keys.forEach(JSFunction.fromJavaConsumer((JSString key) -> this.set(key, hooks.get(key.asString()))));
        return this;
    }

    public JSVueComponent setMethods(JSObject methods) {
        this.set("methods", methods);
        return this;
    }
}
