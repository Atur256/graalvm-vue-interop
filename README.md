# GraalVM Vue WebImage Integration

This project demonstrates how to define **Vue.js components in Java using the Vue Options API**, and how to generate JavaScript code automatically using [GraalVM](https://www.graalvm.org/) together with the [WebImage Interop Extension](https://github.com/Atur256/web-image-interop).

GraalVM is used to **translate Java-defined Vue components into executable JavaScript** via its WebImage runtime (`mx` build tool).  
The generated JavaScript bundle (`app.js`) can then be loaded into the included HTML demo file — [**`demo.html`**](https://github.com/Atur256/vue-webimage-interop/blob/master/html-demo/demo.html) — for direct execution in the browser.

This repository forms part of a **bachelor’s thesis** exploring Java–JavaScript interoperability and the integration of Vue’s Options API with GraalVM’s managed runtime environment.

Some parts of the **Composition API** (`setup()`, `ref()`, `reactive()`, etc.) are also included, allowing developers to define reactive state and methods in a style similar to Vue 3’s Composition API.

---

## Features

- **Java-defined Vue components:** Create Vue components entirely in Java using the **Options API** (template, data, computed properties, methods, lifecycle hooks, and more).
- **Core interop classes:** Includes [`Component`](https://github.com/Atur256/-vue-webimage-interop/blob/master/src/main/java/io/github/atur256/vuewebimageinterop/api/Component.java), [`Vue`](https://github.com/Atur256/-vue-webimage-interop/blob/master/src/main/java/io/github/atur256/vuewebimageinterop/api/Vue.java), and [`VueApp`](https://github.com/Atur256/-vue-webimage-interop/blob/master/src/main/java/io/github/atur256/vuewebimageinterop/api/VueApp.java) — providing the foundation for defining, registering, and mounting Vue components.
- **Near-complete Options API support:** The [`Component`](https://github.com/Atur256/-vue-webimage-interop/blob/master/src/main/java/io/github/atur256/vuewebimageinterop/api/Component.java) class implements nearly all standard Vue Options API fields.
- **Educational purpose:** Built as part of a bachelor’s thesis exploring **Java–JavaScript interoperability** through GraalVM and Vue’s Options API.

---

## Project Structure

```
src/
├─ main/
│ ├─ java/
│ │ └─ io/github/atur256/vuewebimageinterop/
│ │ ├─ api/ # Core API classes
│ │ │ ├─ Component.java             # Base class implementing most of the Vue Options API
│ │ │ ├─ Vue.java                   # Provides access to Vue runtime functionality
│ │ │ └─ VueApp.java                # Manages Vue application creation and mounting
│ │ ├─ examples/                    # Example Vue components written in Java
│ │ │ ├─ basics/                    # Basic examples
│ │ │ │ ├─ counter/
│ │ │ │ └─ helloworld/
│ │ │ ├─ composition/               # Composition examples
│ │ │ │ ├─ componenthierarchy/
│ │ │ │ ├─ directivesandattrs/
│ │ │ │ ├─ exposechild/
│ │ │ │ ├─ globalcomponentplugin/
│ │ │ │ └─ mixins/
│ │ │ ├─ advanced/                  # Advanced examples
│ │ │ │ ├─ shoppinglist/
│ │ │ │ │ ├─ viaoptionsapi/
│ │ │ │ │ └─ viasetup/
│ │ │ │ └─ svggraph/
│ │ │ │ └─ setup/
│ │ │ └─ reactivity/                # Reactivity examples
│ │ │ │ ├─ lifecycle/
│ │ │ │ ├─ rendernexttick/
│ │ │ │ └─ watcheffects/
│ │ └─ Main.java                    # Entry point for generating the JavaScript bundle (app.js)
└─ html-demo/                       # Demo HTML and CSS files
├─ Demo.html                        # Example HTML page for running the generated Vue app
└─ styles.css                       # Basic styles for the demoo
  └─ styles.css                     # Styles for the demo
```

---

## Core APIs

### [`Component`](https://github.com/Atur256/-vue-webimage-interop/blob/master/src/main/java/io/github/atur256/vuewebimageinterop/api/Component.java)

Abstract base class for Vue components defined in Java via GraalVM WebImage interop.  
This class mirrors the **Vue Options API** and also supports parts of the **Composition API** (`setup`).

#### Key Fields and Methods

- `template` — Vue template as a `JSString`
- `data()` — reactive state, returned as a `JSObject`
- `methods` — Vue methods accessible from Java
- `computed` — computed properties derived from reactive state
- `props` — props accepted from parent components
- `components` — child components used in this component
- `inject` / `provide` — dependency injection
- `watch` — watchers for reactive properties
- `directives` — local custom directives
- `emits` — declared custom events
- `setup` — Composition API setup function, returns `JSValue.undefined()` by default; can be overridden in subclasses to expose reactive state or methods
- Lifecycle hooks:
    - `beforeCreate`, `created`
    - `beforeMount`, `mounted`
    - `beforeUpdate`, `updated`
    - `beforeUnmount`, `unmounted`
    - `errorCaptured`
    - `activated`, `deactivated`
- `mixins` — merge additional component options
- `name` — optional component name for debugging/devtools
- `inheritAttrs` — controls automatic inheritance of non-prop attributes
- `expose` — controls which properties are exposed via template refs

#### Notes

- The `setup` function is **optional**; if not overridden, `data()` and other Options API fields are used as usual.
- Subclasses typically override `template`, `data()`, and optionally `setup` to provide reactive state, methods, and computed properties.
- Used together with `Vue` and `VueApp` to define and mount Java-based Vue components.


---

### [`Vue`](https://github.com/Atur256/-vue-webimage-interop/blob/master/src/main/java/io/github/atur256/vuewebimageinterop/api/Vue.java)

Static access to the global Vue API via GraalVM WebImage interop.  
Provides core Vue functions and reactive helpers for Java-defined Vue components.

#### Key Methods

- **App creation**
    - `createApp(Component|JSObject)` — creates a `VueApp` from a Java component or raw JS object
    - `createApp(Component, JSObject config)` — app with additional options (e.g., `compilerOptions`)
- **Reactivity**
    - `ref(Object)` — reactive reference
    - `reactive(JSObject)` — reactive object
    - `computed(JSFunction)` — computed property
    - `watch(JSObject, JSFunction)` — watch a reactive source
    - `watchEffect(JSFunction)` — reactive effect
- **Lifecycle hooks**
    - `onMounted(JSFunction)` — run after component is mounted
    - `onUnmounted(JSFunction)` — run after component is unmounted
- **Virtual DOM helpers**
    - `h(tag)` / `h(tag, props)` / `h(tag, props, children)` — create VNodes
    - `nextTick(JSFunction)` — defer execution to the next DOM update
- **Component helpers**
    - `defineComponent(JSObject options)` — define a reusable component

#### Notes

- Used with `Component` and `VueApp` to define and mount Vue applications entirely from Java.
- Reactive helpers (`ref`, `reactive`, `computed`, `watch`) integrate with both the Options API (`data()`, `methods`) and Composition API (`setup()`).
- Virtual DOM helpers (`h`) allow defining render functions in Java without templates.

---

### [`VueApp`](https://github.com/Atur256/-vue-webimage-interop/blob/master/src/main/java/io/github/atur256/vuewebimageinterop/api/VueApp.java)

Represents a Vue application instance created from a Java-defined component.  
Provides methods for mounting, managing global components, plugins, and accessing reactive state.

#### Key Methods

- **App lifecycle**
    - `mount()` — mounts the app to the DOM element with id `#app`, returns the root component instance
    - `unmount()` — unmounts the app
    - `onUnmountJS(JSFunction)` — register a callback to run when the app is unmounted
- **Global components**
    - `component(String name, JSObject definition)` — register a global component
- **Plugins**
    - `use(JSObject plugin)` — install a plugin into the Vue application
- **Reactive state access**
    - `getValue(String key)` / `setValue(String key, Object value)` — read and write reactive state from Java

#### Notes

- Used together with `Vue.createApp(Component)` to define and mount Java-based Vue applications.
- Supports chaining for `component()` and `use()` calls.
- Integrates seamlessly with `Component` instances and `Vue` reactive helpers.

---

## Getting Started

### Prerequisites

- **Java 22+** (GraalVM distribution)
- [**GraalVM `mx` build tool**](https://github.com/graalvm/mx) for WebImage compilation
- Modern browser (to run compiled JS)

---

## Build & Generate JS

### 1. Clone the repository

Clone the repository and move into the project folder.

```bash
git clone https://github.com/Atur256/vue-webimage-interop.git
cd vue-webimage-interop
```

### 2. Build the Java project

Compile all Java classes before generating the JavaScript output.

```bash
mvn clean package
```

### 3. Run the GraalVM WebImage build

Use GraalVM's `mx` tool to generate the JavaScript file.  
This command must be run from the GraalVM `web-image` directory.

```bash
mx web-image -Ob -H:-ClosureCompiler \
  -cp /path/to/compiled/vue-webimage-interop.jar \
  fully.qualified.MainClassName
```

> **Notes:**
> - Replace `/path/to/compiled/vue-webimage-interop.jar` with the actual path to your **built JAR file**.
> - Replace `fully.qualified.MainClassName` with your **entry-point class** (e.g.,
    `io.github.atur256.vuewebimageinterop.Main`).
> - Ensure you run this command from the **GraalVM `web-image` directory** where the `mx` tool is available.

### 4. Use the Generated JS in Browser

After running the `mx` build, the resulting JavaScript file will appear in the **directory where you ran the command
**.  
The filename is determined by your `MainClassName` and may differ from `app.js`.

The `html-demo` folder contains a pre-made demo HTML file (`Demo.html`) that you can use to test the generated JS.  
Include the generated JS file in `Demo.html` using a `<script>` tag if it’s not already referenced.

---

## Examples & Testing

The [`examples/`](https://github.com/Atur256/-vue-webimage-interop/blob/master/src/main/java/io/github/atur256/vuewebimageinterop/examples/) folder contains demos showcasing:

- **Basic apps:** counters, HelloWorld, and simple Composition API examples
- **Composition API demos:** component hierarchy, mixins, exposing child components, custom directives/attributes, and global plugins
- **Component hierarchies:** nested components and plugin usage
- **Reactivity & lifecycle:** lifecycle hooks, watch effects, `nextTick` updates, and reactive state handling
- **Advanced apps:** shopping lists (via Options API or via `setup`) and SVG graphs

The [`Main.java`](https://github.com/Atur256/-vue-webimage-interop/blob/master/src/main/java/io/github/atur256/vuewebimageinterop/Main.java) class serves as a **launcher**: it executes example components (e.g., [`HelloWorldExample`](https://github.com/Atur256/-vue-webimage-interop/blob/master/src/main/java/io/github/atur256/vuewebimageinterop/examples/basics/helloworld)) to generate their JavaScript output for the browser.  
This ensures that all Vue components, whether defined using the Options API or partially via the Composition API, are compiled and included in the generated JS bundle.

---

## Limitations

This project has several important limitations to be aware of:

1. **JavaScript `this` usage in Vue functions**
    - Java code **cannot access the JavaScript `this`** in Vue functions because GraalVM currently **does not support `this` in Java lambdas**.
    - All functions that rely on JavaScript `this` (e.g., computed properties, methods accessing props or injected values) **must be fully written in JavaScript**.
    - JavaScript code can be embedded using:
        - `JSFunction.fromArgs(...)`
        - `JSFunction.fromBody(...)`
        - `JSEval.eval(...)`
    - Using the Composition API (`setup()`) can partially mitigate this limitation, as reactive bindings returned from `setup()` can be used without relying on JavaScript `this`.

2. **Composition API (`setup`)**
    - `setup()` is **declared in `Component` by default** and returns `JSValue.undefined()`.
    - If a component **does not use the Composition API**, `setup()` **must not be overridden**, as doing so will **break the `data()` function** and reactive state initialization.
    - If a component **uses `setup()`**, it must be **overridden** to provide the reactive bindings.

3. **Deep watchers**
    - Must be defined as **anonymous `JSObject`** instead of named Java classes.
    - Named Java classes can cause Vue's deep reactivity system to recursively traverse the proxy, leading to stack overflows or "too much recursion" errors.

4. **`extends`**
    - Vue’s `extends` option **clashes with Java’s `extends` keyword**.
    - As a result, extending components via Vue’s `extends` is currently skipped.
5. **Compiler options**
    - `compilerOptions` currently do not work and are **not yet supported in this integration**.

6. **Server-side prefetch**
    - `serverPrefetch` is skipped, as SSR is not supported in this project.

---

## Future Development

Potential improvements include:

1. **Enable JavaScript `this` access in Java**
    - Likely requires changes in GraalVM or additional interop handling.

2. **Reduce boilerplate via reflection**
   - Components could be defined using annotated fields and methods that automatically map to Vue options. This annotated class could then be automatically converted into the existing `Component` structure with `data()` and `methods` classes, reducing boilerplate. 
   - Example of annotated definition:
   ```java
    public class Counter extends Component {
    
        @Vue.data
        public int count = 0;
    
        @Vue.template
        public String template = """
            <div class="app">
                <h1>{{ title }}</h1>
                <p>Count: {{ count }}</p>
                <button @click="increment">Increment</button>
            </div>
        """;
    
        @Vue.method
        public void increment(JSObject data) {
            int current = JSValue.checkedCoerce(data.get("count"), Integer.class);
            data.set("count", JSNumber.of(current + 1));
        }
    }
    ```
   - Equivalent generated form in the current Component structure:
   ```java
    public class Counter extends Component {
   
        public Counter() {
            this.template = JSString.of("""
                    div class="app">
                        <h1>{{ title }}</h1>
                        <p>Count: {{ count }}</p>
                        <button @click="increment">Increment</button>
                    </div>
                    """);
    
            this.methods = new Methods();
        }
    
        @Override
        public JSObject data() {
            return new Data();
        }
    
        private static class Data extends JSObject {
            public int count = 0;
        }
    
        public static class Methods extends JSObject {
            public JSFunction increment = JSFunction.fromThisJSCons((JSObject data) -> {
                int current = JSValue.checkedCoerce(data.get("count"), Integer.class);
                data.set("count", JSNumber.of(current + 1));
            });
        }
    }
    ```

3. **Enhanced Composition API support**
    - Improve handling of the `setup` object and reactive bindings. 
    - Enable easier integration with `ref()`, `reactive()`, and returning values from `setup()` in Java.

4. **Other improvements**
   - Further integration with Vue 3 features while maintaining Java-first definitions.
   - Support for advanced reactivity patterns and cleaner interop with injected values and props.

> **Note:** This project was developed as part of a bachelor thesis and **may not see further updates or active development**. While contributions are welcome, there is **no guarantee of ongoing maintenance or new feature additions**.
----

## License

This project’s license is currently undecided.

This section will be replaced with appropriate license when it is chosen.
