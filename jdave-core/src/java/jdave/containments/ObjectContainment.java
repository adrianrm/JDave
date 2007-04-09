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
package jdave.containments;

import java.util.Collection;

import jdave.IContainment;

/**
 * @author Joni Freeman
 */
public class ObjectContainment implements IContainment {
    private final Object object;

    public ObjectContainment(Object object) {
        this.object = object;
    }

    public boolean matches(Collection<?> actual) {
        return actual.contains(object);
    }

    public String error(Collection<?> actual) {
        return "The specified collection " + actual + " does not contain '" + this + "'";
    }
    
    @Override
    public String toString() {
        return object.toString();
    }
}
