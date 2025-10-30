package io.github.atur256.vuewebimageinterop.api;

import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSValue;
import org.graalvm.webimage.api.JS;

public class JSUtils {

    /**
     * Returns the keys of a JSObject as a JS array using Object.keys().
     * Works with GraalVM proxies (Vue component instances).
     */
    @JS.Coerce
    @JS("return Object.keys(obj);")
    public static native JSValue objectKeys(JSObject obj);

    /**
     * Example helper to print keys in Java.
     */
    public static void printKeys(JSObject obj) {
        JSValue keys = objectKeys(obj);
        System.out.println("[JSObject keys] " + keys);
    }
}
