package io.github.atur256.vuewebimageinterop.oldClasses;

import io.github.atur256.vuewebimageinterop.reworkedClasses.VueApp;
import io.github.atur256.vuewebimageinterop.demos.simpledemo.DemoComponent;
import io.github.atur256.webimageinterop.builtin.JSArray;
import io.github.atur256.webimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.*;


public class VueDemo {

    // Vue's "this" context cannot be accessed directly from Java — all logic relying on "this" must be coded as raw JS code.
    // This applies to computed properties, methods, and lifecycle hooks that reference component state.

    // Retain original ref objects in Java — GraalVM unwraps .value when accessed, so we store the full ref for mutation and binding.
    private static final JSVueRef<String> textRef = JSVueRef.of("A quick brown fox jumps over the lazy dog!");
    private static final JSVueRef<Integer> countRef = JSVueRef.of(10);
    private static final JSVueRef<String> ageInputRef = JSVueRef.of("35");
    private static final JSVueRef<String> themeRef = JSVueRef.of("light");

    public static void main(String[] args) {

        // Create nested object: profiles
        JSObject profile1 = JSObject.create();
        profile1.set("age", JSNumber.of(35));
        profile1.set("location", JSString.of("Austria"));

        JSObject profile2 = JSObject.create();
        profile2.set("age", JSNumber.of(27));
        profile2.set("location", JSString.of("Germany"));

        // Create nested array: roles
        JSArray roles1 = JSArray.of("admin", "editor");
        JSArray roles2 = JSArray.of("editor", "tester", "student");

        // Create reactive user objects
        JSObject user1 = JSObject.create();
        user1.set("name", JSString.of("Bob"));
        user1.set("loggedIn", JSBoolean.of(true));
        user1.set("profile", profile1);
        user1.set("roles", roles1);

        JSObject user2 = JSObject.create();
        user2.set("name", JSString.of("Alice"));
        user2.set("loggedIn", JSBoolean.of(true));
        user2.set("profile", profile2);
        user2.set("roles", roles2);

        JSObject reactiveUser = JSVueRef.reactive(user1);
        JSVueRef<JSObject> userRef = JSVueRef.of(reactiveUser);

        // Build reactive data object — this will be wrapped into a Vue-compatible data function.
        JSVueData data = JSVueData.builder()
                .set("message", "World!")
                .set("count", countRef)
                .set("buttonLabel", "Reset Count")
                .set("text", "The five boxing wizards jump quickly!")
                .set("textRef", textRef.raw())
                .set("ageInput", ageInputRef.raw())
                .set("user", userRef.raw())
                .set("showRole", true)
                .set("showPanel", true)
                .set("isRed", true)
                .set("color", "green")
                .set("editText", "Edit me")
                .set("checked", true)
                .set("groceryList", createGroceryList())
                .set("newItemText", "")
                .set("nextId", 3)
                .build();

        // Wrap Java-side data into a Vue data function — ensures Vue reactivity and proper "this" binding.
        JSObject dataFn = JSVueData.wrapAsDataFunction(() -> data);

        // Define Vue methods — must be coded as JS functions if they rely on "this".
        JSObject methods = setupMethods(userRef, user1, user2);

        // Panel component with named slots
        JSVueTemplate panelTemplate = createPanelTemplate();

        // Register reusable components
        JSObject components = JSObject.create();
        components.set("TodoItem", createToDoList());
        components.set("Panel", JSVueComponent.create().setTemplate(panelTemplate));
        components.set("TimedPanel", createTimedPanel());

        // Define template — must reference reactive properties by name (e.g., {{ name }}, {{ count }})
        String html = createHtmlTemplate();

        // Provide reactive theme value via Vue's provide/inject system
        JSVueProvide provide = JSVueProvide.create().set("theme", themeRef.raw());

        // Define computed property — doubleCount = count * 2
        JSVueComputed<Integer> doubleCount = JSVueComputed.of("doubleCount", JSFunction.fromBody("return this.count * 2;"));

//         Compose Vue options — include data, methods, template, computed, hooks, etc.
//        JSObject component = JSObject.create();
////                .setData(dataFn) // TODO: convert to fields
////                .setMethods(methods)
////                .setTemplate(html)
////                .setComputed(doubleCount)
////                .setComponents(components)
////                .setProvide(provide);
//
//        component.set("data", dataFn);
//        component.set("methods", methods);
//        component.set("template",JSString.of(html));

        DemoComponent component = new DemoComponent();

        // Create Vue app instance
        VueApp app = Vue.createApp(component);

        // Mount app to DOM and retain reference
        Vue.mountAndStore(app);
    }

