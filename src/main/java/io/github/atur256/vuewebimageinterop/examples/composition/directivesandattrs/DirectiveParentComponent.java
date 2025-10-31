package io.github.atur256.vuewebimageinterop.examples.composition.directivesandattrs;

import io.github.atur256.vuewebimageinterop.api.Component;
import org.graalvm.webimage.api.*;


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
