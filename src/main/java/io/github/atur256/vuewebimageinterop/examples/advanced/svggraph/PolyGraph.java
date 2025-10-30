package io.github.atur256.vuewebimageinterop.examples.advanced.svggraph;

import io.github.atur256.vuewebimageinterop.api.Component;
import io.github.atur256.webimageinterop.builtin.JSArray;
import io.github.atur256.webimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.*;

import java.util.function.BiConsumer;


/**
 * PolyGraph is a child Vue component used to render the SVG visualization.
 * <p>
 * Demonstrates:
 * <ul>
 *   <li>Computed geometry for polygon points</li>
 *   <li>Rendering SVG elements: polygon, circle, and axis labels</li>
 *   <li>Prop passing from parent component</li>
 *   <li>Child component registration</li>
 * </ul>
 */
public class PolyGraph extends Component {

    public PolyGraph() {
        // Vue template: SVG group with polygon, circle, and axis labels
        this.template = JSString.of("""
                <g>
                    <polygon :points="points"></polygon>
                    <circle cx="100" cy="100" r="80"></circle>
                    <axis-label
                      v-for="(stat, index) in stats"
                      :stat="stat"
                      :index="index"
                      :total="stats.length">
                    </axis-label>
                </g>
                """);

        // Props received from parent component
        this.props = new Props();

        // Register child component <axis-label>
        this.components = new Components();

        // Computed property to calculate polygon points
        this.computed = new Computed();
    }

    /**
     * Props received from parent component.
     */
    private static class Props extends JSObject {

        /**
         * Array of stat objects containing label and value
         */
        public JSArray stats;
    }

    /**
     * Child components used in this template.
     */
    private static class Components extends JSObject {

        public Component axisLabel = new AxisLabel();
    }

    /**
     * Computed properties for derived polygon geometry.
     */
    private static class Computed extends JSObject {

        // Note: must be written entirely in JS due to GraalVM limitations — `this` cannot be accessed from Java lambdas. And computed functions are called without arguments (this) in vue.
        public JSFunction points = JSFunction.fromBody("""
                return this.stats
                    .map((stat, i) => {
                        const x = 0;
                        const y = -stat.value * 0.8;
                        const angle = ((Math.PI * 2) / this.stats.length) * i;
                        const cos = Math.cos(angle);
                        const sin = Math.sin(angle);
                        const tx = x * cos - y * sin + 100;
                        const ty = x * sin + y * cos + 100;
                        return `${tx},${ty}`;
                    })
                    .join(' ');
                """);
    }
}
