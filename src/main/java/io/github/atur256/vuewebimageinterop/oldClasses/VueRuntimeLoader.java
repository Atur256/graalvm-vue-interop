package io.github.atur256.vuewebimageinterop.oldClasses;

import org.graalvm.webimage.api.JS;


// Used to include Vue runtime via Java if not loaded from HTML
@JS.Code.Include("/vue.global.prod.js")
public class VueRuntimeLoader {

    public static void injectVueFile() {
    }
}