    private static String createHtmlTemplate() {
        return """
                <div>
                    <panel>
                        <template v-slot:header>
                          <h2>Named Header</h2>
                        </template>
                
                        <template v-slot:default>
                            <div>
                                <p>This is the main content injected into the default slot.</p>
                                <h1>Hello {{ message }}</h1>
                
                                <hr />
                
                                <p>Count: {{ count }}</p>
                                <p>Double Count via Computed: {{ doubleCount }}</p>
                                <div style="display: flex; gap: 0.5em; flex-wrap: wrap; margin-top: 1em;">
                                    <button @click="increment()">Increment Count</button>
                                    <button @click="reset()">{{ buttonLabel }}</button>
                                </div>
                
                                <hr />
                
                                <p>Text: {{ text }}</p>
                                <p>Text via ref: {{ textRef }}</p>
                                <div style="display: flex; gap: 0.5em; flex-wrap: wrap; margin-top: 1em;">
                                    <button @click="appendLetter()">Append first letter of text to its end</button>
                                    <button @click="mirrorText()">Mirror Text via ref</button>
                                </div>
                
                                <hr />
                
                                <p>User: {{ user.name }}</p>
                                <p v-if="user.loggedIn">Welcome back! {{user.name}}</p>
                                <p>Age: {{ user.profile.age }}</p>
                                <p>Location: {{ user.profile.location }}</p>
                                <ul v-if="showRole && user.loggedIn">
                                  <li v-for="role in user.roles">{{ role }}</li>
                                </ul>
                                <input
                                  v-model="ageInput"
                                  type="text"
                                  placeholder="Enter new age"
                                  @keyup.enter="updateAge"
                                />
                                <div style="display: flex; gap: 0.5em; flex-wrap: wrap; margin-top: 1em;">
                                    <button @click="switchUser()">Switch user</button>
                                    <button @click="toggleLogin()">
                                      {{ user.loggedIn ? "Log out" : "Log in" }}
                                    </button>
                                    <button @click="toggleRole()">
                                      {{ showRole ? "Hide Role Info" : "Show Role Info" }}
                                    </button>
                                </div>
                
                                <hr />
                
                                <button @click="showPanel = !showPanel"> {{ showPanel ? "Hide Panel" : "Show Panel" }}</button>
                                <timed-panel v-if="showPanel"></timed-panel>
                
                                <hr />
                
                                <p :class="{ red: isRed }" @click="toggleRed()">Toggle Red</p>
                                <p :style="{ color }" @click="toggleColor()">Toggle Color</p>
                
                                <hr />
                
                                <input v-model="editText">
                                <p>{{ editText }}</p>
                
                                <hr />
                
                                <input type="checkbox" v-model="checked">
                                <label>Checked: {{ checked }}</label>
                
                                <hr />
                
                                <h2>Grocery List</h2>
                                <input v-model="newItemText">
                                <button @click="addItem()">Add Item</button>
                                <todo-item
                                  v-for="(item, index) in groceryList"
                                  :todo="item"
                                  :index="index"
                                  :key="item.id"
                                  @remove="removeItem"
                                >
                                  <template v-slot>
                                    <div style="font-size:smaller;">Extra slot content here</div>
                                  </template>
                                </todo-item>
                
                                <hr />
                
                                <button @click="toggleTheme">Change Theme</button>
                
                                <hr />
                
                            </div>
                        </template>
                    </panel>
                </div>
                """;
    }

