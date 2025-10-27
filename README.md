# GraalVM Vue WebImage Integration

This project demonstrates how to define **Vue.js components in Java** and generate JavaScript
using [GraalVM](https://www.graalvm.org/) and
the [WebImage Interop Extension](https://github.com/Atur256/web-image-interop).

GraalVM is used to **translate Java-defined Vue components into JavaScript** via its WebImage runtime (`mx` build tool).
The resulting JS file (`app.js`) can then be loaded in the provided HTML file [`Demo.html`](https://github.com/Atur256/vue-webimage-interop/blob/master/html-demo/Demo.html) for browser execution.

This repository is part of a **bachelor thesis** exploring Java-JavaScript interop and GraalVM-managed runtimes.

---

## Features

- **Java-first Vue components:** Define templates, reactive state, computed properties, and methods in Java.
- **Reactive APIs:** Composition API-style [`VueRef`](https://github.com/Atur256/-vue-webimage-interop/blob/master/src/main/java/io/github/atur256/vuewebimageinterop/api/VueRef.java) and [`VueReactive`](https://github.com/Atur256/-vue-webimage-interop/blob/master/src/main/java/io/github/atur256/vuewebimageinterop/api/VueReactive.java) objects for reactive state.
- **Component hierarchies:** Nested components, props, and dependency injection are supported.
- **Generates browser-ready JS:** Produces `app.js` that can be loaded in any HTML page.
- **Examples included:** Counter, HelloWorld, lifecycle hooks, shopping lists, SVG graphs, and more.

---

## Project Structure

```
src/
├─ main/
│ ├─ java/
│ │ └─ io/github/atur256/vuewebimageinterop/
│ │ ├─ api/ # Core API classes
│ │ │ ├─ Component.java
│ │ │ ├─ Vue.java
│ │ │ ├─ VueApp.java
│ │ │ ├─ VueReactive.java
│ │ │ └─ VueRef.java
│ │ ├─ examples/ # Demo examples
│ │ │ ├─ basics/
│ │ │ │ ├─ counter/
│ │ │ │ ├─ helloworld/
│ │ │ │ └─ composition/
│ │ │ │ ├─ componenthierarchy/
│ │ │ │ └─ globalcomponentplugin/
│ │ │ ├─ advanced/
│ │ │ │ ├─ shoppinglist/
│ │ │ │ └─ svggraph/
│ │ │ └─ reactivity/
│ │ │ ├─ lifecycle/
│ │ │ ├─ rendernexttick/
│ │ │ └─ watcheffects/
│ │ └─ Main.java # Entry point for generating JS
└─ html-demo/ # Demo HTML and CSS files
  ├─ VueDemo.html # Example HTML to run the demo
  └─ styles.css # Styles for the demo
```

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

After running the `mx` build, the resulting JavaScript file will appear in the **directory where you ran the command**.  
The filename is determined by your `MainClassName` and may differ from `app.js`.

The `html-demo` folder contains a pre-made demo HTML file (`Demo.html`) that you can use to test the generated JS.  
Include the generated JS file in `Demo.html` using a `<script>` tag if it’s not already referenced.

---

## Core APIs

### [`Component`](https://github.com/Atur256/-vue-webimage-interop/blob/master/src/main/java/io/github/atur256/vuewebimageinterop/api/Component.java)

Abstract base class for Vue components:

- `template` — Vue template as `JSString`
- `data()` — reactive state
- `methods`, `computed`, `props`, `components`, `inject`, `provide` — Vue options accessible from Java

---

### [`Vue`](https://github.com/Atur256/-vue-webimage-interop/blob/master/src/main/java/io/github/atur256/vuewebimageinterop/api/Vue.java)

Static access to Vue API:

- `createApp(Component|JSObject)` — returns a `VueApp`
- `ref(Object)` / `reactive(JSObject)` — create reactive refs or objects
- `computed(JSFunction)`, `watch`, `watchEffect` — reactive helpers
- Lifecycle hooks: `onMounted`, `onUnmounted`
- Virtual DOM helpers: `h(tag, props, children)`
- `defineComponent(JSObject options)` — define a component

---

### [`VueApp`](https://github.com/Atur256/-vue-webimage-interop/blob/master/src/main/java/io/github/atur256/vuewebimageinterop/api/VueApp.java)

Represents a Vue application instance:

- Stores the component hierarchy
- `mount()` — prepares JS output
- `component(name, JSObject)` — register global components
- `use(JSObject)` — install plugins
- `getValue(String)` / `setValue(String, Object)` — access reactive state

---

### [`VueReactive`](https://github.com/Atur256/-vue-webimage-interop/blob/master/src/main/java/io/github/atur256/vuewebimageinterop/api/VueReactive.java) & [`VueRef`](https://github.com/Atur256/-vue-webimage-interop/blob/master/src/main/java/io/github/atur256/vuewebimageinterop/api/VueRef.java)

Composition API-style reactive objects:

- `VueReactive` — wraps reactive `JSObject`s
- `VueRef` — wraps reactive values
- Typed getters and setters for reactive state

---

## Examples & Testing

The [`examples/`](https://github.com/Atur256/-vue-webimage-interop/blob/master/src/main/java/io/github/atur256/vuewebimageinterop/examples/) folder contains demos showcasing:

- **Basic apps:** counters, HelloWorld, and simple composition examples
- **Component hierarchies:** nested components and global plugins
- **Reactivity & lifecycle:** lifecycle hooks, watch effects, and reactive state updates
- **Advanced apps:** shopping lists and SVG graphs

The [`Main.java`](https://github.com/Atur256/-vue-webimage-interop/blob/master/src/main/java/io/github/atur256/vuewebimageinterop/Main.java) class serves as a **launcher**: it calls example components (e.g., [`HelloWorldExample`](https://github.com/Atur256/-vue-webimage-interop/blob/master/src/main/java/io/github/atur256/vuewebimageinterop/examples/basics/helloworld)) to generate their JavaScript output for the browser.  
This ensures that Vue components are compiled and included in the generated JS.

---

## Future Development

This project is part of a bachelor thesis and may not receive further updates or active development.

While contributions are welcome, there is **no guarantee of ongoing maintenance** or future feature additions.

---

## License

This project’s license is currently undecided.

Please replace this section with the appropriate license when it is chosen.
