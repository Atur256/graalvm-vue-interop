package io.github.atur256.vuewebimageinterop.reworkedClasses;

import org.graalvm.webimage.api.JS;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSValue;


@JS.Import("Vue")
public class Vue extends JSObject {

    private Vue() {
    }

    @JS.Coerce
    @JS("return Vue.createApp(component);")
    public static native VueApp createApp(Component component);

    @JS.Coerce
    @JS("return Vue.createApp(component);")
    public static native VueApp createApp(JSObject component);

    @JS.Coerce
    @JS("return Vue.ref(value);")
    public static native JSObject rawRef(JSValue value);

    public static VueRef ref(JSValue initialValue) {
        return VueRef.of(rawRef(initialValue));
    }
}