    private static JSObject setupMethods(JSVueRef<JSObject> userRef, JSObject user1, JSObject user2) {
        JSObject methods = JSObject.create();

        methods.set("increment", JSFunction.fromRunnable(() -> countRef.set(countRef.get() + 1)));

        methods.set("reset", JSFunction.fromRunnable(() -> Vue.setValue("count", 0)));

        methods.set("appendLetter", JSFunction.fromRunnable(() -> {
            String original = Vue.getValue("text", String.class);
            char firstChar = original.charAt(0);
            String updated = original.substring(1) + firstChar;
            Vue.setValue("text", updated);
        }));

        methods.set("mirrorText", JSFunction.fromRunnable(() -> {
            String original = textRef.get();
            String mirrored = new StringBuilder(original).reverse().toString();
            textRef.set(mirrored);
        }));

        methods.set("switchUser", JSFunction.fromRunnable(() -> {
            JSObject user = userRef.get();
            String userName = JSValue.checkedCoerce(user.get("name"), String.class);
            userRef.set(userName.equals("Bob") ? user2 : user1);
            JSObject newUser = userRef.get();
            JSObject profile = JSValue.checkedCoerce(newUser.get("profile"), JSObject.class);
            ageInputRef.set(JSValue.checkedCoerce(profile.get("age"), Integer.class));
        }));

        methods.set("toggleRole", JSFunction.fromRunnable(() -> {
            boolean current = Vue.getValue("showRole", Boolean.class);
            Vue.setValue("showRole", !current);
        }));

        methods.set("toggleLogin", JSFunction.fromRunnable(() -> {
            JSObject user = userRef.get();
            boolean isLoggedIn = JSValue.checkedCoerce(user.get("loggedIn"), Boolean.class);
            user.set("loggedIn", JSBoolean.of(!isLoggedIn));
        }));

        methods.set("updateAge", JSFunction.fromRunnable(() -> {
            String input = ageInputRef.get();
            try {
                int newAge = Integer.parseInt(input.trim());
                JSObject user = userRef.get();
                JSObject profileObj = JSValue.checkedCoerce(user.get("profile"), JSObject.class);
                profileObj.set("age", JSNumber.of(newAge));
            } catch (NumberFormatException e) {
                System.out.println("Invalid age input: " + input);
            }
        }));

        methods.set("toggleRed", JSFunction.fromRunnable(() -> Vue.setValue("isRed", !Vue.getValue("isRed", Boolean.class))));

        methods.set("toggleColor", JSFunction.fromRunnable(() -> {
            String current = Vue.getValue("color", String.class);
            Vue.setValue("color", current.equals("green") ? "blue" : "green");
        }));

        methods.set("addItem", JSFunction.fromRunnable(() -> {
            JSObject vue = Vue.getMountedInstance();
            if(vue == null) return;

            String text = Vue.getValue("newItemText", String.class);
            if(text == null || text.trim().isEmpty()) return;

            int nextId = Vue.getValue("nextId", Integer.class);
            JSObject newItem = JSObject.create();
            newItem.set("id", JSNumber.of(nextId));
            newItem.set("text", JSString.of(text));

            JSArray list = Vue.getValue("groceryList", JSArray.class);
            list.push(newItem);

            Vue.setValue("groceryList", list);
            Vue.setValue("newItemText", "");
            Vue.setValue("nextId", nextId + 1);
        }));

        methods.set("removeItem", JSFunction.fromConsumer(idVal -> {
            int id = idVal.asInt();
            JSArray list = Vue.getValue("groceryList", JSArray.class);

            JSArray filtered = JSArray.of();
            for(int i = 0; i < list.length; i++) {
                JSObject item = JSValue.checkedCoerce(list.get(i), JSObject.class);
                if(JSValue.checkedCoerce(item.get("id"), Integer.class) != id) {
                    filtered.push(item);
                }
            }
            Vue.setValue("groceryList", filtered);
        }));

        methods.set("toggleTheme", JSFunction.fromRunnable(() -> themeRef.set(themeRef.get().equals("dark") ? "light" : "dark")));

        return methods;
    }

