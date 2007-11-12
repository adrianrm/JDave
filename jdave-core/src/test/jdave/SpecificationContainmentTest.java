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
package jdave;

import java.util.Arrays;

import org.junit.Test;

/**
 * @author Joni Freeman
 */
public class SpecificationContainmentTest {
    private Specification<Object> spec = new Specification<Object>() {};

    @Test
    public void testContainmentsForVariousLeftHandSides() {
        spec.specify(Arrays.asList(1, 2, 3), spec.containsAll(Arrays.asList(1, 2)));
        spec.specify(Arrays.asList(1, 2, 3).iterator(), spec.containsAll(Arrays.asList(1, 2)));
        spec.specify((Iterable<?>) Arrays.asList(1, 2, 3), spec.containsAll(Arrays.asList(1, 2)));
        spec.specify(new Integer[] { 1, 2, 3 }, spec.containsAll(Arrays.asList(1, 2)));
    }
    
    @Test
    public void testAllContainmentsForVariousRightHandSides() {
        spec.specify(Arrays.asList(1, 2, 3), spec.containsAll(Arrays.asList(1, 2)));
        spec.specify(Arrays.asList(1, 2, 3), spec.containsAll(Arrays.asList(1, 2).iterator()));
        spec.specify(Arrays.asList(1, 2, 3), spec.containsAll(((Iterable<?>) Arrays.asList(1, 2))));
        spec.specify(Arrays.asList(1, 2, 3), spec.containsAll(1, 2));
    }
    
    @Test
    public void testExactContainmentsForVariousRightHandSides() {
        spec.specify(Arrays.asList(1, 2, 3), spec.containsExactly(Arrays.asList(1, 2, 3)));
        spec.specify(Arrays.asList(1, 2, 3), spec.containsExactly(Arrays.asList(1, 2, 3).iterator()));
        spec.specify(Arrays.asList(1, 2, 3), spec.containsExactly(((Iterable<?>) Arrays.asList(1, 2, 3))));
        spec.specify(Arrays.asList(1, 2, 3), spec.containsExactly(1, 2, 3));
    }
    
    @Test
    public void testAnyContainmentsForVariousRightHandSides() {
        spec.specify(Arrays.asList(1, 2, 3), spec.containsAny(Arrays.asList(1, 2)));
        spec.specify(Arrays.asList(1, 2, 3), spec.containsAny(Arrays.asList(1, 2).iterator()));
        spec.specify(Arrays.asList(1, 2, 3), spec.containsAny(((Iterable<?>) Arrays.asList(1, 2))));
        spec.specify(Arrays.asList(1, 2, 3), spec.containsAny(1, 2));
    }
    
    @Test
    public void testInOrderContainments() {
        spec.specify(Arrays.asList(1, 2), spec.containsInOrder(Arrays.asList(1, 2)));
        spec.specify(Arrays.asList(1, 2), spec.containsInOrder(Arrays.asList(1, 2).iterator()));
        spec.specify(Arrays.asList(1, 2), spec.containsInOrder(((Iterable<?>) Arrays.asList(1, 2))));
        spec.specify(Arrays.asList(1, 2), spec.containsInOrder(1, 2));
    }
    
    @Test
    public void testInPartialOrderContainments() {
        spec.specify(Arrays.asList(1, 2), spec.containsInPartialOrder(Arrays.asList(1, 2)));
        spec.specify(Arrays.asList(1, 2), spec.containsInPartialOrder(Arrays.asList(1, 2).iterator()));
        spec.specify(Arrays.asList(1, 2), spec.containsInPartialOrder(((Iterable<?>) Arrays.asList(1, 2))));
        spec.specify(Arrays.asList(1, 2), spec.containsInPartialOrder(1, 2));
    }
}
