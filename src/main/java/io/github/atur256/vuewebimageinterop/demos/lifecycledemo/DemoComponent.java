package io.github.atur256.vuewebimageinterop.demos.lifecycledemo;

import io.github.atur256.vuewebimageinterop.reworkedClasses.Component;
import io.github.atur256.vuewebimageinterop.reworkedClasses.Vue;
import io.github.atur256.vuewebimageinterop.reworkedClasses.VueApp;
import io.github.atur256.webimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;


public class DemoComponent extends Component {

    public VueApp app;

    public DemoComponent() {
        this.template = JSString.of("""
                    <div class="app">
                        <h2>Lifecycle Demo</h2>
                        <p>Open console to see mount/unmount messages.</p>
                        <button @click="unmountApp">Unmount App</button>
                    </div>
                """);

        this.methods = new Methods(this);
    }

    public void setApp(VueApp app) {
        this.app = app;
    }

    public JSObject data() {
        Vue.onMounted(JSFunction.fromRunnable(() -> {
            System.out.println("Component mounted!");
        }));

        Vue.onUnmounted(JSFunction.fromRunnable(() -> {
            System.out.println("Component unmounted!");
        }));

        return new Data();
    }

    private static class Data extends JSObject {

    }

    private static class Methods extends JSObject {

        public JSFunction unmountApp;

        public Methods(DemoComponent outer) {

            unmountApp = JSFunction.fromRunnable(() -> {
                System.out.println("Unmount button clicked!");
                if(outer.app != null) {
                    outer.app.unmount();
                }
            });
        }
    }
}
