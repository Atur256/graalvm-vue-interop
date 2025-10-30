package io.github.atur256.vuewebimageinterop.examples.advanced.svggraph;

import io.github.atur256.vuewebimageinterop.api.Component;
import io.github.atur256.vuewebimageinterop.api.VueApp;
import io.github.atur256.webimageinterop.builtin.JSArray;
import io.github.atur256.webimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.JSNumber;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;
import org.graalvm.webimage.api.JSValue;


/**
 * RootComponent is the root Vue component for this GraalVM-based SVG graph example.
 * <p>
 * Demonstrates:
 * <ul>
 *   <li>Reactive state via {@code data()}</li>
 *   <li>Dynamic SVG rendering via <poly-graph> child component</li>
 *   <li>Interactive stat controls using {@code v-model} and {@code @click}</li>
 * </ul>
 */
public class RootComponent extends Component {

    public RootComponent() {
        // Vue template: SVG graph plus interactive controls
        this.template = JSString.of("""
                    <div id="app">
                        <svg width="200" height="200">
                            <poly-graph :stats="stats"></poly-graph>
                        </svg>
                        <div v-for="stat in stats">
                            <label>{{stat.label}}</label>
                            <input type="range" v-model="stat.value" min="0" max="100">
                            <span>{{stat.value}}</span>
                            <button @click="remove(stat)" class="remove">X</button>
                        </div>
                        <form id="add">
                            <input name="newlabel" v-model="newLabel">
                            <button @click="add">Add a Stat</button>
                        </form>
                        <pre id="raw">{{ stats }}</pre>
                    </div>
                """);

        // Bind Vue methods
        this.methods = new Methods();

        // Register child components
        this.components = new Components();
    }

    /**
     * Provides reactive state for this component:
     * <ul>
     *   <li>{@code newLabel} – input field for new stats</li>
     *   <li>{@code stats} – array of stat objects (label + value)</li>
     * </ul>
     */
    public JSObject data() {
        return new Data();
    }

    /**
     * Reactive state model for the SVG graph component.
     */
    private static class Data extends JSObject {

        public JSString newLabel = JSString.of("");
        public JSArray stats = initialiseStatArray();
    }

    /**
     * Vue method bindings for adding/removing stats.
     */
    private static class Methods extends JSObject {

        public JSFunction add = JSFunction.fromJSConsWithThis((JSObject data, JSObject e) -> {
            JSFunction.fromArgs("obj", "obj.preventDefault();").call(e);

            String newLabel = JSValue.checkedCoerce(data.get("newLabel"), String.class);
            if(newLabel.isEmpty()) return;

            JSArray stats = JSValue.checkedCoerce(data.get("stats"), JSArray.class);
            stats.push(createItem(newLabel));
            data.set("newLabel", "Test");
        });

        public JSFunction remove = JSFunction.fromJSConsWithThis((JSObject data, JSObject stat) -> {
            JSArray stats = JSValue.checkedCoerce(data.get("stats"), JSArray.class);
            if(stats.length > 3) stats.splice(stats.indexOf(stat), 1);
            else System.err.println("Can't delete more!");
        });
    }

    /**
     * Registers child components used in the template, including <poly-graph>.
     */
    private static class Components extends JSObject {

        public Component polyGraph = new PolyGraph();
    }

    private static JSArray initialiseStatArray() {
        return JSArray.of(
                createItem("A"),
                createItem("B"),
                createItem("C"),
                createItem("D"),
                createItem("E"),
                createItem("F")
        );
    }

    private static JSObject createItem(String label) {
        JSObject item = JSObject.create();
        item.set("label", JSString.of(label));
        item.set("value", JSNumber.of(100));
        return item;
    }
}
