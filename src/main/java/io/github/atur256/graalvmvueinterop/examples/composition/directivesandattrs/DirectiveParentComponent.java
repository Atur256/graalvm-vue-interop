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

package io.github.atur256.graalvmvueinterop.examples.composition.directivesandattrs;

import io.github.atur256.graalvmvueinterop.api.Component;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;


/**
 * DirectiveParentComponent demonstrates usage of DirectiveComponent with externally passed attributes.
 * <p>
 * Demonstrates:
 * <ul>
 *   <li>Component registration via {@code components}</li>
 *   <li>Passing non-prop attributes to child components (e.g. {@code id}, {@code class})</li>
 *   <li>Effect of attribute inheritance on child component styling</li>
 * </ul>
 */
public class DirectiveParentComponent extends Component {

    public DirectiveParentComponent() {
        // Vue template: passes id and class to child component
        this.template = JSString.of("""
                    <div>
                        <directive-component id="myInput" class="highlighted" />
                    </div>
                """);

        // Register child components
        this.components = new Components();
    }

    /**
     * Registers child components used in the parent template.
     */
    public static class Components extends JSObject {

        public Component directiveComponent = new DirectiveComponent();
    }
}
