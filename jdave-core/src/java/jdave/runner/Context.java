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

import java.lang.reflect.Method;

import jdave.Specification;

/**
 * @author Joni Freeman
 * @author Pekka Enberg
 */
public abstract class Context {
    static final String INITIALIZER_NAME = "create";
    static final String DISPOSER_NAME = "destroy";
    private final Class<? extends Specification<?>> specType;
    private final Class<?> contextType;

    public Context(Class<? extends Specification<?>> specType, Class<?> contextType) {
        this.specType = specType;
        this.contextType = contextType;
    }

    public String getName() {
        return contextType.getSimpleName();
    }
    
    protected abstract SpecificationMethod newSpecificationMethod(Method method, Class<? extends Specification<?>> specType, Class<?> contextType);
    
    void run(ISpecVisitor callback) throws Exception {
        for (Method method : contextType.getMethods()) {
            if (isSpecificationMethod(method)) {
                callback.onSpecMethod(newSpecificationMethod(method, specType, contextType));
            }
        }
    }

    private boolean isSpecificationMethod(Method method) {
        if (method.getDeclaringClass().equals(Object.class)) {
            return false;
        }
        if (method.getName().equals(INITIALIZER_NAME)) {
            return false;
        }
        if (method.getName().equals(DISPOSER_NAME)) {
            return false;
        }
        return true;
    }
}
