package io.github.atur256.vuewebimageinterop.reworkedCode;

import org.graalvm.webimage.api.JS;
import org.graalvm.webimage.api.JSObject;


public class VueApp extends JSObject {

    @JS.Coerce
    @JS("return this.mount('#app')")
    public native JSObject mount();

    @JS.Coerce
    @JS("return this.unmount('#app')")
    public native JSObject unmount();

}
