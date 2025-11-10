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

        // Computed property to calculate label position
        this.computed = new Computed();
    }

    /**
     * Props received from parent component.
     */
    private static class Props extends JSObject {

        /**
         * Stat object containing label and value
         */
        public JSObject stat;
        /**
         * Position index in array
         */
        public JSNumber index;
        /**
         * Total number of stats (used for angle calculation)
         */
        public JSNumber total;
    }

    /**
     * Computed properties for derived geometry.
     */
    private static class Computed extends JSObject {

        // Note: must be written entirely in JS due to a bug with GraalVM and Vue — `this` does not get passed correctly.
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
