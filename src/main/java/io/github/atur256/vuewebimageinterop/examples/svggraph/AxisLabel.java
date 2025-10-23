package io.github.atur256.vuewebimageinterop.examples.svggraph;

import io.github.atur256.vuewebimageinterop.api.Component;
import io.github.atur256.webimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.*;


/**
 * AxisLabel is a Vue subcomponent rendered inside <poly-graph>.
 * It displays a stat label positioned around a circular axis using computed geometry.
 * <p>
 * This class overrides template, props, computed, and data to match Vue's Options API.
 * It receives stat data and layout parameters from its parent and calculates label position.
 */
public class AxisLabel extends Component {

    public AxisLabel() {

        // Vue template: SVG <text> element positioned using computed coordinates
        this.template = JSString.of("""
                <text :x="point.x" :y="point.y">{{stat.label}}</text>
                """);

        // Declare props received from parent (stat object, index, total count)
        this.props = new Props();

        // Define computed property to calculate label position
        this.computed = new Computed();
    }

    /**
     * Overrides Component.data.
     * AxisLabel does not define local reactive state, but must return a JSObject.
     */
    public JSObject data() {
        return new Data();
    }

    /**
     * Empty data model — required by Component base class.
     * AxisLabel relies entirely on props and computed logic.
     */
    private static class Data extends JSObject {

    }

    /**
     * Props defines the input data passed from the parent component.
     * - stat: the stat object containing label and value
     * - index: the stat's position in the array
     * - total: total number of stats (used for angular spacing)
     */
    private static class Props extends JSObject {

        public JSObject stat;
        public JSNumber index;
        public JSNumber total;
    }

    /**
     * Computed defines derived properties based on props.
     * - point: calculates the (x, y) position for the label around the circle
     * <p>
     * Note: must be written entirely in JS due to GraalVM limitations —
     * `this` cannot be accessed from Java lambdas.
     */
    private static class Computed extends JSObject {

        public JSFunction point = JSFunction.fromBody("""
                const value = this.stat.value;
                const index = this.index;
                const total = this.total;
                
                const x = 0;
                const y = -value * 0.8;
                const angle = ((Math.PI * 2) / total) * index;
                const cos = Math.cos(angle);
                const sin = Math.sin(angle);
                const tx = x * cos - y * sin + 100;
                const ty = x * sin + y * cos + 100;
                
                return { x: tx, y: ty };
                """);
    }
}
