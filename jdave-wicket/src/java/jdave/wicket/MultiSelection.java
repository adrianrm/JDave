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
package jdave.wicket;

import java.util.List;

import org.apache.wicket.Component;
import org.apache.wicket.MarkupContainer;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;

/**
 * @author Joni Freeman
 * @author Timo Rantalaiho
 */
public class MultiSelection<S extends Component> {
    private final Class<S> componentType;
    private final String wicketId;
    private Matcher<?> matcher;

    MultiSelection(Class<S> componentType) {
        this(componentType, null);
    }

    MultiSelection(Class<S> componentType, String wicketId) {
        this.componentType = componentType;
        this.wicketId = wicketId;
        this.matcher = Matchers.anything();
    }

    public List<S> from(MarkupContainer root) {
        Selector selector = new Selector();
        if (wicketId != null) {
            return selector.all(root, componentType, wicketId);
        }
        return selector.all(root, componentType, matcher);
    }

    public MultiSelection<S>  which(Matcher<?> matcher) {
        this.matcher = matcher;
        return this;
    }
}
