package io.github.atur256.vuewebimageinterop;

import io.github.atur256.vuewebimageinterop.simpleDemo.DemoComponent;
import org.graalvm.webimage.api.JS;
import org.graalvm.webimage.api.JSObject;


public class Main {

    public static void main(String[] args) {

        System.out.println(new VueApp());

        VueDemo.main(null);

        test(new DemoComponent());
    }


    @JS(value = "console.log(x.data());")
    public static native void test(JSObject x);
}
