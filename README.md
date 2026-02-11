# GraalVM Vue Interop

This library demonstrates how to define **Vue.js components in Java using the Vue Options API**, and how to generate executable JavaScript automatically using [GraalVM](https://www.graalvm.org/) with the [GraalVM WebImage Interop Extension](https://github.com/Atur256/graalvm-webimage-interop).

It allows developers to write **Java-first Vue components**, supporting both the **Options API** and parts of the **Composition API** (`setup()`, `ref()`, `reactive()`) for reactive state and computed properties.

This repository forms part of a **bachelor’s thesis** exploring Java–JavaScript interoperability and the integration of Vue’s Options API with GraalVM’s managed runtime environment.

---

## Dependency

> **Important:** This project depends on the [GraalVM WebImage Interop Extension](https://github.com/Atur256/graalvm-webimage-interop), which in turn relies on GraalVM.  
> Ensure the extension is included in your project to enable Java-to-JavaScript interop and generate JavaScript bundles from Java-defined Vue components.

---

## Features

- **Java-defined Vue components:** Create Vue components entirely in Java using the Options API.
- **Composition API support:** Partial support for `setup()`, `ref()`, and `reactive()`.
- **Core APIs:** `Component`, `Vue`, `VueApp` for defining, mounting, and managing applications.
- **Educational purpose:** Developed as part of a bachelor’s thesis exploring Java–JavaScript interop.

---

## Project Structure
```
src/
├─ main/
│ ├─ java/io/github/atur256/vuewebimageinterop/
│ │ ├─ api/                 # Core API classes
│ │ │ ├─ Component.java
│ │ │ ├─ Vue.java
│ │ │ └─ VueApp.java
│ │ ├─ examples/            # Example Vue components written in Java
│ │ └─ Main.java            # Entry point for generating JavaScript bundles
└─ html-demo/               # Demo HTML and CSS files
```
---

## Available APIs

### `Component`

Base class for Java-defined Vue components. Mirrors the **Vue Options API** and supports parts of the **Composition API**.  
Key features:

- Template: `template` field
- Reactive state: `data()` method returning a `JSObject`
- Methods: `methods` field
- Computed properties: `computed`
- Props: `props`
- Child components: `components`
- Dependency injection: `provide` / `inject`
- Watchers: `watch`
- Directives: `directives`
- Lifecycle hooks: `beforeCreate`, `created`, `beforeMount`, `mounted`, `beforeUpdate`, `updated`, `beforeUnmount`, `unmounted`, `activated`, `deactivated`, `errorCaptured`
- Composition API: `setup()` function (optional override)
- Mixins: `mixins`
- Misc: `name`, `inheritAttrs`, `expose`

### `Vue`

Static access to the global Vue runtime via WebImage interop. Supports:

- App creation: `createApp(Component|JSObject)`
- Reactive helpers: `ref()`, `reactive()`, `computed()`, `watch()`, `watchEffect()`
- Lifecycle hooks: `onMounted()`, `onUnmounted()`
- Virtual DOM helpers: `h()`, `nextTick()`
- Component helpers: `defineComponent(JSObject)`

### `VueApp`

Represents a Vue application instance. Supports:

- Mounting/unmounting: `mount()`, `unmount()`, `onUnmount()`
- Global components: `component(String, JSObject)`
- Plugins: `use(JSObject)`
- Reactive state access: `getValue(String)`, `setValue(String, Object)`

---

## Build & GraalVM Requirements

This project depends on **GraalVM** with the [GraalVM WebImage Interop Extension](https://github.com/Atur256/graalvm-webimage-interop).
Ensure GraalVM is installed and properly configured in your environment before attempting a build.

**Download the latest GraalVM snapshot release** from the [official GraalVM Early Access GitHub releases](https://github.com/graalvm/oracle-graalvm-ea-builds/releases).

The [GraalVM Maven Plugin](https://graalvm.github.io/native-build-tools/latest/maven-plugin.html) is used for both compilation and for generating native example artifacts.

### Normal Build

To build the library without examples:
```bash
mvn clean package
```
This performs a standard Maven build and produces the main JAR file in:
```
target/
```

#### Notes
- No JavaScript bundles are generated in this mode.
- Use this build for standard library consumption or publishing to Maven repositories.

### Build With Examples

To include a **Vue example** generate browser-ready JavaScript bundles:
```bash
mvn clean package -Pexamples
```
This will:
- Compiles the project
- Uses the GraalVM Maven plugin to build the **native-image vue example**
- Places the example js file into:
```
target/js/
```
This js file is used in the HTML demo in `html-demo/`.

---

## Maven Releases

Stable releases of this library are published to **Maven Central**.

### Maven Dependency

```xml
<!-- Source: https://mvnrepository.com/artifact/io.github.atur256.graalvm-vue-interop/graalvm-vue-interop -->
<dependency>
    <groupId>io.github.atur256.graalvm-vue-interop</groupId>
    <artifactId>graalvm-vue-interop</artifactId>
    <version>RELEASE_VERSION</version>
    <scope>compile</scope>
</dependency>
```

Replace `RELEASE_VERSION` with the desired version.

### Maven Central

All released versions of [GraalVM Vue Interop](https://mvnrepository.com/artifact/io.github.atur256.graalvm-vue-interop/graalvm-vue-interop) are available on Maven Central.

---

## Limitations

1. **`setup()`** must be overridden only if using Composition API.
2. **Deep watchers** must be anonymous `JSObject` instances.
3. Vue’s **`extends`** option cannot be used due to Java keyword conflicts.
4. **`compilerOptions`** are currently unsupported.
5. **Server-side prefetch** (`serverPrefetch`) is not supported.

---

## Future Development

Potential improvements include:

1. **Reduce boilerplate via reflection**
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

2. **Enhanced Composition API support**
    - Improve handling of the `setup` object and reactive bindings. 
    - Enable easier integration with `ref()`, `reactive()`, and returning values from `setup()` in Java.

3. **Other improvements**
   - Further integration with Vue 3 features while maintaining Java-first definitions.
   - Support for advanced reactivity patterns and cleaner interop with injected values and props.

> **Note:** This project was developed as part of a bachelor thesis and **may not see further updates or active development**. While contributions are welcome, there is **no guarantee of ongoing maintenance or new feature additions**.
----

## License

This project is licensed under the **Apache License, Version 2.0**.

You may obtain a copy of the License at:

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software distributed under this License is distributed on an "AS IS" basis, without warranties or conditions of any kind.

See the [LICENSE](./LICENSE) file for details.
