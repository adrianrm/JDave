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

import java.lang.reflect.Method;

import jdave.runner.SpecRunner;
import junit.framework.TestCase;

/**
 * @author Joni Freeman
 */
public class FailingSpecificationTest extends TestCase {
    private SpecRunner runner;
    private Method actualMethod;
    
    @Override
    protected void setUp() throws Exception {
        runner = new SpecRunner();
    }
    
    public void testShouldNotPassExpectation() {
        FailingIntegerSpecification spec = new FailingIntegerSpecification();
        runner.run(spec, new SpecRunner.Results() {
            public void expected(Method method) {
            }
            public void unexpected(Method method) {
                actualMethod = method;
            }
        });
        assertEquals("isNegative", actualMethod.getName());
    }
    
    public class FailingIntegerSpecification extends Specification<Integer> {
        @Context
        public class Zero {
            public Integer context() {
                return new Integer(0);
            }
            
            public void isNegative() {
                specify(should.be < 0);
            }
        }
    }
}
