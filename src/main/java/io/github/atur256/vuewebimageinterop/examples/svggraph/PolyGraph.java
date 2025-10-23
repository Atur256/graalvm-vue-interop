package io.github.atur256.vuewebimageinterop.examples.svggraph;

import io.github.atur256.vuewebimageinterop.api.Component;
import io.github.atur256.webimageinterop.builtin.JSArray;
import io.github.atur256.webimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;


/**
 * PolyGraph is a Vue subcomponent rendered inside the SVG of MainComponent.
 * It visualizes stat data as a polygon and labels using GraalVM interop.
 * <p>
 * This class overrides template, props, components, computed, and data
 * to match Vue's Options API structure in Java.
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
                    :total="stats.length"
                  >
                  </axis-label>
                </g>
                """);

        // Register child component <axis-label>
        this.components = new Components();

        // Declare props received from parent (MainComponent)
        this.props = new Props();

        // Define computed property for polygon points
        this.computed = new Computed();
    }

    /**
     * Overrides Component.data.
     * PolyGraph doesn't define local reactive state, but must return a JSObject.
     */
    public JSObject data() {
        return new Data();
    }

    /**
     * Empty data model — required by Component base class.
     * PolyGraph relies entirely on props for rendering.
     */
    private static class Data extends JSObject {

    }

    /**
     * Props defines the input data passed from the parent component.
     * Here, `stats` is an array of stat objects used for rendering.
     */
    private static class Props extends JSObject {

        public JSArray stats;
    }

    /**
     * Components registers child components used in the template.
     * This includes <axis-label>, which renders labels around the circle.
     */
    private static class Components extends JSObject {

        public Component axisLabel = new AxisLabel();
    }

    /**
     * Computed defines derived properties based on reactive state or props.
     * `points` calculates the polygon vertices from stat values.
     * <p>
     * Note: must be written entirely in JS due to GraalVM limitations
     * — `this` cannot be accessed from Java lambdas.
     */
    private static class Computed extends JSObject {

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
