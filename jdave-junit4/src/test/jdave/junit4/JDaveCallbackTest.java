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
package jdave.junit4;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import jdave.Specification;
import jdave.runner.Behavior;
import jdave.runner.Context;
import jdave.runner.ExecutingBehavior;
import jdave.runner.IBehaviorResults;
import junit.framework.TestCase;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;
import org.junit.runner.notification.StoppedByUserException;

/**
 * @author Lasse Koskela
 */
public class JDaveCallbackTest extends TestCase {
    private List<String> events = new ArrayList<String>();
    private JDaveCallback callback;
    private boolean specVerifyCalled;
    
    @Override
    @Before
    public void setUp() throws Exception {
        RunNotifier notifier = new RunNotifier() {
            @Override
            public void fireTestRunStarted(Description d) {
                events.add("fireTestRunStarted(" + d.getDisplayName() + ")");
            }

            @Override
            public void fireTestStarted(Description d) throws StoppedByUserException {
                events.add("fireTestStarted(" + d.getDisplayName() + ")");
            }

            @Override
            public void fireTestFailure(Failure d) {
            }

            @Override
            public void fireTestFinished(Description d) {
                events.add("fireTestFinished(" + d.getDisplayName() + ")");
            }

            @Override
            public void fireTestRunFinished(Result result) {
                events.add("fireTestRunFinished(" + result.getFailureCount() + ")");
            }
        };
        callback = new JDaveCallback(notifier);
        specVerifyCalled = false;
    }

    @Test
    public void testEventOrder() throws Exception {
        Context context = new Context(StackSpec.class, StackSpec.EmptyStack.class) {
            @Override
            protected Behavior newBehavior(Method method, Class<? extends Specification<?>> specType, Class<?> contextType) {
                throw new UnsupportedOperationException();
            }            
        };
        callback.onContext(context);
        Behavior method = createSpecMethodByName(getClass(), "testEventOrder");
        callback.onBehavior(method);
    }
        
    private Behavior createSpecMethodByName(final Class<?> target, String name)
            throws NoSuchMethodException {
        Behavior method = new ExecutingBehavior(target.getDeclaredMethod(name), null, Object.class) {
            @Override
            protected void destroyContext() {
            }

            @Override
            protected Object newContext(Specification<?> spec) throws Exception {
                try {
                    return target.newInstance();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            protected Specification<?> newSpecification() {
                return new StackSpec();
            }
        };
        return method;
    }
    
    private class StubBehavior extends Behavior {
        public StubBehavior() throws SecurityException, NoSuchMethodException {
            super(JDaveCallbackTest.class, JDaveCallbackTest.class.getDeclaredMethod("setUp"));
        }
        
        @Override
        public String getName() {
            return "";
        }
        
        @Override
        public void run(IBehaviorResults results) {
        }
    }
}