    // Creates a slot-based panel component with header, body, and footer slots
    private static JSVueTemplate createPanelTemplate() {
        return JSVueTemplate.of("""
                    <div class='panel'>
                      <header><slot name='header'>Default Header</slot></header>
                      <main><slot>Default Body</slot></main>
                      <footer><slot name='footer'>Default Footer</slot></footer>
                    </div>
                """);
    }

    // Creates a timed panel component that tracks elapsed time and allows reset
    private static JSVueComponent createTimedPanel() {
        JSObject timedPanelMethods = JSObject.create();

        // Resets the timer and starts the update loop
        timedPanelMethods.set("resetTimer",
                JSFunction.fromBody("""
                        this.elapsed = 0;
                        this.lastTime = performance.now();
                        this.update();
                        """));

        // Updates the elapsed time and schedules the next frame
        timedPanelMethods.set("update",
                JSFunction.fromBody("""
                        this.elapsed = performance.now() - this.lastTime;
                        if (this.elapsed >= this.duration) {
                            cancelAnimationFrame(this.handle);
                        } else {
                            this.handle = requestAnimationFrame(this.update);
                        }
                        """));

        // Lifecycle hook: starts the timer when mounted
        JSVueLifecycle panelLifecycle = JSVueLifecycle.create()
                .onMounted(
                        JSFunction.fromBody("""
                                console.log("Time panel mounted")
                                this.resetTimer();
                                """))
                .onUnmounted(
                        JSFunction.fromBody("""
                                console.log("Time panel unmounted")
                                cancelAnimationFrame(this.handle);
                                """));

        // Template for the timed panel UI
        JSVueTemplate timedPanelTemplate = JSVueTemplate.of("""
                  <div>
                    <label>Elapsed Time: <progress :value="progressRate"></progress></label>
                    <div>{{ (elapsed / 1000).toFixed(1) }}s</div>
                    <div>
                      Duration: <input type="range" v-model="duration" min="1" max="30000">
                      {{ (duration / 1000).toFixed(1) }}s
                    </div>
                    <button @click="resetTimer()">Reset Timer</button>
                  </div>
                """);

        // Initial reactive data for the timed panel
        JSVueData timedPanelData = JSVueData.builder()
                .set("duration", 15000)
                .set("elapsed", 0.0)
                .build();

        // Computed property for progress bar value
        JSObject computed = JSObject.create();
        computed.set("progressRate", JSFunction.fromBody("return Math.min(this.elapsed / this.duration, 1);"));

        return JSVueComponent.create()
                .setTemplate(timedPanelTemplate)
                .setData(JSVueData.wrapAsDataFunction(() -> timedPanelData))
                .setMethods(timedPanelMethods)
                .setComputed(computed)
                .setHooks(panelLifecycle.getHooks());
    }

    // Creates a todo list item component that displays theme-aware styling
    private static JSVueComponent createToDoList() {
        JSVueTemplate todoItemTemplate = JSVueTemplate.of("""
                                  <li :style="{ color: theme === 'dark' ? '#ccc' : '#333' }">
                                    {{ index + 1 }}. {{ todo.text }}
                                    <span style="cursor:pointer; margin-left:10px;" @click="$emit('remove', todo.id)">&#128465;&#65039;</span>
                                    <div style="font-size:smaller;">Theme: {{ theme }}</div>
                                  </li>
                """);

        // Injects the theme value from the parent using Vue's Composition API
        JSObject todoItemSetup = JSFunction.fromBody("return { theme: Vue.inject('theme') };");

        return JSVueComponent.create()
                .setProps(JSArray.of("todo", "index"))
                .setTemplate(todoItemTemplate)
                .set("setup", todoItemSetup);
    }

    // Creates a sample grocery list with predefined items
    private static JSArray createGroceryList() {
        JSObject item0 = JSObject.create();
        item0.set("id", JSNumber.of(0));
        item0.set("text", JSString.of("Vegetables"));

        JSObject item1 = JSObject.create();
        item1.set("id", JSNumber.of(1));
        item1.set("text", JSString.of("Cheese"));

        JSObject item2 = JSObject.create();
        item2.set("id", JSNumber.of(2));
        item2.set("text", JSString.of("Whatever else humans are supposed to eat"));

        return JSArray.of(item0, item1, item2);
    }
}

