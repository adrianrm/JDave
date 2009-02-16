/*
 * Copyright 2007 the original author or authors.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package jdave.unfinalizer.internal;

import static org.mockito.Mockito.when;
import jdave.Specification;
import jdave.junit4.JDaveRunner;

import org.junit.runner.RunWith;
import org.mockito.Mockito;

/**
 * @author Tuomas Karkkainen
 */
@RunWith(JDaveRunner.class)
public class DelegatingClassFileTransformerSpec extends Specification<DelegatingClassFileTransformer> {
    public class WhenLoaderIsNotNull {
        private ClassVisitorDelegator delegator;

        public DelegatingClassFileTransformer create() {
            delegator = Mockito.mock(ClassVisitorDelegator.class);
            return new DelegatingClassFileTransformer(delegator);
        }

        public void unfinalizesClass() throws Exception {
            final byte[] originalBytes = new byte[] { 1, 2, 3 };
            final byte[] transformedBytes = new byte[] { 4, 5, 6 };
            when(delegator.transform(originalBytes)).thenReturn(transformedBytes);
            final byte[] resultBytes = context.transform(ClassLoader.getSystemClassLoader(), "lol", String.class, String.class
                    .getProtectionDomain(), originalBytes);
            specify(transformedBytes, equal(resultBytes));
        }
    }

    public class WhenLoaderIsNull {
        private ClassVisitorDelegator delegator;

        public DelegatingClassFileTransformer create() {
            delegator = mock(ClassVisitorDelegator.class);
            return new DelegatingClassFileTransformer(delegator);
        }

        public void returnsOriginalClass() throws Exception {
            final byte[] originalBytes = new byte[] { 1, 2, 3 };
            final byte[] resultBytes = context.transform(null, "wut", String.class, String.class.getProtectionDomain(), originalBytes);
            specify(originalBytes, equal(resultBytes));
        }
    }
}
