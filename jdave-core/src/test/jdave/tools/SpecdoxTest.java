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
package jdave.tools;

import jdave.Specification;

import org.jmock.Expectations;
import org.jmock.integration.junit3.MockObjectTestCase;

/**
 * @author Joni Freeman
 */
public class SpecdoxTest extends MockObjectTestCase {
    private IDoxStore doxStore = mock(IDoxStore.class);
    private Specdox dox;
    
    @Override
    protected void setUp() throws Exception {
        dox = new Specdox(doxStore);
    }
    
    public void testSavesPlainTextVersionToFileStore() {
        final String expectedOutput = 
            "Full stack\n" +
            "  - complains on push\n";
        checking(new Expectations() {{ 
            one(doxStore).store("StackSpec", expectedOutput);
        }});
        dox.generate(StackSpec.class, new PlainTextFormat());
    }

    public static class StackSpec extends Specification<Void> {
        public class FullStack {
            public void create() {                
            }
            
            public void complainsOnPush() {                
            }
        }
    }
}
