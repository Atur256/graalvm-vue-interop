package io.github.atur256.vuewebimageinterop;

import org.graalvm.webimage.api.JSString;


public record JSVueTemplate(JSString html) {

    public String get() {
        return html.asString();
    }

    public JSString getJS() {
        return html;
    }

    public static JSVueTemplate of(String rawHtml) {
        return new JSVueTemplate(JSString.of(rawHtml));
    }
}
