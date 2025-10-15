package io.github.atur256.vuewebimageinterop.reworkedClasses;

import io.github.atur256.webimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;


public abstract class Component extends JSObject {

    public JSObject methods = null;
    public JSString template = null;
    public JSObject props = null;
    public JSObject computed = null;
    public JSObject components = null;
    public JSObject provide = null;
    protected JSObject data = JSFunction.fromSupplier(this::data);

    public JSObject data() {
        return JSObject.create();
    }
}
