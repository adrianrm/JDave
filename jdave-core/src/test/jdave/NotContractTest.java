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

import junit.framework.TestCase;

/**
 * @author Joni Freeman
 */
public class NotContractTest extends TestCase {
    private Specification<Void> spec;
    private IContract passingContract;
    private IContract failingContract;

    @Override
    protected void setUp() throws Exception {
        spec = new Specification<Void>() {};
        passingContract = new IContract() {
            public void isSatisfied(Object obj) throws ExpectationFailedException {
            }
        };
        failingContract = new IContract() {
            public void isSatisfied(Object obj) throws ExpectationFailedException {
                throw new ExpectationFailedException("fail");
            }
        };
    }
    
    public void testIsNegationOfPassingContract() {
        try {
            spec.specify(null, spec.should.not().satisfy(passingContract));
            fail();
        } catch (ExpectationFailedException e) {
        }
    }
    
    public void testIsNegationOfFailingContract() {
        spec.specify(null, spec.should.not().satisfy(failingContract));        
    }
}
