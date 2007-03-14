/*
 * Copyright 2007 the original author or authors.
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
package jdave;

/**
 * @author Pekka Enberg
 */
public class InverseExpectedException<T> implements IExpectedException<T> {
    private final IExpectedException<T> expectation;

    public InverseExpectedException(IExpectedException<T> expectation) {
        this.expectation = expectation;
    }

    public boolean matches(Throwable t) {
        return !expectation.matches(t);
    }

    public String error(Throwable t) {
        throw new ExpectationFailedException("The specified block threw " + t.getClass().getName(), t);
    }

    public boolean propagateException() {
        return true;
    }

    public String nothrow() {
        throw new UnsupportedOperationException();
    }
}