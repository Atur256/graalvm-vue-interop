package io.github.atur256.vuewebimageinterop.examples.advanced.setup;

import io.github.atur256.vuewebimageinterop.api.Component;
import io.github.atur256.vuewebimageinterop.api.Vue;
import org.graalvm.webimage.api.*;
import io.github.atur256.webimageinterop.builtin.JSFunction;


/**
 * SetupComponent demonstrates use of Vue 3's Composition API via {@code setup()} in GraalVM interop.
 * <p>
 * Demonstrates:
 * <ul>
 *   <li>Reactive state using {@code Vue.ref()}</li>
 *   <li>Method binding and event handling inside {@code setup()}</li>
 *   <li>Reactive side effects using {@code watch()} and {@code watchEffect()}</li>
 * </ul>
 */
public class SetupComponent extends Component {

    public SetupComponent() {
        // Component name used in Vue DevTools
        this.name = JSString.of("SetupComponent");

        // Vue template: displays and updates reactive count
        this.template = JSString.of("""
                    <div>
                        <p>Count: {{ count }}</p>
                        <button @click="increment">Increment</button>
                    </div>
                """);
    }

    /**
     * Composition API setup function.
     * <p>
     * Returns a {@link JSObject} containing reactive state and methods
     * that are exposed to the template and component context.
     */

    public JSObject setup() {
        return new Setup();
    }

    /**
     * Reactive bindings exposed by the {@code setup()} function.
     * <p>
     * Includes:
     * <ul>
     *   <li>{@code count} — a reactive number initialized to 0</li>
     *   <li>{@code increment()} — a method that increases {@code count.value} by 1</li>
     *   <li>{@code watchEffect} — a side effect that runs whenever any reactive dependency changes</li>
     *   <li>{@code watch} — a watcher that tracks changes to {@code count} specifically</li>
     * </ul>
     */
    public static class Setup extends JSObject {

        // Reactive state: count initialized to 0
        public JSObject count = Vue.ref(0);

        // Method to increment count
        public JSFunction increment = JSFunction.fromRun(() ->
                count.set("value", JSValue.checkedCoerce(count.get("value"), Integer.class) + 1));

        // Reactive effect: runs whenever any reactive dependency used inside changes
        public JSObject watchEffect = Vue.watchEffect(JSFunction.fromRun(() ->
                System.out.println("[WatchEffect] count changed to: " + JSValue.checkedCoerce(count.get("value"), Integer.class))));

        // Watcher: runs only when 'count' changes
        public JSObject watch = Vue.watch(count, JSFunction.fromRun(() ->
                System.out.println("[watch] count changed to: " + JSValue.checkedCoerce(count.get("value"), Integer.class))));
    }
}
