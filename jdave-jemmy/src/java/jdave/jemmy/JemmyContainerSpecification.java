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
package jdave.jemmy;

import java.awt.Container;
import java.io.InputStream;

import javax.swing.JButton;
import javax.swing.JFrame;

import jdave.DefaultLifecycleListener;
import jdave.ExpectationFailedException;
import jdave.Specification;
import jdave.support.Assert;

import org.netbeans.jemmy.JemmyProperties;
import org.netbeans.jemmy.QueueTool;
import org.netbeans.jemmy.operators.JButtonOperator;
import org.netbeans.jemmy.operators.JFrameOperator;

/**
 * @author Pekka Enberg
 * @author Mikko Peltonen
 */
public abstract class JemmyContainerSpecification<T extends Container> extends Specification<T> {
    protected JemmyOperations jemmy = new JemmyOperations();
    protected JFrameOperator frame;
    protected T container;

    public JemmyContainerSpecification() {
        initJemmy();
        addListener(newLifecycleListener());
    }

    protected DefaultLifecycleListener newLifecycleListener() {
        return new DefaultLifecycleListener() {
            @Override
            public void afterContextDestroy(Object contextInstance) {
                assertFrameCreated();
                frame.dispose();
                frame.waitClosed();
            }
        };
    }

    protected void assertFrameCreated() {
        Assert.notNull(frame, "Frame is null. Make sure startContainer() is called in context's create method.");
    }

    protected void initJemmy() {
        InputStream timeouts = getTimeouts();
        try {
            JemmyProperties.getCurrentTimeouts().load(timeouts);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected InputStream getTimeouts() {
        return getClass().getClassLoader().getResourceAsStream("jdave-jemmy.timeouts");
    }

    protected abstract T newContainer();

    protected T startContainer() {
        frame = new JFrameOperator(new JFrame());
        container = newContainer();
        frame.add(container);
        frame.setVisible(true);
        frame.pack();
        jemmy.queue.waitEmpty();
        return container;
    }

    public void specify(IContainerContainment containment) {
        if (!containment.isIn(container)) {
            throw new ExpectationFailedException(containment.error(container));
        }
    }

    public IContainerContainment containsLabel(String expected) {
        return new JLabelContainment(expected);
    }

    protected class JemmyOperations {
        public QueueTool queue = new QueueTool();

        public void pushButton(String text) {
            pushButton(text, 0);
        }

        public void pushButton(String text, int index) {
            JButton button = findButton(text, index);
            if (button == null) {
                throw new NoSuchButtonException(text);
            }
            new JButtonOperator(button).push();
        }
        
        public JButton findButton(String text) {
            return JButtonOperator.findJButton(container, text, true, true, 0);
        }
        
        public JButton findButton(String text, int index) {
            return JButtonOperator.findJButton(container, text, true, true, index);
        }
    }
}
