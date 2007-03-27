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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;
import java.util.LinkedList;

import jdave.ExpectationFailedException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunListener;
import org.junit.runner.notification.RunNotifier;

/**
 * @author Lasse Koskela
 */
public class ResultsAdapterTest extends RunListener {
    private ResultsAdapter adapter;
    private LinkedList<Failure> failures;
    private Description description;
    private Method method;

    @Before
    public void setUp() throws Exception {
        method = getClass().getMethod("setUp");
        failures = new LinkedList<Failure>();
        description = Description.createSuiteDescription("Foo");
        RunNotifier notifier = new RunNotifier();
        notifier.addListener(this);
        adapter = new ResultsAdapter(notifier, description);
    }

    @Test
    public void errorsAreDelegatedThroughAsTestFailure() {
        Throwable e = new Exception("Test failed");
        adapter.error(method, e);
        runNotifierShouldHaveReceived(e);
    }

    @Test
    public void unexpectedFailuresAreDelegatedThroughAsTestFailure() {
        ExpectationFailedException e = new ExpectationFailedException("Test failed");
        adapter.unexpected(method, e);
        runNotifierShouldHaveReceived(e);
    }

    @Test
    public void expectedFailuresAreIgnored() {
        adapter.expected(method);
        assertTrue(failures.isEmpty());
    }
    
    @Test
    public void reportsNumberOfErrors() {
        assertEquals(0, adapter.getErrorCount());
        adapter.error(method, new Throwable());
        assertEquals(1, adapter.getErrorCount());
    }

    private void runNotifierShouldHaveReceived(Throwable e) {
        assertEquals(1, failures.size());
        Failure failure = failures.getFirst();
        assertSame(e, failure.getException());
        assertEquals(description, failure.getDescription());
    }

    @Override
    public void testFailure(Failure failure) throws Exception {
        failures.add(failure);
    }
}
