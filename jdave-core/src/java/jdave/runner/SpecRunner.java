/*
 * Copyright 2006 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package jdave.runner;

import static jdave.util.Classes.declaredClassesOf;
import static jdave.util.Methods.isPublic;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import jdave.ExpectationFailedException;
import jdave.Specification;

/**
 * @author Pekka Enberg
 * @author Joni Freeman
 */
public class SpecRunner {
    public interface Results {
        public void expected(Method method);
        public void unexpected(Method method);
    }

    public void run(Class<? extends Specification<?>> specType, Results results) {
        for (Class<?> contextType : declaredClassesOf(specType)) {
            for (Method method : contextType.getMethods()) {
                if (isSpecMethod(method)) {
                    Specification<?> spec = newSpec(specType);
                    Object context = newContext(spec, contextType);
                    spec.be = invokeContext(context);
                    try {
                        method.invoke(context);
                        results.expected(method);
                    } catch (InvocationTargetException e) {
                        if (e.getCause().getClass().equals(ExpectationFailedException.class)) {
                            results.unexpected(method);
                        }
                    } catch (Throwable t) {
                        t.printStackTrace();
                        // FIXME report failure
                    }
                }
            }
        }
    }

    private <T extends Specification<?>> T newSpec(Class<T> specType) {
        try {
            Constructor<T> constructor = specType.getConstructor();
            return constructor.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private <T> T invokeContext(Object context) {
        try {
            Method method = context.getClass().getMethod("context");
            return (T) method.invoke(context);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Object newContext(Specification<?> spec, Class<?> contextType) {
        try {
            Constructor<?> constructor = contextType.getDeclaredConstructor(spec.getClass());
            return constructor.newInstance(spec);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private boolean isSpecMethod(Method method) {
        if (!isPublic(method)) {
            return false;
        }
        if (method.getDeclaringClass().equals(Object.class)) {
            return false;
        }
        if (method.getName().equals("context")) {
            return false;
        }
        return true;
    }
}