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

import java.lang.reflect.Method;

import jdave.Specification;

/**
 * @author Pekka Enberg
 * @author Joni Freeman
 */
public class SpecRunner {
    public interface Results {
        public void expected(Method method);
    }

    public void run(Specification<?> spec, Results results) {
        for (Class<?> contextType : declaredClassesOf(spec.getClass())) {
            for (Method method : contextType.getMethods()) {
                if (isSpecMethod(method)) {
                    results.expected(method);
                }
            }
        }
    }

    private boolean isSpecMethod(Method method) {
        if (!isPublic(method)) {
            return false;
        }
        if (method.getDeclaringClass().equals(Object.class)) {
            return false;
        }
        return true;
    }
}