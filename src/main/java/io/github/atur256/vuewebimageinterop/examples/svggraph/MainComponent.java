package io.github.atur256.vuewebimageinterop.examples.svggraph;

import io.github.atur256.vuewebimageinterop.api.Component;
import io.github.atur256.vuewebimageinterop.api.VueApp;
import io.github.atur256.webimageinterop.builtin.JSArray;
import io.github.atur256.webimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.JS;
import org.graalvm.webimage.api.JSNumber;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;


/**
 * MainComponent is the root Vue component for this GraalVM-based example.
 * <p>
 * It overrides key fields from the abstract Component class: template, data, methods, and components.
 * The template replicates Vue's SVG stats example, rendered via Java interop.
 */
public class MainComponent extends Component {

    public MainComponent() {

        // Vue template: SVG graph + stat sliders + add/remove controls
        this.template = JSString.of("""
                <div id="app">
                  <svg width="200" height="200">
                    <poly-graph :stats="stats"></poly-graph>
                  </svg>
                
                  <!-- controls -->
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

        // Vue method bindings: add/remove stat items
        this.methods = new Methods();

        // Register child component <poly-graph> for SVG rendering
        this.components = new Components();
    }

    /**
     * Overrides Component.data() to expose reactive state:
     * - newLabel: bound to input field
     * - stats: array of stat objects (label + value)
     */
    public JSObject data() {
        return new Data();
    }

    /**
     * Data defines the reactive state model for this component.
     * It is returned by the overridden data() method.
     */
    private static class Data extends JSObject {

        public JSString newLabel = JSString.of("");
        public JSArray stats = initialiseStatArray();
    }

    /**
     * Methods defines Vue event handlers.
     * These are bound to template actions via @click and v-model.
     */
    private static class Methods extends JSObject {

        // Adds a new stat item with default value
        public JSFunction add = JSFunction.fromJavaConsumer((JSObject e) -> {
            preventDefault(e);
            String newLabel = VueApp.getValue("newLabel", String.class);
            if(newLabel.isEmpty()) {
                return;
            }
            JSArray stats = VueApp.getValue("stats", JSArray.class);
            stats.push(createItem(newLabel));
            VueApp.setValue("newLabel", "");
        });

        // Removes a stat item if more than 3 remain
        public JSFunction remove = JSFunction.fromJavaConsumer((JSObject stat) -> {
            JSArray stats = VueApp.getValue("stats", JSArray.class);
            if(stats.length > 3) {
                stats.splice(stats.indexOf(stat), 1);
            }
            else {
                System.err.println("Can't delete more!");
            }
        });
    }

    /**
     * Components registers child components used in the template.
     * This includes <poly-graph>, which renders the SVG visualization.
     */
    private static class Components extends JSObject {

        public Component polyGraph = new PolyGraph();
    }

    // Creates the initial array of stat items
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

    // Creates a stat object with label and numeric value
    private static JSObject createItem(String label) {
        JSObject item = JSObject.create();
        item.set("label", JSString.of(label));
        item.set("value", JSNumber.of(100));

        return item;
    }

    // Prevents form submission from reloading the page
    @JS.Coerce
    @JS(value = "obj.preventDefault();")
    private static native void preventDefault(JSObject obj);

}
