/*
 * Copyright (c) 2025 Arthur Schwaiger
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.github.atur256.graalvmvueinterop.examples.advanced.svggraph;

import io.github.atur256.graalvmvueinterop.api.Component;
import io.github.atur256.graalvmwebimageinterop.builtin.JSArray;
import io.github.atur256.graalvmwebimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.JSNumber;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;
import org.graalvm.webimage.api.JSValue;


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

        public JSFunction points = JSFunction.withThis((JSObject self) -> {
            JSArray stats = self.as(Props.class).stats;
            int length = stats.length;
            String joined = stats.map(JSFunction.of((JSObject stat, JSNumber i) -> {
                var coords = compute(stat, i.asInt(), length);
                return coords.x + "," + coords.y;
            })).join(" ");
            return JSString.of(joined);
        });
    }

    public static class Coords extends JSObject {
        public double x;
        public double y;
    }

    public static Coords compute(JSObject stat, int index, int total) {
        JSValue val = stat.get("value", JSValue.class);

        double value;
        if (val instanceof JSNumber) {
            value = val.asDouble();
        } else {
            value = Double.parseDouble(val.asString());
        }

        double x = 0;
        double y = -value * 0.8;
        double angle = ((Math.PI * 2) / total) * index;
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        Coords coords = new Coords();
        coords.x = x * cos - y * sin + 100;
        coords.y = x * sin + y * cos + 100;
        return coords;
    }
}
