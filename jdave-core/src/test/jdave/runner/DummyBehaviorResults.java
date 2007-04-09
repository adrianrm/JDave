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
package jdave.runner;

import java.lang.reflect.Method;

import jdave.ExpectationFailedException;

/**
 * @author Joni Freeman
 */
public class DummyBehaviorResults implements IBehaviorResults {
    public void error(Method method, Throwable t) {
    }

    public void expected(Method method) {
    }

    public void unexpected(Method method, ExpectationFailedException e) {
    }
}
