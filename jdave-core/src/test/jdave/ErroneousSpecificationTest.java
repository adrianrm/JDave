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

import static org.junit.Assert.assertEquals;

import java.lang.reflect.Method;

import jdave.runner.SpecRunner;

import org.junit.Test;

/**
 * @author Pekka Enberg
 */
public class ErroneousSpecificationTest {
    private Throwable actualException;
    private Method actualMethod;
    private SpecRunner runner = new SpecRunner();

    @Test
    public void testShouldPassError() throws Exception {
        runner.run(ErroneousSpecification.class, new SpecVisitorAdapter(new ResultsAdapter() {
            @Override
            public void error(Method method, Throwable t) {
                actualMethod = method;
                actualException = t;
            }
        }));
        assertEquals("throwsException", actualMethod.getName());
        assertEquals(UnsupportedOperationException.class, actualException.getClass());
        assertEquals("Throws an exception", actualException.getMessage());
    }
    
    public static class ErroneousSpecification extends Specification<Integer> {
        public class Zero {
            public Integer create() {
                return new Integer(0);
            }
            
            public void throwsException() {
                throw new UnsupportedOperationException("Throws an exception");
            }
        }
    }
}
