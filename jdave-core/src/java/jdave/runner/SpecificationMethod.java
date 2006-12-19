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

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import jdave.ExpectationFailedException;
import jdave.NoContextInitializerSpecifiedException;
import jdave.Specification;
import jdave.runner.SpecRunner.Results;

/**
 * @author Joni Freeman
 * @author Pekka Enberg
 */
public class SpecificationMethod {
    private final Class<? extends Specification<?>> specType;
    private final Class<?> contextType;
    private final Method method;

    public SpecificationMethod(Class<? extends Specification<?>> specType, Class<?> contextType, Method method) {
        this.specType = specType;
        this.contextType = contextType;
        this.method = method;
    }
    
    public String getName() {
        return method.getName();
    }

    public void run(Results results) {
        Specification<?> spec = newSpecification(specType);
        Object context = newContext(spec);
        Object contextObject = initializeContext(context);
        try {
            spec.getClass().getField("be").set(spec, contextObject);
            method.invoke(context);
            results.expected(method);
        } catch (InvocationTargetException e) {
            if (e.getCause().getClass().equals(ExpectationFailedException.class)) {
                results.unexpected(method, (ExpectationFailedException) e.getCause());
            } else {
                results.error(method, e.getCause());
            }
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    private <T extends Specification<?>> T newSpecification(Class<T> specType) {
        try {
            Constructor<T> constructor = specType.getConstructor();
            return constructor.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Object newContext(Specification<?> spec) {
        try {
            Constructor<?> constructor = contextType.getDeclaredConstructor(spec.getClass());
            return constructor.newInstance(spec);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Object initializeContext(Object context) {
        try {
            Method method = context.getClass().getMethod(Context.CONTEXT_INITIALIZER_NAME);
            return method.invoke(context);
        } catch (Exception e) {
            throw new NoContextInitializerSpecifiedException("Initializer missing for " + context.getClass(), e);
        }
    }
}
