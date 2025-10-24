package io.github.atur256.vuewebimageinterop.examples.advanced.svggraph;

import io.github.atur256.vuewebimageinterop.api.Component;
import io.github.atur256.webimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.*;


/**
 * AxisLabel is a child Vue component used to render individual labels around the SVG circle.
 * <p>
 * Demonstrates:
 * <ul>
 *   <li>Computed geometry for label positioning</li>
 *   <li>Prop passing from parent component</li>
 *   <li>SVG text rendering via {@code <text>}</li>
 * </ul>
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
     * Props defines the input data passed from the parent component.
     * - stat: object containing label and value
     * - index: position of the stat in the array
     * - total: total number of stats (used for angle calculation)
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
