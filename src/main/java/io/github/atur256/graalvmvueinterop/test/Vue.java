package io.github.atur256.graalvmvueinterop.test;

import io.github.atur256.graalvmvueinterop.api.VueApp;
import org.graalvm.webimage.api.JS;
import org.graalvm.webimage.api.JSObject;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@JS.Import("Vue")
public class Vue {

    private Vue() {
    }

    @JS.Coerce
    @JS("return Vue.createApp(component);")
    private static native JSObject createAppRaw(ComponentTest component);

    @JS.Coerce
    @JS("return Vue.createApp(component, config);")
    private static native JSObject createAppRaw(ComponentTest component, JSObject config);

    @JS.Coerce
    @JS("return Vue.createApp(component);")
    private static native JSObject createAppRaw(JSObject component);

    public static VueApp createApp(ComponentTest component) {
        return new VueApp(createAppRaw(component));
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface Template {
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface Method {
    }

    @Retention(RetentionPolicy.RUNTIME)
    public @interface Data {}

    @Retention(RetentionPolicy.RUNTIME)
    public @interface Computed {
    }
}
