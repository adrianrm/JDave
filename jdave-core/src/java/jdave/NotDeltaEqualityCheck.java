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
 * @author Joni Freeman
 */
public class NotDeltaEqualityCheck implements IEqualityCheck {
    private final Number expectedNumber;
    private final double delta;

    public NotDeltaEqualityCheck(Number expectedNumber, double delta) {
        this.expectedNumber = expectedNumber;
        this.delta = delta;
    }

    public boolean matches(Object actual) {
        return (Math.abs(((Number) actual).doubleValue() - expectedNumber.doubleValue()) > delta);
    }

    public String error(Object actual) {
        return "Did not expect: " + expectedNumber + ", but was: " + actual;
    }
}
