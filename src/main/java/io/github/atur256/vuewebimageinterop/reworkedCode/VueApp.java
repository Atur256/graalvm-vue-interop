package io.github.atur256.vuewebimageinterop.reworkedCode;

import org.graalvm.webimage.api.JS;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;


public class VueApp extends JSObject {

    private static VueApp mountedAppInstance;

    @JS.Coerce
    @JS("return this.mount('#app')")
    private native VueApp mountJS();

    public VueApp mount() {
        mountedAppInstance = (VueApp) this.mountJS();
        return mountedAppInstance;
    }

    @JS.Coerce
    @JS("this.unmount('#app')")
    private native void unmountJS();

    public void unmount() {
        mountedAppInstance = null;
        unmountJS();
    }

    public static void setValue(String key, String value) {
        mountedAppInstance.set(key, JSString.of(value));
    }
}
