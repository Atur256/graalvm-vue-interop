package io.github.atur256.graalvmvueinterop.apiwithreflection;

import io.github.atur256.graalvmwebimageinterop.builtin.JSFunction;
import org.graalvm.webimage.api.JSObject;
import org.graalvm.webimage.api.JSString;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

// TODO: reflection could be replaced by a compiler feature (possible idea)
public abstract class ComponentWithReflection extends JSObject {

    public JSString template = null;

//    public JSObject data() {
//        return JSObject.create();
//    }

    protected JSObject data = JSFunction.of(this::createData);

    public JSObject methods = methods();

    public JSObject computed = computed();

    protected JSString template() {
        return Arrays.stream(this.getClass().getDeclaredFields())
                .filter(field -> field.getAnnotation(VueWithReflection.Template.class) != null)
                .peek(field -> System.out.println("Found Template data: " + field))
                .findFirst()
                .map(field -> {
                    try {
                        return JSString.of((String) field.get(this)); // TODO: exception happens here at .get(this)
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Failed to wrap template: " + field, e);
                    }
                })
                .orElseThrow(() -> new RuntimeException("No field annotated with @Vue.Template found"));
    }

    protected JSObject methods() {
        JSObject ms = JSObject.create();

        Arrays.stream(this.getClass().getDeclaredMethods())
                .filter(method -> method.getAnnotation(VueWithReflection.Method.class) != null)
                .forEach(method -> {
                    System.out.println("Found Vue method: " + method);
                    try {
                        ms.set(JSString.of(method.getName()), createFunction(method));
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to wrap method: " + method, e);
                    }
                });

        return ms;
    }

    protected JSObject createData() {
        JSObject d = JSObject.create();

        Arrays.stream(this.getClass().getDeclaredMethods())
                .filter(field -> field.getAnnotation(VueWithReflection.Data.class) != null)
                .forEach(field -> {
                    System.out.println("Found Vue data: " + field);
                    try {
                        d.set(JSString.of(field.getName()), field.invoke(this));  // TODO: exception happens here at .get(this)
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Failed to access data field: " + field, e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    }
                });

        return d;
    }

    protected JSObject computed() {
        JSObject co = JSObject.create();

        Arrays.stream(this.getClass().getDeclaredMethods())
                .filter(method -> method.getAnnotation(VueWithReflection.Computed.class) != null)
                .forEach(method -> {
                    System.out.println("Found Vue computed: " + method);
                    try {
                        co.set(JSString.of(method.getName()), createFunction(method));
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to wrap computed: " + method, e);
                    }
                });

        return co;
    }


    private JSFunction createFunction(Method method) {
        JSFunction f;

        int paramCount = method.getParameterCount() - 1; // -1 to account for the data object
        boolean hasReturn = !method.getReturnType().equals(void.class);

        if(paramCount == 0 && hasReturn) {
            f = JSFunction.withThis((Object data) -> {
                try {
                    return method.invoke(this, data);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        else if(paramCount == 0) {
            f = JSFunction.withThis((Object data) -> {
                try {
                    method.invoke(this, data);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        else if(paramCount == 1 && hasReturn) {
            f = JSFunction.withThis((Object data, Object arg) -> {
                try {
                    return method.invoke(this, data, arg);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        else if(paramCount == 1) {
            f = JSFunction.withThis((Object data, Object arg) -> {
                try {
                    method.invoke(this, data, arg);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        else if(paramCount == 2 && hasReturn) {
            f = JSFunction.withThis((Object data, Object arg1, Object arg2) -> {
                try {
                    return method.invoke(this, data, arg1, arg2);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        else if(paramCount == 2) {
            f = JSFunction.withThis((Object data, Object arg1, Object arg2) -> {
                try {
                    method.invoke(this, data, arg1, arg2);
                } catch (IllegalAccessException | InvocationTargetException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        else {
            throw new RuntimeException("Unsupported method signature: " + method);
        }
        return f;
    }
}
