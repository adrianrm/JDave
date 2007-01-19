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

import jdave.Specification;
import jdave.runner.SpecRunner;

import org.junit.runner.Description;
import org.junit.runner.Result;
import org.junit.runner.Runner;
import org.junit.runner.notification.Failure;
import org.junit.runner.notification.RunNotifier;

/**
 * The JDaveRunner is a JUnit4 test runner implementation for JDave
 * specifications. Just tag your JDave specification class with the
 * <code>@RunWith(JDaveRunner.class)</code> annotation and you're good to go!
 * 
 * @author lkoskela
 */
public class JDaveRunner extends Runner {
    private Class<? extends Specification<?>> spec;

    public JDaveRunner(Class<? extends Specification<?>> spec) {
        this.spec = spec;
    }

    @Override
    public Description getDescription() {
        return DescriptionFactory.create(spec);
    }

    @Override
    public void run(final RunNotifier notifier) {
        Result result = new Result();
        notifier.addListener(result.createListener());
        notifier.fireTestRunStarted(getDescription());
        try {
            runJDave(notifier);
            notifier.fireTestRunFinished(result);
        } catch (Throwable t) {
            notifier.fireTestFailure(new Failure(getDescription(), t));
        }
    }

    private void runJDave(final RunNotifier notifier) throws Exception {
        SpecRunner runner = new SpecRunner();
        JDaveCallback callback = new JDaveCallback(notifier);
        runner.run(spec, callback);
        callback.runFinished();
    }
}
